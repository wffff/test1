package cn.gomro.mid.core.biz.goods.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.goods.entity.GoodsEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsModelEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsSpecEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by yaodw on 2016/7/15.
 */
public interface IGoodsModelService extends IService<GoodsModelEntity> {

    ReturnMessage<List<GoodsModelEntity>> addGoodsItems(List<GoodsModelEntity> goodsList);
    ReturnMessage<List<GoodsModelEntity>> queryGoodsModelList(GoodsType type, Integer goodsId, String goodsName, String modelName, String specName,
                                                            String brandName, Integer memberId, Boolean specSaled, String OrderBy, Double priceMin, Double priceMax,
                                                            Integer page, Integer size);
}
