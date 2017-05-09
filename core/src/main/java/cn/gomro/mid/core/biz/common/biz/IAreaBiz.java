package cn.gomro.mid.core.biz.common.biz;

import cn.gomro.mid.core.biz.common.entity.AreaEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.io.Serializable;

/**
 * Created by momo on 2016/8/3.
 */
public interface IAreaBiz extends Serializable {

    ReturnMessage<AreaEntity> getAreaItem(Integer id);
}
