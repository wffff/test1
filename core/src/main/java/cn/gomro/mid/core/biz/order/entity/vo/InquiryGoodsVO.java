package cn.gomro.mid.core.biz.order.entity.vo;

import cn.gomro.mid.core.biz.inquiry.entity.InquiryGoodsEntity;
import cn.gomro.mid.core.common.utils.DateUtils;
import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.FormParam;
import java.io.Serializable;

import static cn.gomro.mid.core.biz.inquiry.biz.impl.InquiryBiz.GOODS_STATUS_NO_QUOTATION;

/**
 * Created by adam on 2017/4/27.
 * 询价商品对象
 */
public class InquiryGoodsVO implements Serializable {
    private Integer id; //商品id
    @FormParam(value="name")
    private String name; //商品名
    @FormParam(value="brand")
    private String brand; //品牌
    @FormParam(value="model")
    private String model; //型号
    @FormParam(value="spec")
    private String spec; //规格
    @FormParam(value="description")
    private String description; //描述
    @FormParam(value="address")
    private String address; //收货地
    @FormParam(value="delivery")
    private Integer delivery; //需求货期
    @FormParam(value="num")
    private Double num; //数量
    @FormParam(value="unit")
    private String unit; //单位
    @FormParam(value="packageNum")
    private Double packageNum; //包装数量
    @FormParam(value="packageUnit")
    private String packageUnit; //包装单位
    @FormParam(value="expire")
    private String expire; //过期时间
    @FormParam(value="attachment")
    private String attachment; //附件信息
    @FormParam(value="url")
    private String url; //参考链接
    @FormParam(value="memo")
    private String memo; //备注

    public InquiryGoodsVO() {
    }

    public InquiryGoodsVO(Integer id, String name, String brand, String model, String spec, String description, String address, Integer delivery, Double num, String unit, Double packageNum, String packageUnit, String expire, String attachment, String url, String memo) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.spec = spec;
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

    public static InquiryGoodsVO valueOf(String value){
        return JSONObject.parseObject(value,InquiryGoodsVO.class);
    }

    public InquiryGoodsEntity toInquiryGoodsEntity(InquiryGoodsVO goods) {
        InquiryGoodsEntity inquiryGoodsEntity = new InquiryGoodsEntity(goods.getName(), goods.getBrand(), goods.getModel(),
                goods.getDescription(), goods.getAddress(), goods.getDelivery(), goods.getNum(),
                goods.getUnit(), goods.getPackageNum(), goods.getPackageUnit(), DateUtils.getDateFromString(goods.getExpire()),
                goods.getAttachment(), goods.getUrl(), goods.getMemo());
        inquiryGoodsEntity.setStatus(GOODS_STATUS_NO_QUOTATION);
        inquiryGoodsEntity.setSourceId(goods.getId());
        return inquiryGoodsEntity;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
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

    @Override
    public String toString() {
        return "InquiryGoodsVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", spec='" + spec + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", delivery='" + delivery + '\'' +
                ", num=" + num +
                ", unit='" + unit + '\'' +
                ", packageNum=" + packageNum +
                ", packageUnit='" + packageUnit + '\'' +
                ", expire=" + expire +
                ", attachment='" + attachment + '\'' +
                ", url='" + url + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}