package cn.gomro.mid.api.rest.services;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.goods.biz.GoodsVO;
import cn.gomro.mid.core.biz.goods.biz.IGoodsBizLocal;
import cn.gomro.mid.core.biz.goods.entity.GoodsEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsEntityManyToOne;
import cn.gomro.mid.core.biz.goods.entity.GoodsSpecEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsSpecEntityManyToOne;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.Utils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
@Path("/goods")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.JSON_HEADER)
public class GoodsApi extends AbstractApi {

    final Logger logger = LoggerFactory.getLogger(GoodsApi.class);

    @EJB
    private IGoodsBizLocal goodsBiz;

    public GoodsApi() {
    }

    /**
     * 根据id 获取商品
     */
    @GET
    @Path("/id/{id}")
    public ReturnMessage<GoodsEntity> getGoods(@PathParam("id") Integer id) {
        return goodsBiz.getItem(id);
    }

    /**
     * 根据商品规格 Id 获取商品规格
     */
    @GET
    @Path("/spec/id/{specid}")
    public ReturnMessage<GoodsSpecEntityManyToOne> getGoodsSpecbySpecId(@PathParam("specid") Integer id) {
        return goodsBiz.getGoodsSpecById(id);
    }

    /**
     * 根据商品规格 Id 获取商品规格列表
     */
    @GET
    @Path("/spec/idList")
    public ReturnMessage<List<GoodsSpecEntityManyToOne>> getGoodsSpecListbySpecIds(@QueryParam("idList") String ids) {
        if (ids != null) {
            List<GoodsSpecEntityManyToOne> specList = new ArrayList<>();
            String[] split = ids.split(",");
            HashSet<Integer> idList = new HashSet<>();
            for (String str : split) {
                try {
                    Integer id = Integer.valueOf(str);
                    idList.add(id);
                } catch (NumberFormatException e) {
                    return ReturnMessage.failed();
                }
            }
            for (Integer id : idList) {
                specList.add(goodsBiz.getGoodsSpecById(id).getData());
            }
            if (specList.size() > 0)
                return ReturnMessage.success(specList);
        }
        return ReturnMessage.failed();
    }

    /**
     * 根据商品规格 Id列表 获取商品规格
     */
    @GET
    @Path("/spec/list")
    public ReturnMessage<List<GoodsSpecEntityManyToOne>> getGoodsSpecbySpecIdArray(@QueryParam("id") List<Integer> id) {
        return goodsBiz.getGoodsSpecByIds(id);
    }


    /**
     * 根据商品id，获取商品名称
     */
    @GET
    @Path("/name/id/{id}")
    public ReturnMessage getGoodsName(@PathParam("id") Integer id) {
        return goodsBiz.getName(id);
    }

    /**
     * 根据型号id，获取型号名称
     */
    @GET
    @Path("/name/model/id/{id}")
    public ReturnMessage getModelName(@PathParam("id") Integer id) {
        return goodsBiz.getModelName(id);
    }

    /**
     * 根据规格id，获取规格名称
     */
    @GET
    @Path("/name/spec/id/{id}")
    public ReturnMessage getSpecName(@PathParam("id") Integer id) {
        return goodsBiz.getSpecName(id);
    }

