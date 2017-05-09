package cn.gomro.mid.core.biz.order.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.order.entity.CreditExpenseEntity;
import cn.gomro.mid.core.biz.order.service.ICreditExpenseService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by adam on 2017/4/26.
 */
@Stateless
public class CreditExpenceService extends AbstractSessionService<CreditExpenseEntity> implements ICreditExpenseService {
    @Override
    public ReturnMessage getName(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<CreditExpenseEntity>> getItemsPaged(CreditExpenseEntity entity, Integer page, Integer size) {
        return null;
    }
}
