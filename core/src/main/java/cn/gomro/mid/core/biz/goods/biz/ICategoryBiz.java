package cn.gomro.mid.core.biz.goods.biz;

import cn.gomro.mid.core.biz.goods.entity.GoodsBrandEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsCategoryEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsCategoryEntityWithOutTree;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by yaoo on 2016/8/24.
 */
public interface ICategoryBiz {

    ReturnMessage<List<Map<String,Object>>> queryIdParentList(GoodsType type, Integer id);

    ReturnMessage<GoodsCategoryEntity> getItem(Integer id);

    ReturnMessage<GoodsCategoryEntity> getName(Integer id);

    ReturnMessage<GoodsCategoryEntityWithOutTree> queryOrCreateCategoryByName(Integer pid, GoodsType type, String name);

    ReturnMessage<List<GoodsCategoryEntity>> queryAllCategoryList(GoodsType type, String name, boolean b);

    ReturnMessage<List<GoodsCategoryEntity>> queryListByParent(GoodsType type, Integer pid);

    ReturnMessage<List<GoodsCategoryEntityWithOutTree>> querySelfAndListById(GoodsType type, Integer id, Boolean isUpward);

    ReturnMessage<GoodsCategoryEntity> addItem(GoodsCategoryEntity entity);

    ReturnMessage delItem(Integer entity);

    ReturnMessage<GoodsCategoryEntity> editCategory(Integer id, Integer pid, String name, Integer sort, Boolean saled);

    ReturnMessage<List<GoodsCategoryEntity>> queryLeafsByBrandId(GoodsType type, Integer brandId);

    ReturnMessage<List<GoodsCategoryEntity>> getItemsPaged(GoodsCategoryEntity entity, Integer page, Integer size);

    ReturnMessage<List<GoodsCategoryEntity>> queryAllCategoryList(GoodsType type);


    Integer getCategoryIDsave(GoodsCategoryEntity entity);
    //getBrandincreased
    Integer getCategoryincreased(GoodsCategoryEntity entity);

    Integer getCategorydelete(Integer id);

    GoodsCategoryEntity getCategoryByid(Integer id);
    GoodsCategoryEntity getCategoryByserial(Integer id);
}
