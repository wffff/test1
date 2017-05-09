package cn.gomro.mid.core.biz.goods.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.goods.entity.GoodsBrandEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsCategoryEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsCategoryEntityWithOutTree;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by yaodw on 2016/7/18.
 */
public interface IGoodsCategoryService extends IService<GoodsCategoryEntity> {

    ReturnMessage<GoodsCategoryEntityWithOutTree> queryOrCreateCategoryByName(Integer pid, GoodsType type, String name);

    ReturnMessage<List<GoodsCategoryEntity>> queryAllCategoryList(GoodsType type, String name, boolean root);

    ReturnMessage<List<GoodsCategoryEntity>> queryListByParent(GoodsType type, Integer pid);

    ReturnMessage<List<GoodsCategoryEntityWithOutTree>> querySelfAndListById(GoodsType type, Integer id, Boolean isUpward);

    ReturnMessage<List<GoodsCategoryEntity>> queryLeafsByBrandId(GoodsType type, Integer brandId);

    ReturnMessage<GoodsCategoryEntityWithOutTree> getCategoryWithOutTreeById(Integer id);


    ReturnMessage<List<GoodsCategoryEntity>> queryAllCategoryList(GoodsType type);

    Integer getCategoryIDsave(GoodsCategoryEntity entity);

    Integer getCategoryincreased(GoodsCategoryEntity entity);

    Integer getCategorydelete(Integer id);

    GoodsCategoryEntity getCategoryByid(Integer id);

    GoodsCategoryEntity getCategoryByserial(Integer id);

}
