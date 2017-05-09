package cn.gomro.mid.core.biz.goods.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.goods.entity.SpecInventoryEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by admin on 2016/8/19.
 */
public interface IGoodsInventoryService extends IService<SpecInventoryEntity> {

    ReturnMessage<List<SpecInventoryEntity>> queryGoodsInventoryList(Integer member, Integer page, Integer size);

    ReturnMessage<SpecInventoryEntity> getGoodsInventoryBySpec(Integer spec);

    ReturnMessage delGoodsInventoryBySpec(Integer id);
}
