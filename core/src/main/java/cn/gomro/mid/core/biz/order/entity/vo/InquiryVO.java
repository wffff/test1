package cn.gomro.mid.core.biz.order.entity.vo;

import cn.gomro.mid.core.biz.inquiry.entity.InquiryEntity;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryGoodsEntity;
import cn.gomro.mid.core.common.utils.DateUtils;
import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.FormParam;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/4/27.
 * 询价单对象
 */
public class InquiryVO implements Serializable {

    private Integer id;
    @FormParam(value="contacts")
    private String contacts;
    @FormParam(value="qq")
    private String qq;
    @FormParam(value = "description")
    private String description;
    @FormParam("goods")
    private List<InquiryGoodsVO> goods;

    public InquiryEntity toInquiryEntity(InquiryVO inquiry) {
        List<InquiryGoodsEntity> inquiryGoods = new ArrayList<>();
        List<InquiryGoodsVO> goods = inquiry.getGoods();
        Date time = new Date();
        for (InquiryGoodsVO goodsVO : goods) {
            InquiryGoodsEntity inquiryGoodsEntity = goodsVO.toInquiryGoodsEntity(goodsVO);
            inquiryGoods.add(inquiryGoodsEntity);
            Date dateFromString = DateUtils.getDateFromString(goodsVO.getExpire());
            if(dateFromString.getTime()>time.getTime()){
                time=dateFromString;
            }
        }
        // TODO: 2017/5/4 询价单时间 
        if(time.getTime()<=System.currentTimeMillis()){
            time = new Date(System.currentTimeMillis()+(1000*10*6));
        }
        InquiryEntity inquiryEntity = new InquiryEntity(inquiry.getContacts(), inquiry.getQq(),
                7, inquiry.getDescription(), new Timestamp(time.getTime()), inquiryGoods);
        return inquiryEntity;
    }

    public InquiryVO() {
    }

    public InquiryVO(Integer id, String contacts, String qq, String description, List<InquiryGoodsVO> goods) {
        this.id = id;
        this.contacts = contacts;
        this.qq = qq;
        this.description = description;
        this.goods = goods;
    }

    public static InquiryVO valueOf(String value){
        return JSONObject.parseObject(value,InquiryVO.class);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<InquiryGoodsVO> getGoods() {
        return goods;
    }

    public void setGoods(List<InquiryGoodsVO> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "InquiryVO{" +
                "id=" + id +
                ", contacts='" + contacts + '\'' +
                ", qq='" + qq + '\'' +
                ", description='" + description + '\'' +
                ", goods=" + goods +
                '}';
    }
}