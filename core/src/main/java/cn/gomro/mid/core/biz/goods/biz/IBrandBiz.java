package cn.gomro.mid.core.biz.goods.biz;

import cn.gomro.mid.core.biz.goods.entity.GoodsBrandEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Adam on 2017/1/10.
 */


public interface IBrandBiz {
    ReturnMessage<List<GoodsBrandEntity>> getShortBrandList(GoodsType type);

    ReturnMessage<GoodsBrandEntity> queryOrCreateBrandByName(GoodsType type, String name);

    ReturnMessage<GoodsBrandEntity> getBrandByName(GoodsType type, String name);

    ReturnMessage<List<GoodsBrandEntity>> getItemsPaged(GoodsBrandEntity entity, Integer page, Integer size);

    ReturnMessage<List<GoodsBrandEntity>> querybrandidList(Integer brandId,String brandName,Integer page, Integer rows);

    //ReturnMessage<List<GoodsBrandEntity>> getgetShortBrandListpage(GoodsType type,Integer page, Integer size);

    Integer getbrandIDsave(GoodsBrandEntity goodsBrandEntity);
    //getBrandincreased
    Integer getBrandincreased(GoodsBrandEntity goodsBrandEntity);

    Integer getBranddelete(Integer id);
}
