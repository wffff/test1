package cn.gomro.mid.core.biz.goods.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.goods.entity.*;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;
import java.util.Set;

/**
 * Created by yaodw on 2016/7/15.
 */
public interface IGoodsService extends IService<GoodsEntity> {

    ReturnMessage<List<GoodsEntity>> addGoodsItems(List<GoodsEntity> goodsList);

    ReturnMessage<List<GoodsBrandEntity>> queryGoodsBrandListByCategoryId(GoodsType type, Integer categoryId);

    ReturnMessage<GoodsEntity> getItemBySpecId(Integer specId);

    ReturnMessage<List<GoodsEntity>> queryGoodsList(GoodsType type, Integer brandId, Integer categoryId, boolean onlyCategory, String brandName, String goodsName,
                                                    boolean haseImages,
                                                    String modelName, String specName, Integer memberId, String memberName,String keyWord,
                                                    Boolean goodsSaled, Boolean specSaled, Boolean modelSaled, String orderBy, Double priceMin, Double priceMax,
                                                    Integer page, Integer size);

    ReturnMessage<List<GoodsEntity>> randomQueryGoodsByCategoryOrBrand(GoodsType type, Integer brandId, Integer categoryId, boolean hasImages, Integer size);

    ReturnMessage<List<GoodsEntityManyToOne>> queryGoodsManyToOneList(GoodsType type, Integer brandId, String brandName, Integer categoryId,
                                                                      boolean onlyCategory, boolean hasImages, String goodsName, Boolean goodsSaled, Boolean goodsDel,
                                                                      String orderBy, Double priceMin, Double priceMax, Integer page, Integer size);

    ReturnMessage<GoodsEntityManyToOne> editGoodsManyToOne(GoodsEntityManyToOne goods);

    ReturnMessage<GoodsEntity> getItemById(Integer id);

    ReturnMessage forSaleGoodsByBrandId(Integer id);

    ReturnMessage setForSale(Integer[] ids);

}

