package cn.gomro.mid.core.biz.common.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yaoo on 2016/10/25.
 */
@Entity
@Table(name = "t_access_token")
public class AccessTokenEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.REFRESH)
    private AccountEntity account;
    private String token;
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;

    public AccessTokenEntity() {
    }

    public AccessTokenEntity(AccountEntity account, String token, Date expires,boolean del,Date last,Date time) {
        this.account = account;
        this.token = token;
        this.expires = expires;
        this.del=del;
        this.last=last;
        this.time=time;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
