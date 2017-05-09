package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 * 此类为了不加载子列表
 * 因领导业务要求需要双向同时展示
 */
@Entity
@Table(name = "t_goods_category_2")
@Cacheable
public class GoodsCategoryEntityWithOutTree extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer pid;
    @Enumerated
    private GoodsType type;
    @Column(nullable = false,length = 50)
    private String name;
    private Integer sort;
    private Boolean saled;


    public GoodsCategoryEntityWithOutTree() {
    }

    public GoodsCategoryEntityWithOutTree(Integer pid, GoodsType type, String name, Integer sort, Boolean saled, Boolean del, Date last, Date time) {
        this.pid = pid;
        this.type = type;
        this.name = name;
        this.sort = sort;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getSaled() {
        return saled;
    }

    public void setSaled(Boolean saled) {
        this.saled = saled;
    }


    @Override
    public String toString() {
        return "GoodsCategoryEntityWithOutTree{" +
                "id=" + id +
                ", pid=" + pid +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", saled=" + saled +
                '}';
    }
}
