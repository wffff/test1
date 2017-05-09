package cn.gomro.mid.core.biz.goods.biz;

import cn.gomro.mid.core.biz.goods.entity.SpecInventoryEntity;
import cn.gomro.mid.core.biz.goods.entity.MemberInventoryEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by yaoo on 2016/8/24.
 */
public interface IInventoryBiz {

    ReturnMessage<MemberInventoryEntity> getMemberInventory(Integer id);

    ReturnMessage<SpecInventoryEntity> getGoodsInventory(Integer id);

    ReturnMessage<SpecInventoryEntity> getGoodsInventoryBySpec(Integer spec);

    ReturnMessage<List<MemberInventoryEntity>> queryMemberInventoryList(Integer spec, Integer memberId, String warehouseName, Integer page, Integer size);

    ReturnMessage<List<SpecInventoryEntity>> queryGoodsInventoryList(Integer memberId, Integer page, Integer size);

    ReturnMessage delMemberInventory(Integer id);

    ReturnMessage delInventory(Integer id);

    ReturnMessage addMemberInventory(MemberInventoryEntity entity);

    ReturnMessage addGoodsInventory(SpecInventoryEntity entity);

    ReturnMessage editMemberInventory(Integer id, Integer num, Integer warehouse);

    ReturnMessage editGoodsInventory(Integer id, Integer amount, Integer useabled, Integer freezed);

}
