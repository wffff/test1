package cn.gomro.mid.core.biz.order.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.order.entity.VendorOrderEntity;
import cn.gomro.mid.core.biz.order.service.IVendorOrderService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by adam on 2017/4/26.
 */
@Stateless
public class VendorOrderService extends AbstractSessionService<VendorOrderEntity> implements IVendorOrderService {
    @Override
    public ReturnMessage getName(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<VendorOrderEntity>> getItemsPaged(VendorOrderEntity entity, Integer page, Integer size) {
        return null;
    }
}
