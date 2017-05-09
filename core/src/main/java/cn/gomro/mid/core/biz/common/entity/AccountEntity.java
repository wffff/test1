package cn.gomro.mid.core.biz.common.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yaoo on 2016/10/25.
 */
@Entity
@Table(name = "t_pm_account")
public class AccountEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private Integer status;

    public AccountEntity() {
    }

    public AccountEntity(String username, String password, String fullname, Integer status) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.status = status;
    }

    public AccountEntity(Boolean del, Date last, Date time, String username, String password, String fullname, Integer status) {
        super(del, last, time);
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
