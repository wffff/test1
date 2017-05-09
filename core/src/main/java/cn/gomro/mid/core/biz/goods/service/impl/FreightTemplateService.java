package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.goods.entity.FreightTemplateEntity;
import cn.gomro.mid.core.biz.goods.service.IFreightTemplateService;
import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import cn.gomro.mid.core.common.utils.Utils;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by momo on 2016/8/1.
 */
@Stateless
public class FreightTemplateService extends AbstractSessionService<FreightTemplateEntity> implements IFreightTemplateService {

    public FreightTemplateService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {

        id = Utils.reqInt(id, 0);
        if (id <= 0) return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);

        try {
            FreightTemplateEntity entity = em.find(FreightTemplateEntity.class, id);
            if (entity == null) return ReturnMessage.success(entity.getName(),entity.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<FreightTemplateEntity>> getMemberFreightTemplateList(Integer memberId) {

        memberId = Utils.reqInt(memberId, 0);
        if (memberId <= 0) return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);

        String where = " WHERE del=false AND member.id=" + memberId;
        String order = " ORDER BY id ASC";

        try {
            List<FreightTemplateEntity> rs = JpaUtils.queryShortResultList(em, "FROM FreightTemplateEntity" + where + order);
            if (rs != null) return ReturnMessage.message(rs.size(), String.valueOf(rs.size()), rs);
        } catch (Exception e) {
        }

        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<FreightTemplateEntity> getFreightTemplateByMemberIdAndName(Integer memberId, String name) {

        memberId = Utils.reqInt(memberId, 0);
        if (memberId <= 0) return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);

        String where = " WHERE e.del=false AND e.member.id=" + memberId +
                " AND e.mid = 0" +
                " AND e.name='" + name + "'";

        try {
            FreightTemplateEntity result = JpaUtils.querySingleResult(em, "FROM FreightTemplateEntity e" + where);
            if (result != null) return ReturnMessage.success(result);
        } catch (Exception e) {
        }

        return ReturnMessage.failed();
    }


    @Override
    public ReturnMessage<List<FreightTemplateEntity>> getItemsPaged(FreightTemplateEntity entity, Integer page, Integer size) {
        return null;
    }
}
