package cn.gomro.mid.core.biz.order.entity.vo;

import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.FormParam;
import java.io.Serializable;

/**
 * Created by adam on 2017/4/27.
 * 发票对象
 */
public class InvoiceVO implements Serializable {
    @FormParam(value = "type")
    private Integer type;   //发票类型 0：无需票 1：普票 2：专票
    @FormParam(value = "invoiceHead")
    private String invoiceHead;   // 发票台头
    @FormParam(value = "taxNumber")
    private String taxNumber;   // 税号
    @FormParam(value = "contactInfo")
    private String contactInfo;   // 地址、电话信息
    @FormParam(value = "bankInfo")
    private String bankInfo;   // 开户行账号
    @FormParam(value = "amount")
    private Double amount;   // 开票金额 注意：总开票金额<=合计总价
    @FormParam(value = "province")
    private String province;   // 收票地址：省
    @FormParam(value = "city")
    private String city;   // 收票地址：城市
    @FormParam(value = "area")
    private String area;   // 收票地址：区县
    @FormParam(value = "address")
    private String address;   // 收票地址：详细地址
    @FormParam(value = "receiver")
    private String receiver;   // 收票地址：联系人
    @FormParam(value = "mobile")
    private String mobile;   // 收票地址：联系电话

    public InvoiceVO() {
    }

    public InvoiceVO(Integer type, String invoiceHead, String taxNumber, String contactInfo, String bankInfo, Double amount, String province, String city, String area, String address, String receiver, String mobile) {
        this.type = type;
        this.invoiceHead = invoiceHead;
        this.taxNumber = taxNumber;
        this.contactInfo = contactInfo;
        this.bankInfo = bankInfo;
        this.amount = amount;
        this.province = province;
        this.city = city;
        this.area = area;
        this.address = address;
        this.receiver = receiver;
        this.mobile = mobile;
    }

    public static InvoiceVO valueOf(String value){
        return JSONObject.parseObject(value,InvoiceVO.class);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInvoiceHead() {
        return invoiceHead;
    }

    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "InvoiceVO{" +
                "type=" + type +
                ", invoiceHead='" + invoiceHead + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", bankInfo='" + bankInfo + '\'' +
                ", amount=" + amount +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", receiver='" + receiver + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}