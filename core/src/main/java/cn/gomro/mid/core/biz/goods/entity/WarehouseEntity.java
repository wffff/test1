package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yaodw on 2016/7/18.
 */
@Entity
@Table(name = "t_member_warehouse_2")
@Cacheable
public class WarehouseEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "member_id")
    private CorporationEntity corporation;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(length = 500)
    private String address;
    private Boolean act;

    public WarehouseEntity() {
    }

    public WarehouseEntity(CorporationEntity corporation, String name, String address, Boolean act, Boolean del, Date last, Date time) {
        this.corporation = corporation;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getAct() {
        return act;
    }

    public void setAct(Boolean act) {
        this.act = act;
    }

    @Override
    public String toString() {
        return "WarehouseEntity{" +
                "id=" + id +
                ", member=" + corporation +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", act=" + act +
                '}';
    }
}
