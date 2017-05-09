package cn.gomro.mid.core.biz.inquiry.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;
import cn.gomro.mid.core.biz.base.AbstractSessionBiz;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 烦 on 2017/4/13.
 */
@Entity
@Cacheable
@Table(name = "t_oauther_user_2")
public class OautherUser extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private  String username;
    private  String password;
    private  Integer status;
    private  String fullname;

    public OautherUser(){}

    public OautherUser(Boolean del, Date last, Date time, Integer id, String username, String password, Integer status, String fullname)
    {
     super(del,last,time);
        this.id=id;
        this.username=username;
        this.password=password;
        this.status=status;
        this.fullname=fullname;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }
}
