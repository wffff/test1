package cn.gomro.mid.core.biz.order.entity.vo;

import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.FormParam;
import java.io.Serializable;
import java.util.List;

/**
 * Created by adam on 2017/4/27.
 * 供应商对象
 */
public class VendorVO implements Serializable {
    private Integer id;
    @FormParam(value = "total")
    private Double total;
    @FormParam(value = "postAmount")
    private Double postAmount;
    @FormParam(value = "amount")
    private Double amount;
    @FormParam(value = "memo")
    private String memo;
    @FormParam("vendorOrderGoods")
    private List<VendorOrderGoodsVO> vendorOrderGoods;
    @FormParam(value = "vendorUsername")
    String vendorUsername;
    public static VendorVO valueOf(String value){
        return JSONObject.parseObject(value,VendorVO.class);
    }

    public VendorVO() {
    }

    public VendorVO(String vendorUsername,Integer id, Double total, Double postAmount, Double amount, String memo, List<VendorOrderGoodsVO> vendorOrderGoods) {
        this.vendorUsername=vendorUsername;
        this.id = id;
        this.total = total;
        this.postAmount = postAmount;
        this.amount = amount;
        this.memo = memo;
        this.vendorOrderGoods = vendorOrderGoods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<VendorOrderGoodsVO> getVendorOrderGoods() {
        return vendorOrderGoods;
    }

    public void setVendorOrderGoods(List<VendorOrderGoodsVO> vendorOrderGoods) {
        this.vendorOrderGoods = vendorOrderGoods;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getVendorUsername() {
        return vendorUsername;
    }

    public void setVendorUsername(String vendorUsername) {
        this.vendorUsername = vendorUsername;
    }

    @Override
    public String toString() {
        return "VendorVO{" +
                "id=" + id +
                ", total=" + total +
                ", postAmount=" + postAmount +
                ", amount=" + amount +
                ", memo='" + memo + '\'' +
                ", vendorOrderGoods=" + vendorOrderGoods +
                '}';
    }
}