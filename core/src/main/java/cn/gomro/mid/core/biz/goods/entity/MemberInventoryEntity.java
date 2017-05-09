package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yaodw on 2016/7/18.
 */
@Entity
@Table(name = "t_member_inventory_2")
@Cacheable
public class MemberInventoryEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "member_id")
    private Integer member;
    @Column(name = "spec_id")
    private Integer spec;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "warehouse_id")
    private WarehouseEntity warehouse;
    private Integer num;

    public MemberInventoryEntity() {
    }


    public MemberInventoryEntity(Integer spec, Integer member, WarehouseEntity warehouse, Integer num, Boolean del, Date last, Date time) {
        this.spec = spec;
        this.member = member;
        this.warehouse = warehouse;
        this.num = num;
        this.del = del;
        this.last = last;
        this.time = time;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpec() {
        return spec;
    }

    public void setSpec(Integer spec) {
        this.spec = spec;
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
