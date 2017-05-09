package cn.gomro.mid.core.biz.order.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.order.entity.MemberOrderEntity;
import cn.gomro.mid.core.biz.order.service.IMemberOrderService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by adam on 2017/4/26.
 */
@Stateless
public class MemberOrderService extends AbstractSessionService<MemberOrderEntity> implements IMemberOrderService {
    @Override
    public ReturnMessage getName(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<MemberOrderEntity>> getItemsPaged(MemberOrderEntity entity, Integer page, Integer size) {
        return null;
    }
}
