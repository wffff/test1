package cn.gomro.mid.core.biz.common.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.common.entity.AreaEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by momo on 2016/8/3.
 */
public interface IAreaService extends IService<AreaEntity> {

    ReturnMessage<List<AreaEntity>> getChilds(Integer id);
}
