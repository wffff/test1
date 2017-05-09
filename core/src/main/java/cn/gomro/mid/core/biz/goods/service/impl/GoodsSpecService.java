package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.common.entity.AccessTokenEntity;
import cn.gomro.mid.core.biz.goods.entity.*;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsCategoryService;
import cn.gomro.mid.core.biz.goods.service.IGoodsSpecService;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import cn.gomro.mid.core.common.utils.StrEscapeUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by yaoo on 2016/8/19.
 */
@Stateless
public class GoodsSpecService extends AbstractSessionService<GoodsSpecEntity> implements IGoodsSpecService {

    final Logger logger = LoggerFactory.getLogger(GoodsSpecService.class);

    @EJB
    IGoodsCategoryService categoryService;

    public GoodsSpecService() {
    }

    @Override
    public ReturnMessage getName(Integer id) {
        ReturnMessage<GoodsSpecEntity> ret = this.getItem(id);
        if (ReturnCode.isSuccess(ret.getCode()))
            return ReturnMessage.success(ret.getData().getName(), ret.getData().getName());
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<GoodsSpecEntity>> getItemsPaged(GoodsSpecEntity entity, Integer page, Integer size) {

        String where = " WHERE del=false";
        String order = " ORDER BY id DESC";

        if (entity != null && StringUtils.isNotBlank(entity.getName())) {
            where += " AND LOWER(name)=LOWER('" + entity.getName() + "')";
        }
        List<GoodsSpecEntity> specEntitie = JpaUtils.queryShortResultList(em, "FROM GoodsSpecEntity" + where);

        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM GoodsSpecEntity" + where,
                "FROM GoodsSpecEntity" + where + order, page, size);
    }

