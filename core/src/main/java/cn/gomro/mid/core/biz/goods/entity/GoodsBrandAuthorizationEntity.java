package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by adam on 10/17/16.
 */
@Entity
@Table(name = "t_goods_brand_authorization_2")
@Cacheable
public class GoodsBrandAuthorizationEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "member_id")
    private Integer member;
    @Column(name = "brand_id")
    private Integer brand;
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public GoodsBrandAuthorizationEntity() {
    }

    public GoodsBrandAuthorizationEntity(Integer member, Integer brand, Date endDate, Boolean del, Date last, Date time) {
        this.member = member;
        this.brand = brand;
        this.endDate = endDate;
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

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
