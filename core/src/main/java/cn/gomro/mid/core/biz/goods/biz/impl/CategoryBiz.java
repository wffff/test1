package cn.gomro.mid.core.biz.goods.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.goods.biz.ICategoryBiz;
import cn.gomro.mid.core.biz.goods.biz.ICategoryBizLocal;
import cn.gomro.mid.core.biz.goods.biz.ICategoryBizRemote;
import cn.gomro.mid.core.biz.goods.entity.GoodsCategoryEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsCategoryEntityWithOutTree;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsCategoryService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yaoo on 2016/8/24.
 */
@Stateless
public class CategoryBiz extends AbstractSessionBiz implements ICategoryBizRemote,ICategoryBizLocal {


    @EJB
    private IGoodsCategoryService categoryService;


    @Override
    public ReturnMessage<GoodsCategoryEntityWithOutTree> queryOrCreateCategoryByName(Integer pid, GoodsType type, String name) {
        return categoryService.queryOrCreateCategoryByName(pid,type,name);
    }

    @Override
    public ReturnMessage<List<Map<String, Object>>> queryIdParentList(GoodsType type, Integer id) {
        if (id != null && id > 0) {
            List<Map<String, Object>> list = new ArrayList<>();
            GoodsCategoryEntity entity = categoryService.getItem(id).getData();
            if (entity != null) {
                list = this.recursiveList(list, entity);
            }
            return ReturnMessage.message(list.size(), String.valueOf(list.size()), list);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<GoodsCategoryEntity> getItem(Integer id) {
        return categoryService.getItem(id);
    }

    @Override
    public ReturnMessage<GoodsCategoryEntity> getName(Integer id) {
        return categoryService.getName(id);
    }

    @Override
    public ReturnMessage<List<GoodsCategoryEntity>> queryAllCategoryList(GoodsType type, String name, boolean b) {
        return categoryService.queryAllCategoryList(type, name, b);
    }

    @Override
    public ReturnMessage<List<GoodsCategoryEntity>> queryListByParent(GoodsType type, Integer pid) {
        return categoryService.queryListByParent(type, pid);
    }

    /**
     * 递归获取父列表
     *
     * @param list
     * @param entity
     * @return
     */
    private List<Map<String, Object>> recursiveList(List<Map<String, Object>> list, GoodsCategoryEntity entity) {
        if (entity != null) {
            if (entity.getPid() != null && entity.getPid() != 0) {
                GoodsCategoryEntity data = categoryService.getItem(entity.getPid()).getData();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", entity.getId());
                map.put("pid", entity.getPid());
                map.put("name", entity.getName());
                list.add(map);
                this.recursiveList(list, data);
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", entity.getId());
                map.put("pid", entity.getPid());
                map.put("name", entity.getName());
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public ReturnMessage<List<GoodsCategoryEntityWithOutTree>> querySelfAndListById(GoodsType type, Integer id, Boolean isUpward){
        return categoryService.querySelfAndListById(type,id,isUpward);
    }

    @Override
    public ReturnMessage<GoodsCategoryEntity> addItem(GoodsCategoryEntity entity) {
        return categoryService.addItem(entity);
    }

    @Override
    public ReturnMessage delItem(Integer id) {
        GoodsCategoryEntity entity = categoryService.getItem(id).getData();
        return categoryService.delItem(entity);
    }

    @Override
    public ReturnMessage<GoodsCategoryEntity> editCategory(Integer id, Integer pid, String name, Integer sort, Boolean saled) {
        GoodsCategoryEntity entity = categoryService.getItem(id).getData();
        if (entity != null) {
            if (pid != null) entity.setPid(pid);
            if (name != null) entity.setName(name);
            if (sort != null) entity.setSort(sort);
            if (saled != null) entity.setSaled(saled);
        }
        return categoryService.editItem(entity);
    }

    @Override
    public ReturnMessage<List<GoodsCategoryEntity>> queryLeafsByBrandId(GoodsType type, Integer brandId) {
        return categoryService.queryLeafsByBrandId(type, brandId);
    }

    @Override
    public ReturnMessage<List<GoodsCategoryEntity>> getItemsPaged(GoodsCategoryEntity entity, Integer page, Integer size)
    {
        return categoryService.getItemsPaged(entity,page, size);
    }

    @Override
    public ReturnMessage<List<GoodsCategoryEntity>> queryAllCategoryList(GoodsType type)
    {

        return categoryService.queryAllCategoryList(type);
    }


    public Integer getCategoryIDsave(GoodsCategoryEntity entityntity)
    {
        return categoryService.getCategoryIDsave(entityntity);
    }
    public Integer getCategoryincreased(GoodsCategoryEntity entityntity)
    {
        return categoryService.getCategoryincreased(entityntity);
    }
    public  Integer getCategorydelete(Integer id)
    {
        return categoryService.getCategorydelete(id);
    }

    public  GoodsCategoryEntity getCategoryByid(Integer id)
    {
        return categoryService.getCategoryByid(id);
    }

    public GoodsCategoryEntity getCategoryByserial(Integer id)
    {
        return categoryService.getCategoryByid(id);
    }
}