    /**
     * 根据条件分页检索 商城 商品列表
     *
     * @param size         每页数量
     * @param page         页码
     * @param brandId      品牌Id
     * @param categoryId   分类Id
     * @param onlyCategory 不包含子分类
     * @param brandName    品牌
     * @param goodsName    名字
     * @param orderBy      可选：salesAsc||salesDesc||priceAsc||priceDesc
     * @param priceMax     最高价格
     * @param priceMin     最低价格
     * @param modelName    型号名
     * @param specName     规格名
     * @param memberId     供应商id
     * @param memberName   供应商名
     * @param goodsSaled   商品可售
     * @param specSaled    规格可售
     * @param modelSaled   型号可售
     * @param size         页尺寸
     * @param page         页码
     * @return
     */
    @GET
    @Path("/shop/list")
    public ReturnMessage<List<GoodsEntity>> getShopGoodsByConditions(
            @QueryParam("brandId") Integer brandId, @QueryParam("categoryId") Integer categoryId,
            @QueryParam("onlyCategory") boolean onlyCategory, @QueryParam("hasImages") boolean hasImages,
            @QueryParam("brandName") String brandName, @QueryParam("goodsName") String goodsName,
            @QueryParam("modelName") String modelName, @QueryParam("specName") String specName,
            @QueryParam("memberId") Integer memberId, @QueryParam("memberName") String memberName, @QueryParam("keyWord") String keyWord,
            @QueryParam("goodsSaled") Boolean goodsSaled, @QueryParam("specSaled") Boolean specSaled,
            @QueryParam("modelSaled") Boolean modelSaled, @QueryParam("orderBy") String orderBy,
            @QueryParam("priceMax") Double priceMax, @QueryParam("priceMin") Double priceMin,
            @QueryParam("size") Integer size, @QueryParam("page") Integer page
    ) {
        GoodsType type = GoodsType.SHOP;
        return goodsBiz.queryGoodsList(type, brandId, categoryId, onlyCategory, brandName, goodsName, hasImages, modelName, specName, memberId, memberName, keyWord,
                goodsSaled, specSaled, modelSaled, orderBy, priceMin, priceMax, page, size);
    }

    /**
     * 根据条件分页检索 市场 商品列表
     */
    @GET
    @Path("/market/list")
    public ReturnMessage<List<GoodsEntity>> getMarketGoodsByConditions(
            @QueryParam("brandId") Integer brandId, @QueryParam("categoryId") Integer categoryId,
            @QueryParam("onlyCategory") boolean onlyCategory, @QueryParam("brandName") String brandName,
            @QueryParam("goodsName") String goodsName, @QueryParam("modelName") String modelName,
            @QueryParam("specName") String specName, @QueryParam("memberId") Integer memberId,
            @QueryParam("memberName") String memberName, @QueryParam("keyWord") String keyWord,
            @QueryParam("goodsSaled") Boolean goodsSaled,
            @QueryParam("specSaled") Boolean specSaled, @QueryParam("modelSaled") Boolean modelSaled,
            @QueryParam("orderBy") String orderBy, @QueryParam("priceMax") Double priceMax,
            @QueryParam("priceMin") Double priceMin, @QueryParam("size") Integer size,
            @QueryParam("page") Integer page
    ) {
        GoodsType type = GoodsType.MARKET;
        return goodsBiz.queryGoodsList(type, brandId, categoryId, onlyCategory, brandName, goodsName, false, modelName, specName, memberId, memberName, keyWord,
                goodsSaled, specSaled, modelSaled, orderBy, priceMin, priceMax, page, size);
    }

    @GET
    @Path("/sList")
    public ReturnMessage<List<GoodsEntityManyToOne>> getShopGoodsList(
            @QueryParam("brandId") Integer brandId, @QueryParam("brandName") String brandName,
            @QueryParam("categoryId") Integer categoryId, @QueryParam("onlyCategory") boolean onlyCategory,
            @QueryParam("goodsName") String goodsName, @QueryParam("hasImages") boolean hasImages,
            @QueryParam("goodsSaled") Boolean goodsSaled, @QueryParam("goodsDel") Boolean goodsDel,
            @QueryParam("orderBy") String orderBy,
            @QueryParam("priceMax") Double priceMax, @QueryParam("priceMin") Double priceMin,
            @QueryParam("size") Integer size, @QueryParam("page") Integer page
    ) {
        GoodsType type = GoodsType.SHOP;
        return goodsBiz.queryGoodsManyToOneList(type, brandId, brandName, categoryId, onlyCategory, goodsName, hasImages,
                goodsSaled, goodsDel, orderBy, priceMin, priceMax, page, size);
    }

