package cn.gomro.mid.core.biz.goods.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yaodw on 2016/7/15.
 */
@Entity
@Table(name = "t_vendor")
@Cacheable
public class CorporationEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 200, nullable = false, insertable = false, updatable = false)
    private String name;
    //    private Boolean isCustomer;
    //    private Boolean isGroupCustomer;
    //    private Boolean isVendor;
    //    private Boolean isGroupVendor;
    @Column(name = "companyname")
    private String companyName;
    private Boolean act;
    private Integer isshowname;  //是否显示商家名称  0不显示   ，  1显示

    private String place;			//发货地
    private String mobile;			//接收通知信息手机
    private String email;			//接收通知邮箱
    private String qq;				//QQ
    private String contact;			//联系人
    private String phone;			//联系电话

    public CorporationEntity() {
    }

    public CorporationEntity(String name, Boolean act, Integer isshowname, Boolean del, Date last, Date time) {
        this.name = name;
        this.act = act;
        this.isshowname = isshowname;
        this.del = del;
        this.last = last;
        this.time = time;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return companyName;
    }

    public void setName(String name) {
        this.companyName = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getIsshowname() {
        return isshowname;
    }

    public void setIsshowname(Integer isshowname) {
        this.isshowname = isshowname;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //    public Boolean getCustomer() {
//        return isCustomer;
//    }
//
//    public void setCustomer(Boolean customer) {
//        isCustomer = customer;
//    }
//
//    public Boolean getGroupCustomer() {
//        return isGroupCustomer;
//    }
//
//    public void setGroupCustomer(Boolean groupCustomer) {
//        isGroupCustomer = groupCustomer;
//    }
//
//    public Boolean getVendor() {
//        return isVendor;
//    }
//
//    public void setVendor(Boolean vendor) {
//        isVendor = vendor;
//    }
//
//    public Boolean getGroupVendor() {
//        return isGroupVendor;
//    }
//
//    public void setGroupVendor(Boolean groupVendor) {
//        isGroupVendor = groupVendor;
//    }

    public Boolean getAct() {
        return act;
    }

    public void setAct(Boolean act) {
        this.act = act;
    }

    @Override
    public String toString() {
        return "MemberEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyName='" + companyName + '\'' +
                ", act=" + act +
                ", isshowname=" + isshowname +
                ", place='" + place + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
