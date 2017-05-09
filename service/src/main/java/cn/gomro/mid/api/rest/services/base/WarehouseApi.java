package cn.gomro.mid.api.rest.services.base;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.goods.entity.CorporationEntity;
import cn.gomro.mid.core.biz.goods.entity.WarehouseEntity;
import cn.gomro.mid.core.biz.goods.service.ICorporationService;
import cn.gomro.mid.core.biz.goods.service.IWarehouseService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.Utils;
import org.apache.commons.lang.StringUtils;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.Date;

/**
 * Created by yaodw on 2016/7/18.
 */
@Path("/warehouse")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.JSON_HEADER)
public class WarehouseApi extends AbstractApi {

    @EJB
    private IWarehouseService warehouseService;
    @EJB
    private ICorporationService corporationService;

    public WarehouseApi() {
    }

    /**
     * 根据仓库id查询仓库
     *
     * @param id
     * @return
     */
    @GET
    @Path("/id/{id}")
    public ReturnMessage<WarehouseEntity> getWarehouse(@PathParam("id") Integer id) {
        id = Utils.reqInt(id, 0);
        return warehouseService.getItem(id);
    }

    /**
     * 根据会员id修改仓库的发货地
     */
    @GET
    @Path("/edit/address")
    public ReturnMessage getWarehouse(@QueryParam("vid") Integer vid,@QueryParam("addr")String addr) {
        return warehouseService.updateWarehouseAddressByMemberId(vid,addr);
    }

    /**
     * 根据ID获取仓库名称
     *
     * @return
     */
    @GET
    @Path("/name/{id}")
    public ReturnMessage<WarehouseEntity> getWarehouseName(@PathParam("id") Integer id) {
        return warehouseService.getName(id);
    }

    /**
     * 根据会员Id获取仓库列表
     */
    @GET
    @Path("/member/id/{id}")
    public ReturnMessage<WarehouseEntity> getWarehouseList(@PathParam("id") Integer id) {
        return warehouseService.getName(id);
    }

    /**
     * 增加仓库
     *
     * @param memberId 会员id
     * @param name     仓库名
     * @param address  地址
     * @param act      是否启用
     * @return
     */
    @POST
    @Path("/add")
    public ReturnMessage<WarehouseEntity> getWarehouseList(Integer memberId, String name, String address, Boolean act) {
        if (memberId != null && memberId > 0) {
            CorporationEntity corporationEntity = corporationService.getItem(memberId).getData();
            if (corporationEntity != null) {
                Date date = new Date();
                WarehouseEntity warehouseEntity = new WarehouseEntity(corporationEntity, name, address, act, false, date, date);
                return warehouseService.addItem(warehouseEntity);
            }
        }
        return ReturnMessage.failed();
    }

    /**
     * 根据id删除仓库
     *
     * @param id
     * @return
     */
    @GET
    @Path("/del/id/{id}")
    public ReturnMessage delWarehouse(@PathParam("id") Integer id) {
        if (id != null && id > 0) {
            WarehouseEntity warehouseEntity = warehouseService.getItem(id).getData();
            return warehouseService.delItem(warehouseEntity);
        }
        return ReturnMessage.failed();
    }

    /**
     * 根据Id编辑仓库
     *
     * @param id
     * @param memberId 会员Id
     * @param name     仓库名
     * @param address  地址
     * @param act      是否启用
     * @return
     */
    @GET
    @Path("/edit/id/{id}")
    public ReturnMessage<WarehouseEntity> getWarehouseList(@PathParam("id") Integer id, Integer memberId, String name, String address, Boolean act) {
        if (id != null && id > 0 && memberId != null && memberId > 0) {
            WarehouseEntity warehouseEntity = warehouseService.getItem(id).getData();
            CorporationEntity corporationEntity = corporationService.getItem(memberId).getData();
            if (warehouseEntity != null && corporationEntity != null) {
                warehouseEntity.setCorporation(corporationEntity);
                if (StringUtils.isNotBlank(name)) warehouseEntity.setName(name);
                if (StringUtils.isNotBlank(address)) warehouseEntity.setAddress(address);
                if (act != null) warehouseEntity.setAct(act);
                return warehouseService.editItem(warehouseEntity);
            }
        }
        return ReturnMessage.failed();
    }
}