package cn.gomro.mid.core.biz.inquiry.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.inquiry.entity.QuotationEntity;
import cn.gomro.mid.core.biz.inquiry.service.IQuotationService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.BigDecimalUtils;
import cn.gomro.mid.core.common.utils.JpaUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/1/18.
 */
@Stateless
public class QuotationService extends AbstractSessionService<QuotationEntity> implements IQuotationService {

    final Logger logger = Logger.getLogger(QuotationService.class);

    public QuotationService() {
    }

    @Override
    public ReturnMessage<QuotationEntity> saveQuotaion(QuotationEntity quotation) {
        try {
            super.addItem(quotation);
            return ReturnMessage.success(quotation);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage removeQuotaion(Integer id) {
        try {
            QuotationEntity quotation = this.getItem(id).getData();
            super.delItem(quotation);
            return ReturnMessage.success(quotation);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<QuotationEntity> updateQuotaion(QuotationEntity quotation) {

        QuotationEntity data = this.getItem(quotation.getId()).getData();

        if (data == null) {
            return ReturnMessage.failed();
        }

        if (quotation.getNum() != null && quotation.getNum() > 0) data.setNum(quotation.getNum());//修改数量
        if (StringUtils.isNotBlank(quotation.getModel())) data.setModel(quotation.getModel());//型号
        if (StringUtils.isNotBlank(quotation.getBrand())) data.setBrand(quotation.getBrand());//品牌
        if (StringUtils.isNotBlank(quotation.getName())) data.setName(quotation.getName());//名字
        if (quotation.getPrice() != null) data.setPrice(quotation.getPrice());//价格
        if (StringUtils.isNotBlank(quotation.getUnit())) data.setUnit(quotation.getUnit());//单位
        if (quotation.getDelivery() != null) data.setDelivery(quotation.getDelivery());//货期
        if (StringUtils.isNotBlank(quotation.getPlace())) data.setPlace(quotation.getPlace());//地址
        if (quotation.getFreight() != null) data.setFreight(quotation.getFreight());//运费
        if (quotation.getExpire() != null) data.setExpire(quotation.getExpire());//有效期
        data.setAmount(BigDecimalUtils.mul(quotation.getNum(), quotation.getPrice()));//总计
        data.setLast(new Date());
        em.merge(data);
        em.flush();
        return ReturnMessage.success(data);
    }

    @Override
    public ReturnMessage<QuotationEntity> getById(Integer id) {
        String jpql = "from QuotationEntity q where q.id=" + id;
        Query query = em.createQuery(jpql, QuotationEntity.class);
        try {
            QuotationEntity quotation = (QuotationEntity) query.getSingleResult();
            return ReturnMessage.success(quotation);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<QuotationEntity>> listQuotaionsByInquiryId(Integer id, Integer page, Integer size) {
        try {
            String jpql = "from QuotationEntity q where q.del=false and q.inquiryGoods.inquiry.id=" + id;
            String count = "select count(q.id) from QuotationEntity q where q.del=false and q.inquiryGoods.inquiry.id=" + id;
            return JpaUtils.queryPaged(em, count, jpql, page, size);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<QuotationEntity>> listQuotaionsByInquiryGoodsId(Integer id) {
        try {
            String jpql = "from QuotationEntity q where q.del=false and q.inquiryGoods.id=" + id;
            Query query = em.createQuery(jpql);
            List<QuotationEntity> resultList = query.getResultList();
            return ReturnMessage.success(resultList);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<QuotationEntity>> listByConditions(QuotationEntity quotation, Integer page, Integer size, Date startDate, Date endDate) {
        try {
            String count = "select count(q.id) ";
            String jpql = "from QuotationEntity q";
            String where = " where q.del=false";
            if (quotation.getId() != null && quotation.getId() > 0) {
                //更具询价单Id查询
                where += " and q.id=" + quotation.getMember().getId();
            }
            if (quotation.getMember() != null && quotation.getMember().getId() != null) {
                //根据报价的会员Id检索
                where += " and q.vendor.id=" + quotation.getMember().getId();
            }
            if (quotation.getName() != null) {
                //根据报价商品名检索
                where += " and LOWER(q.name) like LOWER('%" + quotation.getName() + "%')";
            }
            if (quotation.getBrand() != null) {
                //根据品牌检索
                where += " and LOWER(q.brand) like LOWER('%" + quotation.getBrand() + "%')";
            }
            if (quotation.getModel() != null) {
                //根据型号检索
                where += " and LOWER(q.model) like LOWER('%" + quotation.getModel() + "%')";
            }
            if (quotation.getPlace() != null) {
                //根据地址检索
                where += " and LOWER(q.location) like LOWER('%" + quotation.getPlace() + "%')";
            }
            if (quotation.getInquiryGoods() != null && quotation.getInquiryGoods().getInquiry() != null
                    && quotation.getInquiryGoods().getInquiry().getMember() != null
                    && quotation.getInquiryGoods().getInquiry().getMember().getId() != null) {
                //根据询价的会员id检索
                where += " and q.inquiryGoods.inquiry.member.id" + quotation.getInquiryGoods().getInquiry().getMember().getId();
            }

            if (startDate != null) {
                //按询价商品起始时间搜索
                where += " and q.time >= :startDate";
            }
            if (endDate != null) {
                //按询价商品起始时间搜索
                where += " and q.time <= :endDate";
            }

            return JpaUtils.queryPaged(em, count + jpql + where, jpql + where, page, size);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<QuotationEntity> purchaseOrder(Integer quotationId, Integer orderId) {
        if (quotationId != null && orderId != null) {
            try {
                String jpqlQuotation = "update QuotationEntity set purchaseOrderId =:orderId where id=:quotationId";
                String jpqlInquiry = "update InquiryGoodsEntity set purchaseOrderId =:orderId where id=(select inquiryGoods.id from QuotationEntity where id=:quotationId)";
                Query queryQuotation = em.createQuery(jpqlQuotation);
                Query queryInquiry = em.createQuery(jpqlInquiry);
                queryQuotation.setParameter("orderId",orderId);
                queryQuotation.setParameter("quotationId",quotationId);
                queryInquiry.setParameter("orderId",orderId);
                queryInquiry.setParameter("quotationId",quotationId);
                int i1 = queryQuotation.executeUpdate();
                int i2 = queryInquiry.executeUpdate();
                if (i1 > 0 && i2 > 0) {
                    return ReturnMessage.success();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return ReturnMessage.failed();
    }
}
