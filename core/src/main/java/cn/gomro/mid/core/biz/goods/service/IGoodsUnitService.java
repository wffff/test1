package cn.gomro.mid.core.biz.goods.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.goods.entity.GoodsUnitEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
public interface IGoodsUnitService extends IService<GoodsUnitEntity> {
    ReturnMessage<List<GoodsUnitEntity>> getShortunitList(GoodsType type);

    ReturnMessage<GoodsUnitEntity> queryOrCreateUnitByName(GoodsType type, String name);

    ReturnMessage<List<GoodsUnitEntity>>  queryunitidList(Integer unitId,String unitName,Integer page,Integer rows);

    Integer getbrandIDsave(GoodsUnitEntity entity);

    Integer getBrandincreased(GoodsUnitEntity entity);

    Integer getBranddelete(Integer id);
}
