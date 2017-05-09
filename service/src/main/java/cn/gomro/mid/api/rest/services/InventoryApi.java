package cn.gomro.mid.api.rest.services;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.goods.biz.IInventoryBiz;
import cn.gomro.mid.core.biz.goods.entity.SpecInventoryEntity;
import cn.gomro.mid.core.biz.goods.entity.MemberInventoryEntity;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.Utils;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */

@Path("/inventory")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.JSON_HEADER)
public class InventoryApi extends AbstractApi {

    @EJB
    private IInventoryBiz inventoryBiz;

    public InventoryApi() {
    }


    /**
     * 根据ID返回分库存对象
     *
     * @param id
     * @return MemberInventoryEntity
     */
    @GET
    @Path("/member/id/{id}")
    public ReturnMessage<MemberInventoryEntity> getMemberInventory(@PathParam("id") Integer id) {
        if (id <= 0) return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);
        return inventoryBiz.getMemberInventory(id);
    }

    /**
     * 根据ID返回总库存对象
     *
     * @param id
     * @return MemberInventoryEntity
     */
    @GET
    @Path("/goods/id/{id}")
    public ReturnMessage<SpecInventoryEntity> getGoodsInventory(@PathParam("id") Integer id) {
        id = Utils.reqInt(id, 0);
        if (id <= 0) return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);
        return inventoryBiz.getGoodsInventory(id);
    }

    /**
     * 根据specID 和 memberId 返回总库存对象
     *
     * @param spec
     * @return
     */
    @GET
    @Path("/goods/spec/{id}")
    public ReturnMessage<SpecInventoryEntity> getGoodsInventoryBySpec(@PathParam("id") Integer spec) {
        spec = Utils.reqInt(spec, 0);
        if (spec <= 0) return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);
        return inventoryBiz.getGoodsInventoryBySpec(spec);
    }

    /**
     * 根据条件分页检索商品 分库存 列表
     *
     * @param size          每页数量
     * @param page          页码
     * @param spec          规格
     * @param memberId      会员ID
     * @param warehouseName 仓库名
     * @return ReturnMessage<List<MemberInventoryEntity>>
     */
    @GET
    @Path("/member/list")
    public ReturnMessage<List<MemberInventoryEntity>> getMemberInventoryList(
            @QueryParam("spec") Integer spec,
            @QueryParam("memberId") Integer memberId,
            @QueryParam("warehouse") String warehouseName,
            @QueryParam("size") Integer size,
            @QueryParam("page") Integer page) {

        return inventoryBiz.queryMemberInventoryList(spec, memberId, warehouseName, page, size);
    }

    /**
     * 根据条件分页检索商品 总库存 列表
     *
     * @param size     每页数量
     * @param page     页码
     * @param memberId 会员ID
     * @return
     */
    @GET
    @Path("/goods/list")
    public ReturnMessage<List<SpecInventoryEntity>> getGoodsInventoryList(
            @QueryParam("memberId") Integer memberId,
            @QueryParam("size") Integer size,
            @QueryParam("page") Integer page) {

        return inventoryBiz.queryGoodsInventoryList(memberId, page, size);
    }

    /**
     * 增加一条分库存
     *
     * @param entity
     * @return
     */
    @POST
    @Path("/member/add")
    public ReturnMessage addMemberInventory(MemberInventoryEntity entity) {
        return inventoryBiz.addMemberInventory(entity);
    }

    /**
     * 增加一条总库存
     *
     * @param entity
     * @return
     */
    @POST
    @Path("/goods/add")
    public ReturnMessage addGoodsInventory(SpecInventoryEntity entity) {
        return inventoryBiz.addGoodsInventory(entity);
    }

    /**
     * 根据id 删除分库存
     *
     * @param id
     * @return
     */
    @GET
    @Path("/member/del/{id}")
    public ReturnMessage delMemberInventory(@PathParam("id") Integer id) {
        return inventoryBiz.delMemberInventory(id);
    }

    /**
     * 根据id 删除总库存
     *
     * @param id
     * @return
     */
    @GET
    @Path("/del/id/{id}")
    public ReturnMessage delInventory(@PathParam("id") Integer id) {
        return inventoryBiz.delInventory(id);
    }

    @POST
    @Path("/member/edit/id/{id}")
    public ReturnMessage editMemberInventory(@PathParam("id") Integer id,Integer num,Integer warehouse){
        return inventoryBiz.editMemberInventory(id,num,warehouse);
    }

    @POST
    @Path("/goods/edit/id/{id}")
    public ReturnMessage editGoodsInventory(@PathParam("id") Integer id,Integer amount,Integer useabled,Integer freezed){
        return inventoryBiz.editGoodsInventory(id,amount,useabled,freezed);
    }

}