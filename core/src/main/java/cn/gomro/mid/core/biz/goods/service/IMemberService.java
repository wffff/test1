package cn.gomro.mid.core.biz.goods.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.goods.entity.MemberEntity;
import cn.gomro.mid.core.biz.inquiry.entity.TempUserEntity;
import cn.gomro.mid.core.biz.order.entity.MemberInvoicesEntity;
import cn.gomro.mid.core.biz.order.entity.MemberOrderEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
public interface IMemberService extends IService<MemberEntity> {
    ReturnMessage<TempUserEntity> getTempMember(Integer id);
    ReturnMessage<List<MemberOrderEntity>> listExistOrder(Integer openSourceId);
}
