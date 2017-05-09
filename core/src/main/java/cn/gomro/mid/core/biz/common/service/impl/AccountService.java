package cn.gomro.mid.core.biz.common.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.common.entity.AccessTokenEntity;
import cn.gomro.mid.core.biz.common.entity.AccountEntity;
import cn.gomro.mid.core.biz.common.service.IAccountService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import cn.gomro.mid.core.common.utils.MD5Utils;
import cn.gomro.mid.core.common.utils.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by yaoo on 2016/10/25.
 */
@Stateless
@Remote(IAccountService.class)
public class AccountService extends AbstractSessionService<AccountEntity> implements IAccountService {

    final Logger logger = Logger.getLogger(AccountService.class);

    public AccountService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        if (id != null) {
            String jpql = "select u.username FROM AccountEntity u WHERE u.id=:id";
            Query query = em.createQuery(jpql);
            query.setParameter("id", id);
            Object singleResult = query.getSingleResult();
            return ReturnMessage.success(singleResult);
        }
        return ReturnMessage.failed();
    }


    @Override
    public ReturnMessage<List<AccountEntity>> getItemsPaged(AccountEntity entity, Integer page, Integer size) {
        String where = " WHERE del=false";
        String order = " ORDER BY id DESC";

        if (null != entity && StringUtils.isNotBlank(entity.getFullname())) {
            where += " AND fullname like '%" + entity.getFullname() + "'%";
        }

        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM AccountEntity" + where,
                "FROM AccountEntity" + where + order, page, size);
    }


    @Override
    public ReturnMessage<Boolean> isRepeatName(String username) {
        String jpql = "select count(u.id) FROM AccountEntity u WHERE u.username=:username";
        Query query = em.createQuery(jpql);
        query.setParameter("username", username);
        Long singleResult = (Long) query.getSingleResult();
        return ReturnMessage.success(singleResult > 0);
    }

    @Override
    public ReturnMessage<AccessTokenEntity> getToken(String username, String password) {

        String queryPwdSql = "select u from AccountEntity u WHERE u.del=false AND u.username=:username AND u.password=:password";
        Query query = em.createQuery(queryPwdSql, AccountEntity.class);
        query.setParameter("username", username);
        query.setParameter("password", MD5Utils.encryption(password));
        AccountEntity loginUser = null;
        try {
            loginUser = (AccountEntity) query.getSingleResult();

            //如果登录成功
            if (loginUser != null) {
                //获取帐户信息
                String queryAccountSql = "select a from AccountEntity a where a.username=:username";
                query = em.createQuery(queryAccountSql, AccountEntity.class);
                query.setParameter("username", username);
                AccountEntity accountEntity = (AccountEntity) query.getSingleResult();

                Date expiresDate = new Date(System.currentTimeMillis() + (30 * 60 * 1000));
                Date date = new Date();

                //更新token
                String queryTokenSql = "select a from AccessTokenEntity a where a.del=false and a.account.id=:accountId";
                query = em.createQuery(queryTokenSql, AccessTokenEntity.class);
                query.setParameter("accountId", accountEntity.getId());
                List<AccessTokenEntity> accessTokenList = query.getResultList();

                AccessTokenEntity accessTokenEntity = null;
                String token = TokenUtils.createToken(accountEntity.getId()+"");
                if (accessTokenList != null && accessTokenList.size() > 0) {
                    //更新过期时间和修改时间
                    accessTokenEntity = accessTokenList.get(0);
                    accessTokenEntity.setExpires(expiresDate);
                    accessTokenEntity.setLast(date);
                    accessTokenEntity.setToken(token);
                } else {
                    //如果没有token id,则新建
                    accessTokenEntity = new AccessTokenEntity(accountEntity, token, expiresDate, false, date, date);
                }
                em.merge(accessTokenEntity);
                em.flush();
                //返回Token信息

                return ReturnMessage.success(accessTokenEntity);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<AccessTokenEntity> getAccountByToken(String token) {
        //如果token存在 且未过期 更新token
        Date date = new Date();
        String queryTokenSql = "select a from AccessTokenEntity a where a.del=false and a.token=:token and a.expires<:date";
        Query query = em.createQuery(queryTokenSql, AccessTokenEntity.class);
        query.setParameter("token", token);
        query.setParameter("date", date);
        AccessTokenEntity accessTokenEntity = (AccessTokenEntity) query.getSingleResult();
        if (accessTokenEntity != null) {
            /**
             * 如果过期时间在30分钟之内，则直接延长token
             */
            boolean extendExpire = accessTokenEntity.getExpires().getTime() < (System.currentTimeMillis() + (30 * 60 * 1000));
            if (extendExpire){
                Date expiresDate = new Date(System.currentTimeMillis() + (30 * 60 * 1000));
                accessTokenEntity.setExpires(expiresDate);
            }
            accessTokenEntity.setLast(date);
            em.merge(accessTokenEntity);
            em.flush();
            return ReturnMessage.success(accessTokenEntity);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage removeAccessToken(Integer accountId) {
        String queryTokenSql = "select a from AccessTokenEntity a where a.del=false and a.account.id=:accountId";
        Query query = em.createQuery(queryTokenSql, AccessTokenEntity.class);
        query.setParameter("accountId", accountId);
        List<AccessTokenEntity> list = query.getResultList();
        for (AccessTokenEntity a : list) {
            em.remove(a);
            em.flush();
        }
        return ReturnMessage.success();
    }
}
