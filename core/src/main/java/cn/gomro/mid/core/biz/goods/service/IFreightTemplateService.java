package cn.gomro.mid.core.biz.goods.service;

import cn.gomro.mid.core.biz.goods.entity.FreightTemplateEntity;
import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by momo on 2016/8/1.
 */
public interface IFreightTemplateService extends IService<FreightTemplateEntity> {

    ReturnMessage<List<FreightTemplateEntity>> getMemberFreightTemplateList(Integer memberId);

    ReturnMessage<FreightTemplateEntity> getFreightTemplateByMemberIdAndName(Integer memberId, String name);
}
