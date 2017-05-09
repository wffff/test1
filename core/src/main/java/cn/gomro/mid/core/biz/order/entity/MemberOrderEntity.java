package cn.gomro.mid.core.biz.order.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单临时映射表
 */
@Entity
@Table(name = "t_member_order")
public class MemberOrderEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer sourceid;   //来源ID
    private Integer cid;    //集团组织ID
    private Integer mid;    //会员ID
    private Double total;    //商品总价（显示用，可调整，所有产品的总价）
    private Double amount;  //订单总价（显示用，可调整，产品总价+运费+发票邮寄费  amount=total+postage+invoicecost）
    private Double postage;  //运费总价（显示用，可调整，运费）
    @Column(name = "total_original")
    private Double totalOriginal;  //商品总价（不显示，不可调整，所有产品的总价）
    @Column(name = "amount_original")
    private Double amountOriginal;  //订单总价（不显示，不可调整，产品总价+运费+发票邮寄费）
    @Column(name = "postage_original")
    private Double postageOriginal;  //运费总价（不显示，不可调整，运费）
    @Column(columnDefinition = "Integer default 4")
    private Integer paymenttypeid;  //1:通联支付 2:支付宝 3：财付通 4: 信用支付 98：银行转账 99:银行转账已审核
    @Column(name = "amount_final")
    private Double amountFinal;  //最终支付金额(实际支付的金额)
    private Integer invoicetypeid;
    private Integer isinvoice;
    private String invoicename;
    private String detailedaddress;
    private String receiver;    //收货人
    private String receivemobile;    //手机或电话
    private String receiveaddress;    //地址
    @Column(columnDefinition = "Integer default 2")
    private Double payment; //第三方返回的实际支付金额（= amount_final = amount + 支付手续费）最终支付金额，可能包含或者不包含支付手续费//支付信息（和第三方返回的数据）
    @Column(columnDefinition = "TIMESTAMP(0) default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymenttime;//付款时间
    private Integer paymentoperator; //付款操作者
    @Column(columnDefinition = "Integer default 2")
    private Integer paymentmethod; //1:客户付款操作 2:系统付款操作
    private String memo; //备注
    @Column(columnDefinition = "Integer default 2")
    private Integer stateid;
    @Column(columnDefinition = "Integer default 1")
    private Integer type;   //0普通订单 1询报价订单 2淘宝订单 3天猫订单 4普通预付款订单 5询报价预付款订单
    @Column(name = "client_operation", columnDefinition = "Integer default 2")
    private Integer clientOperation;   //下单操作端 0web 1移动端 2汉得接入端
    private Integer invoiceid;  //发票id

    public MemberOrderEntity() {
    }

    public MemberOrderEntity(Integer sourceid, Integer cid, Integer mid, Double total, Double amount, Double postage, Double totalOriginal, Double amountOriginal, Double postageOriginal, Integer paymenttypeid, Double amountFinal, Integer invoicetypeid, Integer isinvoice, String invoicename, String detailedaddress, String receiver, String receivemobile, String receiveaddress, Double payment, Date paymenttime, Integer paymentoperator, Integer paymentmethod, String memo, Integer stateid, Integer type, Integer clientOperation, Integer invoiceid) {
        this.sourceid = sourceid;
        this.cid = cid;
        this.mid = mid;
        this.total = total;
        this.amount = amount;
        this.postage = postage;
        this.totalOriginal = totalOriginal;
        this.amountOriginal = amountOriginal;
        this.postageOriginal = postageOriginal;
        this.paymenttypeid = paymenttypeid;
        this.amountFinal = amountFinal;
        this.invoicetypeid = invoicetypeid;
        this.isinvoice = isinvoice;
        this.invoicename = invoicename;
        this.detailedaddress = detailedaddress;
        this.receiver = receiver;
        this.receivemobile = receivemobile;
        this.receiveaddress = receiveaddress;
        this.payment = payment;
        this.paymenttime = paymenttime;
        this.paymentoperator = paymentoperator;
        this.paymentmethod = paymentmethod;
        this.memo = memo;
        this.stateid = stateid;
        this.type = type;
        this.clientOperation = clientOperation;
        this.invoiceid = invoiceid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceid() {
        return sourceid;
    }

    public void setSourceid(Integer sourceid) {
        this.sourceid = sourceid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
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

    public Double getAmountFinal() {
        return amountFinal;
    }

    public void setAmountFinal(Double amountFinal) {
        this.amountFinal = amountFinal;
    }

    public Integer getInvoicetypeid() {
        return invoicetypeid;
    }

    public void setInvoicetypeid(Integer invoicetypeid) {
        this.invoicetypeid = invoicetypeid;
    }

    public Integer getIsinvoice() {
        return isinvoice;
    }

    public void setIsinvoice(Integer isinvoice) {
        this.isinvoice = isinvoice;
    }

    public String getInvoicename() {
        return invoicename;
    }

    public void setInvoicename(String invoicename) {
        this.invoicename = invoicename;
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

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Date getPaymenttime() {
        return paymenttime;
    }

    public void setPaymenttime(Date paymenttime) {
        this.paymenttime = paymenttime;
    }

    public Integer getPaymentoperator() {
        return paymentoperator;
    }

    public void setPaymentoperator(Integer paymentoperator) {
        this.paymentoperator = paymentoperator;
    }

    public Integer getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(Integer paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Integer getClientOperation() {
        return clientOperation;
    }

    public void setClientOperation(Integer clientOperation) {
        this.clientOperation = clientOperation;
    }

    public Integer getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Integer invoiceid) {
        this.invoiceid = invoiceid;
    }

    @Override
    public String toString() {
        return "MemberOrderEntity{" +
                "id=" + id +
                ", sourceid=" + sourceid +
                ", cid=" + cid +
                ", mid=" + mid +
                ", total=" + total +
                ", amount=" + amount +
                ", postage=" + postage +
                ", totalOriginal=" + totalOriginal +
                ", amountOriginal=" + amountOriginal +
                ", postageOriginal=" + postageOriginal +
                ", paymenttypeid=" + paymenttypeid +
                ", amountFinal=" + amountFinal +
                ", invoicetypeid=" + invoicetypeid +
                ", isinvoice=" + isinvoice +
                ", invoicename='" + invoicename + '\'' +
                ", detailedaddress='" + detailedaddress + '\'' +
                ", receiver='" + receiver + '\'' +
                ", receivemobile='" + receivemobile + '\'' +
                ", receiveaddress='" + receiveaddress + '\'' +
                ", payment=" + payment +
                ", paymenttime=" + paymenttime +
                ", paymentoperator=" + paymentoperator +
                ", paymentmethod=" + paymentmethod +
                ", memo='" + memo + '\'' +
                ", stateid=" + stateid +
                ", type=" + type +
                ", clientOperation=" + clientOperation +
                ", invoiceid=" + invoiceid +
                '}';
    }
}
