package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.goods.entity.CorporationEntity;
import cn.gomro.mid.core.biz.goods.entity.WarehouseEntity;
import cn.gomro.mid.core.biz.goods.service.IWarehouseService;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import cn.gomro.mid.core.common.utils.Utils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
@Stateless
public class WarehouseService extends AbstractSessionService<WarehouseEntity> implements IWarehouseService {

    final Logger logger = LoggerFactory.getLogger(GoodsSpecService.class);

    public WarehouseService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        ReturnMessage<WarehouseEntity> ret = this.getItem(id);
        if (ReturnCode.isSuccess(ret.getCode()) && ret.getData() != null)
            return ReturnMessage.success(ret.getData().getName(), ret.getData().getName());
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<WarehouseEntity>> getItemsPaged(WarehouseEntity entity, Integer page, Integer size) {
        String where = " WHERE del=false";
        String order = " ORDER BY id DESC";
        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM WarehouseEntity" + where,
                "FROM WarehouseEntity" + where + order, page, size);
    }

    @Override
    public ReturnMessage<WarehouseEntity> queryOrCreateWarehouseByMemberAndName(Integer memberId, String name) {

        String where = " WHERE e.del=false AND e.member.id=" + memberId;

        if (!Utils.isNotBlankTrimed(name)) {
            name = Constants.DEFAULT_WAREHOUSENAME;
        }
        where += " AND LOWER(e.name)=LOWER('" + StringEscapeUtils.escapeSql(name) + "')";

        WarehouseEntity entity = JpaUtils.querySingleResult(em, "FROM WarehouseEntity e" + where);
        if (entity == null) {
            Date date = new Date();
            String address = "";
            CorporationEntity corporation = new CorporationEntity();
            corporation.setId(memberId);
            entity = new WarehouseEntity(corporation, name, address, true, false, date, date);
            em.persist(entity);
            em.flush();
        }

        return entity == null ? ReturnMessage.failed() : ReturnMessage.success(entity);
    }

    @Override
    // TODO: 2016/10/25 临时使用，在仓库管理页面设计完成之后则废弃！
    public ReturnMessage updateWarehouseAddressByMemberId(Integer vid, String addr) {
        if(vid!=null || addr!=null) {
            String jpql = "update WarehouseEntity w set w.address=:addr where w.corporation.id=:vid";
            Query query = em.createQuery(jpql);
            query.setParameter("vid", vid);
            query.setParameter("addr", String.valueOf(addr));
            int i = query.executeUpdate();
            if (i > 0) return ReturnMessage.message(ReturnCode.SUCCESS, "成功更新了" + i + "条数据！", i);
        }
        return ReturnMessage.message(ReturnCode.FAILED, "更新失败，请联系管理员！", null);
    }

    public ReturnMessage<WarehouseEntity> getwarehouseById(Integer id) {
        WarehouseEntity warehouseEntity = null;
        try {
            //em.setProperty(Constants.JPA_CACHEABLE, true);
            warehouseEntity = em.find(WarehouseEntity.class, id);
            return ReturnMessage.success(warehouseEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            if(e.getCause()!=null){
                logger.error(e.getCause().getMessage());
            }
        } finally {
            em.clear();
        }
        return ReturnMessage.failed();
    }

    public Integer getwarehousesave(WarehouseEntity warehouseEntity,Integer memberid,boolean enabledboolead)
    {
        Integer returned=1;
        Date date = new Date();
        String where=" where del=false ";
        String and=" and id="+warehouseEntity.getId();
        warehouseEntity.setLast(date);

        CorporationEntity corporationEntity=JpaUtils.querySingleResult(em, "FROM CorporationEntity" + where+" and id="+memberid);
        warehouseEntity.setDel(false);
        warehouseEntity.setCorporation(corporationEntity);
        warehouseEntity.setAct(enabledboolead);
        WarehouseEntity WarehouseEntityListgetbyid =  JpaUtils.querySingleResult(em, "FROM WarehouseEntity"+where+and);
        warehouseEntity.setTime(WarehouseEntityListgetbyid.getLast());
        WarehouseEntity merge = em.merge(warehouseEntity);
        if (merge.equals(WarehouseEntityListgetbyid))
        {
            returned=1;
        }else
        {
            returned=2;
        }
        return returned;
    }

    public Integer addwarehouse(WarehouseEntity warehouseEntity,Integer memberid,String namestr,String addressstr,Boolean enabledboolead)
    {
        Integer returned=1;
        String where=" where del=false ";
        Date date = new Date();
        CorporationEntity corporationEntity=JpaUtils.querySingleResult(em, "FROM CorporationEntity" + where+" and id="+memberid);
        warehouseEntity.setAct(enabledboolead);
        warehouseEntity.setName(namestr);
        warehouseEntity.setDel(false);
        warehouseEntity.setAddress(addressstr);
        warehouseEntity.setCorporation(corporationEntity);
        warehouseEntity.setLast(date);
        warehouseEntity.setTime(date);
        em.persist(warehouseEntity);
        Integer id = em.find(warehouseEntity.getClass(), warehouseEntity.getId()).getId();
        if (id==0)
        {
            returned=1;
        }else
        { returned=2;

        }
        return returned;
    }

    public Integer deletewarehouse(Integer id)
    {

        Integer returned=1;
        String where = " WHERE del=false and id="+id;
        WarehouseEntity warehouseEntityList =  JpaUtils.querySingleResult(em, "FROM WarehouseEntity"+where);
        warehouseEntityList.setDel(true);
        WarehouseEntity merge = em.merge(warehouseEntityList);

        if (merge.getDel()==true)
        {
            returned=2;
        }else
            {
                returned=1;
            }

        return returned;
    }

     public ReturnMessage<List<WarehouseEntity>> querywarehouseByid(Integer warehouseid,String warehousename,String warehousesitr,Integer page, Integer rows)
     {
         String where=" WHERE del=false  ";
         String order = " ORDER BY id ASC";
         if (warehouseid != null && warehouseid > 0){ where+=" AND id="+warehouseid;}
         if (!warehousename.equals("")){ where+=" AND name like LOWER('%" + warehousename + "%')";}
         if (!warehousesitr.equals("")){ where+=" AND address like LOWER('%" + warehousesitr + "%')";}
         return  JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM WarehouseEntity" + where,
                 "FROM WarehouseEntity" + where + order, page, rows);

     }

}
