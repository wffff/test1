package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.goods.entity.GoodsBrandEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsBrandService;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import cn.gomro.mid.core.common.utils.Utils;
import org.apache.commons.lang.StringEscapeUtils;
import org.jboss.logging.Logger;
import org.omg.CORBA.TIMEOUT;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
//
@Stateless
public class GoodsBrandService extends AbstractSessionService<GoodsBrandEntity> implements IGoodsBrandService {

    final Logger logger =Logger.getLogger(GoodsBrandService.class);

    public GoodsBrandService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        ReturnMessage<GoodsBrandEntity> ret = this.getItem(id);
        if (ReturnCode.isSuccess(ret.getCode()) && ret.getData() != null)
            return ReturnMessage.success(ret.getData().getName(), ret.getData().getName());
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<GoodsBrandEntity> queryOrCreateBrandByName(GoodsType type, String name) {
        String where = " WHERE del=false";
        if (type != GoodsType.ALL) where += " AND type=" + type.ordinal();
        if (Utils.isNotBlankTrimed(name)) where += " AND LOWER(name)=LOWER('" + StringEscapeUtils.escapeSql(name) + "')";

        GoodsBrandEntity entity = JpaUtils.querySingleResult(em, "FROM GoodsBrandEntity" + where);
        if (entity == null) {
            Date date = new Date();
            entity = new GoodsBrandEntity(false,date,date,type, name, "", "", "", "", 1, true);
            em.persist(entity);
        }
        return entity == null ? ReturnMessage.failed() : ReturnMessage.success(entity);
    }

    @Override
    public ReturnMessage<List<GoodsBrandEntity>> getItemsPaged(GoodsBrandEntity entity, Integer page, Integer size) {
        String where = " WHERE del=false ";
        String order = " ORDER BY id ASC";
       // String whare = " WHERE del=false ";
       // System.out.println("***-  -  -  -  - >"+page+"      "+size);
//        if (null != entity) {
//            if (entity.getType() != GoodsType.ALL) where += " AND type=" + entity.getType().ordinal();
//
//            if (null != entity.getName()) where += " AND LOWER(name) like LOWER('%" + StringEscapeUtils.escapeSql(entity.getName()) + "%')";
//        }

        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM GoodsBrandEntity" + where,
                "FROM GoodsBrandEntity" + where + order, page, size);
    }

    @Override
    public ReturnMessage<GoodsBrandEntity> getBrandByName(GoodsType type, String name) {
        try {
            String where = " WHERE del=false";
            if (name != null && type != null && type != GoodsType.ALL) {
                where += " AND LOWER(name)=LOWER('" + name + "') AND type=" + type.ordinal();
            }
            GoodsBrandEntity brandEntity = JpaUtils.querySingleResult(em, "FROM GoodsBrandEntity" + where);
            return ReturnMessage.success(brandEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ReturnMessage.failed();
        }
    }

    @Override
    public ReturnMessage<List<GoodsBrandEntity>> getBrandListByCategoryId(GoodsType type, List<Integer> idList, Integer categoryId) {
        String where = " WHERE del=false";
        String order = " ORDER BY id ASC";
        String jpql = "FROM GoodsBrandEntity where id in (select goods.brand.id from GoodsEntity goods where goods.category.id in (:idList))";

        em.clear();
        Query query = em.createQuery(jpql + where + order);
        query.setParameter("idList", idList);
        query.setHint(Constants.JPA_CACHEABLE,true);
        List<GoodsBrandEntity> resultList = query.getResultList();

        if (resultList != null && resultList.size() > 0) {
            return ReturnMessage.message(resultList.size(), String.valueOf(resultList.size()), resultList);
        }
        return ReturnMessage.failed();
    }

    public Integer getbrandIDsave(GoodsBrandEntity brandEntity)
    {
        Integer returned=1;
        Date date = new Date();
        String where=" where del=false and saled=true";
        String and=" and id="+brandEntity.getId();
        brandEntity.setLast(date);
        brandEntity.setTime(date);
        brandEntity.setNameEn(" ");
        brandEntity.setNameCn(brandEntity.getName());

        List<GoodsBrandEntity> brandEntityListgetbyid =  JpaUtils.queryShortResultList(em, "FROM GoodsBrandEntity"+where+and);
        GoodsBrandEntity merge = em.merge(brandEntity);
        if (merge.equals(brandEntityListgetbyid))
        {
            returned=1;
        }else
            {
                returned=2;
            }
        List<GoodsBrandEntity> brandEntityList =  JpaUtils.queryShortResultList(em, "FROM GoodsBrandEntity"+where+and);
        return returned;
    }

    public Integer getBrandincreased(GoodsBrandEntity brandEntity)
    {
        Integer returned=1;
        Date date = new Date();
        brandEntity.setLast(date);
        brandEntity.setTime(date);
        brandEntity.setNameEn(" ");
        brandEntity.setNameCn(brandEntity.getName());
        em.persist(brandEntity);
         Integer id = em.find(brandEntity.getClass(), brandEntity.getId()).getId();
      if (id==0)
      {

          returned=1;
      }else
          { returned=2;

          }
      return returned;
    }

    public Integer getBranddelete(Integer id)
    {
        String where = " WHERE del=false and id="+id;
        List<GoodsBrandEntity> brandEntityList =  JpaUtils.queryShortResultList(em, "FROM GoodsBrandEntity"+where);
        Integer returned=1;
        GoodsBrandEntity goodsBrandEntity=new GoodsBrandEntity();
        for (GoodsBrandEntity b:brandEntityList)
        {
            b.setDel(true);
            goodsBrandEntity=em.merge(b);

        }
        for (GoodsBrandEntity b:brandEntityList)
        {
            if (b.equals(goodsBrandEntity))
            {
                returned=1;
            }else
                {
                    returned=2;
                }
        }
        return returned;
    }

    public ReturnMessage<List<GoodsBrandEntity>> querybrandidList(Integer brandId,String brandName,Integer page, Integer rows)
    {
        String where=" WHERE del=false and saled=true ";
        String order = " ORDER BY id ASC";
        if (brandId != null && brandId > 0){ where+=" AND id="+brandId;}
        if (!brandName.equals("")){ where+=" AND name like LOWER('%" + brandName + "%')";}
        return  JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM GoodsBrandEntity" + where,
                "FROM GoodsBrandEntity" + where + order, page, rows);
    }

    @Override
    public ReturnMessage<List<GoodsBrandEntity>> getShortBrandList(GoodsType type) {
        try {
            String where = " WHERE del=false and saled=true";
            String order = " order by name";
            if (type != null && type != GoodsType.ALL) {
                where += " AND type=" + type.ordinal();
            }
      List<GoodsBrandEntity> brandEntityList = JpaUtils.queryShortResultList(em, "FROM GoodsBrandEntity" + where+order);
            return ReturnMessage.success(brandEntityList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ReturnMessage.failed();
        }
    }

}
