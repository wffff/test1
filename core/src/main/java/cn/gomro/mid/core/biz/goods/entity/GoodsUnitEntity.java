package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yaodw on 2016/7/18.
 */
@Entity
@Table(name = "t_goods_unit_2")
@Cacheable
public class GoodsUnitEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated
    private GoodsType type;
    @Column(nullable = false, length = 0)
    private String name;

    public GoodsUnitEntity() {
    }

    public GoodsUnitEntity(GoodsType type, String name, Boolean del, Date last, Date time) {
        this.type = type;
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

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoodsUnitEntity{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
