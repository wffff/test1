package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.goods.entity.CorporationEntity;
import cn.gomro.mid.core.biz.goods.service.ICorporationService;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
@Stateless
public class CorporationService extends AbstractSessionService<CorporationEntity> implements ICorporationService {

    public CorporationService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        ReturnMessage<CorporationEntity> ret = this.getItem(id);
        if (ReturnCode.isSuccess(ret.getCode()) && ret.getData()!=null) return ReturnMessage.success(ret.getData().getId()+"",ret.getData().getName());
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<CorporationEntity>> getItemsPaged(CorporationEntity entity, Integer page, Integer size) {
        String where = " WHERE del=false";
        String order = " ORDER BY id DESC";

        if (null != entity && StringUtils.isNotBlank(entity.getName())) {
            where += " AND name='" + entity.getName() + "'";
        }
        if (null != entity && entity.getAct() != null) {
            where += " AND act=" + entity.getAct();
        }

        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM CorporationEntity" + where,
                "FROM CorporationEntity" + where + order, page, size);
    }

}
