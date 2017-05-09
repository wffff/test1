package cn.gomro.mid.core.biz.goods.biz;

import cn.gomro.mid.core.biz.goods.entity.WarehouseEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by 烦 on 2017/3/9.
 */
public interface IWarehouseBiz {

    ReturnMessage<List<WarehouseEntity>> getItemsPaged(WarehouseEntity entity, Integer page, Integer size);

    ReturnMessage<WarehouseEntity> getwarehouseById(Integer id);

    Integer getwarehousesave(WarehouseEntity warehouseEntity,Integer memberid,boolean enabledboolead);

    Integer addwarehouse(WarehouseEntity warehouseEntity,Integer memberid,String namestr,String addressstr,Boolean enabledboolead);

    Integer deletewarehouse(Integer id);

    ReturnMessage<List<WarehouseEntity>> querywarehouseByid(Integer warehouseid,String warehousename,String warehousesitr,Integer page, Integer rows);

}