    @GET
    @Path("/mList")
    public ReturnMessage<List<GoodsEntityManyToOne>> getMarketGoodsList(
            @QueryParam("brandId") Integer brandId, @QueryParam("brandName") String brandName,
            @QueryParam("categoryId") Integer categoryId, @QueryParam("onlyCategory") boolean onlyCategory,
            @QueryParam("goodsName") String goodsName, @QueryParam("hasImages") boolean hasImages,
            @QueryParam("goodsSaled") Boolean goodsSaled,
            @QueryParam("goodsDel") Boolean goodsDel,
            @QueryParam("orderBy") String orderBy,
            @QueryParam("priceMax") Double priceMax, @QueryParam("priceMin") Double priceMin,
            @QueryParam("size") Integer size, @QueryParam("page") Integer page
    ) {
        GoodsType type = GoodsType.MARKET;
        return goodsBiz.queryGoodsManyToOneList(type, brandId, brandName, categoryId, onlyCategory, goodsName, hasImages,
                goodsSaled, goodsDel, orderBy, priceMin, priceMax, page, size);
    }

    @GET
    @Path("/shop/spec/list")
    public ReturnMessage<List<GoodsSpecEntityManyToOne>> getShopGoodsSpecByConditions(
            @QueryParam("brandId") Integer brandId, @QueryParam("categoryId") Integer categoryId,
            @QueryParam("brandName") String brandName, @QueryParam("goodsId") Integer goodsId,
            @QueryParam("goodsName") String goodsName, @QueryParam("modelId") Integer modelId,
            @QueryParam("modelName") String modelName, @QueryParam("specId") Integer specId,
            @QueryParam("specName") String specName, @QueryParam("memberId") Integer memberId,
            @QueryParam("memberName") String memberName, @QueryParam("keyWord") String keyWord,
            @QueryParam("goodsSaled") Boolean goodsSaled,
            @QueryParam("specSaled") Boolean specSaled, @QueryParam("modelSaled") Boolean modelSaled,
            @QueryParam("priceMax") Double priceMax, @QueryParam("priceMin") Double priceMin,
            @QueryParam("orderBy") String orderBy, @QueryParam("hasImages") boolean hasImages,
            @QueryParam("size") Integer size, @QueryParam("page") Integer page
    ) {
        GoodsType type = GoodsType.SHOP;
        return goodsBiz.specList(type, goodsId, goodsName, goodsSaled, brandId, brandName, categoryId, modelId, modelName, modelSaled, specId, specName, specSaled,
                priceMin, priceMax, memberId, memberName, keyWord, orderBy, hasImages, page, size);
    }


    @GET
    @Path("/market/spec/list")
    public ReturnMessage<List<GoodsSpecEntityManyToOne>> getMarketGoodsSpecByConditions(
            @QueryParam("brandId") Integer brandId,
            @QueryParam("categoryId") Integer categoryId,
            @QueryParam("brandName") String brandName,
            @QueryParam("goodsId") Integer goodsId,
            @QueryParam("goodsName") String goodsName,
            @QueryParam("modelId") Integer modelId,
            @QueryParam("modelName") String modelName,
            @QueryParam("specId") Integer specId,
            @QueryParam("specName") String specName,
            @QueryParam("memberId") Integer memberId,
            @QueryParam("memberName") String memberName,
            @QueryParam("keyWord") String keyWord,
            @QueryParam("goodsSaled") Boolean goodsSaled,
            @QueryParam("specSaled") Boolean specSaled,
            @QueryParam("modelSaled") Boolean modelSaled,
            @QueryParam("priceMax") Double priceMax,
            @QueryParam("priceMin") Double priceMin,
            @QueryParam("orderBy") String orderBy,
            @QueryParam("hasImages") boolean hasImages,
            @QueryParam("size") Integer size,
            @QueryParam("page") Integer page) {
        GoodsType type = GoodsType.MARKET;
        ReturnMessage<List<GoodsSpecEntityManyToOne>> listReturnMessage = goodsBiz.specList(type, goodsId, goodsName, goodsSaled, brandId, brandName, categoryId,
                modelId, modelName, modelSaled, specId, specName, specSaled, priceMin, priceMax, memberId, memberName, keyWord, orderBy, hasImages, page, size);
        return listReturnMessage;
    }

