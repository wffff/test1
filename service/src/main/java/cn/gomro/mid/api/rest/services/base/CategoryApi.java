package cn.gomro.mid.api.rest.services.base;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.goods.biz.ICategoryBizLocal;
import cn.gomro.mid.core.biz.goods.entity.GoodsCategoryEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsCategoryEntityWithOutTree;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yaoo on 2016/8/23.
 */
@Path("/category")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.JSON_HEADER)
public class CategoryApi extends AbstractApi {

    @EJB
    ICategoryBizLocal categoryBiz;

    public CategoryApi() {
    }


    /**
     * 根据id获取分类树
     */
    @GET
    @Path("/id/{id}")
    public ReturnMessage<GoodsCategoryEntity> getCategory(@PathParam("id") Integer id) {
        return categoryBiz.getItem(id);
    }

    /**
     * 根据ID获取名称
     */
    @GET
    @Path("/name/{id}")
    public ReturnMessage<GoodsCategoryEntity> getCategoryName(@PathParam("id") Integer id) {
        return categoryBiz.getName(id);
    }

    /**
     * 根据id获取 商城 自身及递归列表
     *
     * @param id
     * @param isUpward 是否是向上列表
     * @return
     */
    @GET
    @Path("/shop/list/id/{id}")
    public ReturnMessage<List<GoodsCategoryEntityWithOutTree>> queryShopSelfAndUpwardListById(@PathParam("id") Integer id,
                                                                                              @QueryParam("isUpward") boolean isUpward) {
        GoodsType type = GoodsType.SHOP;
        return categoryBiz.querySelfAndListById(type, id, isUpward);
    }

    /**
     * 根据id获取 市场 自身及递归列表
     */
    @GET
    @Path("/market/list/id/{id}")
    public ReturnMessage<List<GoodsCategoryEntityWithOutTree>> queryMarketSelfAndUnderListById(@PathParam("id") Integer id,
                                                                                    @QueryParam("isUpward") boolean isUpward) {
        GoodsType type = GoodsType.MARKET;
        return categoryBiz.querySelfAndListById(type, id, isUpward);
    }

    /**
     * 根据品牌id获取 商城 分类列表
     */
    @GET
    @Path("/shop/brand/id/{brandId}")
    public ReturnMessage<List<GoodsCategoryEntity>> getShopCategoryListByBrandId(@PathParam("brandId") Integer brandId) {
        GoodsType type = GoodsType.SHOP;
        return categoryBiz.queryLeafsByBrandId(type, brandId);
    }

    /**
     * 根据品牌id获取 市场 分类列表
     */
    @GET
    @Path("/market/brand/id/{brandId}")
    public ReturnMessage<List<GoodsCategoryEntity>> getMarketCategoryListByBrandId(@PathParam("brandId") Integer brandId) {

        GoodsType type = GoodsType.MARKET;
        return categoryBiz.queryLeafsByBrandId(type, brandId);
    }

    /**
     * 递归获取 商城 分类列表树
     *
     * @return
     */
    @GET
    @Path("/shop/tree")
    public ReturnMessage<List<GoodsCategoryEntity>> queryAllShopCategoryTreeList() {

        GoodsType type = GoodsType.SHOP;
        ReturnMessage<List<GoodsCategoryEntity>> listReturnMessage = categoryBiz.queryAllCategoryList(type, null, true);
        return listReturnMessage;
    }

    /**
     * 递归获取 市场 分类列表树
     *
     * @return
     */
    @GET
    @Path("/market/tree")
    public ReturnMessage<List<GoodsCategoryEntity>> queryAllMarketCategoryTreeList() {

        GoodsType type = GoodsType.MARKET;
        ReturnMessage<List<GoodsCategoryEntity>> listReturnMessage = categoryBiz.queryAllCategoryList(type, null, true);
        return listReturnMessage;
    }


    /**
     * 根据父ID获取 商城 子列表树
     *
     * @param pid
     * @return
     */
    @GET
    @Path("/shop/child/{pid}")
    public ReturnMessage<List<GoodsCategoryEntity>> getShopCategoryListByParent(@PathParam("pid") Integer pid) {
        GoodsType type = GoodsType.SHOP;
        return categoryBiz.queryListByParent(type, pid);
    }


    /**
     * 根据父ID获取 市场 子列表树
     *
     * @param pid
     * @return
     */
    @GET
    @Path("/market/child/{pid}")
    public ReturnMessage<List<GoodsCategoryEntity>> getMarketCategoryListByParent(@PathParam("pid") Integer pid) {
        GoodsType type = GoodsType.MARKET;
        return categoryBiz.queryListByParent(type, pid);
    }

    /**
     * 增加市场分类
     *
     * @param pid   父id
     * @param name  分类名
     * @param sort  排序
     * @param saled 是否可售
     * @return
     */
    @POST
    @Path("/market/add")
    public ReturnMessage<GoodsCategoryEntity> addMarketCategory(Integer pid, String name, Integer sort, Boolean saled) {
        GoodsType type = GoodsType.MARKET;
        GoodsCategoryEntity entity = new GoodsCategoryEntity(pid, type, name, sort, saled, false, new Date(), new Date());
        return categoryBiz.addItem(entity);
    }

    /**
     * 增加商城分类
     *
     * @param pid   父id
     * @param name  分类名
     * @param sort  排序
     * @param saled 是否可售
     * @return
     */
    @POST
    @Path("/shop/add")
    public ReturnMessage<GoodsCategoryEntity> addShopCategory(Integer pid, String name, Integer sort, Boolean saled) {
        GoodsType type = GoodsType.SHOP;
        GoodsCategoryEntity entity = new GoodsCategoryEntity(pid, type, name, sort, saled, false, new Date(), new Date());
        return categoryBiz.addItem(entity);
    }

    /**
     * 根据id删除分类
     *
     * @param id
     * @return
     */
    @GET
    @Path("/del/id/{id}")
    public ReturnMessage delCategory(Integer id) {
        return categoryBiz.delItem(id);
    }

    /**
     * 编辑市场分类
     *
     * @param id
     * @param pid   父id
     * @param name  分类名
     * @param sort  排序
     * @param saled 是否可售
     * @return
     */
    @POST
    @Path("/edit/id/{id}")
    public ReturnMessage<GoodsCategoryEntity> editCategory(@PathParam("id") Integer id, Integer pid, String name, Integer sort, Boolean saled) {
        return categoryBiz.editCategory(id, pid, name, sort, saled);
    }
}
