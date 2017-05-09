package cn.gomro.mid.core.biz.order.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by adam on 2017/4/27.
 */
@Entity
@Table(name = "t_member_invoices")
public class MemberInvoicesEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer mid;
    private Integer invoicetypeid;
    private String invoicename;
    private String invoiceaddress;
    private String invoicephone;
    private String invoiceaccount;
    private String invoicetax;
    private String invoicebank;
    private String invoicesendaddress;
    private String invoicesendcontact;
    private String invoicesendphone;
    private Integer ispass;
    @Column(columnDefinition = "TIMESTAMP(0) default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passtime;

    public MemberInvoicesEntity() {
    }

    public MemberInvoicesEntity(Integer mid, Integer invoicetypeid, String invoicename, String invoiceaddress, String invoicephone, String invoiceaccount, String invoicetax, String invoicebank, String invoicesendaddress, String invoicesendcontact, String invoicesendphone, Integer ispass, Date passtime) {
        this.mid = mid;
        this.invoicetypeid = invoicetypeid;
        this.invoicename = invoicename;
        this.invoiceaddress = invoiceaddress;
        this.invoicephone = invoicephone;
        this.invoiceaccount = invoiceaccount;
        this.invoicetax = invoicetax;
        this.invoicebank = invoicebank;
        this.invoicesendaddress = invoicesendaddress;
        this.invoicesendcontact = invoicesendcontact;
        this.invoicesendphone = invoicesendphone;
        this.ispass = ispass;
        this.passtime = passtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getInvoicetypeid() {
        return invoicetypeid;
    }

    public void setInvoicetypeid(Integer invoicetypeid) {
        this.invoicetypeid = invoicetypeid;
    }

    public String getInvoicename() {
        return invoicename;
    }

    public void setInvoicename(String invoicename) {
        this.invoicename = invoicename;
    }

    public String getInvoiceaddress() {
        return invoiceaddress;
    }

    public void setInvoiceaddress(String invoiceaddress) {
        this.invoiceaddress = invoiceaddress;
    }

    public String getInvoicephone() {
        return invoicephone;
    }

    public void setInvoicephone(String invoicephone) {
        this.invoicephone = invoicephone;
    }

    public String getInvoiceaccount() {
        return invoiceaccount;
    }

    public void setInvoiceaccount(String invoiceaccount) {
        this.invoiceaccount = invoiceaccount;
    }

    public String getInvoicetax() {
        return invoicetax;
    }

    public void setInvoicetax(String invoicetax) {
        this.invoicetax = invoicetax;
    }

    public String getInvoicebank() {
        return invoicebank;
    }

    public void setInvoicebank(String invoicebank) {
        this.invoicebank = invoicebank;
    }

    public String getInvoicesendaddress() {
        return invoicesendaddress;
    }

    public void setInvoicesendaddress(String invoicesendaddress) {
        this.invoicesendaddress = invoicesendaddress;
    }

    public String getInvoicesendcontact() {
        return invoicesendcontact;
    }

    public void setInvoicesendcontact(String invoicesendcontact) {
        this.invoicesendcontact = invoicesendcontact;
    }

    public String getInvoicesendphone() {
        return invoicesendphone;
    }

    public void setInvoicesendphone(String invoicesendphone) {
        this.invoicesendphone = invoicesendphone;
    }

    public Integer getIspass() {
        return ispass;
    }

    public void setIspass(Integer ispass) {
        this.ispass = ispass;
    }

    public Date getPasstime() {
        return passtime;
    }

    public void setPasstime(Date passtime) {
        this.passtime = passtime;
    }
}