    /**
     * 根据尺寸size 随机取 商城商品
     * 如果不传 分类id 则size为一级分类里的数量
     *
     * @param size       随机商品数
     * @param categoryId 分类id
     * @param brandId    品牌id
     * @return
     */
    @GET
    @Path("/shop/list/random/{size}")
    public ReturnMessage<List<GoodsEntity>> getShopGoodsRandom(@PathParam("size") Integer size,
                                                               @QueryParam("categoryId") Integer categoryId,
                                                               @QueryParam("brandId") Integer brandId) {
        GoodsType type = GoodsType.SHOP;
        boolean hasImages = true;
        return goodsBiz.randomQueryGoodsByCategoryOrBrand(type, brandId, categoryId, hasImages, size);
    }

    /**
     * 添加 市场 商品
     *
     * @param data json数组
     * @return 返回改变标识的GoodsVO列表
     */
    @POST
    @Path("/market/add")
    public ReturnMessage<List<GoodsVO>> addMarketGoodsByCommit(String data) {

        GoodsType type = GoodsType.MARKET;

        try {
            List<GoodsVO> goodsVOList = JSON.parseArray(data, GoodsVO.class);
            return goodsBiz.addGoodsVOList(type, goodsVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);
        }
    }

    /**
     * 添加 商城 商品
     *
     * @param images           图片
     * @param brand            品牌id
     * @param category         分类id
     * @param unspsc           u码
     * @param hscode           h码
     * @param goodsName        商品名
     * @param tags             标签
     * @param priceMin         最小价格
     * @param priceMax         最大价格
     * @param goodsSaled       商品可售
     * @param packages         包装数
     * @param technology       技术信息
     * @param goodsDescription 商品描述
     * @param memo             备注
     * @param modelName        型号名
     * @param modelSaled       型号可售
     * @param specName         规格名
     * @param price            价格
     * @param marketPrice      市场价
     * @param discount         折扣
     * @param minOrder         最小起订量
     * @param unit             单位id
     * @param packageNum       包装数量
     * @param packageUnit      包装单位id
     * @param delivery         货期
     * @param sku              卖家sku
     * @param weight           重
     * @param length           长
     * @param width            宽
     * @param height           高
     * @param warranty         质保
     * @param member           会员
     * @param freightTemplate  运费
     * @param description      描述
     * @param saled            规格可售
     * @return
     */
    @POST
    @Path("/shop/add")
    public ReturnMessage addShopGoodsByCommit(String images, Integer brand, Integer category, String unspsc, String hscode, String goodsName, String tags,
                                              Double priceMin, Double priceMax, Boolean goodsSaled, String packages, String technology, String goodsDescription, String memo,
                                              List<String> modelName, List<Boolean> modelSaled,
                                              List<String> specName, List<Double> price, List<Double> marketPrice, List<Double> discount, List<Integer> minOrder, List<Integer> unit, List<Integer> packageNum,
                                              List<Integer> packageUnit, List<Integer> delivery, List<String> sku, List<Double> weight, List<Double> length, List<Double> width, List<Double> height,
                                              List<String> warranty, List<Integer> member, List<Integer> freightTemplate, List<String> description, List<Boolean> saled) {

        GoodsType type = GoodsType.SHOP;
        return goodsBiz.addItem(type, images, brand, category, unspsc, hscode, goodsName, tags,
                priceMin, priceMax, goodsSaled, packages, technology, goodsDescription, memo,
                modelName, modelSaled,
                specName, price, marketPrice, discount, minOrder, unit, packageNum,
                packageUnit, delivery, sku, weight, length, width, height,
                warranty, member, freightTemplate, description, saled);
    }

