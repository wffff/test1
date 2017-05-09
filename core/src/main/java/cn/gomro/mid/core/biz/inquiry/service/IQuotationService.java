package cn.gomro.mid.core.biz.inquiry.service;

import cn.gomro.mid.core.biz.inquiry.entity.QuotationEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/1/18.
 */
public interface IQuotationService {
    /**
     * 新增一个新的报价商品
     * @param quotation
     * @return
     */
    ReturnMessage<QuotationEntity> saveQuotaion(QuotationEntity quotation);

    /**
     * 删除一个报价商品
     * @param id
     * @return
     */
    ReturnMessage removeQuotaion(Integer id);

    /**
     * 更新一个报价商品
     * 可根据Id修改 产生的订单号
     *
     * @param quotation
     * @return
     */
    ReturnMessage<QuotationEntity> updateQuotaion(QuotationEntity quotation);

    /**
     * 查询一个报价商品
     * @param id
     * @return
     */
    ReturnMessage<QuotationEntity> getById(Integer id);

    /**
     * 根据询价单ID 分页查询报价商品列表
     * @param id
     * @param page
     * @param size
     * @return
     */
    ReturnMessage<List<QuotationEntity>> listQuotaionsByInquiryId(Integer id, Integer page, Integer size);

    /**
     * 根据询价单商品ID 分页查询报价商品列表
     * @param id
     * @return
     */
    ReturnMessage<List<QuotationEntity>> listQuotaionsByInquiryGoodsId(Integer id);

    /**
     * 根据属性条件获取报价商品列表
     * @param quotation
     * @param page
     * @param size
     * @return
     */
    ReturnMessage<List<QuotationEntity>> listByConditions(QuotationEntity quotation, Integer page, Integer size, Date startDate, Date endDate);
    /**
     * 根据报价单ID和订单ID 修改报价单状态 同时修改询价单商品状态
     * @param quotationId
     * @param orderId
     * @return
     */
    ReturnMessage<QuotationEntity> purchaseOrder(Integer quotationId, Integer orderId);

}
