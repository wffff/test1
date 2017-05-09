package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.goods.entity.GoodsBrandEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsCategoryEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsCategoryEntityWithOutTree;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsCategoryService;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import cn.gomro.mid.core.common.utils.Utils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by yaodw on 2016/7/18.
 */
@Stateless
public class GoodsCategoryService extends AbstractSessionService<GoodsCategoryEntity> implements IGoodsCategoryService {

    final Logger logger =Logger.getLogger(GoodsCategoryService.class);

    public GoodsCategoryService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        GoodsCategoryEntity goodsCategory = this.getItem(id).getData();
        if (goodsCategory != null)
            return ReturnMessage.success(goodsCategory.getName(), goodsCategory.getName());
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<GoodsCategoryEntity>> getItemsPaged(GoodsCategoryEntity entity, Integer page, Integer size) {

        String where = " WHERE del=false";
        String order = " ORDER BY id ASC";

//        if (null != entity && StringUtils.isNotBlank(entity.getName())) {
//            where += " AND LOWER(name)=LOWER('" + entity.getName() + "')";
//        }
        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM GoodsCategoryEntity" + where,
                "FROM GoodsCategoryEntity" + where + order, page, size);
    }


    @Override
    public ReturnMessage<GoodsCategoryEntityWithOutTree> queryOrCreateCategoryByName(Integer pid, GoodsType type, String name) {

        String where = " WHERE del=false";
        where += " AND " + (pid != null ? "pid=" + pid : "pid IS NULL");

        if (type != GoodsType.ALL) where += " AND type=" + type.ordinal();
        if (!Utils.isNotBlankTrimed(name)) name = "其它";
        where += " AND LOWER(name)=LOWER('" + StringEscapeUtils.escapeSql(name) + "')";

        GoodsCategoryEntityWithOutTree entity = JpaUtils.querySingleResult(em, "from GoodsCategoryEntityWithOutTree" + where);
        if (entity == null) {
            Date date = new Date();
            entity = new GoodsCategoryEntityWithOutTree(pid, type, name, 1, true, false, date, date);
            em.persist(entity);
            em.flush();
        }

        return entity == null ? ReturnMessage.failed() : ReturnMessage.success(entity);
    }

    @Override
    public ReturnMessage<List<GoodsCategoryEntity>> queryAllCategoryList(GoodsType type, String name, boolean root) {

        String where = " WHERE del=false";
        String order = " ORDER BY pid ASC";

        if (type != GoodsType.ALL) where += " AND type=" + type.ordinal();
        if (Utils.isNotBlankTrimed(name)) where += " AND LOWER(name)=LOWER('" + StringEscapeUtils.escapeSql(name) + "')";
        if (root) where += " AND pid is null";
        where += " AND name <> ''";

        List<GoodsCategoryEntity> categoryList = JpaUtils.queryShortResultList(em, "FROM GoodsCategoryEntity" + where + order);
        return categoryList == null ? ReturnMessage.failed() : ReturnMessage.success(String.valueOf(categoryList.size()), categoryList);
    }

    @Override
    public ReturnMessage<List<GoodsCategoryEntity>> queryListByParent(GoodsType type, Integer pid) {

        String where = " WHERE del=false";
        String order = " ORDER BY pid ASC";

        if (pid != null) where += " AND pid=" + pid;
        List<GoodsCategoryEntity> ts = JpaUtils.queryShortResultList(em, "FROM GoodsCategoryEntity" + where + order);
        if (ts != null) {
            return ReturnMessage.message(ts.size(), ts.size() + "", ts);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<GoodsCategoryEntityWithOutTree>> querySelfAndListById(GoodsType type, Integer id, Boolean isUpward) {

        String where = " WHERE del=false";
        if (type != null && type != GoodsType.ALL) where += " AND type=" + type.ordinal();
        if (id != null && id > 0) where += " AND id=" + id;
        else where += " AND pid is null";

        //如果入参是向上递归则改条件为向上
        String forWard = " t_goods_category_2.pid = c.id";
        if (isUpward) forWard = " t_goods_category_2.id = c.pid";

        Query nativeQuery = em.createNativeQuery("WITH RECURSIVE c AS (" +
                " SELECT * FROM t_goods_category_2" + where +
                " UNION ALL" +
                " SELECT t_goods_category_2.* FROM t_goods_category_2, c WHERE" + forWard +
                " ) SELECT * FROM c ORDER BY id", GoodsCategoryEntityWithOutTree.class);

        List<GoodsCategoryEntityWithOutTree> resultList = nativeQuery.getResultList();
        nativeQuery.setHint(Constants.JPA_CACHEABLE, true);
        if (resultList != null && resultList.size() > 0) {
            ReturnMessage<List<GoodsCategoryEntityWithOutTree>> message = ReturnMessage.message(resultList.size(), String.valueOf(resultList.size()), resultList);
            return message;
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<GoodsCategoryEntity>> queryLeafsByBrandId(GoodsType type, Integer brandId) {
        String sql = "SELECT DISTINCT category.* FROM t_goods_category_2 category " +
                "INNER JOIN t_goods_profile_2 goods " +
                "ON goods.category_id = category.id " +
                "AND category.type=" + type.ordinal() +
                " AND goods.brand_id=" + brandId;

        Query nativeQuery = em.createNativeQuery(sql, GoodsCategoryEntity.class);
        nativeQuery.setHint(Constants.JPA_CACHEABLE,true);
        List<GoodsCategoryEntity> resultList = nativeQuery.getResultList();
        em.clear();
        return ReturnMessage.message(resultList.size(), String.valueOf(resultList.size()), resultList);
    }
    @Override
    public ReturnMessage<GoodsCategoryEntityWithOutTree> getCategoryWithOutTreeById(Integer id){
        try {
            GoodsCategoryEntityWithOutTree goodsCategoryEntityWithOutTree = em.find(GoodsCategoryEntityWithOutTree.class, id);
            return ReturnMessage.success(goodsCategoryEntityWithOutTree);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnMessage.failed();
        }
    }

    public ReturnMessage<List<GoodsCategoryEntity>> queryAllCategoryList(GoodsType type)
    {
        try {
            String where = " WHERE del=false and saled=true";
            String order = " order by name";
            if (type != null && type != GoodsType.ALL) {
                where += " AND type=" + type.ordinal();
            }
            List<GoodsCategoryEntity> goodsCategoryEntities = JpaUtils.queryShortResultList(em, "FROM GoodsCategoryEntity" + where+order);
            return ReturnMessage.success(goodsCategoryEntities);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ReturnMessage.failed();
        }

    }

    public Integer getCategoryIDsave(GoodsCategoryEntity entity)
    {

        Integer returned=1;
        Date date = new Date();

        String where=" where del=false and saled=true";
        String and=" and id="+entity.getId();
        entity.setLast(date);
        entity.setTime(date);
        entity.setSaled(true);
        List<GoodsCategoryEntity> CategoryEntityListgetbyid =  JpaUtils.queryShortResultList(em, "FROM GoodsCategoryEntity"+where+and);
        GoodsCategoryEntity merge = em.merge(entity);
        if (merge.equals(CategoryEntityListgetbyid))
        {
            returned=1;
        }else
        {
            returned=2;
        }
        List<GoodsCategoryEntity> CategoryEntityList =  JpaUtils.queryShortResultList(em, "FROM GoodsCategoryEntity"+where+and);

        return returned;
    }

    public Integer getCategoryincreased(GoodsCategoryEntity entity)
    {

        Integer returned=1;
        Date date = new Date();
        entity.setLast(date);

        entity.setTime(date);
        entity.setSaled(true);

        em.persist(entity);
        Integer id = em.find(entity.getClass(), entity.getId()).getId();
        if (id==0)
        {

            returned=1;
        }else
        { returned=2;

        }
        return returned;
    }

    public Integer getCategorydelete(Integer id)
    {
        String where = " WHERE del=false and id="+id;
        List<GoodsCategoryEntity> CategoryEntityList =  JpaUtils.queryShortResultList(em, "FROM GoodsCategoryEntity"+where);
        Integer returned=1;
        GoodsCategoryEntity CategoryEntity=new GoodsCategoryEntity();
        for (GoodsCategoryEntity b:CategoryEntityList)
        {
            b.setDel(true);
            System.out.println(b);
            CategoryEntity=em.merge(b);
        }
        for (GoodsCategoryEntity b:CategoryEntityList)
        {
            if (b.equals(CategoryEntity))
            {
                returned=2;
            }else
            {
                returned=1;
            }
        }
        return null;
    }
    public GoodsCategoryEntity getCategoryByid(Integer id)
    {
        String where = " WHERE del=false and id="+id;

        GoodsCategoryEntity goodsCategoryEntity =JpaUtils.querySingleResult(em, "FROM GoodsCategoryEntity" + where);

        return goodsCategoryEntity;
    }

    public GoodsCategoryEntity getCategoryByserial(Integer id)
    {
        String where = " WHERE del=false and id="+id;

        GoodsCategoryEntity goodsCategoryEntity =JpaUtils.querySingleResult(em, "FROM GoodsCategoryEntity" + where);

        String where2 = " WHERE del=false and id="+goodsCategoryEntity.getPid();

        GoodsCategoryEntity goodsCategoryEntity2 =JpaUtils.querySingleResult(em, "FROM GoodsCategoryEntity" + where);
        return goodsCategoryEntity2;
    }

}
