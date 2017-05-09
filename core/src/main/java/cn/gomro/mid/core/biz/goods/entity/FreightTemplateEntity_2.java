package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by momo on 2016/8/1.
 */
@Entity
@Table(name = "t_member_freight_template_2")
@Cacheable
public class FreightTemplateEntity_2 extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private CorporationEntity corporation;
    @Column(name = "spec_id")
    private Integer goodsSpecId;
    @Column(length = 50, nullable = false)
    private String name;

    public FreightTemplateEntity_2() {
    }

    public FreightTemplateEntity_2(CorporationEntity corporation, Integer goodsSpecId, String name , Boolean del, Date last, Date time) {
        this.corporation = corporation;
        this.goodsSpecId = goodsSpecId;
        this.name = name;
        this.del = del;
        this.last = last;
        this.time = time;
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

    public Integer getGoodsSpecId() {
        return goodsSpecId;
    }

    public void setGoodsSpecId(Integer goodsSpecId) {
        this.goodsSpecId = goodsSpecId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
