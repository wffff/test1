package cn.gomro.mid.core.biz.order.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.order.entity.CompanyOrgEntity;
import cn.gomro.mid.core.biz.order.service.ICompanyOrgService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by adam on 2017/4/26.
 */
@Stateless
public class CompanyOrgService extends AbstractSessionService<CompanyOrgEntity> implements ICompanyOrgService {

    @Override
    public ReturnMessage getName(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<CompanyOrgEntity>> getItemsPaged(CompanyOrgEntity entity, Integer page, Integer size) {
        return null;
    }
}
