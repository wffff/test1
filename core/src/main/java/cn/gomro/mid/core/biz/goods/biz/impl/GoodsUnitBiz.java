package cn.gomro.mid.core.biz.goods.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.goods.biz.IGoodsUnitBizLocal;
import cn.gomro.mid.core.biz.goods.biz.IGoodsUnitBizRemote;
import cn.gomro.mid.core.biz.goods.entity.GoodsUnitEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsUnitService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Adam on 2017/1/12.
 */
@Stateless
public class GoodsUnitBiz extends AbstractSessionBiz implements IGoodsUnitBizLocal,IGoodsUnitBizRemote {

    @EJB
    IGoodsUnitService goodsUnitService;

    @Override
    public ReturnMessage<List<GoodsUnitEntity>> getShortunitList(GoodsType type) {
        return goodsUnitService.getShortunitList(type);
    }

    @Override
    public ReturnMessage<GoodsUnitEntity> queryOrCreateUnitByName(GoodsType type, String name) {
        return goodsUnitService.queryOrCreateUnitByName(type,name);
    }

    @Override
    public  ReturnMessage<List<GoodsUnitEntity>>  getItemsPaged(GoodsUnitEntity entity, Integer page, Integer size)
    {
        return goodsUnitService.getItemsPaged(entity,page,size);
    }

    public  ReturnMessage<List<GoodsUnitEntity>>  queryunitidList(Integer unitId,String unitName,Integer page,Integer rows)
    {
        return goodsUnitService.queryunitidList(unitId,unitName,page,rows);
    }

    public Integer getbrandIDsave(GoodsUnitEntity entity)
    {
        return goodsUnitService.getbrandIDsave(entity);
    }
    public Integer getBrandincreased(GoodsUnitEntity entity)
    {
        return goodsUnitService.getBrandincreased(entity);
    }
    public  Integer getBranddelete(Integer id)
    {
        return goodsUnitService.getBranddelete(id);
    }
}
