package cn.gomro.mid.core.biz.common.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.common.entity.AccountEntity;
import cn.gomro.mid.core.biz.common.entity.AccountRoleEntity;
import cn.gomro.mid.core.biz.common.service.IAccountRoleService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by adam on 2016/12/28.
 */
@Stateless
@Remote(IAccountRoleService.class)
public class AccountRoleService extends AbstractSessionService<AccountRoleEntity> implements IAccountRoleService{
    @Override
    public ReturnMessage getName(Integer id) {
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<AccountRoleEntity>> getItemsPaged(AccountRoleEntity entity, Integer page, Integer size) {
        return ReturnMessage.failed();
    }

    @Override
    public int addList(List<AccountRoleEntity> list) {
        for(AccountRoleEntity accountRole:list){
            this.addItem(accountRole);
        }
        return list.size();
    }


    @Override
    public List<AccountRoleEntity> query(Integer userId) {
        String jpql = "from AccountRoleEntity ar";
        String where ="";
        if(userId!=null) where += " where ar.account.id="+userId;
        Query query = em.createQuery(jpql + where);
        return query.getResultList();
    }

    @Override
    public int delItemByAccount(AccountEntity account) {
        String jpql = "from AccountRoleEntity ar where ar.account.id="+account.getId();
        Query query = em.createQuery(jpql);
        List<AccountEntity> resultList = query.getResultList();
        for(AccountEntity accountEntity:resultList){
            em.remove(accountEntity);
            em.flush();
        }
        return resultList.size();
    }
}
