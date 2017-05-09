package cn.gomro.mid.core.biz.goods.biz.impl;

import cn.gomro.mid.core.biz.goods.entity.SpecInventoryEntity;
import cn.gomro.mid.core.biz.goods.entity.MemberInventoryEntity;
import cn.gomro.mid.core.biz.goods.entity.WarehouseEntity;
import cn.gomro.mid.core.biz.goods.service.IGoodsInventoryService;
import cn.gomro.mid.core.biz.goods.service.IGoodsSpecService;
import cn.gomro.mid.core.biz.goods.service.IMemberInventoryService;
import cn.gomro.mid.core.biz.goods.service.IWarehouseService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.Utils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

/**
 * Created by yaoo on 2016/8/24.
 */
@Stateless
public class InventoryBiz implements cn.gomro.mid.core.biz.goods.biz.IInventoryBiz {

    @EJB
    IGoodsInventoryService goodsInventoryService;
    @EJB
    IMemberInventoryService memberInventoryService;
    @EJB
    IWarehouseService warehouseService;
    @EJB
    IGoodsSpecService specService;


    /**
     * 根据id获取分库存对象
     *
     * @param id
     * @return
     */
    @Override
    public ReturnMessage<MemberInventoryEntity> getMemberInventory(Integer id) {
        return memberInventoryService.getItem(id);
    }

    /**
     * 根据id获取总库存对象
     *
     * @param id
     * @return
     */
    @Override
    public ReturnMessage<SpecInventoryEntity> getGoodsInventory(Integer id) {
        return goodsInventoryService.getItem(id);
    }

    /**
     * 根据商品规格ID获取总库存对象
     *
     * @param spec
     * @return
     */
    @Override
    public ReturnMessage<SpecInventoryEntity> getGoodsInventoryBySpec(Integer spec) {
        return goodsInventoryService.getGoodsInventoryBySpec(spec);
    }

    /**
     * 查询分库存列表
     *
     * @param spec
     * @param memberId
     * @param warehouseName
     * @param page
     * @param size
     * @return
     */
    @Override
    public ReturnMessage<List<MemberInventoryEntity>> queryMemberInventoryList(Integer spec, Integer memberId, String warehouseName, Integer page, Integer size) {

        size = Utils.reqPageSize(size);
        page = Utils.reqInt(page, 1);

        memberId = Utils.reqInt(memberId, 0);
        spec = Utils.reqInt(spec, 0);
        return memberInventoryService.queryMemberInventoryList(spec, memberId, warehouseName, page, size);
    }

    /**
     * 查询总库存列表
     *
     * @param memberId
     * @param page
     * @param size
     * @return
     */
    @Override
    public ReturnMessage<List<SpecInventoryEntity>> queryGoodsInventoryList(Integer memberId, Integer page, Integer size) {

        size = Utils.reqPageSize(size);
        page = Utils.reqInt(page, 1);

        memberId = Utils.reqInt(memberId, 0);
        return goodsInventoryService.queryGoodsInventoryList(memberId, page, size);
    }

    /**
     * 根据id删除分库存
     *
     * @param id
     * @return
     */
    @Override
    public ReturnMessage delMemberInventory(Integer id) {
        MemberInventoryEntity inventory = memberInventoryService.getItem(id).getData();
        return inventory != null ? memberInventoryService.delItem(inventory) : ReturnMessage.failed();
    }

    /**
     * 根据id删除总库存
     *
     * @param id
     * @return
     */
    @Override
    public ReturnMessage delInventory(Integer id) {
        SpecInventoryEntity inventory = goodsInventoryService.getItem(id).getData();
        return inventory != null ? goodsInventoryService.delItem(inventory) : ReturnMessage.failed();
    }

    @Override
    public ReturnMessage addMemberInventory(MemberInventoryEntity entity) {
        return memberInventoryService.addItem(entity);
    }

    @Override
    public ReturnMessage addGoodsInventory(SpecInventoryEntity entity) {
        return goodsInventoryService.addItem(entity);
    }

    @Override
    public ReturnMessage editMemberInventory(Integer id, Integer num, Integer warehouse) {
        if (id != null && id > 0) {
            MemberInventoryEntity inventoryEntity = memberInventoryService.getItem(id).getData();
            if (inventoryEntity != null) {
                if (num != null && num > 0) inventoryEntity.setNum(num);
                if (warehouse != null && warehouse > 0) {
                    WarehouseEntity warehouseEntity = warehouseService.getItem(warehouse).getData();
                    if (warehouseEntity != null) {
                        inventoryEntity.setWarehouse(warehouseEntity);
                    }
                }
                inventoryEntity.setLast(new Date());
                return memberInventoryService.editItem(inventoryEntity);
            }
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage editGoodsInventory(Integer id, Integer amount, Integer useabled, Integer freezed) {
        if (id != null && id > 0) {
            SpecInventoryEntity inventoryEntity = goodsInventoryService.getItem(id).getData();
            if (inventoryEntity != null) {
                if (amount != null && amount > 0) inventoryEntity.setAmount(amount);
                if (useabled != null && useabled > 0) inventoryEntity.setUseabled(useabled);
                if (freezed != null && freezed > 0) inventoryEntity.setFreezed(freezed);
                inventoryEntity.setLast(new Date());
                return goodsInventoryService.editItem(inventoryEntity);
            }
        }
        return ReturnMessage.failed();
    }
}
