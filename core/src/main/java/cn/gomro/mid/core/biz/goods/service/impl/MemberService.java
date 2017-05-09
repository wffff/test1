package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.goods.entity.MemberEntity;
import cn.gomro.mid.core.biz.goods.service.IMemberService;
import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.inquiry.entity.TempUserEntity;
import cn.gomro.mid.core.biz.order.entity.MemberInvoicesEntity;
import cn.gomro.mid.core.biz.order.entity.MemberOrderEntity;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
@Stateless
public class MemberService extends AbstractSessionService<MemberEntity> implements IMemberService {

    public MemberService() {
    }


    @Override
    public ReturnMessage<TempUserEntity> getTempMember(Integer id) {
        Query query = em.createQuery("from TempUserEntity where id="+id);
        List<TempUserEntity> rs = query.setMaxResults(2).getResultList();
        if (rs != null & rs.size() == 1) {
            return ReturnMessage.success(rs.get(0));
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<MemberOrderEntity>> listExistOrder(Integer openOrderId) {
        String jpql = "select i from MemberOrderEntity i where i.sourceid=" + openOrderId ;
        Query query = em.createQuery(jpql,MemberOrderEntity.class);
        List<MemberOrderEntity> resultList = query.getResultList();
        return ReturnMessage.success(resultList);
    }

    @Override
    public ReturnMessage getName(Integer id) {
        ReturnMessage<MemberEntity> ret = this.getItem(id);
        if (ReturnCode.isSuccess(ret.getCode()) && ret.getData()!=null) return ReturnMessage.success(ret.getData().getId()+"",ret.getData().getCorporation().getName());
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<MemberEntity>> getItemsPaged(MemberEntity entity, Integer page, Integer size) {
        String where = " WHERE del=false";
        String order = " ORDER BY id DESC";

        if (null != entity && StringUtils.isNotBlank(entity.getCorporation().getName())) {
            where += " AND name='" + entity.getCorporation().getName() + "'";
        }
        if (null != entity && entity.getCorporation().getAct() != null) {
            where += " AND act=" + entity.getCorporation().getAct();
        }

        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM MemberEntity" + where,
                "FROM MemberEntity" + where + order, page, size);
    }

}
