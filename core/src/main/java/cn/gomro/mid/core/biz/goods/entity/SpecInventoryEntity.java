package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2016/8/19.
 * 商品总库存实体，同一商品，同一供应商的所有数量记录
 */
@Entity
@Table(name = "t_spec_inventory_2")
@Cacheable
public class SpecInventoryEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "member_id")
    private Integer member;
    @Column(name = "spec_id")
    private Integer spec;
    private Integer amount;
    private Integer usabled;
    private Integer freezed;

    public SpecInventoryEntity() {
    }

    public SpecInventoryEntity(Integer member, Integer spec, Integer amount, Integer usabled, Integer freezed, Boolean del, Date last, Date time) {
        this.member = member;
        this.spec = spec;
        this.amount = amount;
        this.usabled = usabled;
        this.freezed = freezed;
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

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public Integer getSpec() {
        return spec;
    }

    public void setSpec(Integer spec) {
        this.spec = spec;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getUseabled() {
        return usabled;
    }

    public void setUseabled(Integer usabled) {
        this.usabled = usabled;
    }

    public Integer getFreezed() {
        return freezed;
    }

    public void setFreezed(Integer freezed) {
        this.freezed = freezed;
    }
}
