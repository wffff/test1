package cn.gomro.mid.core.search.lucene;

import cn.gomro.mid.core.biz.goods.entity.GoodsEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by momo on 2016/8/5.
 */
public interface IGoodsSearchBiz extends Serializable {

    ReturnMessage initialize(int size, int step);

    ReturnMessage<List<SearchGoodsVO>> search(int page, int size, String q, int sort);

    ReturnMessage del();

    ReturnMessage addDocument(SearchGoodsVO goods);

    ReturnMessage addDocumentList(Set<SearchGoodsVO> goods);

    ReturnMessage delDocumentById(GoodsEntity entity);

    ReturnMessage addBrandDictionary();

    ReturnMessage analysisTest(String q) throws IOException;
}
