package cn.gomro.mid.core.biz.common.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.common.entity.RoleEntity;
import cn.gomro.mid.core.biz.common.service.IRoleService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by admin on 2016/12/26.
 */
@Stateless
public class RoleService extends AbstractSessionService<RoleEntity> implements IRoleService {
    @Override
    public ReturnMessage getName(Integer id) {
        RoleEntity roleEntity = this.getItem(id).getData();
        return ReturnMessage.success(roleEntity.getName());
    }

    @Override
    public ReturnMessage<List<RoleEntity>> getItemsPaged(RoleEntity entity, Integer page, Integer size) {
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<RoleEntity>> queryAll() {
        String jpql = "FROM RoleEntity where del=false";
        Query query = em.createQuery(jpql);
        List<RoleEntity> roleEntityList = query.getResultList();
        return ReturnMessage.success(roleEntityList);
    }

}
