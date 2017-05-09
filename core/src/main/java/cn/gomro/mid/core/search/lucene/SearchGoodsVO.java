package cn.gomro.mid.core.search.lucene;

import cn.gomro.mid.core.biz.goods.entity.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by momo on 16/8/12.
 */
public class SearchGoodsVO implements Serializable {

    public static final String FIELD_INDEX_SUFFIX = "_IDX";

    public static final String FIELD_ID = "id";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_IMAGES = "images";
    public static final String FIELD_CATEGORY_ID = "categoryId";
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_BRAND_ID = "brandId";
    public static final String FIELD_BRAND = "brand";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_MODEL = "model";
    public static final String FIELD_SPEC = "spec";
    public static final String FIELD_TAGS = "tags";
    public static final String FIELD_PRICE_MIN = "priceMin";
    public static final String FIELD_PRICE_MAX = "priceMax";
    public static final String FIELD_SALES = "sales";
    public static final String FIELD_VIEWS = "views";
    public static final String FIELD_WAREHOUSE_ADDRESS = "warehouseAddress";
    public static final String FIELD_MEMBER_ID = "memberId";
    public static final String FIELD_MEMBER_QQ = "qq";

    private String id;             //商品ID
    private String type;           //商品类型
    private String images;      //商品图片
    private String categoryId;     //商品类目ID
    private String category;    //类目
    private String brandId;        //品牌
    private String brand;       //品牌
    private String name;        //商品名称
    private String model;       //型号
    private String spec;        //规格
    private String tags;        //商品标签
    private String priceMin;    //商品区间价格最低价
    private String priceMax;    //商品区间价格最高价
    private String sales;          //已售数
    private String views;          //浏览量
    private String warehouseAddress;//发货仓库地址
    private String memberId;       //供应商ID
    private String qq;       //供应商QQ

    public SearchGoodsVO() {
    }

    public SearchGoodsVO(String id, String type, String images, String categoryId, String category, String brandId, String brand, String name, String model, String spec, String tags, String priceMin, String priceMax, String sales, String views, String warehouseAddress, String memberId, String qq) {
        this.id = id;
        this.type = type;
        this.images = images;
        this.categoryId = categoryId;
        this.category = category;
        this.brandId = brandId;
        this.brand = brand;
        this.name = name;
        this.model = model;
        this.spec = spec;
        this.tags = tags;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.sales = sales;
        this.views = views;
        this.warehouseAddress = warehouseAddress;
        this.memberId = memberId;
        this.qq=qq;
    }


    public SearchGoodsVO(GoodsEntity entity){
        String priceMax = entity.getPriceMax() == null ? "" : String.valueOf(entity.getPriceMax());
        Set<String> memberId = new LinkedHashSet<>();
        Set<String> memberQQ = new LinkedHashSet<>();
        Set<String> modelName = new LinkedHashSet<>();
        Set<String> specName = new LinkedHashSet<>();
        Set<String> warehouseAddress = new LinkedHashSet<>();
        List<GoodsModelEntity> goodsModelList = entity.getGoodsModelList();
        for (GoodsModelEntity modelEntity : goodsModelList) {
            modelName.add(modelEntity.getName());
            List<GoodsSpecEntity> goodsSpecList = modelEntity.getGoodsSpecList();
            for (GoodsSpecEntity spec : goodsSpecList) {
                memberQQ.add(spec.getCorporation().getQq());
                memberId.add(spec.getCorporation().getId() + "");
                specName.add(String.valueOf(spec.getName()));
                List<MemberInventoryEntity> memberInventoryList = spec.getMemberInventoryList();
                for (MemberInventoryEntity memberInventory : memberInventoryList) {
                    warehouseAddress.add(memberInventory.getWarehouse().getAddress());
                }
            }
        }

        String modelNameStr = "";
        String specNameStr = "";
        String warehouseAddressStr = "";
        String memberIdStr = "";
        String memberQQStr = "";
        if (modelName.size() > 0) {
            modelNameStr = modelName.toString().substring(1, modelName.toString().length() - 1);
        }
        if (specName.size() > 0) {
            specNameStr = specName.toString().substring(1, specName.toString().length() - 1);
        }
        if (warehouseAddress.size() > 0) {
            specNameStr = warehouseAddress.toString().substring(1, warehouseAddress.toString().length() - 1);
        }
        if (memberId.size() > 0) {
            memberIdStr = memberId.toString().substring(1, memberId.toString().length() - 1);
        }
        if (memberQQ.size() > 0) {
            memberQQStr = memberQQ.toString().substring(1, memberQQ.toString().length() - 1);
        }
        this.id = String.valueOf(entity.getId());
        this.type = String.valueOf(entity.getType().ordinal());
        this.images = entity.getImages();
        this.categoryId = String.valueOf(entity.getCategory().getId());
        this.category = entity.getCategory().getName();
        this.brandId = String.valueOf(entity.getBrand().getId());
        this.brand = entity.getBrand().getName();
        this.name = entity.getName();
        this.model = modelNameStr;
        this.spec = specNameStr;
        this.tags = entity.getTags();
        this.priceMin = String.valueOf(entity.getPriceMin());
        this.priceMax = priceMax;
        this.sales = String.valueOf(entity.getSales());
        this.views = String.valueOf(entity.getViews());
        this.warehouseAddress = warehouseAddressStr;
        this.memberId = memberIdStr;
        this.qq=memberQQStr;
    }

