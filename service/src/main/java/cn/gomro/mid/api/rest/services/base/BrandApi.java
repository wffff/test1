package cn.gomro.mid.api.rest.services.base;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.goods.biz.ICategoryBizLocal;
import cn.gomro.mid.core.biz.goods.entity.GoodsBrandEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsCategoryEntityWithOutTree;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsBrandService;
import cn.gomro.mid.core.biz.goods.service.IGoodsService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.ChinaInitialUtil;
import cn.gomro.mid.core.common.utils.Utils;
import org.apache.commons.lang.StringUtils;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by yaoo on 2016/8/23.
 */
@Path("/brand")
@Produces(RestMediaType.JSON_HEADER)
public class BrandApi extends AbstractApi {

    @EJB
    private IGoodsBrandService brandService;
    @EJB
    ICategoryBizLocal categoryBiz;
    @EJB
    IGoodsService goodsService;

    public BrandApi() {
    }

    /**
     * 分页获取 商城 品牌列表
     *
     * @return
     */
    @GET
    @Path("/shop/list")
    public ReturnMessage<List<GoodsBrandEntity>> queryShopBrandList(@QueryParam("name") String name,
                                                                    @QueryParam("page") Integer page,
                                                                    @QueryParam("size") Integer size) {
        GoodsType type = GoodsType.SHOP;

        GoodsBrandEntity goodsBrandEntity = new GoodsBrandEntity();
        goodsBrandEntity.setType(type);
        goodsBrandEntity.setName(name);

        size = Utils.reqPageSize(size);
        page = Utils.reqInt(page, 1);

        return brandService.getItemsPaged(goodsBrandEntity, page, size);
    }

    /**
     * 分页获取 市场 品牌列表
     *
     * @return
     */
    @GET
    @Path("/market/list")
    public ReturnMessage<List<GoodsBrandEntity>> queryMarketBrandList(@QueryParam("name") String name,
                                                                      @QueryParam("page") Integer page,
                                                                      @QueryParam("size") Integer size) {
        GoodsType type = GoodsType.MARKET;

        GoodsBrandEntity goodsBrandEntity = new GoodsBrandEntity();
        goodsBrandEntity.setType(type);
        goodsBrandEntity.setName(name);

        size = Utils.reqPageSize(size);
        page = Utils.reqInt(page, 1);

        return brandService.getItemsPaged(goodsBrandEntity, page, size);
    }

    /**
     * 不分页获取 商城 品牌列表(map简洁版本)
     *
     * @param name 字母搜索关键字
     * @return
     */
    @GET
    @Path("/shop/shortlist")
    public ReturnMessage<List> queryShopShortBrandList(@QueryParam("name") String name) {
        GoodsType type = GoodsType.SHOP;
        return this.shortList(type,name);
    }

    /**
     * 不分页获取 市场 品牌列表(map简洁版本)
     */
    @GET
    @Path("/market/shortlist")
    public ReturnMessage<List> queryMarketShortBrandList(@QueryParam("name") String name) {
        GoodsType type = GoodsType.MARKET;
        return this.shortList(type,name);
    }

    /**
     * map简洁版本 map
     */
    private ReturnMessage<List> shortList(GoodsType type,String name) {
        List<GoodsBrandEntity> data = brandService.getShortBrandList(type).getData();
        List brand = new ArrayList();
        for (GoodsBrandEntity b : data) {
            Character wordsIndexStr = ChinaInitialUtil.getWordsIndexStr(b.getName(), false);
            Map<String, Object> map = new HashMap<>();
            map.put("brand_id", b.getId());
            map.put("logo",b.getLogo());
            map.put("name", b.getName());
            map.put("sortLetters", wordsIndexStr);
            if (StringUtils.isNotBlank(name)) {
                //如果输入的是一个字符则仅搜索以此开头的品牌，如果输入是多个字符则搜索包含关系
                if (name.length() >= 2) {
                    if (b.getName().toLowerCase().contains(name)) {
                        brand.add(map);
                    }
                } else {
                    if(name.equals("9") && (b.getName().toLowerCase().startsWith("0") ||
                            b.getName().toLowerCase().startsWith("1") ||
                            b.getName().toLowerCase().startsWith("2") ||
                            b.getName().toLowerCase().startsWith("3") ||
                            b.getName().toLowerCase().startsWith("4") ||
                            b.getName().toLowerCase().startsWith("5") ||
                            b.getName().toLowerCase().startsWith("6") ||
                            b.getName().toLowerCase().startsWith("7") ||
                            b.getName().toLowerCase().startsWith("8") ||
                            b.getName().toLowerCase().startsWith("9"))){
                        brand.add(map);
                    }else if (name.charAt(0)==wordsIndexStr) {
                        brand.add(map);
                    }
                }
            } else {
                brand.add(map);
            }
        }
        return ReturnMessage.message(brand.size(), String.valueOf(brand.size()), brand);
    }

    /**
     * 根据分类Id获取 商城 品牌列表
     */
    @GET
    @Path("/shop/category/id/{id}")
    public ReturnMessage<List<GoodsBrandEntity>> queryShopBrandList(@PathParam("id") Integer categoryId) {
        GoodsType type = GoodsType.SHOP;
        return this.queryBrandList(type,categoryId);
    }

