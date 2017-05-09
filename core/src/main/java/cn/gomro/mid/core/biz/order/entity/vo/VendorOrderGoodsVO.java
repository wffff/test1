package cn.gomro.mid.core.biz.order.entity.vo;

import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.FormParam;
import java.io.Serializable;

/**
 * Created by adam on 2017/4/27.
 * 供应商订单商品对象
 */
public class VendorOrderGoodsVO implements Serializable {
    private Integer id;// 定标商品id
    @FormParam(value = "goodsId")
    private Integer goodsId;//询价商品id  goods.id
    @FormParam(value = "name")
    private String name;// 商品名
    @FormParam(value = "brand")
    private String brand;// 品牌
    @FormParam(value = "model")
    private String model;// 型号
    @FormParam(value = "spec")
    private String spec;// 规格
    @FormParam(value = "description")
    private String description;// 描述
    @FormParam(value = "price")
    private Double price;// 价格
    @FormParam(value = "address")
    private String address;// 收货地
    @FormParam(value = "place")
    private String place;// 供货地
    @FormParam(value = "delivery")
    private Integer delivery;// 供货期
    @FormParam(value = "num")
    private Double num;// 数量
    @FormParam(value = "unit")
    private String unit;// 单位
    @FormParam(value = "packageNum")
    private Double packageNum;// 包装数量
    @FormParam(value = "packageUnit")
    private String packageUnit;// 包装单位
    @FormParam(value = "freight")
    private Double freight;// 运费
    @FormParam(value = "amount")
    private Double amount;// 小计
    @FormParam(value = "expire")
    private String expire;// 过期时间
    @FormParam(value = "memo")
    private String memo;    //备注


    public VendorOrderGoodsVO() {
    }

    public VendorOrderGoodsVO(Integer id, Integer goodsId, String name, String brand, String model, String spec, String description, Double price, String address, String place, Integer delivery, Double num, String unit, Double packageNum, String packageUnit, Double freight, Double amount, String expire, String memo) {
        this.id = id;
        this.goodsId = goodsId;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.spec = spec;
        this.description = description;
        this.price = price;
        this.address = address;
        this.place = place;
        this.delivery = delivery;
        this.num = num;
        this.unit = unit;
        this.packageNum = packageNum;
        this.packageUnit = packageUnit;
        this.freight = freight;
        this.amount = amount;
        this.expire = expire;
        this.memo = memo;
    }

    public static VendorOrderGoodsVO valueOf(String value){
        return JSONObject.parseObject(value,VendorOrderGoodsVO.class);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "VendorOrderGoodsVO{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", spec='" + spec + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", address='" + address + '\'' +
                ", place='" + place + '\'' +
                ", delivery=" + delivery +
                ", num=" + num +
                ", unit='" + unit + '\'' +
                ", packageNum=" + packageNum +
                ", packageUnit='" + packageUnit + '\'' +
                ", freight=" + freight +
                ", amount=" + amount +
                ", expire=" + expire +
                ", memo='" + memo + '\'' +
                '}';
    }
}