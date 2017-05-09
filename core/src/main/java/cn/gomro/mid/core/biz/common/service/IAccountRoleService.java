package cn.gomro.mid.core.biz.common.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.common.entity.AccountEntity;
import cn.gomro.mid.core.biz.common.entity.AccountRoleEntity;

import java.util.List;

/**
 * Created by adam on 2016/12/27.
 */
public interface IAccountRoleService extends IService<AccountRoleEntity> {

    int addList(List<AccountRoleEntity> list);

    List<AccountRoleEntity> query(Integer userId);

    int delItemByAccount(AccountEntity account);
}
