package cn.gomro.mid.core.biz.common.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.common.entity.MenuEntity;
import cn.gomro.mid.core.biz.common.service.IMenuService;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by yaoo on 2016/10/25.
 */
@Stateless
public class MenuService extends AbstractSessionService<MenuEntity> implements IMenuService {

    public MenuService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        ReturnMessage<MenuEntity> item = this.getItem(id);
        if (item.getCode() >= 0) {
            return ReturnMessage.success(item.getData().getName());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<MenuEntity>> getItemsPaged(MenuEntity entity, Integer page, Integer size) {
        return ReturnMessage.failed();
    }

    @Override
    public List<MenuEntity> queryMainMenu(Integer userId, Integer parentId, Integer status) {
        String jpql = "FROM MenuEntity m";
        String where = " where m.del=false";
        if (status != null) where += " AND m.status=" + status;
        if (userId != null) where += " AND m.id in (" +
                " select rm.menu.id from RoleMenuEntity rm where rm.del=false and rm.role.id in (" +
                " select ar.role.id from AccountRoleEntity ar where ar.del=false and ar.account.id=" + userId + "))";
        if (parentId != null) where += " and parentId=" + parentId + " order by m.sort";
        Query query = em.createQuery(jpql + where);
        query.setHint(Constants.JPA_CACHEABLE, true);
        List<MenuEntity> menuEntityList = query.getResultList();
        return menuEntityList;
    }

    @Override
    public Boolean updateMenuSort(Integer id, Boolean isDown) {
        MenuEntity menuEntity = this.getItem(id).getData();
        if (isDown) {
            menuEntity.setSort(menuEntity.getSort() + 1);
        } else {
            menuEntity.setSort(menuEntity.getSort() - 1);
        }
        return this.editItem(menuEntity).getCode() >= 0;
    }

    /**
     * 获取角色已分配菜单
     */
    @Override
    public List<MenuEntity> queryMenuY(Integer parentId, Integer roleId, Integer status) {
        String jpql = "FROM MenuEntity m";
        String where = " where m.del=false";
        if (status != null) where += " AND m.status=" + status;
        if (roleId != null) where += " AND m.id in (" +
                " select rm.menu.id from RoleMenuEntity rm where rm.del=false and rm.role.id = " + roleId + ")";
        if (parentId != null) where += " AND parentId=" + parentId + " order by m.sort";
        Query query = em.createQuery(jpql + where);
        query.setHint(Constants.JPA_CACHEABLE, true);
        List<MenuEntity> menuEntityList = query.getResultList();
        return menuEntityList;
    }

    /**
     * 获取角色未分配菜单
     */
    @Override
    public List<MenuEntity> queryMenuN(Integer parentId, Integer roleId, Integer status) {
        String jpql = "FROM MenuEntity m";
        String where = " where m.del=false";
        if (status != null) where += " AND m.status=" + status;
        where += " AND m.id in (" +
                    " select rm.menu.id from RoleMenuEntity rm where rm.del=false and rm.role.id <> " + roleId + " and rm.menu.parentId="+parentId+")" +
                    " OR m.id not in ( select rm.menu.id from RoleMenuEntity rm where rm.del=false )";
        //if (parentId != null) where += " AND parentId=" + parentId;
        Query query = em.createQuery(jpql + where);
        query.setHint(Constants.JPA_CACHEABLE, true);
        List<MenuEntity> menuEntityList = query.getResultList();
        return menuEntityList;
    }

    @Override
    public List<MenuEntity> queryMenuByParentId(int parentId) {
        String jpql = "from MenuEntity m where m.parentId=" + parentId;
        Query query = em.createQuery(jpql);
        query.setHint(Constants.JPA_CACHEABLE, true);
        List<MenuEntity> resultList = query.getResultList();
        return resultList;
    }

}
