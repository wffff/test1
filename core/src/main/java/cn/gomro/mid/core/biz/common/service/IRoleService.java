package cn.gomro.mid.core.biz.common.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.common.entity.RoleEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by admin on 2016/12/26.
 */

public interface IRoleService extends IService<RoleEntity> {
    ReturnMessage<List<RoleEntity>> queryAll();
}
