package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.goods.entity.GoodsEntity;
import cn.gomro.mid.core.biz.goods.entity.GoodsModelEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsModelService;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by yaodw on 2016/7/15.
 */
@Stateless
public class GoodsModelService extends AbstractSessionService<GoodsModelEntity> implements IGoodsModelService {

    public GoodsModelService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        ReturnMessage<GoodsModelEntity> ret = this.getItem(id);
        if (ReturnCode.isSuccess(ret.getCode()) && ret.getData()!=null) return ReturnMessage.success(ret.getData().getName(),ret.getData().getName());
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<GoodsModelEntity>> getItemsPaged(GoodsModelEntity entity, Integer page, Integer size) {
        String where = " WHERE del=false";
        String order = " ORDER BY id DESC";

        if (null != entity && StringUtils.isNotBlank(entity.getName())) {
            where += " AND name='" + entity.getName() + "'";
        }

        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM GoodsModelEntity" + where,
                "FROM GoodsModelEntity" + where + order, page, size);
    }
    @Override
    public ReturnMessage<List<GoodsModelEntity>> addGoodsItems(List<GoodsModelEntity> goodsList) {
        if (goodsList != null) {
            try {
                for (GoodsModelEntity goods : goodsList) {
                    this.addItem(goods);
                }
                ReturnMessage.success(goodsList);
            } catch (Exception e) {
                ctx.setRollbackOnly();
                e.printStackTrace();
            }
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<GoodsModelEntity>> queryGoodsModelList(GoodsType type, Integer goodsId, String goodsName, String modelName, String specName, String brandName, Integer memberId, Boolean specSaled, String OrderBy, Double priceMin, Double priceMax, Integer page, Integer size) {
        return null;
    }
}
