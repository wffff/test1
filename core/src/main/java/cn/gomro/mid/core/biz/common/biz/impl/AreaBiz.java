package cn.gomro.mid.core.biz.common.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.common.biz.IAreaBiz;
import cn.gomro.mid.core.biz.common.entity.AreaEntity;
import cn.gomro.mid.core.biz.common.service.IAreaService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by momo on 2016/8/3.
 */
@Stateless
public class AreaBiz extends AbstractSessionBiz implements IAreaBiz {

    @EJB
    IAreaService areaService;

    public AreaBiz() {
    }

    @Override
    public ReturnMessage<AreaEntity> getAreaItem(Integer id) {
        return areaService.getItem(id);
    }
}
