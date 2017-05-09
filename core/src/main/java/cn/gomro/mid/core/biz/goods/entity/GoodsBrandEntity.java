package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yaodw on 2016/7/18.
 */
@Entity
@Table(name = "t_goods_brand_2")
@Cacheable
public class GoodsBrandEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated
    private GoodsType type;
    @Column(length = 200, nullable = false)
    private String name;
    @Column(name = "name_en", length = 100)
    private String nameEn;
    @Column(name = "name_cn", length = 100)
    private String nameCn;
    @Column(length = 200)
    private String logo;
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer sort;
    private Boolean saled;

    public GoodsBrandEntity() {
    }

    public GoodsBrandEntity(GoodsType type, String name, String nameEn, String nameCn, String logo, String description, Integer sort, Boolean saled) {
        this.type = type;
        this.name = name;
        this.nameEn = nameEn;
        this.nameCn = nameCn;
        this.logo = logo;
        this.description = description;
        this.sort = sort;
        this.saled = saled;
    }

    public GoodsBrandEntity(Boolean del, Date last, Date time, GoodsType type, String name, String nameEn, String nameCn, String logo, String description, Integer sort, Boolean saled) {
        super(del, last, time);
        this.type = type;
        this.name = name;
        this.nameEn = nameEn;
        this.nameCn = nameCn;
        this.logo = logo;
        this.description = description;
        this.sort = sort;
        this.saled = saled;
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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "GoodsBrandEntity{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameCn='" + nameCn + '\'' +
                ", logo='" + logo + '\'' +
                ", description='" + description + '\'' +
                ", sort=" + sort +
                ", saled=" + saled +
                '}';
    }
}