    public SearchGoodsVO(GoodsEntityManyToOne entity) {
        String priceMax = entity.getPriceMax() == null ? "" : String.valueOf(entity.getPriceMax());
        Set<String> memberId = new LinkedHashSet<>();
        Set<String> memberQQ = new LinkedHashSet<>();
        Set<String> modelName = new LinkedHashSet<>();
        Set<String> specName = new LinkedHashSet<>();
        Set<String> warehouseAddress = new LinkedHashSet<>();
        List<GoodsModelEntityManyToOne> goodsModelList = entity.getGoodsModelList();
        for (GoodsModelEntityManyToOne modelEntity : goodsModelList) {
            modelName.add(modelEntity.getName());
            List<GoodsSpecEntityManyToOne> goodsSpecList = modelEntity.getGoodsSpecList();
            for (GoodsSpecEntityManyToOne spec : goodsSpecList) {
                memberQQ.add(spec.getCorporation().getQq());
                memberId.add(spec.getCorporation().getId() + "");
                specName.add(String.valueOf(spec.getName()));
            }
        }

        String modelNameStr = "";
        String specNameStr = "";
        String memberIdStr = "";
        String memberQQStr = "";
        if (modelName.size() > 0) {
            modelNameStr = modelName.toString().substring(1, modelName.toString().length() - 1);
        }
        if (specName.size() > 0) {
            specNameStr = specName.toString().substring(1, specName.toString().length() - 1);
        }
        if (memberId.size() > 0) {
            memberIdStr = memberId.toString().substring(1, memberId.toString().length() - 1);
        }
        if (memberQQ.size() > 0) {
            memberQQStr = memberQQ.toString().substring(1, memberQQ.toString().length() - 1);
        }
        this.id = String.valueOf(entity.getId());
        this.type = String.valueOf(entity.getType().ordinal());
        this.images = entity.getImages();
        this.categoryId = String.valueOf(entity.getCategory().getId());
        this.category = entity.getCategory().getName();
        this.brandId = String.valueOf(entity.getBrand().getId());
        this.brand = entity.getBrand().getName();
        this.name = entity.getName();
        this.model = modelNameStr;
        this.spec = specNameStr;
        this.tags = entity.getTags();
        this.priceMin = String.valueOf(entity.getPriceMin());
        this.priceMax = priceMax;
        this.sales = String.valueOf(entity.getSales());
        this.views = String.valueOf(entity.getViews());
        this.memberId = memberIdStr;
        this.qq = memberQQStr;
    }

    public SearchGoodsVO(GoodsSpecEntityManyToOne entity){
        String priceMax = entity.getModel().getGoods().getPriceMax() == null ? "" : String.valueOf(entity.getModel().getGoods().getPriceMax());
        this.id = String.valueOf(entity.getModel().getGoods().getId());
        this.type = String.valueOf(entity.getModel().getGoods().getType().ordinal());
        this.images = entity.getModel().getGoods().getImages();
        this.categoryId = String.valueOf(entity.getModel().getGoods().getCategory().getId());
        this.category = entity.getModel().getGoods().getCategory().getName();
        this.brandId = String.valueOf(entity.getModel().getGoods().getBrand().getId());
        this.brand = entity.getModel().getGoods().getBrand().getName();
        this.name = entity.getModel().getGoods().getName();
        this.model = entity.getModel().getName();
        this.spec = entity.getName();
        this.tags = entity.getModel().getGoods().getTags();
        this.priceMin = String.valueOf(entity.getModel().getGoods().getPriceMin());
        this.priceMax = priceMax;
        this.sales = String.valueOf(entity.getModel().getGoods().getSales());
        this.views = String.valueOf(entity.getModel().getGoods().getViews());
        this.memberId = String.valueOf(entity.getCorporation().getId());
        this.qq=entity.getCorporation().getQq();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchGoodsVO that = (SearchGoodsVO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (images != null ? !images.equals(that.images) : that.images != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (brandId != null ? !brandId.equals(that.brandId) : that.brandId != null) return false;
        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (spec != null ? !spec.equals(that.spec) : that.spec != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        if (priceMin != null ? !priceMin.equals(that.priceMin) : that.priceMin != null) return false;
        if (priceMax != null ? !priceMax.equals(that.priceMax) : that.priceMax != null) return false;
        if (sales != null ? !sales.equals(that.sales) : that.sales != null) return false;
        if (views != null ? !views.equals(that.views) : that.views != null) return false;
        if (warehouseAddress != null ? !warehouseAddress.equals(that.warehouseAddress) : that.warehouseAddress != null)
            return false;
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        return qq != null ? qq.equals(that.qq) : that.qq == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (brandId != null ? brandId.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (spec != null ? spec.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (priceMin != null ? priceMin.hashCode() : 0);
        result = 31 * result + (priceMax != null ? priceMax.hashCode() : 0);
        result = 31 * result + (sales != null ? sales.hashCode() : 0);
        result = 31 * result + (views != null ? views.hashCode() : 0);
        result = 31 * result + (warehouseAddress != null ? warehouseAddress.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (qq != null ? qq.hashCode() : 0);
        return result;
    }
}
