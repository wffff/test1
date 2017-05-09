package cn.gomro.mid.core.biz.inquiry.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.goods.service.ICorporationService;
import cn.gomro.mid.core.biz.goods.service.IMemberService;
import cn.gomro.mid.core.biz.inquiry.biz.IInquiryBizLocal;
import cn.gomro.mid.core.biz.inquiry.biz.IInquiryBizRemote;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryCategoryEntity;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryEntity;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryGoodsEntity;
import cn.gomro.mid.core.biz.inquiry.entity.TempUserEntity;
import cn.gomro.mid.core.biz.inquiry.service.IInquiryCategoryService;
import cn.gomro.mid.core.biz.inquiry.service.IInquiryService;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import org.apache.commons.lang.StringUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/1/17.
 */
@Stateless
public class InquiryBiz extends AbstractSessionBiz implements IInquiryBizLocal, IInquiryBizRemote {

    @EJB
    IInquiryService inquiryService;
    @EJB
    IInquiryCategoryService inquiryCategoryService;
    @EJB
    IMemberService memberService;
    @EJB
    ICorporationService corporationService;


    /**
     * 询价单状态
     * 0、新建 1、审核中 2、全部驳回 3、未报价 4、部分报价中 5、报价中 6、全部结束 7、全部完成 8、全部关闭
     */
    final int INQUIRY_STATUS_NEW = 0;
    final int INQUIRY_STATUS_SUBMITED = 1;
    final int INQUIRY_STATUS_REJECTED = 2;
    final int INQUIRY_STATUS_NO_QUOTATION = 3;
    final int INQUIRY_STATUS_PART_QUOTATION = 4;
    final int INQUIRY_STATUS_QUOTING = 5;
    final public static int INQUIRY_STATUS_TIME_UP = 6;
    final int INQUIRY_STATUS_FINISHED = 7;
    final public static int INQUIRY_STATUS_CLOSED = 8;
    /**
     * 询价商品状态
     * 0、新建 1、待分配 2、驳回 3、未报价 4、报价中 5、已完成 6、已关闭 7、已结束
     */
    final int GOODS_STATUS_NEW = 0;
    final int GOODS_STATUS_SUBMITED = 1;
    final int GOODS_STATUS_REJECTED = 2;
    final public static int GOODS_STATUS_NO_QUOTATION = 3;
    final public static int GOODS_STATUS_QUOTING = 4;
    final public static int GOODS_STATUS_FINISHED = 5;
    final int GOODS_STATUS_CLOSED = 6;
    final public static int GOODS_STATUS_TIME_UP = 7;

    final String AUDITING_MSG_CLOSE = "询价商品已关闭！";
    final String AUDITING_MSG_QUOTING = "已开放报价！";
    final String INQUIRY_MSG_NOT_COMPLETE = "询价单信息不完整！";
    final String INQUIRY_GOODS_MSG_IMPERFECT = "询价商品信息不完整！";
    final String INQUIRY_GOODS_MSG_ALREADY = "商品已报价，不可修改！";
    final String INQUIRY_GOODS_MSG_ORDERED = "询价商品已下单不可关闭！";
    final String INQUIRY_MSG_PARAMS_ERROR = "参数不正确！";

    public InquiryBiz() {
    }