    /**
     * 根据规格id编辑商城商品
     *
     * @param images           图片
     * @param brand            品牌id
     * @param category         分类id
     * @param unspsc           u码
     * @param hscode           h码
     * @param goodsName        商品名
     * @param tags             标签
     * @param priceMin         最小价格
     * @param priceMax         最大价格
     * @param goodsSaled       商品可售
     * @param packages         包装数
     * @param technology       技术信息
     * @param goodsDescription 商品描述
     * @param memo             备注
     * @param modelName        型号名
     * @param specName         规格名
     * @param price            价格
     * @param marketPrice      市场价
     * @param discount         折扣
     * @param minOrder         最小起订量
     * @param unit             单位id
     * @param packageNum       包装数量
     * @param packageUnit      包装单位id
     * @param delivery         货期
     * @param sku              卖家sku
     * @param weight           重
     * @param length           长
     * @param width            宽
     * @param height           高
     * @param warranty         质保
     * @param member           会员
     * @param freightTemplate  运费
     * @param description      描述
     * @param saled            规格可售
     * @return
     */
    @POST
    @Path("/edit/id/{id}")
    @Consumes(RestMediaType.FORM_HEADER)
    public ReturnMessage<GoodsSpecEntity> editGoods(@PathParam("id") Integer id, String images, Integer brand, Integer category, String unspsc, String hscode, String goodsName, String tags,
                                                    Double priceMin, Double priceMax, Integer sales, Integer views, Boolean goodsSaled, String packages, String technology, String goodsDescription, String memo,
                                                    List<Integer> modelId, List<String> modelName, List<Boolean> modelSaled,
                                                    List<Integer> specId, List<String> specName, List<Double> price, List<Double> marketPrice, List<Double> discount, List<Integer> minOrder,
                                                    List<Integer> unit, List<Integer> packageNum, List<Integer> packageUnit, List<Integer> delivery, List<String> sku, List<Double> weight,
                                                    List<Double> length, List<Double> width, List<Double> height, List<String> warranty, List<Integer> member, List<Integer> freightTemplate,
                                                    List<String> description, List<Boolean> saled) {
        return goodsBiz.editGoods(id, images, brand, category, unspsc, hscode, goodsName, tags, priceMin, priceMax, sales, views, goodsSaled,
                packages, technology, goodsDescription, memo, modelId, modelName, modelSaled, specId, specName, price,
                marketPrice, discount, minOrder, unit, packageNum, packageUnit,
                delivery, sku, weight, length, width, height, warranty, member,
                freightTemplate, description, saled);
    }

    /**
     * 编辑 市场 商品
     *
     * @param id
     * @param data
     * @return
     */
    @POST
    @Path("/market/edit/id/{id}")
    public ReturnMessage<GoodsVO> editGoods(@PathParam("id") Integer id, String data) {
        GoodsType type = GoodsType.MARKET;

        id = Utils.reqInt(id, 0);
        if (id <= 0) return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);

        try {
            GoodsVO goodsVO = JSON.parseObject(data, GoodsVO.class);
            return goodsBiz.updateGoods(type, id, goodsVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnMessage.failed("修改失败:" + e.getMessage());
        }
    }

    /**
     * 批量 编辑 市场 商品
     *
     * @param data
     * @return
     */
    @POST
    @Path("/market/edit/batch")
    public ReturnMessage<List<GoodsVO>> editGoodsList(String data) {
        GoodsType type = GoodsType.MARKET;
        try {
            List<GoodsVO> goodsVOS = JSON.parseArray(data, GoodsVO.class);
//            List<GoodsVO> goodsVOList = JSON.parseObject(data, ArrayList.class);
            for (GoodsVO goods : goodsVOS) {
                if (goodsBiz.updateGoods(type, goods.getId(), goods).getCode() < 0) {

                    logger.error("批量更新处理错误！");
                    return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);

                }
            }
            return ReturnMessage.success(goodsVOS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ReturnMessage.failed("修改失败:" + e.getMessage());
        }
    }

    /**
     * 根据id删除商品
     *
     * @param id
     * @return
     */
    @GET
    @Path("/del/id/{id}")
    public ReturnMessage delGoods(@PathParam("id") Integer id) {
        return goodsBiz.delGoods(id);
    }

    @GET
    @Path("/views/id/{id}")
    public ReturnMessage goodsViewsAdd(@PathParam("id") Integer id) {
        return goodsBiz.goodsViewsAdd(id);
    }

}