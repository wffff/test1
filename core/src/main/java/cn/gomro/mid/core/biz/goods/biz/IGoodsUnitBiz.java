package cn.gomro.mid.core.biz.goods.biz;

import cn.gomro.mid.core.biz.goods.entity.GoodsUnitEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by Adam on 2017/1/12.
 */
public interface IGoodsUnitBiz {
    ReturnMessage<List<GoodsUnitEntity>> getShortunitList(GoodsType type);

    ReturnMessage<GoodsUnitEntity> queryOrCreateUnitByName(GoodsType type, String name);

    ReturnMessage<List<GoodsUnitEntity>> getItemsPaged(GoodsUnitEntity entity, Integer page, Integer size);

    ReturnMessage<List<GoodsUnitEntity>>  queryunitidList(Integer unitId,String unitName,Integer page,Integer rows);

    Integer getbrandIDsave(GoodsUnitEntity entity);

    Integer getBrandincreased(GoodsUnitEntity entity);

    Integer getBranddelete(Integer id);
}
