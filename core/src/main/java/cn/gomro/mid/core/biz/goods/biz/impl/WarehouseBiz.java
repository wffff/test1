package cn.gomro.mid.core.biz.goods.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.goods.biz.IWarehouseLocal;
import cn.gomro.mid.core.biz.goods.biz.IWarehouseRemote;
import cn.gomro.mid.core.biz.goods.entity.WarehouseEntity;
import cn.gomro.mid.core.biz.goods.service.IWarehouseService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by 烦 on 2017/3/9.
 */
@Stateless
public class WarehouseBiz extends AbstractSessionBiz implements IWarehouseLocal,IWarehouseRemote {

    @EJB
   private IWarehouseService iWarehouseService;

    @Override
    public ReturnMessage<List<WarehouseEntity>> getItemsPaged(WarehouseEntity entity, Integer page, Integer size)
    {
        return iWarehouseService.getItemsPaged(entity,page,size);
    }


    public ReturnMessage<WarehouseEntity> getwarehouseById(Integer id) {
        return iWarehouseService.getwarehouseById(id);
    }
    public Integer getwarehousesave(WarehouseEntity warehouseEntity,Integer memberid,boolean enabledboolead)
    {
        return iWarehouseService.getwarehousesave(warehouseEntity,memberid,enabledboolead);
    }
    public  Integer addwarehouse(WarehouseEntity warehouseEntity,Integer memberid,String namestr,String addressstr,Boolean enabledboolead)
    {
        return iWarehouseService.addwarehouse(warehouseEntity,memberid,namestr,addressstr,enabledboolead);
    }
    public Integer deletewarehouse(Integer id)
    {
        return iWarehouseService.deletewarehouse(id);
    }
    public ReturnMessage<List<WarehouseEntity>> querywarehouseByid(Integer warehouseid,String warehousename,String warehousesitr,Integer page, Integer rows)
    {

        return iWarehouseService.querywarehouseByid(warehouseid,warehousename,warehousesitr,page,rows);
    }
}