    /**
     * 新增询价单 同时添加询价商品列表
     *
     * @param memberId
     * @param inquiry
     * @return
     */
    @Override
    public ReturnMessage<InquiryEntity> saveInquiry(Integer memberId, InquiryEntity inquiry) {
        TempUserEntity tempUserEntity = memberService.getTempMember(memberId).getData();
        boolean inquiryComplete =
                inquiry != null
                        && tempUserEntity != null
                        && StringUtils.isNotBlank(inquiry.getContacts())
                        && inquiry.getExpire() != null
                        && inquiry.getExpire().getTime() > System.currentTimeMillis();
        if (!inquiryComplete) {
            return ReturnMessage.failed(INQUIRY_MSG_NOT_COMPLETE);
        }
        for (InquiryGoodsEntity goods : inquiry.getGoods()) {
            goods.setStatus(GOODS_STATUS_NEW);
            //判断必填数据完整性
            boolean complete =
                    StringUtils.isNotBlank(goods.getUnit())
                            && StringUtils.isNotBlank(goods.getPackageUnit())
                            && StringUtils.isNotBlank(goods.getModel())
                            && StringUtils.isNotBlank(goods.getBrand())
                            && StringUtils.isNotBlank(goods.getName())
                            && StringUtils.isNotBlank(goods.getAddress())
                            && goods.getDelivery() != null
                            && goods.getDelivery() > 0
                            && goods.getExpire() != null
                            && goods.getExpire().getTime() > System.currentTimeMillis()
                            && goods.getNum() != null
                            && goods.getNum() > 0
                            && goods.getPackageNum() != null
                            && goods.getPackageNum() > 0;
            if (!complete) {
                return ReturnMessage.failed(INQUIRY_GOODS_MSG_IMPERFECT);
            }
        }
        inquiry.setStatus(INQUIRY_STATUS_NEW);//询价单状态
        inquiry.setMember(tempUserEntity);
        return inquiryService.saveInquiry(inquiry);
    }

    /**
     * [批量] 提交询价单 状态为待审核
     *
     * @param id
     */
    @Override
    public ReturnMessage submitInquiryGoods(List<Integer> id) {
        if (id == null && id.size() == 0) {
            return ReturnMessage.failed(INQUIRY_MSG_PARAMS_ERROR);
        }

        for (Integer goodsId : id) {
            if (goodsId == null || goodsId <= 0) {
                return ReturnMessage.failed(INQUIRY_MSG_PARAMS_ERROR);
            }
        }

        for (Integer goodsId : id) {
            // 获取到询价商品
            InquiryGoodsEntity goodsEntity = inquiryService.getById(goodsId).getData();
            if (goodsEntity.getStatus() != INQUIRY_STATUS_NEW) {
                return ReturnMessage.failed("ID:" + goodsId + INQUIRY_MSG_PARAMS_ERROR);
            }
            goodsEntity = this.updateInquiryGoodsStatus(goodsEntity, GOODS_STATUS_SUBMITED);   //改变询价商品状态为 提交
            inquiryService.updateInquiryGoods(goodsEntity);
        }
        return ReturnMessage.success();
    }

