package cn.gomro.mid.api.rest.services.base;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.goods.entity.GoodsUnitEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsUnitService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.Utils;
import org.apache.commons.lang.StringUtils;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yaoo on 2016/8/23.
 */
@Path("/unit")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.JSON_HEADER)
public class UnitApi extends AbstractApi {

    @EJB
    private IGoodsUnitService unitService;

    public UnitApi() {
    }

    /**
     * 分页获取 商城 单位列表
     *
     * @return
     */
    @GET
    @Path("/shop/list")
    public ReturnMessage<List<GoodsUnitEntity>> queryShopBrandList(@QueryParam("name") String name,
                                                                   @QueryParam("page") Integer page,
                                                                   @QueryParam("size") Integer size) {
        GoodsType type = GoodsType.SHOP;

        GoodsUnitEntity goodsUnitEntity = new GoodsUnitEntity();
        goodsUnitEntity.setType(type);
        goodsUnitEntity.setName(name);

        size = Utils.reqPageSize(size);
        page = Utils.reqInt(page, 1);

        return unitService.getItemsPaged(goodsUnitEntity, page, size);
    }

    /**
     * 分页获取 市场 单位列表
     *
     * @return
     */
    @GET
    @Path("/market/list")
    public ReturnMessage<List<GoodsUnitEntity>> queryMarketBrandList(@QueryParam("name") String name,
                                                                     @QueryParam("page") Integer page,
                                                                     @QueryParam("size") Integer size) {
        GoodsType type = GoodsType.MARKET;

        GoodsUnitEntity goodsUnitEntity = new GoodsUnitEntity();
        goodsUnitEntity.setType(type);
        goodsUnitEntity.setName(name);

        size = Utils.reqPageSize(size);
        page = Utils.reqInt(page, 1);

        return unitService.getItemsPaged(goodsUnitEntity, page, size);
    }

    /**
     * 根据ID获取单位对象
     *
     * @return
     */
    @GET
    @Path("/id/{id}")
    public ReturnMessage<GoodsUnitEntity> getUnit(@PathParam("id") Integer id) {
        return unitService.getItem(id);
    }

    /**
     * 根据ID获取单位名称
     *
     * @return
     */
    @GET
    @Path("/name/{id}")
    public ReturnMessage<GoodsUnitEntity> getUnitName(@PathParam("id") Integer id) {
        return unitService.getName(id);
    }

    /**
     * 增加市场单位
     * @param name 名称
     * @return
     */
    @POST
    @Path("/market/add")
    public ReturnMessage addMarketUnit(String name) {
        GoodsType type = GoodsType.MARKET;
        if (StringUtils.isNotBlank(name)) {
            Date date = new Date();
            GoodsUnitEntity goodsUnitEntity = new GoodsUnitEntity(type, name, false, date, date);
            return unitService.addItem(goodsUnitEntity);
        }
        return ReturnMessage.failed();
    }

    /**
     * 增加商城单位
     * @param name 名称
     * @return
     */
    @POST
    @Path("/shop/add")
    public ReturnMessage addShopUnit(String name) {
        GoodsType type = GoodsType.SHOP;
        if (StringUtils.isNotBlank(name)) {
            Date date = new Date();
            GoodsUnitEntity goodsUnitEntity = new GoodsUnitEntity(type, name, false, date, date);
            return unitService.addItem(goodsUnitEntity);
        }
        return ReturnMessage.failed();
    }

    /**
     * 根据Id编辑单位名
     * @param id
     * @param name
     * @return
     */
    @POST
    @Path("/edit/id/{id}")
    public ReturnMessage addShopUnit(@PathParam("id") Integer id, String name) {

        if (id != null && id > 0 && StringUtils.isNotBlank(name)) {
            GoodsUnitEntity data = unitService.getItem(id).getData();
            if (data != null) {
                data.setName(name);
                data.setLast(new Date());
                return unitService.editItem(data);
            }
        }
        return ReturnMessage.failed();
    }

}