    @Override
    public ReturnMessage<List<GoodsSpecEntity>> addGoodsItems(List<GoodsSpecEntity> goodsList) {
        if (goodsList != null) {
            try {
                for (GoodsSpecEntity spec : goodsList) {
                    this.addItem(spec);
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
    public ReturnMessage<List<GoodsSpecEntityManyToOne>> queryGoodsSpecList(GoodsType type,
                                                                            Integer goodsId, String goodsName, Boolean goodsSaled,
                                                                            Integer brandId, String brandName,
                                                                            Integer categoryId,
                                                                            Integer modelId, String modelName, Boolean modelSaled,
                                                                            Integer specId, String specName, Boolean specSaled,
                                                                            Double priceMin, Double priceMax,
                                                                            Integer corporationId, String corprationName,String keyWord,
                                                                            String orderBy, boolean hasImages,
                                                                            Integer page, Integer size) {
        String where = " AND spec.del=false";
        String order = " ORDER BY goods.id DESC";
        String sql = " FROM GoodsSpecEntityManyToOne spec left join spec.model model left join model.goods goods left join goods.brand brand " +
                "WHERE spec.del=false AND model.del=false AND goods.del=false AND brand.del=false " +
                "AND spec.model=model.id AND model.goods=goods.id AND goods.brand=brand.id AND goods.saled=true ";
        String jpql = "SELECT spec" + sql;
        String jpqlCount = "SELECT COUNT(spec.id)" + sql;

        if(StringUtils.isNotBlank(keyWord)){
            String[] keyWordArray = keyWord.split(" ");
            for(int i=0;i<keyWordArray.length;i++){
                where += " AND (LOWER(brand.name) like LOWER('%" + keyWordArray[i] + "%')";
                where += " OR LOWER(goods.name) like LOWER('%" + keyWordArray[i] + "%')";
                where += " OR LOWER(model.name) like LOWER('%" + keyWordArray[i] + "%')";
                where += " OR LOWER(spec.name) like LOWER('%" + keyWordArray[i] + "%'))";
            }
        }

        if (type != GoodsType.ALL && type != null) where += " AND goods.type=" + type.ordinal();
        if (goodsId != null && goodsId > 0) where += " AND goods.id = " + goodsId;
        if (StringUtils.isNotBlank(goodsName)) where += " AND LOWER(goods.name) like LOWER('%" + goodsName + "%')";
//        if (goodsSaled != null) where += " AND goods.saled = true";
        if (brandId != null && brandId > 0) where += " AND brand.id = " + brandId;
        if (StringUtils.isNotBlank(brandName)) where += " AND LOWER(brand.name) like LOWER('%" + brandName + "%')";
//        if (categoryId != null && categoryId > 0) where += " AND goods.category.id = " + categoryId;

        List<Integer> alist = null;
        //如果是当前分类及子分类下查询
        if (categoryId != null && categoryId > 0) {
            alist = getCategorysRecursive(type, categoryId);
            where += " AND goods.category.id IN (:alist)";
        }
        if (modelId != null && modelId > 0) where += " AND model.id = " + modelId;
        if (StringUtils.isNotBlank(modelName)) where += " AND LOWER(model.name) like LOWER('%" + modelName + "%')";
        if (modelSaled != null) where += " AND model.saled = " + modelSaled;
        if (specId != null && specId > 0) where += " AND spec.id = " + specId;
        if (StringUtils.isNotBlank(specName)) where += " AND LOWER(spec.name) like LOWER('%" + specName + "%')";
        if (specSaled != null) where += " AND spec.saled = " + specSaled;
        if (priceMax != null && priceMax > 0) where += " AND spec.price<=" + priceMax;
        if (priceMin != null && priceMin > 0) where += " AND spec.price>=" + priceMin;
        if (corporationId != null && corporationId > 0) where += " AND spec.corporation.id=" + corporationId;
        if (StringUtils.isNotBlank(corprationName))
            where += " AND LOWER(spec.corporation.companyName) like LOWER('%" + corprationName + "%')";
        if (StringUtils.isNotBlank(orderBy)) {
            if (orderBy.equalsIgnoreCase("salesAsc")) order = " ORDER BY goods.sales ASC,spec.id DESC";
            if (orderBy.equalsIgnoreCase("salesDesc")) order = " ORDER BY goods.sales DESC,spec.id DESC";
            if (orderBy.equalsIgnoreCase("priceAsc")) order = " ORDER BY spec.price ASC,spec.id DESC";
            if (orderBy.equalsIgnoreCase("priceDesc")) order = " ORDER BY spec.price DESC,spec.id DESC";
            if (orderBy.equalsIgnoreCase("viewsAsc")) order = " ORDER BY spec.views ASC,spec.id DESC";
            if (orderBy.equalsIgnoreCase("viewsDesc")) order = " ORDER BY spec.views DESC,spec.id DESC";
        }
        if (hasImages) where += " AND goods.images is not null AND goods.images <> ''";

        Query queryCount = em.createQuery(jpqlCount + where);
        queryCount = queryCount.setHint(Constants.JPA_CACHEABLE, true);
        Query query = em.createQuery(jpql + where + order);
        query = query.setHint(Constants.JPA_CACHEABLE, true);

        if (categoryId != null && categoryId > 0 && alist != null && alist.size() > 0) {
            queryCount.setParameter("alist", alist);
            query.setParameter("alist", alist);
        }
        int count = new Long((long) queryCount.getSingleResult()).intValue();
        int pages = size == 0 ? 0 : (count / size);
        if (count % size != 0) pages++;
        if (page < 1) page = 1;
        if (page > pages) page = pages;

        int start = (page - 1) * size;
        if (count > 0 && start < count) {
            query.setFirstResult(start);
            query.setMaxResults(size);
        }
        List<GoodsSpecEntityManyToOne> resultList = query.getResultList();

        return ReturnMessage.message(count,String.valueOf(resultList.size()),resultList);
    }

    @Override
    public ReturnMessage<GoodsSpecEntityManyToOne> getExistsGoodsSpec(GoodsType type, Integer corporationId, String brandName, String goodsName, String modelName,
                                                                      String specName, Integer packageNum, String unitName, String packageUnitName) {
        String where = " AND spec.del=false AND model.del=false AND goods.del=false";
        String jpql = "SELECT spec FROM GoodsSpecEntityManyToOne AS spec LEFT JOIN spec.model AS model LEFT JOIN model.goods AS goods  WHERE spec.model = model.id  AND model.goods = goods.id";
        if (corporationId != null && type != GoodsType.ALL && type != null
                && brandName != null && goodsName != null
                && modelName != null && specName != null
                && packageNum != null && unitName != null
                && packageUnitName != null) {

            where += " AND spec.corporation.id=" + corporationId +
                    " AND goods.type=" + type.ordinal() +
                    " AND LOWER(goods.brand.name)=LOWER('" + StringEscapeUtils.escapeSql(StrEscapeUtils.escapeQuots(brandName)) + "')" +
                    " AND LOWER(goods.name)=LOWER('" + StringEscapeUtils.escapeSql(StrEscapeUtils.escapeQuots(goodsName)) + "')" +
                    " AND LOWER(model.name)=LOWER('" + StringEscapeUtils.escapeSql(StrEscapeUtils.escapeQuots(modelName)) + "')" +
                    " AND LOWER(spec.name)=LOWER('" + StringEscapeUtils.escapeSql(StrEscapeUtils.escapeQuots(specName)) + "')" +
                    " AND spec.packageNum=" + packageNum +
                    " AND spec.unit.name='" + unitName + "'" +
                    " AND spec.packageUnit.name='" + packageUnitName + "'";

            GoodsSpecEntityManyToOne entity = JpaUtils.querySingleResult(em, jpql + where);
            return ReturnMessage.success(entity);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<Map<String, Double>> queryGoodsMaxAndMinPrice(Integer goodsId) {

        if (goodsId != null) {
            String jpql = "SELECT MAX(spec.price),MIN(spec.price) " +
                    "FROM GoodsSpecEntity spec,GoodsModelEntity model,GoodsEntity goods " +
                    "WHERE spec.model = model.id AND model.del=false AND spec.del=false AND model.saled=true " +
                    "AND spec.saled=true AND model.goods = goods.id AND goods.id=" + goodsId;

            Query query = em.createQuery(jpql);
            query.setHint(Constants.JPA_CACHEABLE, true);
            Object[] cells = (Object[]) query.getSingleResult();
            Double min = (Double) cells[0];
            Double max = (Double) cells[1];
            max = max != min ? max : null;
            Map<String, Double> map = new HashMap();
            map.put("priceMin", min);
            map.put("priceMax", max);
            return ReturnMessage.success(map);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<GoodsSpecEntityManyToOne> getManyToOneItem(Integer id) {
        GoodsSpecEntityManyToOne goodsSpecEntityManyToOne = null;
        try {
            //em.setProperty(Constants.JPA_CACHEABLE, true);
            goodsSpecEntityManyToOne = em.find(GoodsSpecEntityManyToOne.class, id);
            return ReturnMessage.success(goodsSpecEntityManyToOne);
        } catch (Exception e) {
            logger.error(e.getMessage());
            if(e.getCause()!=null){
                logger.error(e.getCause().getMessage());
            }
        } finally {
            em.clear();
        }
        return ReturnMessage.failed();
    }

    public List<GoodsSpecEntityManyToOne> getGoodsSpecModelGoodByIds(Integer id)
    {
        List<GoodsSpecEntityManyToOne> GoodspecList=new ArrayList<GoodsSpecEntityManyToOne>();
        List<Integer> list=new ArrayList<>();
        GoodsSpecEntityManyToOne goodsSpecEntityManyToOne=JpaUtils.querySingleResult(em, "FROM GoodsSpecEntityManyToOne s where s.id="+id);//+" and s.model.goods.brand.id="+
        GoodsSpecEntityManyToOne goodsSpecEntityManyToOne1 = JpaUtils.querySingleResult(em, "FROM GoodsSpecEntityManyToOne s where s.id=" + id+" and s.model.goods.brand="+goodsSpecEntityManyToOne.getModel().getGoods().getBrand().getId());
        List<GoodsModelEntityManyToOne> goodsEntityManyToOneList = JpaUtils.queryShortResultList(em, "FROM GoodsModelEntityManyToOne  where goods.name  LIKE  lower('%"+goodsSpecEntityManyToOne.getModel().getGoods().getName()+"%') and goods.brand="+goodsSpecEntityManyToOne.getModel().getGoods().getBrand().getId()+" and goods.category="+goodsSpecEntityManyToOne.getModel().getGoods().getCategory().getId());
        for (GoodsModelEntityManyToOne g:goodsEntityManyToOneList)
        {
            System.out.println("数据 ："+g);
            list.add(g.getId());
        }
        for(Integer i:list)
        {
            GoodspecList.add(JpaUtils.querySingleResult(em, "FROM GoodsSpecEntityManyToOne where model_id="+i));
        }
       return GoodspecList;
    }

    @Override
    public ReturnMessage<GoodsSpecEntityManyToOne> editSpec(GoodsSpecEntityManyToOne spec) {
        try {
            GoodsSpecEntityManyToOne merge = em.merge(spec);
            em.flush();
            return ReturnMessage.success(merge);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ReturnMessage.failed();
        }
    }

    @Override
    public ReturnMessage<GoodsSpecEntityManyToOne> addSpec(GoodsSpecEntityManyToOne spec) {
        try {
            em.persist(spec);
            em.flush();
            return ReturnMessage.success(spec);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ReturnMessage.failed();
        }
    }

    @Override
    public ReturnMessage<GoodsSpecEntityManyToOne> addSpec(GoodsSpecEntityManyToOne spec, Integer modelid, Integer brandId, Integer categoryid,Integer unitid,String goodsname,String modelname,String images,String UNSpsc,String HSCode,Integer packageUnitid) {
        Date date=new Date();
        try {
            GoodsBrandEntity brandEntity = JpaUtils.querySingleResult(em, "FROM GoodsBrandEntity where id="+brandId);
            GoodsCategoryEntityWithOutTree goodsCategoryEntityWithOutTree= JpaUtils.querySingleResult(em, "FROM GoodsCategoryEntityWithOutTree where id="+categoryid);
            GoodsEntityManyToOne goodsEntityManyToOne=new GoodsEntityManyToOne();
            goodsEntityManyToOne.setHscode(HSCode);
            goodsEntityManyToOne.setUnspsc(UNSpsc);
            goodsEntityManyToOne.setSaled(true);
            goodsEntityManyToOne.setName(goodsname);
            goodsEntityManyToOne.setImages("");
            goodsEntityManyToOne.setDescription(" ");
            goodsEntityManyToOne.setViews(6);
            goodsEntityManyToOne.setMemo("");
            goodsEntityManyToOne.setPriceMin(10.0);
            goodsEntityManyToOne.setPriceMax(10.0);
            goodsEntityManyToOne.setDel(false);
            goodsEntityManyToOne.setBrand(brandEntity);
            goodsEntityManyToOne.setCategory(goodsCategoryEntityWithOutTree);
            goodsEntityManyToOne.setGoodsModelList(null);
            goodsEntityManyToOne.setSales(1);
            goodsEntityManyToOne.setTags("");
            goodsEntityManyToOne.setType(GoodsType.MARKET);
            goodsEntityManyToOne.setLast(date);
            goodsEntityManyToOne.setTime(date);
            goodsEntityManyToOne.setPackages(" ");
            goodsEntityManyToOne.setTechnology("");


            em.persist(goodsEntityManyToOne);
            em.flush();
            Query query=em.createQuery("select max(id) from GoodsEntityManyToOne");
            GoodsEntityManyToOne goodsEntityManyToOne1=(GoodsEntityManyToOne)query.getSingleResult();

            GoodsModelEntityManyToOne goodsModelEntityManyToOne=new GoodsModelEntityManyToOne();
            goodsModelEntityManyToOne.setGoods(goodsEntityManyToOne1);
            goodsModelEntityManyToOne.setName(modelname);
            goodsModelEntityManyToOne.setSaled(true);
            goodsModelEntityManyToOne.setDel(false);
            goodsModelEntityManyToOne.setLast(date);
            goodsModelEntityManyToOne.setTime(date);
            goodsModelEntityManyToOne.setGoodsSpecList(null);
            em.persist(goodsModelEntityManyToOne);
            em.flush();
            Query query1=em.createQuery("select max(id) from GoodsModelEntityManyToOne");
            GoodsModelEntityManyToOne goodsModelEntityManyToOne1=(GoodsModelEntityManyToOne)query1.getSingleResult();
            GoodsUnitEntity goodsUnitEntity=JpaUtils.querySingleResult(em, "FROM GoodsUnitEntity where id="+brandId);
            GoodsUnitEntity goodsUnitEntity1=JpaUtils.querySingleResult(em, "FROM GoodsUnitEntity where id="+packageUnitid);
             spec.setModel(goodsModelEntityManyToOne1);
             spec.setUnit(goodsUnitEntity);
             spec.setPackageUnit(goodsUnitEntity1);

            em.persist(spec);
            em.flush();
            return ReturnMessage.success(spec);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ReturnMessage.failed();
        }
    }


    @Override
    public ReturnMessage adjustPrice(AccessTokenEntity access, GoodsType type, Integer brandId, Integer percent) {

        try {
            Double per = Double.valueOf(percent)/100;
            //String where = " WHERE spec.del=false AND spec.saled=true AND spec.model.goods.type=" + type.ordinal();
            String jpql = "UPDATE GoodsSpecEntity spec SET spec.price=spec.price*"+per;
            //if(brandId !=null && brandId>0) where += " AND spec.model.goods.brand.id="+brandId;

            Query query = em.createQuery(jpql);
            logger.info(access.getAccount().getFullname()+"开始修改价格:");
            int i = query.executeUpdate();
            logger.info(access.getAccount().getFullname()+"  商品价格调整: "+ type +" 是否限定品牌: "+ brandId +" 调整比例: "+ percent);
            return ReturnMessage.success(i);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ReturnMessage.failed();
        }finally {
            em.clear();
        }
    }

    //根据分类ID取子分类所有的ID
    private List<Integer> getCategorysRecursive(GoodsType type, Integer categoryId) {
        List<Integer> alist = new ArrayList<>();
        List<GoodsCategoryEntityWithOutTree> categoryList = categoryService.querySelfAndListById(type, categoryId, false).getData();
        if (categoryList != null && categoryList.size() > 0) {
            for (GoodsCategoryEntityWithOutTree c : categoryList) {
                alist.add(c.getId());
            }
        }
        return alist;
    }

    public ReturnMessage<List<GoodsSpecEntityManyToOne>> queryAudiSpecList(GoodsType type,
                                                                            Integer goodsId, String goodsName, Boolean goodsSaled,
                                                                            Integer brandId, String brandName,
                                                                            Integer categoryId,
                                                                            Integer modelId, String modelName, Boolean modelSaled,
                                                                            Integer specId, String specName, Boolean specSaled,
                                                                            Double priceMin, Double priceMax,
                                                                            Integer memberId, String memberName,String keyWord,
                                                                            String orderBy, boolean hasImages,
                                                                            Integer page, Integer size) {
        String where = " AND spec.del=false";
        String order = " ORDER BY goods.id DESC";
        String sql = " FROM GoodsSpecEntityManyToOne spec left join spec.model model left join model.goods goods left join goods.brand brand " +
                "WHERE spec.del=false AND model.del=false AND goods.del=false AND brand.del=false " +
                "AND spec.model=model.id AND model.goods=goods.id AND goods.brand=brand.id AND goods.saled=false";
        String jpql = "SELECT spec" + sql;
        String jpqlCount = "SELECT COUNT(spec.id)" + sql;

        if(StringUtils.isNotBlank(keyWord)){
            String[] keyWordArray = keyWord.split(" ");
            for(int i=0;i<keyWordArray.length;i++){
                where += " AND (LOWER(brand.name) like LOWER('%" + keyWordArray[i] + "%')";
                where += " OR LOWER(goods.name) like LOWER('%" + keyWordArray[i] + "%')";
                where += " OR LOWER(model.name) like LOWER('%" + keyWordArray[i] + "%')";
                where += " OR LOWER(spec.name) like LOWER('%" + keyWordArray[i] + "%'))";
            }
        }

        if (type != GoodsType.ALL && type != null) where += " AND goods.type=" + type.ordinal();
        if (goodsId != null && goodsId > 0) where += " AND goods.id = " + goodsId;
        if (StringUtils.isNotBlank(goodsName)) where += " AND LOWER(goods.name) like LOWER('%" + goodsName + "%')";
//        if (goodsSaled != null) where += " AND goods.saled = true";
        if (brandId != null && brandId > 0) where += " AND brand.id = " + brandId;
        if (StringUtils.isNotBlank(brandName)) where += " AND LOWER(brand.name) like LOWER('%" + brandName + "%')";
//        if (categoryId != null && categoryId > 0) where += " AND goods.category.id = " + categoryId;

        List<Integer> alist = null;
        //如果是当前分类及子分类下查询
        if (categoryId != null && categoryId > 0) {
            alist = getCategorysRecursive(type, categoryId);
            where += " AND goods.category.id IN (:alist)";
        }
        if (modelId != null && modelId > 0) where += " AND model.id = " + modelId;
        if (StringUtils.isNotBlank(modelName)) where += " AND LOWER(model.name) like LOWER('%" + modelName + "%')";
        if (modelSaled != null) where += " AND model.saled = " + modelSaled;
        if (specId != null && specId > 0) where += " AND spec.id = " + specId;
        if (StringUtils.isNotBlank(specName)) where += " AND LOWER(spec.name) like LOWER('%" + specName + "%')";
        if (specSaled != null) where += " AND spec.saled = " + specSaled;
        if (priceMax != null && priceMax > 0) where += " AND spec.price<=" + priceMax;
        if (priceMin != null && priceMin > 0) where += " AND spec.price>=" + priceMin;
        if (memberId != null && memberId > 0) where += " AND spec.member.id=" + memberId;
        if (StringUtils.isNotBlank(memberName))
            where += " AND LOWER(spec.member.companyName) like LOWER('%" + memberName + "%')";
        if (StringUtils.isNotBlank(orderBy)) {
            if (orderBy.equalsIgnoreCase("salesAsc")) order = " ORDER BY goods.sales ASC,spec.id DESC";
            if (orderBy.equalsIgnoreCase("salesDesc")) order = " ORDER BY goods.sales DESC,spec.id DESC";
            if (orderBy.equalsIgnoreCase("priceAsc")) order = " ORDER BY spec.price ASC,spec.id DESC";
            if (orderBy.equalsIgnoreCase("priceDesc")) order = " ORDER BY spec.price DESC,spec.id DESC";
            if (orderBy.equalsIgnoreCase("viewsAsc")) order = " ORDER BY spec.views ASC,spec.id DESC";
            if (orderBy.equalsIgnoreCase("viewsDesc")) order = " ORDER BY spec.views DESC,spec.id DESC";
        }
        if (hasImages) where += " AND goods.images is not null AND goods.images <> ''";

        Query queryCount = em.createQuery(jpqlCount + where);
        queryCount = queryCount.setHint(Constants.JPA_CACHEABLE, true);
        Query query = em.createQuery(jpql + where + order);
        query = query.setHint(Constants.JPA_CACHEABLE, true);

        if (categoryId != null && categoryId > 0 && alist != null && alist.size() > 0) {
            queryCount.setParameter("alist", alist);
            query.setParameter("alist", alist);
        }
        int count = new Long((long) queryCount.getSingleResult()).intValue();
        int pages = size == 0 ? 0 : (count / size);
        if (count % size != 0) pages++;
        if (page < 1) page = 1;
        if (page > pages) page = pages;

        int start = (page - 1) * size;
        if (count > 0 && start < count) {
            query.setFirstResult(start);
            query.setMaxResults(size);
        }
        List<GoodsSpecEntityManyToOne> resultList = query.getResultList();

        return ReturnMessage.message(count,String.valueOf(resultList.size()),resultList);
    }

}
