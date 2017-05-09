package cn.gomro.mid.core.search.lucene;

import cn.gomro.mid.core.biz.goods.entity.GoodsBrandEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsEntityManyToOne;
import cn.gomro.mid.core.biz.goods.entity.GoodsSpecEntityManyToOne;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsBrandService;
import cn.gomro.mid.core.biz.goods.service.IGoodsService;
import cn.gomro.mid.core.biz.goods.service.IGoodsSpecService;
import cn.gomro.mid.core.biz.goods.service.impl.GoodsSpecService;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.ResourcesUtils;
import cn.gomro.mid.core.common.utils.Utils;
import cn.gomro.mid.core.common.utils.brandLex.BrandDictUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.store.RAMDirectory;
import org.lionsoul.jcseg.analyzer.v5x.JcsegAnalyzer5X;
import org.lionsoul.jcseg.tokenizer.core.ADictionary;
import org.lionsoul.jcseg.tokenizer.core.DictionaryFactory;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
@AccessTimeout(20000)
public class GoodsSearchBiz implements IGoodsSearchBiz {
    final Logger logger = LoggerFactory.getLogger(GoodsSpecService.class);

    @EJB
    IGoodsService goodsService;
    @EJB
    IGoodsSpecService specService;
    @EJB
    IGoodsBrandService brandService;
    @EJB
    GoodsSearch search;

    int threadCount = 0;

    public GoodsSearchBiz() {}

