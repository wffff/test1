package cn.gomro.mid.core.biz.order.entity.vo;

import com.alibaba.fastjson.JSON;

import javax.ws.rs.FormParam;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/4/26.
 * 订单参数实体Bean
 */
public class OrderEntity implements Serializable {
    @FormParam(value = "openOrderId")
    private Integer openOrderId;
    @FormParam(value = "token")
    private String token;
    @FormParam(value = "memberId")
    Integer memberId;
    @FormParam(value = "inquiryVO")
    List<InquiryVO> inquiryVO;
    @FormParam(value = "vendorVO")
    List<VendorVO> vendorVO;
    @FormParam(value = "province")
    String province;
    @FormParam(value = "city")
    String city;
    @FormParam(value = "area")
    String area;
    @FormParam(value = "address")
    String address;
    @FormParam(value = "receiver")
    String receiver;
    @FormParam(value = "mobile")
    String mobile;
    @FormParam(value = "total")
    Double total;
    @FormParam(value = "postAmount")
    Double postAmount;
    @FormParam(value = "amount")
    Double amount;
    @FormParam(value = "date")
    String date;
    @FormParam(value = "invoiceVO")
    InvoiceVO invoiceVO;
    @FormParam(value = "memo")
    String memo;
    @FormParam(value = "memberUsername")
    String memberUsername;



    public OrderEntity() {
    }

    public OrderEntity(String memberUsername,String token, Integer openOrderId, Integer memberId, List<InquiryVO> inquiryVO, List<VendorVO> vendorVO, String province, String city, String area, String address, String receiver, String mobile, Double total, Double postAmount, Double amount, String date, InvoiceVO invoiceVO, String memo) {
        this.memberUsername=memberUsername;
        this.token = token;
        this.openOrderId = openOrderId;
        this.memberId = memberId;
        this.inquiryVO = inquiryVO;
        this.vendorVO = vendorVO;
        this.province = province;
        this.city = city;
        this.area = area;
        this.address = address;
        this.receiver = receiver;
        this.mobile = mobile;
        this.total = total;
        this.postAmount = postAmount;
        this.amount = amount;
        this.date = date;
        this.invoiceVO = invoiceVO;
        this.memo = memo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public List<InquiryVO> getInquiryVO() {
        return inquiryVO;
    }

    public void setInquiryVO(List<InquiryVO> inquiryVO) {
        this.inquiryVO = inquiryVO;
    }

    public List<VendorVO> getVendorVO() {
        return vendorVO;
    }

    public void setVendorVO(List<VendorVO> vendorVO) {
        this.vendorVO = vendorVO;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPostAmount() {
        return postAmount;
    }

    public void setPostAmount(Double postAmount) {
        this.postAmount = postAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public InvoiceVO getInvoiceVO() {
        return invoiceVO;
    }

    public void setInvoiceVO(InvoiceVO invoiceVO) {
        this.invoiceVO = invoiceVO;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getOpenOrderId() {
        return openOrderId;
    }

    public void setOpenOrderId(Integer openOrderId) {
        this.openOrderId = openOrderId;

    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
