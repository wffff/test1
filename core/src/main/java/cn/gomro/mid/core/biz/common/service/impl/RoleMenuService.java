package cn.gomro.mid.core.biz.common.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.common.entity.RoleMenuEntity;
import cn.gomro.mid.core.biz.common.service.IRoleMenuService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by adam on 2016/12/28.
 */
@Stateless
public class RoleMenuService extends AbstractSessionService<RoleMenuEntity> implements IRoleMenuService {
    @Override
    public ReturnMessage getName(Integer id) {
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<RoleMenuEntity>> getItemsPaged(RoleMenuEntity entity, Integer page, Integer size) {
        return ReturnMessage.failed();
    }

    @Override
    public int addList(List<RoleMenuEntity> list) {
        for (RoleMenuEntity rm : list) {
            this.addItem(rm);
        }
        return list.size();
    }

    @Override
    public int delete(String[] menuIds,Integer roleId) {
        for(String mid:menuIds){
            String jpql = "FROM RoleMenuEntity rm where rm.menu.id="+mid+" AND rm.role.id="+roleId;
            Query query = em.createQuery(jpql);
            List<RoleMenuEntity> resultList = query.getResultList();
            for (RoleMenuEntity rm:resultList){
                this.delItem(rm);
            }
        }
        return menuIds.length;
    }
}