    /**
     * 审核询价商品 批量审核询价商品 状态为可报价或驳回
     *
     * @param id
     * @param reject
     * @param reason
     * @param categoryId
     */
    @Override
    public ReturnMessage auditing(List<Integer> id, Boolean reject, String reason, Integer categoryId) {

        boolean NPE = id != null && id.size() > 0 && reject != null;
        if (!NPE) {
            return ReturnMessage.failed(ReturnCode.DATA_FORMAT_ERROR);
        }
        for (Integer index : id) {
            //判断当前询价状态
            InquiryGoodsEntity goods = inquiryService.getById(index).getData();
            if (goods.getStatus() != GOODS_STATUS_SUBMITED) {
                return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);
            }

            if (reject && reason != null) {
                //驳回询价
                goods = this.updateInquiryGoodsStatus(goods, GOODS_STATUS_REJECTED);
                goods.setReject(reason);
                inquiryService.updateInquiryGoods(goods);
            }
            InquiryCategoryEntity categoryEntity = null;
            if (categoryId != null) {
                categoryEntity = inquiryCategoryService.getById(categoryId).getData();
            }
            if (!reject && categoryEntity != null) {
                //正常询价
                if (goods.getStatus() != GOODS_STATUS_SUBMITED) {
                    return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);
                }
                goods.setCategory(categoryEntity);
                goods = this.updateInquiryGoodsStatus(goods, GOODS_STATUS_NO_QUOTATION);
                inquiryService.updateInquiryGoods(goods);
            }
        }
        if (reject) {
            return ReturnMessage.success(AUDITING_MSG_CLOSE);
        } else {
            return ReturnMessage.success(AUDITING_MSG_QUOTING);
        }
    }

    /**
     * [批量] 关闭询价商品 同时更新询价单状态
     *
     * @param id
     */
    @Override
    public ReturnMessage closeInquiryGoods(List<Integer> id) {
        if (id == null && id.size() == 0) {
            return ReturnMessage.failed(INQUIRY_MSG_PARAMS_ERROR);
        }

        for (Integer goodsId : id) {
            if (goodsId == null || goodsId <= 0) {
                return ReturnMessage.failed(INQUIRY_MSG_PARAMS_ERROR);
            }
        }

        for (Integer goodsId : id) {
            // 获取到询价商品
            InquiryGoodsEntity goodsEntity = inquiryService.getById(goodsId).getData();
            boolean ordered = goodsEntity != null && goodsEntity.getPurchaseOrderId() != null;
            if (ordered) {
                return ReturnMessage.failed("ID:" + goodsId + INQUIRY_GOODS_MSG_ORDERED);
            }
            goodsEntity = this.updateInquiryGoodsStatus(goodsEntity, GOODS_STATUS_CLOSED);   //改变询价商品状态为 关闭
            inquiryService.updateInquiryGoods(goodsEntity);
        }
        return ReturnMessage.success();
    }

    /**
     * 删除询价商品 未报价时
     */
    @Override
    public ReturnMessage removeInquiryGoods(List<Integer> id) {
        if (id == null || id.size() == 0) {
            return ReturnMessage.failed(INQUIRY_MSG_PARAMS_ERROR);
        }
        List<InquiryGoodsEntity> data = this.getInquiryGoodsById(id).getData();
        for (InquiryGoodsEntity entity : data) {
            //判断询价商品 是否已报价
            if (entity.getStatus() != GOODS_STATUS_NO_QUOTATION) {
                return ReturnMessage.failed(INQUIRY_GOODS_MSG_ALREADY);
            }
        }
        for(Integer index:id) {
            ReturnMessage returnMessage = inquiryService.removeInquiryGoods(index);
            if (returnMessage.getCode() <= 0) {
                return ReturnMessage.failed();
            }
        }
        return ReturnMessage.success();
    }

    /**
     * 修改询价商品 未报价时
     */
    @Override
    public ReturnMessage<InquiryGoodsEntity> updateInquiryGoods(Integer id, String name, String brand, String model, String description,
                                                                String address, Integer delivery, Double num, String unit,
                                                                Double packageNum, String packageUnit, Long expire,
                                                                String attachment, String url, String memo) {
        //保证数据完整性
        boolean complete =
                id != null
                        && id > 0
                        && StringUtils.isNotBlank(name)
                        && StringUtils.isNotBlank(brand)
                        && StringUtils.isNotBlank(model)
                        && StringUtils.isNotBlank(address)
                        && delivery != null
                        && delivery > 0
                        && num != null
                        && num > 0
                        && packageNum != null
                        && packageNum > 0;
        if (!complete) {
            return ReturnMessage.failed(INQUIRY_GOODS_MSG_IMPERFECT);
        }
        InquiryGoodsEntity goodsEntity = inquiryService.getById(id).getData();
        //判断询价商品 是否已报价
        if (goodsEntity.getStatus() != GOODS_STATUS_NO_QUOTATION) {
            return ReturnMessage.failed(INQUIRY_GOODS_MSG_ALREADY);
        }
        InquiryGoodsEntity newGoods = new InquiryGoodsEntity(name, brand, model, description,
                address, delivery, num, unit, packageNum, packageUnit, new Date(expire), attachment, url, memo);
        newGoods.setId(id);
        ReturnMessage<InquiryGoodsEntity> goodsEntityReturnMessage = inquiryService.updateInquiryGoods(newGoods);
        return goodsEntityReturnMessage;
    }

    /**
     * 修改询价单 所有询价商品未有报价时可修改
     */
    @Override
    public ReturnMessage<InquiryEntity> updateInquiry(Integer id, String contacts, String qq, String inquiryDescription,
                                                      Long inquiryExpire) {
        //保证数据完整性
        boolean complete =
                id != null && id > 0
                        && qq != null
                        && StringUtils.isNotBlank(contacts)
                        && inquiryExpire > System.currentTimeMillis();
        if (!complete) {
            return ReturnMessage.failed(INQUIRY_GOODS_MSG_IMPERFECT);
        }
        //根据询价单获取 询价单所有询价商品
        List<InquiryGoodsEntity> goodsEntityList = inquiryService.listByInquiryId(id).getData();
        boolean isAllClosed = true;
        for (InquiryGoodsEntity entity : goodsEntityList) {
            //判断询价单里面 询价商品是否有已报价
            if (entity.getStatus() != INQUIRY_STATUS_NO_QUOTATION) {
                isAllClosed = false;
            }
        }
        //询价单里面没有 报价的 询价商品没有报价（可以修改询价单）
        if (!isAllClosed) {
            return ReturnMessage.failed(INQUIRY_GOODS_MSG_ALREADY);
        }
        //修改询价单的方法
        inquiryService.updateInquiry(id, contacts, qq, inquiryDescription, new Date(inquiryExpire));
        return ReturnMessage.success();
    }

    /**
     * 修改询价商品状态 ...
     */
    @Override
    public InquiryGoodsEntity updateInquiryGoodsStatus(InquiryGoodsEntity goodsEntity, int status) {

        InquiryEntity inquiry = inquiryService.getInquiryById(goodsEntity.getInquiry().getId()).getData();
        List<InquiryGoodsEntity> goodsList = inquiry.getGoods();
        //是否全部一至，更新询价单
        boolean b = true;

        switch (status) {
            case GOODS_STATUS_NEW:
                goodsEntity.setStatus(GOODS_STATUS_NEW);
                for (InquiryGoodsEntity goods : goodsList) {
                    Integer status1 = goods.getStatus();
                    if (status1 != GOODS_STATUS_NEW) {
                        b = false;
                    }
                }
                if (!b) {
                    inquiry.setStatus(INQUIRY_STATUS_NEW);
                }
                break;
            case GOODS_STATUS_SUBMITED:
                goodsEntity.setStatus(GOODS_STATUS_SUBMITED);
                for (InquiryGoodsEntity goods : goodsList) {
                    Integer status1 = goods.getStatus();
                    if (status1 != GOODS_STATUS_SUBMITED) {
                        b = false;
                    }
                }
                if (!b) {
                    inquiry.setStatus(INQUIRY_STATUS_SUBMITED);
                }
                break;
            case GOODS_STATUS_REJECTED:
                goodsEntity.setStatus(GOODS_STATUS_REJECTED);
                for (InquiryGoodsEntity goods : goodsList) {
                    Integer status1 = goods.getStatus();
                    if (status1 != GOODS_STATUS_REJECTED) {
                        b = false;
                    }
                }
                if (!b) {
                    inquiry.setStatus(INQUIRY_STATUS_REJECTED);
                }
                break;
            case GOODS_STATUS_NO_QUOTATION:
                goodsEntity.setStatus(GOODS_STATUS_NO_QUOTATION);
                for (InquiryGoodsEntity goods : goodsList) {
                    Integer status1 = goods.getStatus();
                    if (status1 != GOODS_STATUS_NO_QUOTATION) {
                        b = false;
                    }
                }
                if (!b) {
                    inquiry.setStatus(INQUIRY_STATUS_NO_QUOTATION);
                }
                break;
            case GOODS_STATUS_QUOTING:
                goodsEntity.setStatus(GOODS_STATUS_QUOTING);
                for (InquiryGoodsEntity goods : goodsList) {
                    Integer status1 = goods.getStatus();
                    if (status1 != GOODS_STATUS_QUOTING) {
                        b = false;
                    }
                }
                if (!b) {
                    inquiry.setStatus(INQUIRY_STATUS_QUOTING);
                } else {
                    inquiry.setStatus(INQUIRY_STATUS_PART_QUOTATION);
                }
                break;
            case GOODS_STATUS_CLOSED:
                goodsEntity.setStatus(GOODS_STATUS_CLOSED);
                for (InquiryGoodsEntity goods : goodsList) {
                    Integer status1 = goods.getStatus();
                    if (status1 != GOODS_STATUS_CLOSED) {
                        b = false;
                    }
                }
                if (!b) {
                    inquiry.setStatus(INQUIRY_STATUS_CLOSED);
                }
                break;
            case GOODS_STATUS_FINISHED:
                goodsEntity.setStatus(GOODS_STATUS_FINISHED);
                for (InquiryGoodsEntity goods : goodsList) {
                    Integer status1 = goods.getStatus();
                    if (status1 != GOODS_STATUS_FINISHED) {
                        b = false;
                    }
                }
                if (!b) {
                    inquiry.setStatus(INQUIRY_STATUS_FINISHED);
                }
                break;
            default:
                return null;
        }
        return goodsEntity;
    }

    /**
     * 询价过期 过期的询价商品不可以报价 并更新为已结束（遍历询价商品更新询价单）
     * 定时任务
     */
    @Override
