package cn.gomro.mid.core.biz.goods.biz;

import java.io.Serializable;

/**
 * Created by yaodw on 2016/7/19.
 */
public class GoodsVO implements Serializable {

    //以下字段是提交数据时必须提供的参数
    private Integer rowNumber;//Excel行号
    private Integer flag;//标识 1：信息错误或不完整  2：重复上传  3：重复上传且已更新
    private Integer uid;//上传者id
    private Integer uType;//上传者类型（1:uid 2：mid）
    private Integer memberId;//供应商id
    private Integer pid;//父级分类（编辑时可选）
    //返回添加成功之后的商品Id
    private Integer id;

    // 以下字段务必和EXCEL文件保持一致！！！
    private String category;                     //类目
    private String brand;                        //品牌
    private String name;                         //商品名称
    private String model;                        //型号
    private String spec;                         //规格
    private String packageNum;                  //包装规格
    private String packageUnit;                 //包装单位
    private String minOrder;                    //最小起订量
    private String unit;                         //销售单位
    private String price;                         //价格
    private String marketPrice;                 //市场价
    private String discount;                    //折扣(%)
    private String delivery;                    //交货期(天)
    private String goodsDescript;                   //商品描述
    private String length;                           //长
    private String width;                            //宽
    private String height;                           //高
    private String weight;                           //重量
    private String warranty;                    //质保说明
    private String InventoryNum;                    //库存量
    private String warehouseName;                   //所在仓库
    private String memo;                                 //备注
    private String sku;                                  //sku
    private String freightTemplate;                 //运费模板

    public GoodsVO() {
    }

    public GoodsVO(Integer rowNumber, Integer flag, Integer uid, Integer uType, Integer memberId, Integer pid, String category, String brand, String name, String model, String spec, String packageNum, String packageUnit, String sku, String weight, String length, String width, String height, String warranty, String minOrder, String unit, String price, String marketPrice, String discount, String delivery, String goodsDescript, String inventoryNum, String warehouseName, String memo, String freightTemplate) {
        this.rowNumber = rowNumber;
        this.flag = flag;
        this.freightTemplate = freightTemplate;
        this.uid = uid;
        this.uType = uType;
        this.memberId = memberId;
        this.pid = pid;
        this.category = category;
        this.brand = brand;
        this.name = name;
        this.model = model;
        this.spec = spec;
        this.packageNum = packageNum;
        this.packageUnit = packageUnit;
        this.sku = sku;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.warranty = warranty;
        this.minOrder = minOrder;
        this.unit = unit;
        this.price = price;
        this.marketPrice = marketPrice;
        this.discount = discount;
        this.delivery = delivery;
        this.goodsDescript = goodsDescript;
        InventoryNum = inventoryNum;
        this.warehouseName = warehouseName;
        this.memo = memo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPackageUnit() {
        return packageUnit;
    }

    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getuType() {
        return uType;
    }

    public void setuType(Integer uType) {
        this.uType = uType;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getFreightTemplate() {
        return freightTemplate;
    }

    public void setFreightTemplate(String freightTemplate) {
        this.freightTemplate = freightTemplate;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(String packageNum) {
        this.packageNum = packageNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(String minOrder) {
        this.minOrder = minOrder;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getGoodsDescript() {
        return goodsDescript;
    }

    public void setGoodsDescript(String goodsDescript) {
        this.goodsDescript = goodsDescript;
    }

    public String getInventoryNum() {
        return InventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        InventoryNum = inventoryNum;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
