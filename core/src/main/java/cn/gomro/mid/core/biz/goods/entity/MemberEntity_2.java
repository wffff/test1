package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yaodw on 2016/7/15.
 */
@Entity
@Table(name = "t_member_2")
@Cacheable
public class MemberEntity_2 extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 200, nullable = false)
    private String name;
    private Boolean isCustomer;
    private Boolean isGroupCustomer;
    private Boolean isVendor;
    private Boolean isGroupVendor;
    private Boolean act;

    public MemberEntity_2() {
    }

    public MemberEntity_2(String name, Boolean isCustomer, Boolean isGroupCustomer, Boolean isVendor, Boolean isGroupVendor, Boolean act, Boolean del, Date last, Date time) {
        this.name = name;
        this.isCustomer = isCustomer;
        this.isGroupCustomer = isGroupCustomer;
        this.isVendor = isVendor;
        this.isGroupVendor = isGroupVendor;
        this.act = act;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCustomer() {
        return isCustomer;
    }

    public void setCustomer(Boolean customer) {
        isCustomer = customer;
    }

    public Boolean getGroupCustomer() {
        return isGroupCustomer;
    }

    public void setGroupCustomer(Boolean groupCustomer) {
        isGroupCustomer = groupCustomer;
    }

    public Boolean getVendor() {
        return isVendor;
    }

    public void setVendor(Boolean vendor) {
        isVendor = vendor;
    }

    public Boolean getGroupVendor() {
        return isGroupVendor;
    }

    public void setGroupVendor(Boolean groupVendor) {
        isGroupVendor = groupVendor;
    }

    public Boolean getAct() {
        return act;
    }

    public void setAct(Boolean act) {
        this.act = act;
    }
}
