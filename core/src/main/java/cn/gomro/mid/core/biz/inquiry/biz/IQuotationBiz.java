package cn.gomro.mid.core.biz.inquiry.biz;

import cn.gomro.mid.core.biz.inquiry.entity.QuotationEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/1/18.
 */
public interface IQuotationBiz {

    /**
     * 新建报价 针对询价商品添加报价
     */
    ReturnMessage<QuotationEntity> saveQuotaion(Integer memberId, Integer inquiryGoodsId, String name, String brand,
                                                String model, String description, String place, Integer delivery,
                                                Double price, Double num, String unit, Double packageNum,
                                                String packageUnit, Double amount, Double freight, Long expire);

    /**
     * 删除报价 未成交的报价商品可以删除（更新询价商品的报价数）
     */
    ReturnMessage removeQuotaion(Integer id);

    /**
     * 更新一个报价商品
     */
    ReturnMessage updateQuotation(QuotationEntity quotation);

    /**
     * 获取报价商品（id）
     */
    ReturnMessage<QuotationEntity> getById(Integer id);

    /**
     * 分页查询报价商品列表(询价单ID)
     */
    ReturnMessage<List<QuotationEntity>> listByInquiryId(Integer id, Integer page, Integer size);

    /**
     * 分页查询报价商品列表(询价商品ID)
     */
    ReturnMessage<List<QuotationEntity>> listByInquiryGoodsId(Integer id);

    /**
     * 根据报价商品条件 获取报价商品列表
     */
    ReturnMessage<List<QuotationEntity>> listByConditions(QuotationEntity quotation, Integer page, Integer size, Date startDate, Date endDate);

    /**
     * 根据报价单ID和订单ID 修改报价单状态 同时修改询价单商品状态
     */
    ReturnMessage purchaseOrder(Integer quotationId, Integer orderId);
}
