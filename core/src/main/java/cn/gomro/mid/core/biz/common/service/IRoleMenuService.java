package cn.gomro.mid.core.biz.common.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.common.entity.RoleMenuEntity;

import java.util.List;

/**
 * Created by adam on 2016/12/28.
 */
public interface IRoleMenuService extends IService<RoleMenuEntity> {

    int addList(List<RoleMenuEntity> list);

    int delete(String[] menuIds,Integer roleId);
}
