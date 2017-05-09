package cn.gomro.mid.core.biz.common.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.common.entity.AreaEntity;
import cn.gomro.mid.core.biz.common.service.IAreaService;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by momo on 2016/8/3.
 */
@Stateless
public class AreaService extends AbstractSessionService<AreaEntity> implements IAreaService {

    public AreaService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        ReturnMessage<AreaEntity> ret = this.getItem(id);
        if (ReturnCode.isSuccess(ret.getCode())) return ReturnMessage.success(ret.getData().getName(),ret.getData().getName());
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<AreaEntity>> getItemsPaged(AreaEntity entity, Integer page, Integer size) {
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<AreaEntity>> getChilds(Integer id) {
        List<AreaEntity> rs = this.getItem(id).getData().getChilds();
        return ReturnMessage.message(rs.size(), String.valueOf(rs.size()), rs);
    }
}
