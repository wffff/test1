package cn.gomro.mid.core.biz.order.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;

/**
 * 订单产品列表
 */
@Entity
@Table(name = "t_member_order_product")
public class MemberOrderProductEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer vid;   //供货商ID
    private Integer orderid;    //总订单ID
    private Integer vorderid;   //供货商订单ID
    private Integer vorderprodid;   //供货商订单产品ID
    private String name;    //主产品名称
    private String aname;   //主销售单位
    private String vpname;  //供货商产品名称
    private String vaname;  //供货商销售单位
    private String model;   //主商品型号
    private String amodel;  //主商品销售型号
    private String vmodel;  //供货商产品型号
    private String vamodel; //供货商产品销售型号
    private String spec;    //主商品规格
    private String ASpec;   //主商品属性规格
    private String vspec;   //商品规格
    private String vASpec;  //商品属性规格
    private Double price;   //价格（可调整，付款的价格）
    @Column(name = "price_original")
    private Double priceOriginal;   //价格（不可调整，原价）
    private String deliverydate;    //交货期
    private Double number; //数量
    private Integer quote_id;   //报价商品ID
    private String pro_name;    //报价商品名称
    @Column(name = "from_dispath")
    private String fromDispath;    //发货地
    private Double freight; //运费
    private String brand;   //品牌名称
    private String unit;    //单位名称
    private String specname;    //销售单位名称
    private String memo;    //备注

    public MemberOrderProductEntity() {
    }

    public MemberOrderProductEntity(Integer vid, Integer orderid, Integer vorderid, Integer vorderprodid, String name, String aname, String vpname, String vaname, String model, String amodel, String vmodel, String vamodel, String spec, String ASpec, String vspec, String vASpec, Double price, Double priceOriginal, String deliverydate, Double number, Integer quote_id, String pro_name, String fromDispath, Double freight, String brand, String unit, String specname, String memo) {
        this.vid = vid;
        this.orderid = orderid;
        this.vorderid = vorderid;
        this.vorderprodid = vorderprodid;
        this.name = name;
        this.aname = aname;
        this.vpname = vpname;
        this.vaname = vaname;
        this.model = model;
        this.amodel = amodel;
        this.vmodel = vmodel;
        this.vamodel = vamodel;
        this.spec = spec;
        this.ASpec = ASpec;
        this.vspec = vspec;
        this.vASpec = vASpec;
        this.price = price;
        this.priceOriginal = priceOriginal;
        this.deliverydate = deliverydate;
        this.number = number;
        this.quote_id = quote_id;
        this.pro_name = pro_name;
        this.fromDispath = fromDispath;
        this.freight = freight;
        this.brand = brand;
        this.unit = unit;
        this.specname = specname;
        this.memo = memo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getVorderid() {
        return vorderid;
    }

    public void setVorderid(Integer vorderid) {
        this.vorderid = vorderid;
    }

    public Integer getVorderprodid() {
        return vorderprodid;
    }

    public void setVorderprodid(Integer vorderprodid) {
        this.vorderprodid = vorderprodid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getVpname() {
        return vpname;
    }

    public void setVpname(String vpname) {
        this.vpname = vpname;
    }

    public String getVaname() {
        return vaname;
    }

    public void setVaname(String vaname) {
        this.vaname = vaname;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAmodel() {
        return amodel;
    }

    public void setAmodel(String amodel) {
        this.amodel = amodel;
    }

    public String getVmodel() {
        return vmodel;
    }

    public void setVmodel(String vmodel) {
        this.vmodel = vmodel;
    }

    public String getVamodel() {
        return vamodel;
    }

    public void setVamodel(String vamodel) {
        this.vamodel = vamodel;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getASpec() {
        return ASpec;
    }

    public void setASpec(String ASpec) {
        this.ASpec = ASpec;
    }

    public String getVspec() {
        return vspec;
    }

    public void setVspec(String vspec) {
        this.vspec = vspec;
    }

    public String getvASpec() {
        return vASpec;
    }

    public void setvASpec(String vASpec) {
        this.vASpec = vASpec;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceOriginal() {
        return priceOriginal;
    }

    public void setPriceOriginal(Double priceOriginal) {
        this.priceOriginal = priceOriginal;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Integer getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(Integer quote_id) {
        this.quote_id = quote_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getFromDispath() {
        return fromDispath;
    }

    public void setFromDispath(String fromDispath) {
        this.fromDispath = fromDispath;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpecname() {
        return specname;
    }

    public void setSpecname(String specname) {
        this.specname = specname;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "MemberOrderProductEntity{" +
                "id=" + id +
                ", vid=" + vid +
                ", orderid=" + orderid +
                ", vorderid=" + vorderid +
                ", vorderprodid=" + vorderprodid +
                ", name='" + name + '\'' +
                ", aname='" + aname + '\'' +
                ", vpname='" + vpname + '\'' +
                ", vaname='" + vaname + '\'' +
                ", model='" + model + '\'' +
                ", amodel='" + amodel + '\'' +
                ", vmodel='" + vmodel + '\'' +
                ", vamodel='" + vamodel + '\'' +
                ", spec='" + spec + '\'' +
                ", ASpec='" + ASpec + '\'' +
                ", vspec='" + vspec + '\'' +
                ", vASpec='" + vASpec + '\'' +
                ", price=" + price +
                ", priceOriginal=" + priceOriginal +
                ", deliverydate='" + deliverydate + '\'' +
                ", number=" + number +
                ", quote_id=" + quote_id +
                ", pro_name='" + pro_name + '\'' +
                ", fromDispath='" + fromDispath + '\'' +
                ", freight=" + freight +
                ", brand='" + brand + '\'' +
                ", unit='" + unit + '\'' +
                ", specname='" + specname + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
