package cn.gomro.mid.core.biz.inquiry.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.goods.entity.MemberEntity;
import cn.gomro.mid.core.biz.goods.service.IMemberService;
import cn.gomro.mid.core.biz.inquiry.biz.IInquiryBizLocal;
import cn.gomro.mid.core.biz.inquiry.biz.IQuotationBizLocal;
import cn.gomro.mid.core.biz.inquiry.biz.IQuotationBizRemote;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryGoodsEntity;
import cn.gomro.mid.core.biz.inquiry.entity.QuotationEntity;
import cn.gomro.mid.core.biz.inquiry.service.IQuotationService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/1/18.
 */
@Stateless
public class QuotationBiz extends AbstractSessionBiz implements IQuotationBizLocal, IQuotationBizRemote {
    final Logger logger = Logger.getLogger(QuotationBiz.class);

    @EJB
    IQuotationService quotationService;
    @EJB
    IInquiryBizLocal inquiryBiz;
    @EJB
    IMemberService memberService;

    final String QUOTATION_MSG_NOT_COMPLETE = "报价单信息不完整！";
    final String QUOTATION_MSG_USER_ERROR = "用户信息不正确！";
    final String QUOTATION_MSG_INQUIRY_STATUS_ERROR = "询价状态不正确，不可报价！";
    final String QUOTATION_MSG_PURCHASED = "已生产订单的报价不可修改或删除！";

    public QuotationBiz() {
    }

    /**
     * 新建报价 针对询价商品添加报价
     */
    @Override
    public ReturnMessage<QuotationEntity> saveQuotaion(Integer memberId, Integer inquiryGoodsId, String name, String brand,
                                                       String model, String description, String place, Integer delivery,
                                                       Double price, Double num, String unit, Double packageNum,
                                                       String packageUnit, Double amount, Double freight, Long expire) {
        boolean quotationComplete = memberId != null
                && inquiryGoodsId != null
                && StringUtils.isNotBlank(name)
                && StringUtils.isNotBlank(brand)
                && StringUtils.isNotBlank(model)
                && StringUtils.isNotBlank(description)
                && StringUtils.isNotBlank(place)
                && delivery != null
                && price != null
                && num != null
                && StringUtils.isNotBlank(unit)
                && packageNum != null
                && StringUtils.isNotBlank(packageUnit)
                && amount != null
                && freight != null
                && expire != null;

        ReturnMessage result = null;

        if (!quotationComplete) {
            result = ReturnMessage.failed(QUOTATION_MSG_NOT_COMPLETE);
        }

        MemberEntity memberEntity = memberService.getItem(memberId).getData();
        if (memberEntity == null) {
            result = ReturnMessage.failed(QUOTATION_MSG_USER_ERROR);
        }
        List<Integer> list = new ArrayList<>();
        list.add(inquiryGoodsId);
        List<InquiryGoodsEntity> data = inquiryBiz.getInquiryGoodsById(list).getData();
        if (data == null || data.size() != 1) {
            result = ReturnMessage.failed(QUOTATION_MSG_INQUIRY_STATUS_ERROR);
        }
        for (InquiryGoodsEntity entity : data) {
            if (data == null || entity == null || !(entity.getStatus() == InquiryBiz.GOODS_STATUS_NO_QUOTATION || entity.getStatus() == InquiryBiz.GOODS_STATUS_QUOTING)) {
                result = ReturnMessage.failed(QUOTATION_MSG_INQUIRY_STATUS_ERROR);
            }
        }
        QuotationEntity quotation = new QuotationEntity(memberEntity, data.get(0), null, name,
                brand, model, description, place, delivery, price, num, unit, packageNum, packageUnit, amount, freight,
                new Date(expire));

        if (result != null) {
            return result;
        } else {
            result = quotationService.saveQuotaion(quotation);
        }
        if (result.getCode() >= 0) {
            try {
                //更新询价商品的已报价数量
                inquiryBiz.updateInquiryGoodsQuotationCount(true, quotation.getInquiryGoods().getId());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return result;
    }

    /**
     * 删除报价 未成交的报价商品可以删除（更新询价商品的报价数）
     */
    @Override
    public ReturnMessage removeQuotaion(Integer id) {
        if (id != null) {
            QuotationEntity data = quotationService.getById(id).getData();
            if (data.getPurchaseOrderId() != null) {
                return ReturnMessage.failed(QUOTATION_MSG_PURCHASED);
            }
            ReturnMessage returnMessage = quotationService.removeQuotaion(id);
            if (returnMessage.getCode() >= 0) {
                try {
                    //更新询价商品的已报价数量
                    inquiryBiz.updateInquiryGoodsQuotationCount(false, id);
                    return returnMessage;
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return ReturnMessage.failed();
    }

    /**
     * 更新一个报价商品
     */
    @Override
    public ReturnMessage updateQuotation(QuotationEntity quotation) {
        if (quotation == null || quotation.getId() == null) {
            return ReturnMessage.failed(QUOTATION_MSG_NOT_COMPLETE);
        }
        QuotationEntity data = quotationService.getById(quotation.getId()).getData();
        if (data.getPurchaseOrderId() != null) {
            return ReturnMessage.failed(QUOTATION_MSG_PURCHASED);
        }
        return quotationService.updateQuotaion(quotation);
    }

    /**
     * 获取报价商品（id）
     */
    @Override
    public ReturnMessage<QuotationEntity> getById(Integer id) {
        return quotationService.getById(id);
    }

    /**
     * 分页查询报价商品列表(询价单ID)
     */
    @Override
    public ReturnMessage<List<QuotationEntity>> listByInquiryId(Integer id, Integer page, Integer size) {
        return quotationService.listQuotaionsByInquiryId(id, page, size);
    }

    /**
     * 分页查询报价商品列表(询价商品ID)
     */
    @Override
    public ReturnMessage<List<QuotationEntity>> listByInquiryGoodsId(Integer id) {
        return quotationService.listQuotaionsByInquiryGoodsId(id);
    }

    /**
     * 根据报价商品条件 获取报价商品列表
     */
    @Override
    public ReturnMessage<List<QuotationEntity>> listByConditions(QuotationEntity quotation, Integer page, Integer size, Date startDate, Date endDate) {
        return quotationService.listByConditions(quotation, page, size, startDate, endDate);
    }

    /**
     * 根据报价单ID和订单ID 修改报价单状态 同时修改询价单商品状态
     */
    @Override
    public ReturnMessage<QuotationEntity> purchaseOrder(Integer quotationId, Integer orderId) {


        return quotationService.purchaseOrder(quotationId, orderId);
    }

}
