package cn.gomro.mid.core.biz.goods.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.goods.biz.IBrandBizLocal;
import cn.gomro.mid.core.biz.goods.biz.IBrandBizRemote;
import cn.gomro.mid.core.biz.goods.entity.GoodsBrandEntity;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsBrandService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Adam on 2017/1/10.
 */
@Stateless
public class BrandBiz extends AbstractSessionBiz implements IBrandBizLocal,IBrandBizRemote {

    @EJB
    IGoodsBrandService brandService;

    @Override
    public ReturnMessage<GoodsBrandEntity> queryOrCreateBrandByName(GoodsType type, String name) {
        return brandService.queryOrCreateBrandByName(type,name);
    }

    @Override
    public ReturnMessage<List<GoodsBrandEntity>> getShortBrandList(GoodsType type) {
        return brandService.getShortBrandList(type);
    }

    @Override
    public ReturnMessage<GoodsBrandEntity> getBrandByName(GoodsType type, String name) {
        return brandService.getBrandByName(type,name);
    }

    public ReturnMessage<List<GoodsBrandEntity>> querybrandidList(Integer brandId,String brandName,Integer page, Integer rows)
    {
        return brandService.querybrandidList(brandId,brandName,page,rows);
    }

    @Override
    public ReturnMessage<List<GoodsBrandEntity>> getItemsPaged(GoodsBrandEntity entity, Integer page, Integer size)
    {
        return brandService.getItemsPaged(entity,page, size);
    }

    public Integer getbrandIDsave(GoodsBrandEntity goodsBrandEntity)
    {
        return brandService.getbrandIDsave(goodsBrandEntity);
    }
    public Integer getBrandincreased(GoodsBrandEntity goodsBrandEntity)
    {
        return brandService.getBrandincreased(goodsBrandEntity);
    }
    public  Integer getBranddelete(Integer id)
    {
        return brandService.getBranddelete(id);
    }



//    public ReturnMessage<List<GoodsBrandEntity>> getShortBrandListpage(GoodsType type, Integer page, Integer size)
//    {
//        return brandService.getShortBrandListpage(type,page,size);
//    }


}
