package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2016/8/2.
 */
@Entity
@Table(name = "t_goods_model_2")
@Cacheable
public class GoodsModelEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "goods_id")
    private Integer goods;
    @Column(nullable = false)
    private String name;
    private Boolean saled;
    @OneToMany(targetEntity = GoodsSpecEntity.class,
            fetch = FetchType.EAGER, mappedBy = "model",
            cascade = CascadeType.ALL)
    private List<GoodsSpecEntity> goodsSpecList = new ArrayList<>();

    public GoodsModelEntity() {
    }

    public GoodsModelEntity(Integer goods, String name, Boolean saled, Boolean del, Date last, Date time) {
        this.goods = goods;
        this.name = name;
        this.saled = saled;
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

    public Integer getGoods() {
        return goods;
    }

    public void setGoods(Integer goods) {
        this.goods = goods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSaled() {
        return saled;
    }

    public void setSaled(Boolean saled) {
        this.saled = saled;
    }

    public List<GoodsSpecEntity> getGoodsSpecList() {
        return goodsSpecList;
    }

    public void setGoodsSpecList(List<GoodsSpecEntity> goodsSpecList) {
        this.goodsSpecList = goodsSpecList;
    }
}
