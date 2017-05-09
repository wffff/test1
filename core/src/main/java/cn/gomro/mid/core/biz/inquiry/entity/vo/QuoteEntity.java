package cn.gomro.mid.core.biz.inquiry.entity.vo;

import com.alibaba.fastjson.JSON;

import javax.ws.rs.FormParam;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zr on 2017/5/3.
 * 报价单实体参数
 */
public class QuoteEntity implements Serializable {
    @FormParam(value = "id")
    Integer id;
    @FormParam(value = "member_id")
    Integer member_id;
    @FormParam(value = "inquiry_goods_id")
    Integer inquiry_goods_id;
    @FormParam(value = "purchaseOrderId")
    Integer purchaseOrderId;
    @FormParam(value = "name")
    String name;
    @FormParam(value = "brand")
    String brand;
    @FormParam(value = "model")
    String model;
    @FormParam(value = "description")
    String description;
    @FormParam(value = "place")
    String place;
    @FormParam(value = "receiver")
    String receiver;
    @FormParam(value = "mobile")
    String mobile;
    @FormParam(value = "dellvery")
    Integer delivery;
    @FormParam(value = "price")
    Double price;
    @FormParam(value = "num")
    private Double num;
    @FormParam(value = "unit")
    private String unit;
    @FormParam(value = "packgeNum")
    Double packageNum;
    @FormParam(value = "packageUnit")
    String packageUnit;
    @FormParam(value = "amount")
    Double amount;
    @FormParam(value = "freigh")
    Double freigh;
    @FormParam(value = "expire")
    Date expire;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public Integer getInquiry_goods_id() {
        return inquiry_goods_id;
    }

    public void setInquiry_goods_id(Integer inquiry_goods_id) {
        this.inquiry_goods_id = inquiry_goods_id;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Double getFreigh() {
        return freigh;
    }

    public void setFreigh(Double freigh) {
        this.freigh = freigh;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
    public QuoteEntity(){}

    public QuoteEntity(Integer id, Integer member_id, Integer inquiry_goods_id, Integer purchaseOrderId, String name, String brand, String model, String description, String place, String receiver, String mobile, Integer delivery, Double price, Double num, String unit, Double packageNum, String packageUnit, Double amount, Double freigh, Date expire) {
        this.id = id;
        this.member_id = member_id;
        this.inquiry_goods_id = inquiry_goods_id;
        this.purchaseOrderId = purchaseOrderId;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.place = place;
        this.receiver = receiver;
        this.mobile = mobile;
        this.delivery = delivery;
        this.price = price;
        this.num = num;
        this.unit = unit;
        this.packageNum = packageNum;
        this.packageUnit = packageUnit;
        this.amount = amount;
        this.freigh = freigh;
        this.expire = expire;
    }
}
