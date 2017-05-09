package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yaodw on 2016/7/15.
 * 因领导业务要求需要双向同时展示..
 */
@Entity
@Table(name = "t_goods_profile_2")
@SecondaryTable(name = "t_goods_details_2", pkJoinColumns = {@PrimaryKeyJoinColumn(name = "goods_id", referencedColumnName = "id")})
@Cacheable
public class GoodsEntityManyToOne extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated
    private GoodsType type;
    @Column(length = 500)
    private String images;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "brand_id")
    private GoodsBrandEntity brand;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "category_id")
    private GoodsCategoryEntityWithOutTree category;
    @Column(length = 50)
    private String unspsc;
    @Column(length = 50)
    private String hscode;
    @Column(nullable = false, length = 500)
    private String name;
    @Column(length = 500)
    private String tags;
    @Column(name = "price_min", scale = 4)
    private Double priceMin;
    @Column(name = "price_max", scale = 4)
    private Double priceMax;
    private Integer sales;
    private Integer views;
    private Boolean saled;

    @Column(table = "t_goods_details_2", columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY)
    private String packages;
    @Column(table = "t_goods_details_2", columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY)
    private String technology;
    @Column(table = "t_goods_details_2", columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY)
    private String description;
    @Column(table = "t_goods_details_2", columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY)
    private String memo;

    @OneToMany(mappedBy = "goods",fetch = FetchType.LAZY)
    @JsonIgnore
    @Transient
    private List<GoodsModelEntityManyToOne> goodsModelList = new ArrayList<>();

    public GoodsEntityManyToOne() {
    }

    public GoodsEntityManyToOne(GoodsType type, String images, GoodsBrandEntity brand, GoodsCategoryEntityWithOutTree category, String hscode, String unspsc, String name, String tags, Double priceMin, Double priceMax, Integer sales, Integer views, Boolean saled, String packages, String technology, String description, String memo, Boolean del, Date last, Date time) {
        this.type = type;
        this.images = images;
        this.brand = brand;
        this.category = category;
        this.hscode = hscode;
        this.unspsc = unspsc;
        this.name = name;
        this.tags = tags;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.sales = sales;
        this.views = views;
        this.saled = saled;
        this.packages = packages;
        this.technology = technology;
        this.description = description;
        this.memo = memo;
        this.del = del;
        this.last = last;
        this.time = time;
    }

    public String getUnspsc() {
        return unspsc;
    }

    public void setUnspsc(String unspsc) {
        this.unspsc = unspsc;
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public GoodsBrandEntity getBrand() {
        return brand;
    }

    public void setBrand(GoodsBrandEntity brand) {
        this.brand = brand;
    }

    public GoodsCategoryEntityWithOutTree getCategory() {
        return category;
    }

    public void setCategory(GoodsCategoryEntityWithOutTree category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Boolean getSaled() {
        return saled;
    }

    public void setSaled(Boolean saled) {
        this.saled = saled;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<GoodsModelEntityManyToOne> getGoodsModelList() {
        return goodsModelList;
    }

    public void setGoodsModelList(List<GoodsModelEntityManyToOne> goodsModelList) {
        this.goodsModelList = goodsModelList;
    }

    @Override
    public String toString() {
        return "GoodsEntityManyToOne{" +
                "id=" + id +
                ", type=" + type +
                ", images='" + images + '\'' +
                ", brand=" + brand +
                ", category=" + category +
                ", unspsc='" + unspsc + '\'' +
                ", hscode='" + hscode + '\'' +
                ", name='" + name + '\'' +
                ", tags='" + tags + '\'' +
                ", priceMin=" + priceMin +
                ", priceMax=" + priceMax +
                ", sales=" + sales +
                ", views=" + views +
                ", saled=" + saled +
                ", packages='" + packages + '\'' +
                ", technology='" + technology + '\'' +
                ", description='" + description + '\'' +
                ", memo='" + memo + '\'' +
                ", goodsModelList=" + goodsModelList +
                '}';
    }
}
