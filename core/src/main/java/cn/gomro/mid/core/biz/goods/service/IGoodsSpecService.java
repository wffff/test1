package cn.gomro.mid.core.biz.goods.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.common.entity.AccessTokenEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsSpecEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsSpecEntityManyToOne;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by yaodw on 2016/7/15.
 */
public interface IGoodsSpecService extends IService<GoodsSpecEntity> {

    ReturnMessage<List<GoodsSpecEntity>> addGoodsItems(List<GoodsSpecEntity> goodsList);

    ReturnMessage<List<GoodsSpecEntityManyToOne>> queryGoodsSpecList(GoodsType type,                                                                                                 Integer goodsId, String goodsName, Boolean goodsSaled, Integer brandId,
                                                                     String brandName, Integer categoryId, Integer modelId, String modelName, Boolean modelSaled,
                                                                     Integer specId, String specName, Boolean specSaled, Double priceMin, Double priceMax,
                                                                     Integer memberId, String memberName, String keyWord,String orderBy, boolean hasImages,
                                                                     Integer page, Integer size);
    //queryAudiSpecList
    ReturnMessage<List<GoodsSpecEntityManyToOne>> queryAudiSpecList(GoodsType type,                                                                                                 Integer goodsId, String goodsName, Boolean goodsSaled, Integer brandId,
                                                                     String brandName, Integer categoryId, Integer modelId, String modelName, Boolean modelSaled,
                                                                     Integer specId, String specName, Boolean specSaled, Double priceMin, Double priceMax,
                                                                     Integer memberId, String memberName, String keyWord,String orderBy, boolean hasImages,
                                                                     Integer page, Integer size);

    ReturnMessage<GoodsSpecEntityManyToOne> getExistsGoodsSpec(GoodsType type, Integer memberId, String brandName, String goodsName, String modelName,
                                                               String specName, Integer packageNum, String unitName, String packageUnitName);

    ReturnMessage<Map<String, Double>> queryGoodsMaxAndMinPrice(Integer goodsId);

    ReturnMessage<GoodsSpecEntityManyToOne> getManyToOneItem(Integer id);

    List<GoodsSpecEntityManyToOne> getGoodsSpecModelGoodByIds(Integer id);

    ReturnMessage<GoodsSpecEntityManyToOne> editSpec(GoodsSpecEntityManyToOne spec);

    ReturnMessage<GoodsSpecEntityManyToOne> addSpec(GoodsSpecEntityManyToOne spec);

    ReturnMessage<GoodsSpecEntityManyToOne> addSpec(GoodsSpecEntityManyToOne spec, Integer modelid, Integer brandId, Integer categoryid,Integer unitid,String goodsname,String modelname,String images,String UNSpsc,String HSCode,Integer packageUnitid);

    ReturnMessage adjustPrice(AccessTokenEntity access, GoodsType type, Integer brandId, Integer percent);
}