//    @Schedule(hour = "23")
    public ReturnMessage expireInquiryGoods() {
        return inquiryService.expireInquiryGoods();
    }

    /**
     * 获取询价单（id）
     */
    @Override
    public ReturnMessage<List<InquiryEntity>> getInquiryById(List<Integer> id) {
        if (id == null || id.size() == 0) {
            return ReturnMessage.failed(INQUIRY_MSG_PARAMS_ERROR);
        }
        List<InquiryEntity> list = new ArrayList<>();
        for (Integer index : id) {
            InquiryEntity data = inquiryService.getInquiryById(index).getData();
            list.add(data);
        }
        return ReturnMessage.success(list);
    }

    /**
     * 根据询价单属性条件 分页查询 询价单列表
     */
    @Override
    public ReturnMessage<List<InquiryEntity>> listInquiry(InquiryEntity inquiry, String memberName, Integer page, Integer size, Date start, Date end) {
        return inquiryService.listInquiry(inquiry, memberName, page, size, start, end);
    }

    /**
     * 获取询价商品（id）
     */
    @Override
    public ReturnMessage<List<InquiryGoodsEntity>> getInquiryGoodsById(List<Integer> id) {
        if (id == null || id.size() == 0) {
            return ReturnMessage.failed(INQUIRY_MSG_PARAMS_ERROR);
        }
        List<InquiryGoodsEntity> list = new ArrayList<>();
        for (Integer index : id) {
            InquiryGoodsEntity data = inquiryService.getById(index).getData();
            if(data!=null) {
                list.add(data);
            }
        }
        return ReturnMessage.success(list);
    }

    /**
     * 根据询价商品属性及询价单属性条件 分页查询 询价商品列表
     */
    @Override
    public ReturnMessage<List<InquiryGoodsEntity>> listInquiryGoods(InquiryGoodsEntity goods, Integer page, Integer size, Date start, Date end) {
        return inquiryService.listInquiryGoods(goods, page, size, start, end);
    }

    @Override
    public ReturnMessage<InquiryGoodsEntity> updateInquiryGoodsQuotationCount(boolean increase, Integer id) {
        InquiryGoodsEntity iGoods = inquiryService.getById(id).getData();
        iGoods.setQuotationCount(iGoods.getQuotationCount() + (increase ? 1 : -1));//累加报价计数 或 删除报价计数
        if (iGoods.getQuotationCount() > 0) {
            iGoods = this.updateInquiryGoodsStatus(iGoods, GOODS_STATUS_QUOTING);//修改状态为报价中
        } else {
            iGoods.setStatus(GOODS_STATUS_NO_QUOTATION);//修改状态为未报价
        }
        return inquiryService.updateInquiryGoods(iGoods);
    }

    @Override
    public ReturnMessage<InquiryGoodsEntity> saveInquiryGoodsList(InquiryGoodsEntity inquiryGoodsEntity) {
        return inquiryService.saveInquiryGoodsList(inquiryGoodsEntity);
    }
}
