package cn.gomro.mid.core.biz.inquiry.biz;

import cn.gomro.mid.core.biz.inquiry.entity.InquiryEntity;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryGoodsEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/1/17.
 */
public interface IInquiryBiz {

    /**
     * 新增询价单 同时添加询价商品列表
     */
    ReturnMessage<InquiryEntity> saveInquiry(Integer memberId, InquiryEntity inquiry);

    /**
     * [批量] 提交询价单 状态为待审核
     */
    ReturnMessage submitInquiryGoods(List<Integer> id);

    /**
     * 审核询价商品 批量审核询价商品 状态为可报价或驳回
     */
    ReturnMessage auditing(List<Integer> id, Boolean reject, String reason, Integer categoryId);

    /**
     * [批量] 关闭询价商品 同时更新询价单状态
     */
    ReturnMessage closeInquiryGoods(List<Integer> id);
    /**
     * 删除询价商品 未报价时
     */
    ReturnMessage removeInquiryGoods(List<Integer> id);
    /**
     * 修改询价商品 未报价时
     */
    ReturnMessage<InquiryGoodsEntity> updateInquiryGoods(Integer id, String name, String brand, String model, String description,
                                                         String address, Integer delivery, Double num, String unit,
                                                         Double packageNum, String packageUnit, Long expire,
                                                         String attachment, String url, String memo);

    /**
     * 修改询价单 所有询价商品未有报价时可修改
     */
    ReturnMessage<InquiryEntity> updateInquiry(Integer id,String contacts,String qq,String inquiryDescription,
                                               Long inquiryExpire);

    /**
     * 修改询价商品状态 ...
     */
    InquiryGoodsEntity updateInquiryGoodsStatus(InquiryGoodsEntity goodsEntity,int status);

    /**
     * 询价过期 过期的询价商品不可以报价 并更新为已结束（遍历询价商品更新询价单）
     * 定时任务
     */
    ReturnMessage expireInquiryGoods();

    /**
     * 获取询价单（id）
     */
    ReturnMessage<List<InquiryEntity>> getInquiryById(List<Integer> id);

    /**
     * 根据询价单属性条件 分页查询 询价单列表
     */
    ReturnMessage<List<InquiryEntity>> listInquiry(InquiryEntity inquiry,String memberName, Integer page, Integer size, Date start, Date end);

    /**
     * 获取询价商品（id）
     */
    ReturnMessage<List<InquiryGoodsEntity>> getInquiryGoodsById(List<Integer> id);

    /**
     * 根据询价商品属性及询价单属性条件 分页查询 询价商品列表
     */
    ReturnMessage<List<InquiryGoodsEntity>> listInquiryGoods(InquiryGoodsEntity goods, Integer page, Integer size, Date start, Date end);

    /**
     * 更新询价商品中的报价数量
     */
    ReturnMessage<InquiryGoodsEntity> updateInquiryGoodsQuotationCount(boolean increase, Integer id);

    ReturnMessage<InquiryGoodsEntity> saveInquiryGoodsList(InquiryGoodsEntity inquiryGoodsEntity);
}
