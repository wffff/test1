package cn.gomro.mid.core.biz.inquiry.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;
import cn.gomro.mid.core.biz.goods.entity.CorporationEntity;

import javax.persistence.*;

/**
 * Created by Adam on 2017/4/17.
 * 因会员未合并，所以该实体是临时实体
 */
@Entity
@Table(name = "t_member")
public class TempUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @ManyToOne(cascade = CascadeType.REFRESH,optional = true)
    @JoinColumn(name = "cid")
    private TempCorpEntity corporation;

    public TempUserEntity() {
    }

    public TempUserEntity(TempCorpEntity corporation) {
        this.corporation = corporation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TempCorpEntity getCorporation() {
        return corporation;
    }

    public void setCorporation(TempCorpEntity corporation) {
        this.corporation = corporation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

