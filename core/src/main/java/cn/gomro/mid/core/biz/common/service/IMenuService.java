package cn.gomro.mid.core.biz.common.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.common.entity.MenuEntity;

import java.util.List;

/**
 * Created by yaoo on 2016/10/25.
 */
public interface IMenuService extends IService<MenuEntity> {

    List<MenuEntity> queryMainMenu(Integer userId,Integer parentId,Integer status);

    public Boolean updateMenuSort(Integer id,Boolean isDown);

    List<MenuEntity> queryMenuY(Integer parentId,Integer roleId,Integer status);

    List<MenuEntity> queryMenuN(Integer parentId,Integer roleId,Integer status);

    List<MenuEntity> queryMenuByParentId(int parentId);

}
