package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/8/2.
 * 因领导业务要求需要双向同时展示
 */
@Entity
@Table(name = "t_goods_model_2")
@Cacheable
public class GoodsModelEntityManyToOne extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "goods_id", referencedColumnName = "id")
    private GoodsEntityManyToOne goods;
    @Column(nullable = false)
    private String name;
    private Boolean saled;

    @OneToMany(mappedBy = "model",fetch = FetchType.LAZY)
    @JsonIgnore
    @Transient
    private List<GoodsSpecEntityManyToOne> goodsSpecList = new ArrayList<>();

    public GoodsModelEntityManyToOne() {
    }

    public GoodsModelEntityManyToOne(GoodsEntityManyToOne goods, String name, Boolean saled, Boolean del, Date last, Date time) {
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

    public GoodsEntityManyToOne getGoods() {
        return goods;
    }

    public void setGoods(GoodsEntityManyToOne goods) {
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

    public List<GoodsSpecEntityManyToOne> getGoodsSpecList() {
        return goodsSpecList;
    }

    public void setGoodsSpecList(List<GoodsSpecEntityManyToOne> goodsSpecList) {
        this.goodsSpecList = goodsSpecList;
    }

    @Override
    public String toString() {
        return "GoodsModelEntityManyToOne{" +
                "id=" + id +
                ", goods=" + goods +
                ", name='" + name + '\'' +
                ", saled=" + saled +
                ", goodsSpecList=" + goodsSpecList +
                '}';
    }
}
