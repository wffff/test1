package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.goods.entity.MemberInventoryEntity;
import cn.gomro.mid.core.biz.goods.service.IMemberInventoryService;
import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import cn.gomro.mid.core.common.utils.Utils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
@Stateless
public class MemberInventoryService extends AbstractSessionService<MemberInventoryEntity> implements IMemberInventoryService {

    public MemberInventoryService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);
    }

    @Override
    public ReturnMessage<List<MemberInventoryEntity>> getItemsPaged(MemberInventoryEntity entity, Integer page, Integer size) {
        return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);
    }

    @Override
    public ReturnMessage<List<MemberInventoryEntity>> queryMemberInventoryList(Integer spec, Integer memberId, String warehouseName,
                                                                               Integer page, Integer size) {

        String where = " WHERE del=false";
        String order = " ORDER BY id DESC";

        if (memberId != null && memberId > 0) where += " and warehouse.member.id = " + memberId;
        if (spec != null && spec > 0) where += " and spec = " + spec;
        if (StringUtils.isNotBlank(warehouseName))
            where += " and LOWER(warehouse.name) like LOWER('%" + warehouseName + "%')";

        return JpaUtils.queryPaged(em,
                "select count(id) from MemberInventoryEntity" + where,
                "from MemberInventoryEntity" + where + order, page, size);
    }

    @Override
    public ReturnMessage<MemberInventoryEntity> querySingleMemberInventory(Integer spec, Integer memberId, String warehouseName) {

        if (spec != null && spec > 0 && memberId != null && memberId > 0) {
            if (!Utils.isNotBlankTrimed(warehouseName)) {
                warehouseName = Constants.DEFAULT_WAREHOUSENAME;
            }
            String jpql = "FROM MemberInventoryEntity mie WHERE mie.del=false AND mie.warehouse.member.id="
                    + memberId + " AND mie.spec=" + spec + " AND LOWER(mie.warehouse.name)=LOWER('" + StringEscapeUtils.escapeSql(warehouseName) + "')";
            MemberInventoryEntity memberInventory = JpaUtils.querySingleResult(em, jpql);
            if (memberInventory != null)
                return ReturnMessage.success(memberInventory);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<MemberInventoryEntity> querySingleMemberInventoryByIds(Integer specId, Integer memberId, Integer warehouseId) {
        if (specId != null && specId > 0 && memberId != null && memberId > 0 && warehouseId!=null && warehouseId>0) {
            String jpql = "FROM MemberInventoryEntity mie WHERE mie.del=false AND mie.warehouse.member.id="
                    + memberId + " AND mie.spec=" + specId + " AND mie.warehouse.id="+warehouseId;
            MemberInventoryEntity memberInventory = JpaUtils.querySingleResult(em, jpql);
            if (memberInventory != null)
                return ReturnMessage.success(memberInventory);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<Integer> queryMemberInventoryAmount(Integer member, Integer spec) {

        String where = " WHERE del=false";

        if (member != null && spec != null) {

            where += " AND member=" + member + " AND spec=" + spec;

            //如果检索到商品库存为null的，则直接返回总库存为null
            int i = JpaUtils.queryCount(em, "SELECT COUNT(id) FROM MemberInventoryEntity" + where + " AND num is null");
            if (i > 0) {
                return ReturnMessage.success(null);
            }
            String jpql = "SELECT SUM(num) AS num FROM MemberInventoryEntity" + where;
            //如果检索到商品有库存，则返回总计数
            Query query = em.createQuery(jpql);
            Object singleResult = query.getSingleResult();
            int result = new Long((long) singleResult).intValue();
            return ReturnMessage.success(result);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage delMemberInventoryList(Integer specId, Integer memberId) {
        try {
            Query query = em.createQuery("FROM MemberInventoryEntity WHERE member_id=" + memberId + " AND spec_id=" + specId);
            List<MemberInventoryEntity> resultList = query.getResultList();
            Date date = new Date();
            for(MemberInventoryEntity mie:resultList){
                mie.setDel(true);
                mie.setLast(date);
                em.merge(mie);
                em.flush();
            }
            return ReturnMessage.success(ReturnCode.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnMessage.failed(ReturnCode.DELETE_FAILED);
    }
}
