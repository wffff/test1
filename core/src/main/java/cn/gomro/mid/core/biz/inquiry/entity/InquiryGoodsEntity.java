package cn.gomro.mid.core.biz.inquiry.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by adam on 2017/1/17.
 */
@Entity
@Table(name = "t_inquiry_goods_2")  //former name t_inquiry_price_prod
public class InquiryGoodsEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "inquiry_id",referencedColumnName = "id")
    private InquiryEntity inquiry;//询价表
    @Column(name = "purchase_order_id")
    private Integer purchaseOrderId;
    private String name;
    private String brand;
    private String model;
    private String description;
    private String address;
    private Integer delivery;
    private Double num;
    private String unit;
    @Column(name = "package_num")
    private Double packageNum;
    @Column(name = "package_unit")
    private String packageUnit;
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;
    private Integer status;//'询价商品状态 0、新建 1、待分配 2、驳回 3、未报价 4、报价中 5、已完成 6、已关闭 7、已结束'
    private String reject;
    @Column(name = "quotation_count")
    private Integer quotationCount;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "category_id")
    private InquiryCategoryEntity category;
    private String attachment;
    private String url;
    private String memo;
    @Column(name = "source_id")
    private Integer sourceId;

    public InquiryGoodsEntity() {
    }

    public InquiryGoodsEntity( String name, String brand, String model, String description,
                              String address, Integer delivery, Double num, String unit, Double packageNum,
                              String packageUnit, Date expire, String attachment, String url, String memo) {
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.address = address;
        this.delivery = delivery;
        this.num = num;
        this.unit = unit;
        this.packageNum = packageNum;
        this.packageUnit = packageUnit;
        this.expire = expire;
        this.attachment = attachment;
        this.url = url;
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "InquiryGoodsEntity{" +
                "id=" + id +
                ", inquiry=" + inquiry +
                ", purchaseOrderId=" + purchaseOrderId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", delivery='" + delivery + '\'' +
                ", num=" + num +
                ", unit='" + unit + '\'' +
                ", packageNum=" + packageNum +
                ", packageUnit='" + packageUnit + '\'' +
                ", expire=" + expire +
                ", status=" + status +
                ", reject='" + reject + '\'' +
                ", quotationCount=" + quotationCount +
                ", categoryId=" + category +
                ", attachment='" + attachment + '\'' +
                ", url='" + url + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InquiryEntity getInquiry() {
        return inquiry;
    }

    public void setInquiry(InquiryEntity inquiry) {
        this.inquiry = inquiry;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDelivery() {
        return delivery;
    }

    public void setDelivery(Integer delivery) {
        this.delivery = delivery;
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

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

    public Integer getQuotationCount() {
        return quotationCount;
    }

    public void setQuotationCount(Integer quotationCount) {
        this.quotationCount = quotationCount;
    }

    public InquiryCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(InquiryCategoryEntity categoryId) {
        this.category = categoryId;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }
}