    @Override
    public ReturnMessage initialize(int size, int step) {//全部重新索引
        Integer code = specService.queryGoodsSpecList(GoodsType.ALL, null, null, true, null, null, null, null, null, true, null, null, true, null, null, null, null, null, null, false, 1, 2).getCode();
        if (threadCount > 0) {
            return ReturnMessage.failed("执行中，请稍后……" + code);
        }
        int times = (code / size + 1) / step;//10 pages each loop
        if (times >= 20) {
            return ReturnMessage.failed("线程过多！");
        }
        this.del();//清空
        this.addBrandDictionary();//更新品牌词库
        int pageStart = 1;
        logger.info("Method: initialize() 线程数： " + times + " 执行中……");
        threadCount = times;
        ExecutorService pool = Executors.newCachedThreadPool();
        Thread thread = null;
        for (int i = 0; i <= times; i++) {
            int j = i;
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    doInitialGoods(pageStart + (j * step), step + (j * step), size);
                    threadCount--;
                    logger.info("Method: initialize() Thread end. ");

                }
            });
            pool.execute(thread);
        }
        pool.shutdown();
        return ReturnMessage.success(code);
    }

    //@Schedule(hour = "1", dayOfWeek = "5")
    public void rebuildIndex() {
        this.initialize(800, 20);
        logger.info("Method: rebuildIndex() hour=1");
    }

    private void doInitialGoods(int pageStart, int pageEnd, int size) {
        int page = pageStart;
        int num = -1;
        int totals = 0;
        do {
            ReturnMessage<List<GoodsSpecEntityManyToOne>> ret = specService.queryGoodsSpecList(GoodsType.ALL, null, null, true, null, null, null, null, null, true, null, null, true, null, null, null, null, null, null, false, page, size);
            if (ret != null && ReturnCode.isSuccess(ret.getCode()) && ret.getData() != null && ret.getData().size() > 0) {

                Set<SearchGoodsVO> searchGoodsVOList = new HashSet<>();
                List<GoodsSpecEntityManyToOne> list = ret.getData();

                for (GoodsSpecEntityManyToOne entity : list) {
                    //通过实体对象构造VO对象
                    GoodsEntityManyToOne goods = entity.getModel().getGoods();
                    SearchGoodsVO searchGoodsVO = new SearchGoodsVO(goods);
                    searchGoodsVOList.add(searchGoodsVO);
                }

                num = ret.getData().size();
                totals = ret.getCode();

                logger.info("Method: doInitialGoods(),  Page: {}  Count: {}  totals: {} ", page, num, totals);
                search.addList(searchGoodsVOList);
            } else {
                num = -1;
            }
            search.cleanup();
            int totalPages = totals / size + 1;
            if (page >= totalPages) {
                logger.info("Method: doInitialGoods(), Documents Add finished at : currentPage: {}  totalPages: {} ", page, totalPages);
                break;
            }
            page++;
        } while (num > 0 && page <= pageEnd);
    }


    @Override
    public ReturnMessage<List<SearchGoodsVO>> search(int page, int size, String q, int sort) {

        if (q == null || "".equals(q.trim())) {
            return ReturnMessage.failed();
        }

        size = Utils.reqPageSize(size);

        SortField[] sortField = new SortField[1];
        sortField[0] = SortField.FIELD_SCORE;
        Sort sortx = new Sort(sortField);
        List<Query> queries = search.goodsQuerys(q);
        return search.searchPaged(queries, sortx, page, size);
    }

    @Override
    public ReturnMessage del() {
        //清空索引
        int i = search.deleteAll();
        return ReturnMessage.message(i, i + "索引已清空！", null);
    }

    @Override
    public ReturnMessage addDocument(SearchGoodsVO goods) {
        try {
            search.add(goods);
            return ReturnMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnMessage.failed();
        }
    }

    @Override
    public ReturnMessage addDocumentList(Set<SearchGoodsVO> goods) {
        try {
            search.addList(goods);
            return ReturnMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage delDocumentById(GoodsEntity goods) {
        try {
            Term term = new Term(SearchGoodsVO.FIELD_ID, String.valueOf(goods.getId()));
            search.delete(term);
            search.rebuildIndex();
            return ReturnMessage.success();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage addBrandDictionary() {
        try {
            List<GoodsBrandEntity> data = brandService.getShortBrandList(GoodsType.ALL).getData();
            if (data != null) {

                File dir = new File(ResourcesUtils.loadProLexiconDir());
                if (!dir.exists() || !dir.isDirectory()) {
                    dir.mkdirs();
                }
                File file = new File(ResourcesUtils.loadProLexiconDir() + "/lex-brand.lex");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("CJK_BRANDS\n");
                for (int i = 0; i < data.size(); i++) {
                    String name = data.get(i).getName();
                    name = BrandDictUtil.generate(name);
                    Pattern compile = Pattern.compile("^[\u4e00-\u9fa5]+");
                    Matcher matcher = compile.matcher(name);
                    boolean b = matcher.find();
                    if (b)
                    fileWriter.write(name + "\n");
                }
                fileWriter.flush();
                fileWriter.close();

                File autoLoadFile = new File(ResourcesUtils.loadLexiconDir() + "/lex-autoload.todo");
                FileWriter autoLoadFileWriter = new FileWriter(autoLoadFile);
                autoLoadFileWriter.write("lex-brand.lex");
                autoLoadFileWriter.flush();
                autoLoadFileWriter.close();
                return ReturnMessage.success(data.size());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage analysisTest(String q) throws IOException {

        JcsegTaskConfig config = new JcsegTaskConfig(true);

        config.setAppendCJKSyn(true);
        config.setAppendCJKPinyin(true);
        config.setAutoload(true);
        config.setLexiconPath(new String[]{ResourcesUtils.loadLexiconDir(), ResourcesUtils.loadProLexiconDir()});

        ADictionary dic = DictionaryFactory.createSingletonDictionary(config, true);
        JcsegAnalyzer5X jcsegAnalyzer5X = new JcsegAnalyzer5X(JcsegTaskConfig.SEARCH_MODE, config);
        jcsegAnalyzer5X.setDict(dic);

        //配置使用的词库目录
        RAMDirectory directory = new RAMDirectory();
        IndexWriterConfig iwConfig = new IndexWriterConfig(jcsegAnalyzer5X);
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter iwriter = new IndexWriter(directory, iwConfig);
        iwriter.deleteAll();//清空

        TokenStream stream = null;

        Document doc = new Document();
        doc.add(new TextField(SearchGoodsVO.FIELD_NAME, q, Field.Store.YES));
        iwriter.addDocument(doc);
        iwriter.commit();
        iwriter.close();

        stream = jcsegAnalyzer5X.tokenStream(SearchGoodsVO.FIELD_NAME, q);
        stream.reset();
        CharTermAttribute offsetAtt = stream.addAttribute(CharTermAttribute.class);
        LinkedHashSet<String> strings = new LinkedHashSet<>();
        while (stream.incrementToken()) {
            CharTermAttribute offsetAtt1 = offsetAtt;
            strings.add(offsetAtt1.toString());
        }
        stream.end();
        if (stream != null) stream.close();

        return ReturnMessage.success(strings);
    }
}
