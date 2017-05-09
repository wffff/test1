package cn.gomro.mid.core.biz.inquiry.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;
import cn.gomro.mid.core.biz.goods.entity.MemberEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by adam on 2017/1/17.
 */
@Entity
@Table(name = "T_quotation_2")  //former name t_quote_price
public class QuotationEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private MemberEntity member;
    @ManyToOne
    @JoinColumn(name = "inquiry_goods_id", referencedColumnName = "id")
    private InquiryGoodsEntity inquiryGoods;
    @Column(name = "purchase_order_id")
    private Integer purchaseOrderId;
    private String name;
    private String brand;
    private String model;
    private String description;
    private String place;
    private Integer delivery;
    private Double price;
    private Double num;
    private String unit;
    @Column(name = "package_num")
    private Double packageNum;
    @Column(name = "package_unit")
    private String packageUnit;
    private Double amount;
    private Double freight;
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;
    @Column(name = "source_id")
    private Integer sourceId;



    

    public QuotationEntity(MemberEntity member, InquiryGoodsEntity inquiryGoods, Integer purchaseOrderId, String name, String brand, String model, String description, String place, Integer delivery, Double price, Double num, String unit, Double packageNum, String packageUnit, Double amount, Double freight, Date expire) {
        this.member = member;
        this.inquiryGoods = inquiryGoods;
        this.purchaseOrderId = purchaseOrderId;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.place = place;
        this.delivery = delivery;
        this.price = price;
        this.num = num;
        this.unit = unit;
        this.packageNum = packageNum;
        this.packageUnit = packageUnit;
        this.amount = amount;
        this.freight = freight;
        this.expire = expire;
    }


    public QuotationEntity(Integer id) {
        this.id=id;
    }

    public QuotationEntity() {
    }

    public QuotationEntity(Integer id, String name, String brand, String model, String description, String place, Integer delivery, Double price, Double num, String unit, Double packageNum, String packageUnit, Double freight, Date expire) {
        this.id=id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.place = place;
        this.delivery = delivery;
        this.price = price;
        this.num = num;
        this.unit = unit;
        this.packageNum = packageNum;
        this.packageUnit = packageUnit;
        this.freight = freight;
        this.expire = expire;
    }

    @Override
    public String toString() {
        return "QuotationEntity{" +
                "id=" + id +
                ", member=" + member +
                ", inquiryGoods=" + inquiryGoods +
                ", purchaseOrderId=" + purchaseOrderId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", description='" + description + '\'' +
                ", place='" + place + '\'' +
                ", delivery='" + delivery + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", unit='" + unit + '\'' +
                ", packageNum=" + packageNum +
                ", packageUnit='" + packageUnit + '\'' +
                ", amount=" + amount +
                ", freight=" + freight +
                ", expire=" + expire +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public InquiryGoodsEntity getInquiryGoods() {
        return inquiryGoods;
    }

    public void setInquiryGoods(InquiryGoodsEntity inquiryGoods) {
        this.inquiryGoods = inquiryGoods;
    }

    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Integer purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getDelivery() {
        return delivery;
    }

    public void setDelivery(Integer delivery) {
        this.delivery = delivery;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(Double packageNum) {
        this.packageNum = packageNum;
    }

    public String getPackageUnit() {
        return packageUnit;
    }

    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }
}
