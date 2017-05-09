package cn.gomro.mid.core.biz.goods.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.goods.entity.MemberInventoryEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
public interface IMemberInventoryService extends IService<MemberInventoryEntity> {

    ReturnMessage<List<MemberInventoryEntity>> queryMemberInventoryList(Integer spec, Integer memberId, String warehouseName, Integer page, Integer size);

    ReturnMessage<MemberInventoryEntity> querySingleMemberInventoryByIds(Integer specId, Integer memberId, Integer warehouseId);

    ReturnMessage<MemberInventoryEntity> querySingleMemberInventory(Integer spec, Integer memberId, String warehouseName);

    ReturnMessage<Integer> queryMemberInventoryAmount(Integer member, Integer spec);

    ReturnMessage delMemberInventoryList(Integer specId, Integer memberId);

}
