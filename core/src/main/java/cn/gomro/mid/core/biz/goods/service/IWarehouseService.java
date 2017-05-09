package cn.gomro.mid.core.biz.goods.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.goods.entity.WarehouseEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
public interface IWarehouseService extends IService<WarehouseEntity> {

    ReturnMessage<WarehouseEntity> queryOrCreateWarehouseByMemberAndName(Integer memberId, String name);

    ReturnMessage updateWarehouseAddressByMemberId(Integer vid,String addr);

    ReturnMessage<WarehouseEntity> getwarehouseById(Integer id);

    Integer getwarehousesave(WarehouseEntity warehouseEntity,Integer memberid,boolean enabledboolead);

    Integer addwarehouse(WarehouseEntity warehouseEntity,Integer memberid,String namestr,String addressstr,Boolean enabledboolead);

    Integer deletewarehouse(Integer id);

    ReturnMessage<List<WarehouseEntity>> querywarehouseByid(Integer warehouseid, String warehousename, String warehousesitr, Integer page, Integer rows);

}
