package cn.gomro.mid.core.biz.order.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.order.entity.MemberOrderProductEntity;
import cn.gomro.mid.core.biz.order.service.IMemberOrderProductService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by adam on 2017/4/26.
 */
@Stateless
public class MemberOrderProductService extends AbstractSessionService<MemberOrderProductEntity> implements IMemberOrderProductService {
    //final Logger logger = LoggerFactory.getLogger(MemberOrderProductService.class);
    @Override
    public ReturnMessage getName(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<MemberOrderProductEntity>> getItemsPaged(MemberOrderProductEntity entity, Integer page, Integer size) {
        return null;
    }
}
