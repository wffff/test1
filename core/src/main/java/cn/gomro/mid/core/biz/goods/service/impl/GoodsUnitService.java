package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.goods.entity.GoodsUnitEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsUnitService;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import cn.gomro.mid.core.common.utils.Utils;
import org.apache.commons.lang.StringEscapeUtils;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
@Stateless
public class GoodsUnitService extends AbstractSessionService<GoodsUnitEntity> implements IGoodsUnitService {

    final Logger logger =Logger.getLogger(GoodsBrandService.class);
    public GoodsUnitService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        ReturnMessage<GoodsUnitEntity> ret = this.getItem(id);
        if (ReturnCode.isSuccess(ret.getCode()) && ret.getData()!=null) return ReturnMessage.success(ret.getData().getName(),ret.getData().getName());
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<GoodsUnitEntity>> getItemsPaged(GoodsUnitEntity entity, Integer page, Integer size) {

        String where = " WHERE del=false and type!=0 ";
        String order = " ORDER BY id ASC";

//
//        if (entity != null) {
//            if (entity.getType() != GoodsType.ALL) where += " AND type=" + entity.getType().ordinal();
//            if (Utils.isNotBlankTrimed(entity.getName())) where += " AND LOWER(name) like LOWER('%" + entity.getName() + "%')";
//        }

        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM GoodsUnitEntity" + where,
                "FROM GoodsUnitEntity" + where + order, page, size);
    }

    @Override
    public ReturnMessage<List<GoodsUnitEntity>> getShortunitList(GoodsType type) {
        try {
            String where = " WHERE del=false ";
            String order = " order by name";
            if (type != null && type != GoodsType.ALL) {
                where += " AND type=" + type.ordinal();
            }
            List<GoodsUnitEntity> unitEntityList = JpaUtils.queryShortResultList(em, "FROM GoodsUnitEntity" + where+order);
            return ReturnMessage.success(unitEntityList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ReturnMessage.failed();
        }
    }


    @Override
    public ReturnMessage<GoodsUnitEntity> queryOrCreateUnitByName(GoodsType type, String name) {

        String where = " WHERE del=false";
        if (Utils.isNotBlankTrimed(name)) where += " AND name = '" + StringEscapeUtils.escapeSql(name) + "'";
        if (type != GoodsType.ALL) where += " AND type=" + type.ordinal();

        GoodsUnitEntity entity = JpaUtils.querySingleResult(em, "FROM GoodsUnitEntity" + where);
        if (entity == null) {
            entity = new GoodsUnitEntity(type, name, false, new Date(), new Date());
            em.persist(entity);
        }

        return entity == null ? ReturnMessage.failed() : ReturnMessage.success(entity);
    }

    public Integer getbrandIDsave(GoodsUnitEntity entity)
    {
        String order = " ORDER BY id ASC";
        Integer returned=1;
        Date date = new Date();

        String where=" where del=false and type!=0 ";
        String and=" and id="+entity.getId();
        entity.setLast(date);
        entity.setTime(date);

        List<GoodsUnitEntity> brandEntityListgetbyid =  JpaUtils.queryShortResultList(em, "FROM GoodsUnitEntity"+where+and+order);
        GoodsUnitEntity merge = em.merge(entity);
        if (merge.equals(brandEntityListgetbyid))
        {
            returned=1;
        }else
        {
            returned=2;
        }
        List<GoodsUnitEntity> brandEntityList =  JpaUtils.queryShortResultList(em, "FROM GoodsUnitEntity"+where+and+order);

        return returned;
    }

    public ReturnMessage<List<GoodsUnitEntity>>  queryunitidList(Integer unitId,String unitName,Integer page,Integer rows)
    {
        logger.info(unitId+"  "+unitName+"  "+page+"  "+rows);
        String where = " WHERE del=false and type!=0 ";
        String order = " ORDER BY id ASC";
        if (unitId != null && unitId > 0){ where+=" AND id="+unitId;}
        if (!unitName.equals("")){ where+=" AND name like LOWER('%" + unitName + "%')";}
        return  JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM GoodsUnitEntity" + where,
                "FROM GoodsUnitEntity" + where + order, page, rows);
    }


    public Integer getBrandincreased(GoodsUnitEntity entity)
    {

        Integer returned=1;
        Date date = new Date();
        entity.setLast(date);
        entity.setTime(date);

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

    public Integer getBranddelete(Integer id)
    {

        String where = " WHERE del=false and type!=0 and  id="+id;
        String order = " ORDER BY id ASC";
        List<GoodsUnitEntity> brandEntityList =  JpaUtils.queryShortResultList(em, "FROM GoodsUnitEntity"+where+order);
        Integer returned=1;
        GoodsUnitEntity goodsBrandEntity=new GoodsUnitEntity();
        for (GoodsUnitEntity b:brandEntityList)
        {
            b.setDel(true);
            System.out.println(b);
            goodsBrandEntity=em.merge(b);

        }
        for (GoodsUnitEntity b:brandEntityList)
        {
            if (b.equals(goodsBrandEntity))
            {
                returned=2;
            }else
            {
                returned=1;
            }
        }
        System.out.println(returned);
        return null;
    }


}
