package cn.gomro.mid.core.search.lucene;

import cn.gomro.mid.core.biz.goods.service.impl.GoodsSpecService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.ResourcesUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.lionsoul.jcseg.analyzer.v5x.JcsegAnalyzer5X;
import org.lionsoul.jcseg.tokenizer.core.ADictionary;
import org.lionsoul.jcseg.tokenizer.core.DictionaryFactory;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by momo on 2016/8/5.
 */
@Singleton
public class GoodsSearch {
    final Logger logger = LoggerFactory.getLogger(GoodsSpecService.class);

    public static String DIRECTORY;

    private DirectoryReader directoryReader;
    private IndexReader reader;
    private IndexWriter writer;
    private IndexSearcher searcher;
    private Analyzer analyzer;

    public GoodsSearch() {
    }

    @PostConstruct
    public void initialize() {

        try {
            DIRECTORY = ResourcesUtils.loadGoodsIndexDir();
            getWriter();
            writer.commit();
            getSearcher();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void cleanup() {
        closeReader();
        closeWriter();
    }

    @Lock(LockType.WRITE)
    public synchronized void add(SearchGoodsVO goods) {
        try {
            getWriter();
            Document doc = goodsToDoc(goods);
            writer.addDocument(doc);
            writer.commit();
            getReader(true);//重新打开FSDirectory
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Lock(LockType.WRITE)
    public synchronized void addList(Set<SearchGoodsVO> goodsList) {
        try {
            getWriter();
            for (SearchGoodsVO goods : goodsList) {
                Document doc = goodsToDoc(goods);
                writer.addDocument(doc);
            }
            writer.commit();
            getReader(true);//重新打开FSDirectory
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Lock(LockType.WRITE)
    public synchronized void commit() {
        try {
            getWriter();
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void edit(Term term, Document document) {
        try {
            getWriter();
            writer.updateDocument(term, document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void edit(String field, String value, Document document) {
        edit(new Term(field, value), document);
    }

    public void delete(String field, String value) {
        delete(new Term(field, value));
    }

    public void delete(Query query) {
        try {
            getWriter();
            writer.deleteDocuments(query);
            getReader(true);//重新打开FSDirectory
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(Query[] queries) {
        try {
            getWriter();
            writer.deleteDocuments(queries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term) {
        try {
            getWriter();
            int numDocs = writer.numDocs();
            writer.deleteDocuments(term);
            writer.commit();
            int numDocs1 = writer.numDocs();
            writer.forceMergeDeletes();
            writer.close();
            logger.info("Method: delete(), 删除索引完成：{} >> {}", numDocs, numDocs1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term[] terms) {
        try {
            getWriter();
            writer.deleteDocuments(terms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int deleteAll() {
        int numDocs = 0;//清空的索引数量
        try {
            if (writer != null && writer.isOpen()) {
                writer.deleteAll();
            } else {
                getWriter();
                numDocs = writer.numDocs();
                writer.deleteAll();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numDocs;
    }

    public List<Query> goodsQuerys(String value) {
        ArrayList<Query> queries = new ArrayList<>();
        try {
            //先按品牌型号搜
            String[] fields = {SearchGoodsVO.FIELD_BRAND, SearchGoodsVO.FIELD_MODEL};
            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            String[] split = value.split(" ");
            for (int i = 0; i < split.length; i++) {
                //因为商城商品多型号的特殊性，需要通配符匹配其中一个
                WildcardQuery wildcardQuery = new WildcardQuery(new Term(SearchGoodsVO.FIELD_MODEL, "*" + split[i] + "*"));
                builder.add(wildcardQuery, BooleanClause.Occur.SHOULD);

                WildcardQuery wildcardQuery2 = new WildcardQuery(new Term(SearchGoodsVO.FIELD_BRAND, "*" + split[i] + "*"));
                builder.add(wildcardQuery2, BooleanClause.Occur.SHOULD);

                TermQuery query2 = new TermQuery(new Term(SearchGoodsVO.FIELD_BRAND, split[i]));
                builder.add(query2, BooleanClause.Occur.SHOULD);

            }
            BooleanQuery booleanClauses = builder.build();
            queries.add(booleanClauses);

            //如果没有结果再按品牌名称分类搜
            String[] fields2 = {SearchGoodsVO.FIELD_BRAND, SearchGoodsVO.FIELD_CATEGORY, SearchGoodsVO.FIELD_NAME, SearchGoodsVO.FIELD_MODEL};
            Map<String, Float> boosts = new HashMap<String, Float>();
            boosts.put(SearchGoodsVO.FIELD_BRAND, 2f);
            boosts.put(SearchGoodsVO.FIELD_NAME, 1f);
            boosts.put(SearchGoodsVO.FIELD_CATEGORY, 1f);
            BooleanQuery.Builder builder2 = new BooleanQuery.Builder();
            String[] split2 = value.split(" ");
            for (int i = 0; i < split.length; i++) {
                FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term(SearchGoodsVO.FIELD_MODEL));
                builder2.add(fuzzyQuery, BooleanClause.Occur.SHOULD);
                builder2.add(new MultiFieldQueryParser(fields2, analyzer).parse(QueryParser.escape(split2[i])), BooleanClause.Occur.MUST);
            }
            BooleanQuery booleanClauses2 = builder2.build();
            queries.add(booleanClauses2);

            //如果仍没有结果最后全局搜
            String[] fields3 = {SearchGoodsVO.FIELD_BRAND, SearchGoodsVO.FIELD_CATEGORY, SearchGoodsVO.FIELD_NAME, SearchGoodsVO.FIELD_MODEL, SearchGoodsVO.FIELD_SPEC};
            Map<String, Float> boosts2 = new HashMap<String, Float>();
            boosts.put(SearchGoodsVO.FIELD_NAME, 2f);
            boosts.put(SearchGoodsVO.FIELD_BRAND, 1f);
            boosts.put(SearchGoodsVO.FIELD_CATEGORY, 1f);
            BooleanQuery.Builder builder3 = new BooleanQuery.Builder();
            String[] split3 = value.split(" ");
            for (int i = 0; i < split.length; i++) {
                builder3.add(new MultiFieldQueryParser(fields3, analyzer).parse(QueryParser.escape(split3[i])), BooleanClause.Occur.MUST);
            }
            BooleanQuery booleanClauses3 = builder3.build();
            queries.add(booleanClauses3);
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (e.getCause() != null) {
                logger.error(e.getCause().getMessage());
            }
        }
        return queries;
    }

    public ReturnMessage<List<SearchGoodsVO>> searchPaged(List<Query> query, Sort sort, int page, int size) {
        List<SearchGoodsVO> goodsList = new ArrayList<>();
        try {
            getSearcher();
            if (page < 1) page = 1;
            int start = (page - 1) * size;
            int num = start + size;
            TopFieldCollector c = TopFieldCollector.create(sort, num, true, true, true);

            for (Query q : query) {
                searcher.search(q, c);
                if (c.getTotalHits() >= 1) {
                    break;
                } else {
                    Query reQuery = searcher.rewrite(q);
                    searcher.search(reQuery, c);
                }
            }

            ScoreDoc[] scores = c.topDocs(start, size).scoreDocs;
            for (ScoreDoc score : scores) {
                SearchGoodsVO goods = docToGoods(searcher.doc(score.doc));
                goodsList.add(goods);
            }
            return ReturnMessage.message(c.getTotalHits(), String.valueOf(size), goodsList);
        } catch (IOException e) {
            logger.error(e.getMessage());
            if (e.getCause() != null) {
                logger.error(e.getCause().getMessage());
            }
        }
        return ReturnMessage.failed();
    }

    private SearchGoodsVO docToGoods(Document doc) {

        SearchGoodsVO goods = new SearchGoodsVO();

        goods.setId(doc.get(SearchGoodsVO.FIELD_ID));
        goods.setType(doc.get(SearchGoodsVO.FIELD_TYPE));
        goods.setImages(doc.get(SearchGoodsVO.FIELD_IMAGES));
        goods.setCategoryId(doc.get(SearchGoodsVO.FIELD_CATEGORY_ID));
        goods.setCategory(doc.get(SearchGoodsVO.FIELD_CATEGORY));
        goods.setBrandId(doc.get(SearchGoodsVO.FIELD_BRAND_ID));
        goods.setBrand(doc.get(SearchGoodsVO.FIELD_BRAND));
        goods.setName(doc.get(SearchGoodsVO.FIELD_NAME));
        goods.setModel(doc.get(SearchGoodsVO.FIELD_MODEL));
        goods.setSpec(doc.get(SearchGoodsVO.FIELD_SPEC));
        goods.setTags(doc.get(SearchGoodsVO.FIELD_TAGS));
        goods.setPriceMin(doc.get(SearchGoodsVO.FIELD_PRICE_MIN));
        goods.setPriceMax(doc.get(SearchGoodsVO.FIELD_PRICE_MAX));
        goods.setSales(doc.get(SearchGoodsVO.FIELD_SALES));
        goods.setViews(doc.get(SearchGoodsVO.FIELD_VIEWS));
        goods.setMemberId(doc.get(SearchGoodsVO.FIELD_MEMBER_ID));
        goods.setQq(doc.get(SearchGoodsVO.FIELD_MEMBER_QQ));

        return goods;
    }

    private Document goodsToDoc(SearchGoodsVO goods) {

        Document doc = new Document();
        doc.add(new StoredField(SearchGoodsVO.FIELD_ID, goods.getId()));
        doc.add(new StoredField(SearchGoodsVO.FIELD_TYPE, goods.getType()));
        doc.add(new StoredField(SearchGoodsVO.FIELD_IMAGES, goods.getImages()));
        doc.add(new StoredField(SearchGoodsVO.FIELD_CATEGORY_ID, goods.getCategoryId()));

        doc.add(new TextField(SearchGoodsVO.FIELD_CATEGORY, goods.getCategory(), Field.Store.YES));
        doc.add(new StoredField(SearchGoodsVO.FIELD_BRAND_ID, goods.getBrandId()));
        doc.add(new TextField(SearchGoodsVO.FIELD_BRAND, goods.getBrand(), Field.Store.YES));
        doc.add(new TextField(SearchGoodsVO.FIELD_NAME, goods.getName(), Field.Store.YES));
        doc.add(new StringField(SearchGoodsVO.FIELD_MODEL, goods.getModel(), Field.Store.YES));
        doc.add(new StringField(SearchGoodsVO.FIELD_SPEC, goods.getSpec(), Field.Store.YES));
        doc.add(new StringField(SearchGoodsVO.FIELD_TAGS, goods.getTags(), Field.Store.YES));
        doc.add(new StoredField(SearchGoodsVO.FIELD_PRICE_MIN, goods.getPriceMin()));
        doc.add(new StoredField(SearchGoodsVO.FIELD_PRICE_MAX, goods.getPriceMax()));
        doc.add(new StoredField(SearchGoodsVO.FIELD_SALES, goods.getSales()));
        doc.add(new StoredField(SearchGoodsVO.FIELD_VIEWS, goods.getViews()));
        doc.add(new StoredField(SearchGoodsVO.FIELD_MEMBER_ID, goods.getMemberId()));
        doc.add(new StoredField(SearchGoodsVO.FIELD_MEMBER_QQ, goods.getQq()));

        doc.add(new NumericDocValuesField(SearchGoodsVO.FIELD_ID + SearchGoodsVO.FIELD_INDEX_SUFFIX, Long.parseLong(goods.getId())));
        doc.add(new SortedDocValuesField(SearchGoodsVO.FIELD_NAME + SearchGoodsVO.FIELD_INDEX_SUFFIX, new BytesRef(goods.getName().getBytes())));

        return doc;
    }

    @Schedule(hour = "3")
    public void rebuildIndex() {

        logger.info("Method: rebuildIndex() hour=3");
    }

    private synchronized Analyzer getAnalyzer() {

        if (analyzer == null) {
            JcsegTaskConfig config = new JcsegTaskConfig(true);
            config.setAppendCJKSyn(true);
            config.setAppendCJKPinyin(true);
            config.setAutoload(true);
            config.setSTokenMinLen(2);
            try {
                String[] lexiconPath = new String[]{ResourcesUtils.loadLexiconDir(), ResourcesUtils.loadProLexiconDir()};
                config.setLexiconPath(lexiconPath);
                config.setClearStopwords(true);
                ADictionary dic = DictionaryFactory.createSingletonDictionary(config, true);
                JcsegAnalyzer5X jcsegAnalyzer5X = new JcsegAnalyzer5X(JcsegTaskConfig.COMPLEX_MODE, config);
                jcsegAnalyzer5X.setDict(dic);
                analyzer = jcsegAnalyzer5X;
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return analyzer;
    }

    private synchronized IndexWriter getWriter() throws IOException {

        if (writer == null || !writer.isOpen()) {
            getAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            FSDirectory directory = FSDirectory.open(Paths.get(DIRECTORY));
            writer = new IndexWriter(directory, config);
        }
        logger.info("Method: getWriter(), numDocs: {} ,maxDoc: {} , numRamDocs: {} , IndexWriter initialled!",
                writer.numDocs(), writer.maxDoc(), writer.numRamDocs());
        return writer;
    }

    private synchronized IndexReader getReader(boolean reOpen) throws IOException {

        if (reOpen || reader == null) {
            FSDirectory directory = FSDirectory.open(Paths.get(DIRECTORY));
            reader = DirectoryReader.open(directory);
        }

        return reader;
    }

    private synchronized IndexSearcher getSearcher() throws IOException {
        searcher = new IndexSearcher(getReader(false));
        return searcher;
    }

    private void closeReader() {
        LuceneUtils.closeIndexReader(directoryReader);
    }

    private void closeWriter() {
        if (writer != null || writer.isOpen()) {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

}
