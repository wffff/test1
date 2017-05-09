package cn.gomro.mid.core.biz.inquiry.service;

import cn.gomro.mid.core.biz.inquiry.entity.InquiryEntity;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryGoodsEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/1/17.
 */
public interface IInquiryService {
    /**
     * 添加一个包含多条询价商品的询价单
     * @param inquiry
     * @return inquiry
     */
    ReturnMessage<InquiryEntity> saveInquiry(InquiryEntity inquiry);

    /**
     * 删除一条询价商品（正常仅能关闭询价商品）
     * @param id
     * @return
     */
    ReturnMessage removeInquiryGoods(Integer id);

    /**
     * 修改询价单
     * 修改时，id为必须项，其它项会根据是否为null判断更新
     * @return
     */
    ReturnMessage<InquiryEntity> updateInquiry(Integer id, String contacts, String qq, String description, Date expire);

    /**
     * 修改询价商品（可级联修改询价单）
     * 修改时，id为必须项，其它项会根据是否为null判断更新
     * @param goods
     * @return
     */
    ReturnMessage<InquiryGoodsEntity> updateInquiryGoods(InquiryGoodsEntity goods);

    /**
     * 获取一条询价商品
     * @param id
     * @return
     */
    ReturnMessage<InquiryGoodsEntity> getById(Integer id);

    /**
     * 根据属性条件获取询价商品列表
     * @param goods
     * @return
     */
    ReturnMessage<List<InquiryGoodsEntity>> listInquiryGoods(InquiryGoodsEntity goods, Integer page, Integer size, Date start, Date end);

    /**
     * 根据询价单Id 获取询价商品列表
     * @param id
     * @return
     */
    ReturnMessage<List<InquiryGoodsEntity>> listByInquiryId(Integer id);

    /**
     * 根据属性条件获取询价单列表
     * @param inquiry
     * @param page
     * @param size
     * @param start
     * @param end
     * @return
     */
    ReturnMessage<List<InquiryEntity>> listInquiry(InquiryEntity inquiry,String memberName, Integer page, Integer size, Date start, Date end);

    /**
     * 询价过期 过期的询价商品不可以报价 并更新为已结束（遍历询价商品更新询价单）
     * 定时任务
     */
    ReturnMessage expireInquiryGoods();

    /**
     * 获取询价单（id）
     */
    ReturnMessage<InquiryEntity> getInquiryById(Integer id);

    ReturnMessage<InquiryGoodsEntity> saveInquiryGoodsList(InquiryGoodsEntity inquiryGoodsEntity);

}
