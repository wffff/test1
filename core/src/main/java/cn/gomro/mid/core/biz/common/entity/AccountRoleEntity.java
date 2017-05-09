package cn.gomro.mid.core.biz.common.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yaoo on 2016/10/25.
 */
@Entity
@Table(name = "t_pm_account_role")
public class AccountRoleEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    private AccountEntity account;
    @OneToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private RoleEntity role;

    public AccountRoleEntity() {
    }

    public AccountRoleEntity(AccountEntity account, RoleEntity role) {
        this.account = account;
        this.role = role;
    }

    public AccountRoleEntity(Boolean del, Date last, Date time, AccountEntity account, RoleEntity role) {
        super(del, last, time);
        this.account = account;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
}
