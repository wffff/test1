package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by momo on 2016/8/1.
 */
@Entity
@Table(name = "t_prod_postage")
@Cacheable
public class FreightTemplateEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne()
    @JoinColumn(name = "vid")
    private CorporationEntity corporation;
    @Column(name = "mname",insertable = false,updatable = false)
    private String name;
    private Integer mid;

    public FreightTemplateEntity() {
    }

    public FreightTemplateEntity(CorporationEntity corporation, String name, Boolean del, Date last, Date time) {
        this.corporation = corporation;
        this.name = name;
        this.del = del;
        this.last = last;
        this.time = time;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
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

    public void setCorporation(CorporationEntity member) {
        this.corporation = member;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
