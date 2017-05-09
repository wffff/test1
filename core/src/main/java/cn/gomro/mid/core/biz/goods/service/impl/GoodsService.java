package cn.gomro.mid.core.biz.goods.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.goods.entity.*;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.IGoodsCategoryService;
import cn.gomro.mid.core.biz.goods.service.IGoodsService;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import cn.gomro.mid.core.common.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class GoodsService extends AbstractSessionService<GoodsEntity> implements IGoodsService {

    private final Logger logger;

    @EJB
    IGoodsCategoryService categoryService;

    public GoodsService() {
        logger = LoggerFactory.getLogger(GoodsService.class);
    }

    @Override
    public ReturnMessage getName(Integer id) {
        ReturnMessage<GoodsEntity> ret = this.getItem(id);
        if (ReturnCode.isSuccess(ret.getCode()) && ret.getData() != null)
            return ReturnMessage.success(ret.getData().getName(), ret.getData().getName());
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<GoodsEntity>> getItemsPaged(GoodsEntity entity, Integer page, Integer size) {
        String where = " WHERE del=false AND saled=true";
        String order = " ORDER BY id DESC";

        if (null != entity && StringUtils.isNotBlank(entity.getName())) {
            where += " AND name='" + entity.getName() + "'";
        }
        if (null != entity && entity.getViews() != null) {
            order = " ORDER BY views DESC,goods.id DESC";
        }

        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM GoodsEntity" + where,
                "FROM GoodsEntity" + where + order, page, size);
    }

    @Override
    public ReturnMessage<List<GoodsEntity>> addGoodsItems(List<GoodsEntity> goodsList) {
        if (goodsList != null) {
            try {
                for (GoodsEntity goods : goodsList) {
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
    public ReturnMessage<List<GoodsBrandEntity>> queryGoodsBrandListByCategoryId(GoodsType type, Integer categoryId) {
        String jpql = "SELECT goods.brand FROM GoodsEntity goods WHERE goods.type=" + type.ordinal() + " AND goods.category.id=" + categoryId;
        List<GoodsBrandEntity> goodsBrandList = JpaUtils.queryShortResultList(em, jpql);
        return ReturnMessage.success(goodsBrandList);
    }

    @Override
    public ReturnMessage<GoodsEntity> getItemBySpecId(Integer specId) {
        String jpql = "SELECT goods FROM GoodsEntity goods,GoodsSpecEntity spec,GoodsModelEntity model WHERE goods.id=model.goods AND model.id=spec.model AND spec.id=" + specId;
        GoodsEntity goods = JpaUtils.querySingleResult(em, jpql);
        return ReturnMessage.success(goods);
    }

    @Override
    public ReturnMessage<List<GoodsEntity>> queryGoodsList(GoodsType type, Integer brandId, Integer categoryId, boolean onlyCategory, String brandName, String goodsName,
                                                           boolean haseImages,
                                                           String modelName, String specName, Integer corporationId, String corprationName, String keyWord,
                                                           Boolean goodsSaled, Boolean specSaled, Boolean modelSaled, String orderBy, Double priceMin, Double priceMax,
                                                           Integer page, Integer size) {

        String order = " ORDER BY ";
        String oBy = "goods.id DESC";
        String distinct = "DISTINCT";
        if (StringUtils.isNotBlank(orderBy)) {
            if (orderBy.equalsIgnoreCase("salesAsc")) oBy = "goods.sales ASC,goods.id DESC";
            if (orderBy.equalsIgnoreCase("salesDesc")) oBy = "goods.sales DESC,goods.id DESC";
            if (orderBy.equalsIgnoreCase("priceAsc")) {
                oBy = "goods.priceMin ASC,goods.id DESC";
            }
            if (orderBy.equalsIgnoreCase("priceDesc")) {
                oBy = "goods.priceMax DESC,goods.id DESC";
            }
            if (orderBy.equalsIgnoreCase("viewsAsc")) oBy = "goods.views ASC,goods.id DESC";
            if (orderBy.equalsIgnoreCase("viewsDesc")) oBy = "goods.views DESC,goods.id DESC";
        }

        String jpql = "SELECT " + distinct + " goods FROM GoodsEntity as goods " +
                "left join goods.GoodsModelList as model " +
                "left join model.goodsSpecList as spec " +
                "WHERE goods.del=false AND model.del=false and spec.del=false";

        String countJpql = "SELECT COUNT(DISTINCT goods.id) FROM GoodsEntity as goods " +
                "left join goods.GoodsModelList as model " +
                "left join model.goodsSpecList as spec " +
                "WHERE goods.del=false AND model.del=false and spec.del=false";


        if (type != null && size != null && size > 0) {

            String where = " AND goods.type=" + type.ordinal();
            if (brandId != null && brandId > 0) where += " AND goods.brand.id=" + brandId;

            List<Integer> alist = null;
            if (onlyCategory && categoryId != null && categoryId > 0) where += " AND goods.category.id=" + categoryId;
            if (!onlyCategory && categoryId != null && categoryId > 0) {
                alist = getCategorysRecursive(type, categoryId);
                where += " AND goods.category.id IN (:alist)";
            }
            if (StringUtils.isNotBlank(brandName))
                where += " AND LOWER(goods.brand.name) like LOWER('%" + brandName + "%')";


            if (StringUtils.isNotBlank(goodsName)) where += " AND LOWER(goods.name) like LOWER('%" + goodsName + "%')";
            if (StringUtils.isNotBlank(modelName)) where += " AND LOWER(model.name) like LOWER('%" + modelName + "%')";
            if (StringUtils.isNotBlank(specName)) where += " AND LOWER(spec.name) like LOWER('%" + specName + "%')";
            if (corporationId != null && corporationId > 0) where += " AND spec.corporation.id=" + corporationId;
            if (StringUtils.isNotBlank(corprationName))
                where += " AND LOWER(vendor.companyName) like LOWER('%" + corprationName + "%')";
            if (goodsSaled != null) where += " AND goods.saled=" + goodsSaled;
            if (modelSaled != null) where += " AND model.saled=" + modelSaled;
            if (specSaled != null) where += " AND spec.saled=" + specSaled;

            if (priceMax != null && priceMax > 0) where += " AND spec.price<=" + priceMax;
            if (priceMin != null && priceMin > 0) where += " AND spec.price>=" + priceMin;
            if (haseImages) where += " AND goods.images is not null AND goods.images <> ''";

            if (StringUtils.isNotBlank(keyWord)) {
                String[] keyWordArray = keyWord.split(" ");
                for (String aKeyWordArray : keyWordArray) {
                    where += " AND (LOWER(goods.brand.name) like LOWER('%" + aKeyWordArray + "%')";
                    where += " OR LOWER(goods.name) like LOWER('%" + aKeyWordArray + "%')";
                    where += " OR LOWER(model.name) like LOWER('%" + aKeyWordArray + "%')";
                    where += " OR LOWER(spec.name) like LOWER('%" + aKeyWordArray + "%'))";
                }
            }

            Query queryCount = em.createQuery(countJpql + where);
            queryCount.setHint(Constants.JPA_CACHEABLE, true);

            if (categoryId != null && categoryId > 0 && !onlyCategory) {
                queryCount.setParameter("alist", alist);
            }
            int count = new Long((long) queryCount.getSingleResult()).intValue();

            Query query = em.createQuery(jpql + where + order + oBy);
            query.setHint(Constants.JPA_CACHEABLE, true);

            if (categoryId != null && categoryId > 0 && !onlyCategory) {
                query.setParameter("alist", alist);
            }

            int pages = size == 0 ? 0 : (count / size);
            if (count % size != 0) pages++;
            if (page < 1) page = 1;
            if (page > pages) page = pages;

            int start = (page - 1) * size;
            if (count > 0 && start < count) {
                query.setFirstResult(start);
                query.setMaxResults(size);
            }
            List<GoodsEntity> resultList = query.getResultList();
            return ReturnMessage.message(count, String.valueOf(size), resultList);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<GoodsEntity>> randomQueryGoodsByCategoryOrBrand(GoodsType type, Integer brandId, Integer categoryId, boolean hasImages, Integer size) {

        try {
            String where = " WHERE goods.del=false AND goods.saled=true";
            String order = " ORDER BY random()";
            String jpql = "SELECT goods FROM GoodsEntity goods";
            size = Utils.reqInt(size, 1);

            if (type != GoodsType.ALL && type != null) where += " AND goods.type=" + type.ordinal();
            if (brandId != null && brandId > 0) where += " AND goods.brand.id=" + brandId;
            if (hasImages) where += " AND goods.images is not null AND goods.images <> ''";
            List<Integer> alist = null;
            if (categoryId != null && categoryId > 0) {
                alist = this.getCategorysRecursive(type, categoryId);
                if (alist != null && alist.size() > 0) where += " AND goods.category.id IN (:alist)";
            }

            Query query = em.createQuery(jpql + where + order);
            query.setHint(Constants.JPA_CACHEABLE, true);
            query.setMaxResults(size);
            if (alist != null && alist.size() > 0) {
                query.setParameter("alist", alist);
            }
            List<GoodsEntity> resultList = query.getResultList();
            return ReturnMessage.message(resultList.size(), resultList.size() + "", resultList);
        } catch (Exception e) {
            logger.error("Method: randomQueryGoodsByCategoryOrBrand(), Message: {}", e.getMessage());
            if (e.getCause() != null) {
                logger.error("Cause: {}", e.getCause().getMessage());
            }
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<GoodsEntityManyToOne>> queryGoodsManyToOneList(GoodsType type, Integer brandId, String brandName, Integer categoryId,
                                                                             boolean onlyCategory, boolean haseImages, String goodsName, Boolean goodsSaled,
                                                                             Boolean goodsDel,
                                                                             String orderBy, Double priceMin, Double priceMax, Integer page, Integer size) {

        String order = " ORDER BY ";
        String oBy = "";
        String distinct = "DISTINCT";
        if (StringUtils.isNotBlank(orderBy)) {
            if (orderBy.equalsIgnoreCase("salesAsc")) oBy = "goods.sales ASC";
            if (orderBy.equalsIgnoreCase("salesDesc")) oBy = "goods.sales DESC";
            if (orderBy.equalsIgnoreCase("priceAsc")) oBy = "goods.priceMin ASC";
            if (orderBy.equalsIgnoreCase("priceDesc")) oBy = "goods.priceMax DESC";
            if (orderBy.equalsIgnoreCase("viewsAsc")) oBy = "goods.views ASC";
            if (orderBy.equalsIgnoreCase("viewsDesc")) oBy = "goods.views DESC";
        }
        if (StringUtils.isNotBlank(oBy)) {
            oBy += ",goods.id DESC";
        } else {
            oBy = "goods.id DESC";
        }

        goodsDel = goodsDel == null ? false : true;

        String jpql = "SELECT " + distinct + " goods FROM GoodsEntity as goods " +
                "WHERE goods.del=" + goodsDel;

        String countJpql = "SELECT COUNT(DISTINCT goods.id) FROM GoodsEntity as goods " +
                "WHERE goods.del=" + goodsDel;


        if (type != GoodsType.ALL && type != null && size != null && size > 0) {
            List<Integer> alist = null;
            String where = " AND goods.type=" + type.ordinal();
            if (brandId != null && brandId > 0) where += " AND goods.brand.id=" + brandId;
            //如果参数是仅当前分类下查询
            if (onlyCategory && categoryId != null && categoryId > 0) where += " AND goods.category.id=" + categoryId;
            //如果是当前分类及子分类下查询
            if (!onlyCategory && categoryId != null && categoryId > 0) {
                alist = getCategorysRecursive(type, categoryId);
                where += " AND goods.category.id IN (:alist)";
            }
            if (StringUtils.isNotBlank(brandName))
                where += " AND LOWER(goods.brand.name) like LOWER('%" + brandName + "%')";
            if (StringUtils.isNotBlank(goodsName)) where += " AND LOWER(goods.name) like LOWER('%" + goodsName + "%')";
            if (goodsSaled != null) where += " AND goods.saled=" + goodsSaled;
            if (priceMax != null && priceMax > 0) where += " AND goods.priceMax<=" + priceMax;
            if (priceMin != null && priceMin > 0) where += " AND goods.priceMin>=" + priceMin;
            if (haseImages) where += " AND goods.images is not null AND goods.images <> ''";


            Query queryCount = em.createQuery(countJpql + where);
            queryCount = queryCount.setHint(Constants.JPA_CACHEABLE, true);

            if (categoryId != null && categoryId > 0 && !onlyCategory && alist != null && alist.size() > 0) {
                queryCount.setParameter("alist", alist);
            }
            int count = new Long((long) queryCount.getSingleResult()).intValue();

            Query query = em.createQuery(jpql + where + order + oBy);
            query = query.setHint(Constants.JPA_CACHEABLE, true);

            if (categoryId != null && categoryId > 0 && !onlyCategory) {
                query.setParameter("alist", alist);
            }

            int pages = size == 0 ? 0 : (count / size);
            if (count % size != 0) pages++;
            if (page < 1) page = 1;
            if (page > pages) page = pages;

            int start = (page - 1) * size;
            if (count > 0 && start < count) {
                query.setFirstResult(start);
                query.setMaxResults(size);
            }
            List<GoodsEntityManyToOne> resultList = query.getResultList();

            return ReturnMessage.message(count, String.valueOf(size), resultList);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<GoodsEntityManyToOne> editGoodsManyToOne(GoodsEntityManyToOne goods) {
        try {
            GoodsEntityManyToOne goodsEntityManyToOne = em.merge(goods);
            em.flush();
            return ReturnMessage.success(goodsEntityManyToOne);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnMessage.failed();
        }
    }

    @Override
    public ReturnMessage<GoodsEntity> getItemById(Integer id) {
        try {
            Query query = em.createQuery("FROM GoodsEntity where del=false and saled=true AND id=" + id);
            List<GoodsEntity> resultList = query.getResultList();
            if (resultList.size() > 0) {
                return ReturnMessage.success(resultList.get(0));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
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

    //按品牌下载商品


    @Override
    public ReturnMessage forSaleGoodsByBrandId(Integer id) {
        if (id != null && id > 0) {
            String jpql = "select g from GoodsEntity g where g.brand.id=" + id;
            Query query = em.createQuery(jpql);
            List<GoodsEntity> resultList = query.getResultList();
            for (GoodsEntity g : resultList) {
                g.setSaled(false);
                this.editItem(g);
                System.out.println("下架id:" + g.getId());
            }
            return ReturnMessage.message(ReturnCode.SUCCESS, "成功更新了" + resultList.size() + "条数据！", resultList.size());
        }
        return null;
    }

    @Override
    public ReturnMessage setForSale(Integer[] ids) {
        if (ids != null && ids.length > 0) {
            for (Integer id : ids) {
                GoodsEntity goods = em.find(GoodsEntity.class, id);
                if (goods != null) {
                    goods.setSaled(!goods.getSaled());
                }
                em.merge(goods);
            }
            em.flush();
        }
        return ReturnMessage.success();
    }


    public Integer getgoodsdel(Integer id) {

        String where = " WHERE del=false and id=" + id;
        int i = JpaUtils.queryCount(em, "SELECT COUNT(*) FROM GoodsSpecEntityManyToOne WHERE del=false");
        List<GoodsSpecEntityManyToOne> goodsEntityList = JpaUtils.queryShortResultList(em, "FROM GoodsSpecEntityManyToOne" + where);
        Integer returned = 1;

        GoodsSpecEntityManyToOne goodsEntity = new GoodsSpecEntityManyToOne();

        for (GoodsSpecEntityManyToOne b : goodsEntityList) {
            b.setDel(true);
            goodsEntity = em.merge(b);
        }

        int s = JpaUtils.queryCount(em, "SELECT COUNT(*) FROM GoodsSpecEntityManyToOne WHERE del=false");
        if (i >= s) {
            returned = 2;
        } else {
            returned = 1;
        }

        return returned;
    }

}
