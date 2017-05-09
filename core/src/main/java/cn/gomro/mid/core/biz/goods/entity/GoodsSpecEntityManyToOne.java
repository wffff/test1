package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/8/2.
 * 因领导业务要求需要双向同时展示..
 */
@Entity
@Table(name = "t_goods_spec_2")
@Cacheable
public class GoodsSpecEntityManyToOne extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private GoodsModelEntityManyToOne model;
    @Column(nullable = false, length = 500)
    private String name;
    @Column(scale = 4)
    private Double price;
    @Column(name = "market_price", scale = 4)
    private Double marketPrice;
    @Column(scale = 2)
    private Double discount;
    @Column(name = "min_order")
    private Integer minOrder;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "unit_id")
    private GoodsUnitEntity unit;
    @Column(name = "package_num")
    private Integer packageNum;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "package_unit_id")
    private GoodsUnitEntity packageUnit;
    private Integer delivery;
    @Column(length = 50)
    private String sku;
    @Column(scale = 4)
    private Double weight;
    @Column(scale = 4)
    private Double length;
    @Column(scale = 4)
    private Double width;
    @Column(scale = 4)
    private Double height;
    @Column(length = 200)
    private String warranty; //质保信息
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private CorporationEntity corporation;
    @Column(name = "freight_template_id")
    private Integer freightTemplate;
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "TEXT")
    private String description;
    private Boolean saled;
    @Column(name = "brand_state")
    private Integer brandState;
    @OneToMany(targetEntity = MemberInventoryEntity.class,mappedBy = "spec",fetch = FetchType.LAZY)
    @JsonIgnore
    @Transient
    private List<MemberInventoryEntity> memberInventoryList = new ArrayList<>();


    public GoodsSpecEntityManyToOne() {
    }
    public GoodsSpecEntityManyToOne(GoodsModelEntityManyToOne model, String name, Double price, Double marketPrice, Double discount,
                                    Integer minOrder, GoodsUnitEntity unit, Integer packageNum, GoodsUnitEntity packageUnit,
                                    Integer delivery, String sku, Double weight, Double length, Double width, Double height,
                                    String warranty, CorporationEntity corporation, Integer freightTemplate, String description, Boolean saled,
                                    Boolean del, Date last, Date time) {
        this.model = model;
        this.name = name;
        this.price = price;
        this.marketPrice = marketPrice;
        this.discount = discount;
        this.minOrder = minOrder;
        this.unit = unit;
        this.packageNum = packageNum;
        this.packageUnit = packageUnit;
        this.delivery = delivery;
        this.sku = sku;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.warranty = warranty;
        this.corporation = corporation;
        this.freightTemplate = freightTemplate;
        this.description = description;
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

    public GoodsModelEntityManyToOne getModel() {
        return model;
    }

    public void setModel(GoodsModelEntityManyToOne model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(Integer minOrder) {
        this.minOrder = minOrder;
    }

    public GoodsUnitEntity getUnit() {
        return unit;
    }

    public void setUnit(GoodsUnitEntity unit) {
        this.unit = unit;
    }

    public Integer getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(Integer packageNum) {
        this.packageNum = packageNum;
    }

    public GoodsUnitEntity getPackageUnit() {
        return packageUnit;
    }

    public void setPackageUnit(GoodsUnitEntity packageUnit) {
        this.packageUnit = packageUnit;
    }

    public Integer getDelivery() {
        return delivery;
    }

    public void setDelivery(Integer delivery) {
        this.delivery = delivery;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public CorporationEntity getCorporation() {
        return corporation;
    }

    public void setCorporation(CorporationEntity member) {
        this.corporation = member;
    }

    public Integer getFreightTemplate() {
        return freightTemplate;
    }

    public void setFreightTemplate(Integer freightTemplate) {
        this.freightTemplate = freightTemplate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSaled() {
        return saled;
    }

    public void setSaled(Boolean saled) {
        this.saled = saled;
    }


    public List<MemberInventoryEntity> getMemberInventoryList() {
        return memberInventoryList;
    }

    public void setMemberInventoryList(List<MemberInventoryEntity> memberInventoryList) {
        this.memberInventoryList = memberInventoryList;
    }

    public Integer getBrandState() {
        return brandState;
    }

    public void setBrandState(Integer brandState) {
        this.brandState = brandState;
    }

    @Override
    public String toString() {
        return "GoodsSpecEntityManyToOne{" +
                "id=" + id +
                ", model=" + model +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", marketPrice=" + marketPrice +
                ", discount=" + discount +
                ", minOrder=" + minOrder +
                ", unit=" + unit +
                ", packageNum=" + packageNum +
                ", packageUnit=" + packageUnit +
                ", delivery=" + delivery +
                ", sku='" + sku + '\'' +
                ", weight=" + weight +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", warranty='" + warranty + '\'' +
                ", member=" + corporation +
                ", freightTemplate=" + freightTemplate +
                ", description='" + description + '\'' +
                ", saled=" + saled +
                ", brandState=" + brandState +
                ", memberInventoryList=" + memberInventoryList +
                '}';
    }
}
