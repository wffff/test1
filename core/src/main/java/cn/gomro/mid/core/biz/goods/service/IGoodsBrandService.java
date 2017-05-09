package cn.gomro.mid.core.biz.goods.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.goods.entity.GoodsBrandEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
public interface IGoodsBrandService extends IService<GoodsBrandEntity> {

    ReturnMessage<GoodsBrandEntity> queryOrCreateBrandByName(GoodsType type, String name);

    ReturnMessage<GoodsBrandEntity> getBrandByName(GoodsType type, String name);

    ReturnMessage<List<GoodsBrandEntity>> getBrandListByCategoryId(GoodsType type, List<Integer> idList,Integer categoryId);

    ReturnMessage<List<GoodsBrandEntity>> getShortBrandList(GoodsType type);

    ReturnMessage<List<GoodsBrandEntity>> querybrandidList(Integer brandId,String brandName,Integer page, Integer rows);

    Integer getbrandIDsave(GoodsBrandEntity goodsBrandEntity);

    Integer getBrandincreased(GoodsBrandEntity goodsBrandEntity);

    Integer getBranddelete(Integer id);

}
