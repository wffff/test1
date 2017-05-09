package cn.gomro.mid.core.biz.goods.biz;

import cn.gomro.mid.core.biz.common.entity.AccessTokenEntity;
import cn.gomro.mid.core.biz.goods.entity.*;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.io.Serializable;
import java.util.List;

/**
 * Created by momo on 2016/8/2.
 */
public interface IGoodsBiz extends Serializable {

    ReturnMessage<List<GoodsVO>> checkGoodsVOList(GoodsType type, Integer memberId, List<GoodsVO> goodsVOList);

    public ReturnMessage<GoodsSpecEntityManyToOne> addGoodsBySpec(GoodsSpecEntityManyToOne spec);

    ReturnMessage<GoodsSpecEntityManyToOne> addGoodsBySpec(GoodsSpecEntityManyToOne spec, Integer modelid, Integer brandId, Integer categoryid,Integer unitid,String goodsname,String modelname,String images,String UNSpsc,String HSCode,Integer packageUnitid);

    ReturnMessage<List<GoodsVO>> addGoodsVOList(GoodsType type, List<GoodsVO> goodsVOList);


    ReturnMessage addItem(GoodsType type, String images, Integer brand, Integer category, String unspsc, String hscode, String goodsName, String tags,
                          Double priceMin, Double priceMax, Boolean goodsSaled, String packages, String technology, String goodsDescription, String memo,
                          List<String> modelName, List<Boolean> modelSaled,
                          List<String> specName, List<Double> price, List<Double> marketPrice, List<Double> discount, List<Integer> minOrder, List<Integer> unit, List<Integer> packageNum,
                          List<Integer> packageUnit, List<Integer> delivery, List<String> sku, List<Double> weight, List<Double> length, List<Double> width, List<Double> height,
                          List<String> warranty, List<Integer> member, List<Integer> freightTemplate, List<String> description, List<Boolean> saled);

    @Deprecated
    ReturnMessage<List<GoodsEntity>> queryGoodsList(GoodsType type, Integer brandId, Integer categoryId, boolean onlyCategory,
                                                    String brandName, String goodsName, boolean haseImages, String modelName,
                                                    String specName, Integer memberId, String memberName, String keyWord, Boolean goodsSaled,
                                                    Boolean specSaled, Boolean modelSaled, String orderBy, Double priceMin,
                                                    Double priceMax, Integer page, Integer size);

    ReturnMessage<List<GoodsEntityManyToOne>> queryGoodsManyToOneList(GoodsType type, Integer brandId, String brandName, Integer categoryId,
                                                                      boolean onlyCategory, String goodsName, boolean hasImages, Boolean goodsSaled, Boolean goodsDel,
                                                                      String orderBy, Double priceMin, Double priceMax, Integer page, Integer size);

    ReturnMessage<List<GoodsSpecEntityManyToOne>> specList(GoodsType type, Integer goodsId, String goodsName,
                                                           Boolean goodsSaled, Integer brandId, String brandName,
                                                           Integer categoryId, Integer modelId, String modelName,
                                                           Boolean modelSaled, Integer specId, String specName,
                                                           Boolean specSaled, Double priceMin, Double priceMax,
                                                           Integer memberId, String memberName, String keyWord, String orderBy,
                                                           boolean hasImages, Integer page, Integer size);

    ReturnMessage<List<GoodsSpecEntityManyToOne>> AudiSpecList(GoodsType type, Integer goodsId, String goodsName,
                                                       Boolean goodsSaled, Integer brandId, String brandName,
                                                       Integer categoryId, Integer modelId, String modelName,
                                                       Boolean modelSaled, Integer specId, String specName,
                                                       Boolean specSaled, Double priceMin, Double priceMax,
                                                       Integer memberId, String memberName, String keyWord, String orderBy,
                                                       boolean hasImages, Integer page, Integer size);

    ReturnMessage<GoodsVO> updateGoods(GoodsType type, Integer id, GoodsVO goodsVO);

    ReturnMessage<GoodsEntity> getItem(Integer id);

    ReturnMessage getName(Integer id);

    ReturnMessage getModelName(Integer id);

    ReturnMessage getSpecName(Integer id);

    ReturnMessage delGoods(Integer id);

    ReturnMessage delGoodsSpec(Integer id);

    ReturnMessage<GoodsSpecEntity> editGoodsSpec(Integer id, String images, Integer brand, Integer category, String unspsc, String hscode,
                                                 String goodsName, String tags, Double priceMin, Double priceMax, Integer sales, Integer views, Boolean goodsSaled,
                                                 String packages, String technology, String goodsDescription, String memo, String modelName, String specName, Double price,
                                                 Double marketPrice, Double discount, Integer minOrder, Integer unit, Integer packageNum, Integer packageUnit,
                                                 Integer delivery, String sku, Double weight, Double length, Double width, Double height, String warranty, Integer member,
                                                 Integer freightTemplate, String description, Boolean saled);

    ReturnMessage<GoodsSpecEntity> getGoodsBySpecId(Integer specId);

    ReturnMessage<GoodsSpecEntityManyToOne> getGoodsSpecById(Integer id);

    ReturnMessage<List<GoodsSpecEntityManyToOne>> getGoodsSpecByIds(List<Integer> id);

    List<GoodsSpecEntityManyToOne> getGoodsSpecModelGoodByIds(Integer id);

    ReturnMessage goodsViewsAdd(Integer id);


    ReturnMessage<GoodsSpecEntity> editGoods(Integer id, String images, Integer brand, Integer category, String unspsc, String hscode, String goodsName, String tags,
                                             Double priceMin, Double priceMax, Integer sales, Integer views, Boolean goodsSaled, String packages, String technology,
                                             String goodsDescription, String memo,
                                             List<Integer> modelId, List<String> modelName, List<Boolean> modelSaled,
                                             List<Integer> specId, List<String> specName, List<Double> price, List<Double> marketPrice, List<Double> discount, List<Integer> minOrder,
                                             List<Integer> unit, List<Integer> packageNum, List<Integer> packageUnit, List<Integer> delivery, List<String> sku,
                                             List<Double> weight, List<Double> length, List<Double> width, List<Double> height, List<String> warranty, List<Integer> member,
                                             List<Integer> freightTemplate, List<String> description, List<Boolean> specSaled);

    ReturnMessage<List<GoodsEntity>> getShopGoodsRandom(Integer size, Integer categoryId, boolean eachCategory, Integer brandId);

    ReturnMessage<List<GoodsEntity>> randomQueryGoodsByCategoryOrBrand(GoodsType type, Integer brandId, Integer categoryId, boolean hasImages, Integer size);

    ReturnMessage setForSale(Integer[] id);

    //批量调整价格
    ReturnMessage adjustPrice(AccessTokenEntity access, GoodsType type, Integer brandId, Integer percent);

 }