    /**
     * 根据分类Id获取 市场 品牌列表
     */
    @GET
    @Path("/market/category/id/{id}")
    public ReturnMessage<List<GoodsBrandEntity>> queryMarketBrandList(@PathParam("id") Integer categoryId) {
        GoodsType type = GoodsType.MARKET;
        return this.queryBrandList(type,categoryId);
    }

    private ReturnMessage<List<GoodsBrandEntity>> queryBrandList(GoodsType type,Integer categoryId){
        if (categoryId != null && categoryId > 0) {
            List<GoodsCategoryEntityWithOutTree> data = categoryBiz.querySelfAndListById(type, categoryId, false).getData();
            List<Integer> idList = new ArrayList<>();
            for (GoodsCategoryEntityWithOutTree c : data) {
                idList.add(c.getId());
            }
            return brandService.getBrandListByCategoryId(type, idList, categoryId);
        }
        return ReturnMessage.failed();
    }


    /**
     * 根据ID获取品牌对象
     *
     * @return
     */
    @GET
    @Path("/id/{id}")
    public ReturnMessage<GoodsBrandEntity> getBrand(@PathParam("id") Integer id) {
        return brandService.getItem(id);
    }

    /**
     * 根据ID获取品牌名称
     *
     * @return
     */
    @GET
    @Path("/name/id/{id}")
    public ReturnMessage<GoodsBrandEntity> getBrandName(@PathParam("id") Integer id) {
        return brandService.getName(id);
    }

    /**
     * 根据品牌名获取 市场 品牌对象
     *
     * @param name
     * @return
     */
    @GET
    @Path("/market/name/{name}")
    public ReturnMessage<GoodsBrandEntity> getMarketBrandByName(@PathParam("name") String name) {
        GoodsType type = GoodsType.MARKET;
        return brandService.getBrandByName(type, name);
    }

    /**
     * 根据品牌名获取 商城 品牌对象
     *
     * @param name
     * @return
     */
    @GET
    @Path("/shop/name/{name}")
    public ReturnMessage<GoodsBrandEntity> getShopBrandByName(@PathParam("name") String name) {
        GoodsType type = GoodsType.SHOP;
        return brandService.getBrandByName(type, name);
    }

    /**
     * 增加一个市场品牌
     *
     * @param name        名称
     * @param nameEn      英文名
     * @param nameCn      中文名
     * @param logo        标志
     * @param description 描述
     * @param sort        序号
     * @return
     */
    @POST
    @Path("/market/add")
    public ReturnMessage<GoodsBrandEntity> addMarketBrand(String name, String nameEn, String nameCn, String logo, String description, Integer sort) {
        GoodsType type = GoodsType.MARKET;
        GoodsBrandEntity goodsBrandEntity = new GoodsBrandEntity(type, name, nameEn, nameCn, logo, description, sort, true);
        return brandService.addItem(goodsBrandEntity);
    }

    /**
     * 增加一个商城品牌
     *
     * @param name        名称
     * @param nameEn      英文名
     * @param nameCn      中文名
     * @param logo        标志
     * @param description 描述
     * @param sort        序号
     * @return
     */
    @POST
    @Path("/shop/add")
    @Consumes(MediaType.WILDCARD)
    public ReturnMessage<GoodsBrandEntity> addShopBrand(String name,
                                                        String nameEn,
                                                        String nameCn,
                                                        String logo,
                                                        String description,
                                                        Integer sort) {
        GoodsType type = GoodsType.SHOP;
        Date date = new Date();
        GoodsBrandEntity goodsBrandEntity = new GoodsBrandEntity(type, name, nameEn, nameCn, logo, description, sort, true);
        return brandService.addItem(goodsBrandEntity);
    }

    /**
     * 删除一个品牌
     *
     * @param id
     * @return
     */
    @GET
    @Path("/del/id/{id}")
    public ReturnMessage delBrand(@PathParam("id") Integer id) {
        GoodsBrandEntity entity = brandService.getItem(id).getData();
        return brandService.delItem(entity);
    }

    /**
     * 根据id编辑品牌
     *
     * @param id
     * @param name        品牌名
     * @param nameEn
     * @param nameCn
     * @param logo
     * @param description
     * @param sort
     * @return
     */
    @POST
    @Path("/edit/id/{id}")
    public ReturnMessage editBrand(@PathParam("id") Integer id, String name, String nameEn, String nameCn, String logo, String description, Integer sort) {
        GoodsBrandEntity entity = brandService.getItem(id).getData();
        if (entity != null) {
            if (StringUtils.isNotBlank(name)) entity.setName(name);
            if (StringUtils.isNotBlank(nameCn)) entity.setNameCn(nameCn);
            if (StringUtils.isNotBlank(nameEn)) entity.setNameEn(nameEn);
            if (StringUtils.isNotBlank(logo)) entity.setLogo(logo);
            if (StringUtils.isNotBlank(description)) entity.setDescription(description);
            if (sort != null) entity.setSort(sort);
            return brandService.editItem(entity);
        }
        return ReturnMessage.failed();
    }

    @GET
    @Path("/edit/forSale/{id}")
    public ReturnMessage editBrand(@PathParam("id") Integer id){
        GoodsBrandEntity entity = brandService.getItem(id).getData();
        if (entity != null) {
            entity.setSaled(false);
            goodsService.forSaleGoodsByBrandId(id);
            return brandService.editItem(entity);
        }
        return null;
    }

}
