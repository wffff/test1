package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.goods.entity.SpecInventoryEntity;
import cn.gomro.mid.core.biz.goods.service.IGoodsInventoryService;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/8/19.
 */
@Stateless
public class GoodsInventoryService extends AbstractSessionService<SpecInventoryEntity> implements IGoodsInventoryService {

    public GoodsInventoryService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);
    }

    @Override
    public ReturnMessage<List<SpecInventoryEntity>> getItemsPaged(SpecInventoryEntity entity, Integer page, Integer size) {

        String where = " WHERE del=false";
        String order = " ORDER BY id DESC";

        if (entity != null && entity.getMember() != null) {
            where += " AND member=" + entity.getMember();
        }

        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM SpecInventoryEntity" + where,
                "FROM SpecInventoryEntity" + where + order, page, size);
    }

    @Override
    public ReturnMessage<List<SpecInventoryEntity>> queryGoodsInventoryList(Integer member, Integer page, Integer size) {

        String where = " WHERE del=false";

        if (member != null) {
            where += " AND member=" + member;
            return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM SpecInventoryEntity" + where,
                    "FROM SpecInventoryEntity" + where, page, size);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<SpecInventoryEntity> getGoodsInventoryBySpec(Integer spec) {

        String where = " WHERE del=false";

        if (spec != null) {
            where += " AND spec=" + spec;
            SpecInventoryEntity specInventoryEntity = JpaUtils.querySingleResult(em, "FROM SpecInventoryEntity" + where);
            if(specInventoryEntity!=null){
                return ReturnMessage.success(specInventoryEntity);
            }
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage delGoodsInventoryBySpec(Integer id) {
        try {
            String jpql = "FROM SpecInventoryEntity WHERE del=false AND spec="+id;
            SpecInventoryEntity result = JpaUtils.querySingleResult(em, jpql);
            Date time = new Date();
            if(result!=null){
                result.setDel(true);
                result.setLast(time);
                em.merge(result);
                em.flush();
                return ReturnMessage.success(ReturnCode.DELETE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnMessage.success(ReturnCode.DELETE_FAILED);
    }
}
