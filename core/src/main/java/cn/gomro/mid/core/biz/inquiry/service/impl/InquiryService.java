package cn.gomro.mid.core.biz.inquiry.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.inquiry.biz.impl.InquiryBiz;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryEntity;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryGoodsEntity;
import cn.gomro.mid.core.biz.inquiry.service.IInquiryService;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/1/17.
 */
@Stateless
public class InquiryService extends AbstractSessionService<InquiryEntity> implements IInquiryService {

    final Logger logger = LoggerFactory.getLogger(InquiryService.class);

    public InquiryService() {
    }

    @Override
    public ReturnMessage<InquiryEntity> saveInquiry(InquiryEntity inquiry) {
        boolean NPE = inquiry != null && inquiry.getGoods() != null && inquiry.getGoods().size() > 0;
        if (NPE) {
            return super.addItem(inquiry);
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage removeInquiryGoods(Integer id) {
        try {
            InquiryGoodsEntity data = this.getById(id).getData();
            data.setLast(new Date());
            data.setDel(true);
            em.merge(data);
            em.flush();
            return ReturnMessage.success();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<InquiryEntity> updateInquiry(Integer id, String contacts, String qq, String description, Date expire) {
        InquiryEntity data = getInquiryById(id).getData();
        if (data == null) {
            return ReturnMessage.failed();
        }
        if (contacts != null) {
            data.setContacts(contacts);
        }
        if (qq != null) {
            data.setQq(qq);
        }
        if (description != null) {
            data.setDescription(description);
        }
        if (expire != null) {
            data.setExpire(expire);
        }
        return this.editItem(data);
    }


    @Override
    public ReturnMessage<InquiryGoodsEntity> updateInquiryGoods(InquiryGoodsEntity goods) {

        InquiryGoodsEntity data = this.getById(goods.getId()).getData();

        if (data == null && goods == null) {
            return ReturnMessage.failed();
        }
        Date date = new Date();
        InquiryEntity inquiry = goods.getInquiry();
        if (inquiry != null) {

            if (inquiry.getContacts() != null) {//更新寻价单联系人
                data.getInquiry().setContacts(inquiry.getContacts());
            }
            if (inquiry.getQq() != null) {//更新寻价单联系人QQ
                data.getInquiry().setQq(inquiry.getQq());
            }
            if (inquiry.getStatus() != null) {//更新寻价单状态
                data.getInquiry().setStatus(inquiry.getStatus());
            }
            if (inquiry.getDescription() != null) {//更新寻价单描述
                data.getInquiry().setDescription(inquiry.getDescription());
            }

            inquiry.setLast(date);
        }

        if (goods.getPurchaseOrderId() != null) data.setPurchaseOrderId(goods.getPurchaseOrderId());//更新订单id
        if (goods.getName() != null) data.setName(goods.getName());//更新名称
        if (goods.getBrand() != null) data.setBrand(goods.getBrand());//更新品牌
        if (goods.getModel() != null) data.setModel(goods.getModel());//更新型号
        if (goods.getDescription() != null) data.setDescription(goods.getDescription());//更新商品需求描述
        if (goods.getAddress() != null) data.setAddress(goods.getAddress());//更新交货地址
        if (goods.getDelivery() != null) data.setDelivery(goods.getDelivery());//更新期望交期
        if (goods.getNum() != null && goods.getNum() > 0) data.setNum(goods.getNum());//更新数量
        if (goods.getUnit() != null) data.setUnit(goods.getUnit());//更新单位
        if (goods.getPackageNum() != null) data.setPackageNum(goods.getPackageNum());//更新包装数量
        if (goods.getPackageUnit() != null) data.setPackageUnit(goods.getPackageUnit());//更新包装单位
        if (goods.getStatus() != null) data.setStatus(goods.getStatus());//更新状态
        if (goods.getQuotationCount() != null) data.setQuotationCount(goods.getQuotationCount());//更新报价统计
        if (goods.getCategory() != null) data.setCategory(goods.getCategory());//更新分类id
        if (goods.getAttachment() != null) data.setAttachment(goods.getAttachment());//更新附件
        if (goods.getUrl() != null) data.setUrl(goods.getUrl());//更新链接
        data.setLast(date);
        try {
            em.merge(data);
            em.flush();
            return ReturnMessage.success(data);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<InquiryGoodsEntity> getById(Integer id) {
        Query query = em.createQuery("FROM InquiryGoodsEntity where id=" + id, InquiryGoodsEntity.class);
        query.setHint(Constants.JPA_CACHEABLE, true);
        try {
            InquiryGoodsEntity inquiryGoods = (InquiryGoodsEntity) query.getSingleResult();
            return ReturnMessage.success(inquiryGoods);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<InquiryGoodsEntity>> listInquiryGoods(InquiryGoodsEntity goods, Integer page, Integer size, Date startDate, Date endDate) {
        /**
         * 这里要写成二次判断，因为用了占位符
         */
        String jpql = "select i from InquiryGoodsEntity i";
        String jpqlCount = "select count(i) from InquiryGoodsEntity i";
        String where = " where i.del=false and i.inquiry.del=false";
        String order = " order by i.id desc";

        InquiryEntity inquiry = goods.getInquiry();

        if (inquiry != null) {
            if (inquiry.getContacts() != null) {
                //按联系人搜索
                where += " and i.inquiry.contacts like :contacts";
            }
            if (inquiry.getStatus() != null) {
                //状态搜索
                where += " and i.inquiry.status = :inquiryStatus";
            }
            if (inquiry.getId() != null) {
                //按询价ID搜索
                where += " and i.inquiry.id = :inquiryId";
            }
            if (inquiry.getMember().getCorporation().getName() != null) {
                //按询价公司名字搜索
                where += " and i.corporation.name = :companyName";
            }
        }
        if (goods != null) {
            if (goods.getAddress() != null) {
                //按询价商品交货地搜索
                where += " and i.address = :address";
            }
            if (goods.getName() != null) {
                //按询价商品名搜索
                where += " and i.name = :name";
            }
            if (goods.getStatus() != null) {
                //按询价商品状态搜索
                where += " and i.status = :status";
            }
            if (goods.getCategory() != null && goods.getCategory().getId() != null) {
                //按询价商品分类ID搜索
                where += " and i.categoryId = :categoryId";
            }
            if (goods.getBrand() != null) {
                //按询价商品品牌搜索
                where += " and i.brand = :brand";
            }
            if (goods.getModel() != null) {
                //按询价商品型号搜索
                where += " and i.model = :model";
            }
            if (startDate != null) {
                //按询价商品起始时间搜索
                where += " and i.time >= :startDate";
            }
            if (endDate != null) {
                //按询价商品起始时间搜索
                where += " and i.time <= :endDate";
            }

        }
        Query query = em.createQuery(jpql + where + order);
        Query countQuery = em.createQuery(jpqlCount + where);

        /**
         * 这里要写成二次判断，因为用了占位符
         */
        if (inquiry != null) {
            if (inquiry.getContacts() != null) {
                query.setParameter("contacts", "%" + inquiry.getContacts() + "%");
                countQuery.setParameter("contacts", "%" + inquiry.getContacts() + "%");
            }
            if (inquiry.getStatus() != null) {
                query.setParameter("inquiryStatus", inquiry.getStatus());
                countQuery.setParameter("inquiryStatus", inquiry.getStatus());
            }
            if (inquiry.getId() != null) {
                query.setParameter("inquiryId", inquiry.getId());
                countQuery.setParameter("inquiryId", inquiry.getId());
            }
            if (inquiry.getMember().getCorporation() != null && inquiry.getMember().getCorporation().getName() != null) {
                query.setParameter("companyName", inquiry.getMember().getCorporation().getName());
                countQuery.setParameter("companyName", inquiry.getMember().getCorporation().getName());
            }
        }

        if (goods != null) {
            if (goods.getAddress() != null) {
                //按询价商品交货地搜索
                query.setParameter("address", goods.getAddress());
                countQuery.setParameter("address", goods.getAddress());
            }
            if (goods.getName() != null) {
                //按询价商品名搜索
                query.setParameter("name", goods.getName());
                countQuery.setParameter("name", goods.getName());
            }
            if (goods.getStatus() != null) {
                //按询价商品状态搜索
                query.setParameter("status", goods.getStatus());
                countQuery.setParameter("status", goods.getStatus());
            }
            if (goods.getCategory() != null && goods.getCategory().getId() != null) {
                //按询价商品分类ID搜索
                query.setParameter("categoryId", goods.getCategory().getId());
                countQuery.setParameter("categoryId", goods.getCategory().getId());
            }
            if (goods.getBrand() != null) {
                //按询价商品品牌搜索
                query.setParameter("brand", goods.getBrand());
                countQuery.setParameter("brand", goods.getBrand());
            }
            if (goods.getModel() != null) {
                //按询价商品型号搜索
                query.setParameter("model", goods.getModel());
                countQuery.setParameter("model", goods.getModel());
            }
            if (startDate != null) {
                //按询价商品起始时间搜索
                query.setParameter("startDate", startDate);
                countQuery.setParameter("startDate", startDate);
            }
            if (endDate != null) {
                //按询价商品起始时间搜索
                query.setParameter("endDate", endDate);
                countQuery.setParameter("endDate", endDate);
            }
        }
        try {
            countQuery.setHint(Constants.JPA_CACHEABLE, true);
            int count = new Long((long) countQuery.getSingleResult()).intValue();

            int pages = size == 0 ? 0 : (count / size);
            if (count % size != 0) pages++;
            if (page < 1) page = 1;
            if (page > pages) page = pages;

            int start = (page - 1) * size;
            if (count > 0 && start < count) {
                query.setFirstResult(start);
                query.setMaxResults(size);
            }

            query.setHint(Constants.JPA_CACHEABLE, true);
            List<InquiryGoodsEntity> resultList = query.getResultList();
            return ReturnMessage.success(resultList);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }


    @Override
    public ReturnMessage<List<InquiryGoodsEntity>> listByInquiryId(Integer id) {
        if (id == null) {
            return ReturnMessage.failed();
        }
        String jpql = "select i from InquiryGoodsEntity i";
        String where = " where i.del=false and i.inquiry.del=false";
        String order = " order by i.id desc";
        //按询价ID搜索
        where += " and i.inquiry.id = :inquiryId";
        Query query = em.createQuery(jpql + where + order, InquiryGoodsEntity.class);
        query.setParameter("inquiryId", id);
        query.setHint(Constants.JPA_CACHEABLE, true);
        List<InquiryGoodsEntity> resultList = null;
        try {
            resultList = query.getResultList();
            return ReturnMessage.success(resultList);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<InquiryEntity>> listInquiry(InquiryEntity inquiry, String memberName, Integer page, Integer size, Date start, Date end) {

        /**
         * 这里要写成二次判断，因为用了占位符
         */
        String jpql = "select i from InquiryEntity i";
        String jpqlCount = "select count(i) from InquiryEntity i";
        String where = " where i.del=false and i.del=false";
        String order = " order by i.id desc";
        if (inquiry.getContacts() != null) {
            //按联系人搜索
            where += " and i.inquiry.contacts like :contacts";
        }
        if (inquiry.getStatus() != null) {
            //状态搜索
            where += " and i.inquiry.status = :inquiryStatus";
        }
        if (inquiry.getId() != null) {
            //按询价ID搜索
            where += " and i.inquiry.id = :inquiryId";
        }
        if (memberName != null) {
            //按询价人名字搜索
            where += " and i.member.name = :memberName";
        }


        Query query = em.createQuery(jpql + where + order, InquiryEntity.class);
        Query countQuery = em.createQuery(jpqlCount + where);

        /**
         * 这里要写成二次判断，因为用了占位符
         */
        if (inquiry.getContacts() != null) {
            query.setParameter("contacts", "%" + inquiry.getContacts() + "%");
            countQuery.setParameter("contacts", "%" + inquiry.getContacts() + "%");
        }
        if (inquiry.getStatus() != null) {
            query.setParameter("inquiryStatus", inquiry.getStatus());
            countQuery.setParameter("inquiryStatus", inquiry.getStatus());
        }
        if (inquiry.getId() != null) {
            query.setParameter("inquiryId", inquiry.getId());
            countQuery.setParameter("inquiryId", inquiry.getId());
        }
        if (memberName != null) {
            query.setParameter("memberName", inquiry.getMember().getCorporation().getName());
            countQuery.setParameter("memberName", inquiry.getMember().getCorporation().getName());
        }

        countQuery.setHint(Constants.JPA_CACHEABLE, true);
        int count = new Long((long) countQuery.getSingleResult()).intValue();

        int pages = size == 0 ? 0 : (count / size);
        if (count % size != 0) pages++;
        if (page < 1) page = 1;
        if (page > pages) page = pages;

        int startPage = (page - 1) * size;
        if (count > 0 && startPage < count) {
            query.setFirstResult(startPage);
            query.setMaxResults(size);
        }
        try {
            query.setHint(Constants.JPA_CACHEABLE, true);
            List<InquiryEntity> resultList = query.getResultList();
            return ReturnMessage.success(resultList);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    /**
     * 询价过期 过期的询价商品不可以报价 并更新为已结束（遍历询价商品更新询价单）
     * 定时任务
     */
    @Override
    public ReturnMessage expireInquiryGoods() {
        String jpql = "update InquiryGoodsEntity i set i.status = " + InquiryBiz.GOODS_STATUS_TIME_UP +
                " where i.status <> " + InquiryBiz.GOODS_STATUS_TIME_UP
                + " and i.status <> "
                + InquiryBiz.GOODS_STATUS_FINISHED +
                " and i.status <> "
                + InquiryBiz.INQUIRY_STATUS_CLOSED +
                " and i.expire <= " + new Date();
        Query query = em.createQuery(jpql);
        int i = query.executeUpdate();
        return ReturnMessage.success(i);
    }

    /**
     * 获取询价单（id）
     */
    @Override
    public ReturnMessage<InquiryEntity> getInquiryById(Integer id) {
        try {
            String jpql = "select i from InquiryEntity i where i.id=:inquiryId";
            Query query = em.createQuery(jpql);
            query.setParameter("inquiryId", id);
            InquiryEntity inquiryEntity = (InquiryEntity) query.getSingleResult();
            String inquiryGoodsJpql = "select i from InquiryGoodsEntity i where i.inquiry.id=:inquiryId";
            Query emQuery = em.createQuery(inquiryGoodsJpql);
            emQuery.setParameter("inquiryId", id);
            List<InquiryGoodsEntity> resultList = emQuery.getResultList();
            for (InquiryGoodsEntity goods : resultList) {
                em.detach(goods);//断开连接以防JSON循环解析
                goods.setInquiry(null);
            }
            inquiryEntity.setGoods(resultList);
            return ReturnMessage.success(inquiryEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<InquiryGoodsEntity> saveInquiryGoodsList(InquiryGoodsEntity inquiryGoodsEntity) {

        ReturnMessage returnMessage;
        try {
            em.persist(inquiryGoodsEntity);
            em.flush();
            returnMessage = ReturnMessage.success(inquiryGoodsEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            returnMessage = ReturnMessage.failed("save saveInquiryGoodsList error.");
        }
        return returnMessage;

    }
}
