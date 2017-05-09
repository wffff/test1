package cn.gomro.mid.core.biz.order.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.order.entity.MemberInvoicesEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by adam on 2017/4/26.
 */
public interface IMemberInvoiceService extends IService<MemberInvoicesEntity> {
    ReturnMessage<List<MemberInvoicesEntity>> listExistInvoice(MemberInvoicesEntity entity);
}
