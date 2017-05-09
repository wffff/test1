package cn.gomro.mid.core.biz.order.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * 供货商订单
 */
@Entity
@Table(name = "t_vendor_order")
public class VendorOrderEntity extends AbstractEntity implements ParamConverterProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer cid;    //集团组织ID
    private Integer vid;    //供货商ID
    private Integer orderid;    //总订单ID，对应t_member_order的ID
    private Integer posttype;   //配送方式（1快递 2EMS 3平邮）
    private Double total;   //商品总价（可调整）
    private Double amount;  //订单总价(可调整)产品总价+运费
    private Double postage; //运费总价(可调整)
    @Column(name = "total_original")
    private Double totalOriginal;   //商品总价（不可调整，原价）
    @Column(name = "amount_original")
    private Double amountOriginal;  //订单总价（不可调整，原价）
    @Column(name = "postage_original")
    private Double postageOriginal; //运费总价（不可调整，原价）
    private Integer paymenttypeid;  //1:通联支付 2:支付宝99:银行转账
    private String detailedaddress; //省市区地址
    private String receiver;    //收货人
    private String receivemobile;   //手机或电话
    private String receiveaddress;  //地址
    @Column(columnDefinition = "Integer default 2")
    private Integer stateid;
    @Column(columnDefinition = "Integer default 1")
    private Integer type;   //0普通订单 1询报价订单
    private String memo;    //备注

    public VendorOrderEntity() {
    }

    public VendorOrderEntity(Integer cid, Integer vid, Integer orderid, Integer posttype, Double total, Double amount, Double postage, Double totalOriginal, Double amountOriginal, Double postageOriginal, Integer paymenttypeid, String detailedaddress, String receiver, String receivemobile, String receiveaddress, Integer stateid, Integer type, String memo) {
        this.cid = cid;
        this.vid = vid;
        this.orderid = orderid;
        this.posttype = posttype;
        this.total = total;
        this.amount = amount;
        this.postage = postage;
        this.totalOriginal = totalOriginal;
        this.amountOriginal = amountOriginal;
        this.postageOriginal = postageOriginal;
        this.paymenttypeid = paymenttypeid;
        this.detailedaddress = detailedaddress;
        this.receiver = receiver;
        this.receivemobile = receivemobile;
        this.receiveaddress = receiveaddress;
        this.stateid = stateid;
        this.type = type;
        this.memo = memo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getPosttype() {
        return posttype;
    }

    public void setPosttype(Integer posttype) {
        this.posttype = posttype;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPostage() {
        return postage;
    }

    public void setPostage(Double postage) {
        this.postage = postage;
    }

    public Double getTotalOriginal() {
        return totalOriginal;
    }

    public void setTotalOriginal(Double totalOriginal) {
        this.totalOriginal = totalOriginal;
    }

    public Double getAmountOriginal() {
        return amountOriginal;
    }

    public void setAmountOriginal(Double amountOriginal) {
        this.amountOriginal = amountOriginal;
    }

    public Double getPostageOriginal() {
        return postageOriginal;
    }

    public void setPostageOriginal(Double postageOriginal) {
        this.postageOriginal = postageOriginal;
    }

    public Integer getPaymenttypeid() {
        return paymenttypeid;
    }

    public void setPaymenttypeid(Integer paymenttypeid) {
        this.paymenttypeid = paymenttypeid;
    }

    public String getDetailedaddress() {
        return detailedaddress;
    }

    public void setDetailedaddress(String detailedaddress) {
        this.detailedaddress = detailedaddress;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceivemobile() {
        return receivemobile;
    }

    public void setReceivemobile(String receivemobile) {
        this.receivemobile = receivemobile;
    }

    public String getReceiveaddress() {
        return receiveaddress;
    }

    public void setReceiveaddress(String receiveaddress) {
        this.receiveaddress = receiveaddress;
    }

    public Integer getStateid() {
        return stateid;
    }

    public void setStateid(Integer stateid) {
        this.stateid = stateid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "VendorOrderEntity{" +
                "id=" + id +
                ", cid=" + cid +
                ", vid=" + vid +
                ", orderid=" + orderid +
                ", posttype=" + posttype +
                ", total=" + total +
                ", amount=" + amount +
                ", postage=" + postage +
                ", totalOriginal=" + totalOriginal +
                ", amountOriginal=" + amountOriginal +
                ", postageOriginal=" + postageOriginal +
                ", paymenttypeid=" + paymenttypeid +
                ", detailedaddress='" + detailedaddress + '\'' +
                ", receiver='" + receiver + '\'' +
                ", receivemobile='" + receivemobile + '\'' +
                ", receiveaddress='" + receiveaddress + '\'' +
                ", stateid=" + stateid +
                ", type=" + type +
                ", memo='" + memo + '\'' +
                '}';
    }

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        return null;
    }
}
