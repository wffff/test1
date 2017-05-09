package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yaodw on 2016/7/15.
 */
@Entity
@Table(name = "t_vendor_user")
public class MemberEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "vid")
    private CorporationEntity corporation;

    public MemberEntity() {
    }

    public MemberEntity(Boolean del, Date last, Date time, CorporationEntity corporation) {
        super(del, last, time);
        this.corporation = corporation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CorporationEntity getCorporation() {
        return corporation;
    }

    public void setCorporation(CorporationEntity corporation) {
        this.corporation = corporation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
