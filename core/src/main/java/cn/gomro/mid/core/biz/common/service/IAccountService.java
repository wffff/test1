package cn.gomro.mid.core.biz.common.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.common.entity.AccessTokenEntity;
import cn.gomro.mid.core.biz.common.entity.AccountEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

/**
 * Created by yaoo on 2016/10/25.
 */

public interface IAccountService extends IService<AccountEntity> {

    ReturnMessage<Boolean> isRepeatName(String name);

    ReturnMessage<AccessTokenEntity> getToken(String username, String password);

    ReturnMessage<AccessTokenEntity> getAccountByToken(String token);

    ReturnMessage removeAccessToken(Integer accountId);

}
