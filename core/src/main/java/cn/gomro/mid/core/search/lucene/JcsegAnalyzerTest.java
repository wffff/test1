package cn.gomro.mid.core.search.lucene;

import cn.gomro.mid.core.common.utils.ResourcesUtils;
import cn.gomro.mid.core.common.utils.brandLex.BrandDictUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.lionsoul.jcseg.analyzer.v5x.JcsegAnalyzer5X;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yaoo on 2016/9/2.
 */
public class JcsegAnalyzerTest {
    public static void main(String[] args) throws IOException {
//        String str = "banma-斑马（";
//        String str2="斑马（132更新至456";
//        Pattern compile = Pattern.compile("[\u4e00-\u9fa5]+");
//        Matcher matcher = compile.matcher(str2);
//        if(matcher.find()){
//            System.out.println(matcher.groupCount());
//            System.out.println(matcher.group(0));
//        }
//       // System.out.println(BrandDictUtil.generate("brand品牌"));
//

        tokenTest();
    }

    public static void tokenTest() throws IOException {
        Analyzer analyzer = new JcsegAnalyzer5X(JcsegTaskConfig.SIMPLE_MODE);
        //非必须(用于修改默认配置): 获取分词任务配置实例
        JcsegAnalyzer5X jcseg = (JcsegAnalyzer5X) analyzer;
        JcsegTaskConfig config = jcseg.getTaskConfig();
        //追加同义词到分词结果中, 需要在jcseg.properties中配置jcseg.loadsyn=1
        config.setAppendCJKSyn(true);
        //追加拼音到分词结果中, 需要在jcseg.properties中配置jcseg.loadpinyin=1
        config.setAppendCJKPinyin(true);
        //更多配置, 请查看com.webssky.jcseg.core.JcsegTaskConfig类

        FSDirectory directory = FSDirectory.open(Paths.get("/data/lucene/test"));

        IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter iwriter = new IndexWriter(directory, iwConfig);
        iwriter.deleteAll();//清空

        String words = "STST74306-8-23";
        String brand = "史丹利";
        TokenStream stream = null;

        Document doc = new Document();
        doc.add(new StringField(SearchGoodsVO.FIELD_NAME, words, Field.Store.YES));
        doc.add(new StringField(SearchGoodsVO.FIELD_BRAND, brand, Field.Store.YES));
        iwriter.addDocument(doc);
        iwriter.commit();
        iwriter.close();

        FSDirectory open = FSDirectory.open(Paths.get("/data/lucene/test"));
        IndexReader indexReader = DirectoryReader.open(open);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        String string = "*06-8*";
        String[] split = string.split(" ");
        for (int i = 0; i < split.length; i++) {
            PhraseQuery phraseQuery = new PhraseQuery(SearchGoodsVO.FIELD_NAME, split[i]);
            FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term(SearchGoodsVO.FIELD_NAME, split[i]));
            WildcardQuery wildcardQuery = new WildcardQuery(new Term(SearchGoodsVO.FIELD_NAME, split[i]));

            builder.add(new BooleanClause(wildcardQuery, BooleanClause.Occur.SHOULD));
        }
        BooleanQuery booleanClauses = builder.build();
        TopDocs search = searcher.search(booleanClauses, 100);
        ScoreDoc[] scoreDocs = search.scoreDocs;
        for (ScoreDoc score : scoreDocs) {
            Document doc1 = searcher.doc(score.doc);
            String s = doc1.get(SearchGoodsVO.FIELD_NAME);
            String s2 = doc1.get(SearchGoodsVO.FIELD_BRAND);
            System.out.println(s+"--->>"+s2);
        }
        /*try {
            stream = analyzer.tokenStream(SearchGoodsVO.FIELD_NAME, words);
            stream.reset();
            CharTermAttribute offsetAtt = stream.addAttribute(CharTermAttribute.class);
            while (stream.incrementToken()) {
                System.out.println(offsetAtt.toString());
            }
            stream.end();
            if (stream != null) stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

}
