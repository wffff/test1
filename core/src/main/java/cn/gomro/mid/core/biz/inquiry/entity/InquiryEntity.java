package cn.gomro.mid.core.biz.inquiry.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/1/17.
 */
@Entity
@Cacheable
@Table(name = "t_inquiry_2")
//@Table(name = "t_inquiry_price") // former name
public class InquiryEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private TempUserEntity member;//用户
    private String contacts;
    private String qq;
    private Integer status;//'询价单状态 0、新建 1、审核中 2、全部驳回 3、未报价 4、部分报价中 5、报价中 6、全部结束 7、全部完成 8、全部关闭'
    private String description;
    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;

    @Transient
    @OneToMany(targetEntity = InquiryGoodsEntity.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "inquiry")
    private List<InquiryGoodsEntity> goods = new ArrayList<>();

    public InquiryEntity() {
    }

    public InquiryEntity(String contacts, String qq, Integer status, String description, Timestamp expire, List<InquiryGoodsEntity> goods) {
        this.contacts = contacts;
        this.qq = qq;
        this.status = status;
        this.description = description;
        this.expire = expire;
        this.goods = goods;
    }


    @Override
    public String toString() {
        return "InquiryEntity{" +
                "id=" + id +
                ", member=" + member +
                ", contacts='" + contacts + '\'' +
                ", qq='" + qq + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", expire=" + expire +
                ", goods=" + goods +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TempUserEntity getMember() {
        return member;
    }

    public void setMember(TempUserEntity member) {
        this.member = member;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public List<InquiryGoodsEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<InquiryGoodsEntity> goods) {
        this.goods = goods;
    }
}
