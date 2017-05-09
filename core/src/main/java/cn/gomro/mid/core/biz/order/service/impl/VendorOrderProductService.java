package cn.gomro.mid.core.biz.order.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.order.entity.VendorOrderProductEntity;
import cn.gomro.mid.core.biz.order.service.IVendorOrderProductService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by adam on 2017/4/26.
 */
@Stateless
public class VendorOrderProductService extends AbstractSessionService<VendorOrderProductEntity> implements IVendorOrderProductService {
    @Override
    public ReturnMessage getName(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<VendorOrderProductEntity>> getItemsPaged(VendorOrderProductEntity entity, Integer page, Integer size) {
        return null;
    }
}
