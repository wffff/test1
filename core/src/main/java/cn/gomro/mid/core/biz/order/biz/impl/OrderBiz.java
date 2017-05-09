package cn.gomro.mid.core.biz.order.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.goods.entity.MemberEntity;
import cn.gomro.mid.core.biz.goods.service.IMemberService;
import cn.gomro.mid.core.biz.inquiry.biz.IInquiryBizLocal;
import cn.gomro.mid.core.biz.inquiry.biz.IQuotationBizLocal;
import cn.gomro.mid.core.biz.inquiry.biz.impl.InquiryBiz;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryEntity;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryGoodsEntity;
import cn.gomro.mid.core.biz.inquiry.entity.QuotationEntity;
import cn.gomro.mid.core.biz.inquiry.entity.TempUserEntity;
import cn.gomro.mid.core.biz.order.biz.IOrderBizLocal;
import cn.gomro.mid.core.biz.order.biz.IOrderBizRemote;
import cn.gomro.mid.core.biz.order.entity.*;
import cn.gomro.mid.core.biz.order.entity.vo.InquiryVO;
import cn.gomro.mid.core.biz.order.entity.vo.OrderEntity;
import cn.gomro.mid.core.biz.order.entity.vo.VendorOrderGoodsVO;
import cn.gomro.mid.core.biz.order.entity.vo.VendorVO;
import cn.gomro.mid.core.biz.order.service.*;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */
@Stateless
public class OrderBiz extends AbstractSessionBiz implements IOrderBizLocal, IOrderBizRemote {

    @EJB
    IMemberOrderService memberOrderService;
    @EJB
    IVendorOrderService vendorOrderService;
    @EJB
    IVendorOrderProductService vendorOrderProductService;
    @EJB
    IMemberOrderProductService memberOrderProductService;
    @EJB
    IMemberInvoiceService memberInvoiceService;
    @EJB
    IInquiryBizLocal inquiryBizLocal;
    @EJB
    IQuotationBizLocal iQuotationBiz;
    @EJB
    ICompanyOrgService companyOrgService;
    @EJB
    ICreditExpenseService creditExpenseService;
    @EJB
    IMemberService memberService;
    private final int ORDER_NUMBER = 1000;
    private final int TOTAL_ERROR_NUMBER = 1001;
    private final int PRICE_ERROR_NUMBER = 1002;
    private final int VENDOR_ERROR_NUMBER = 1003;
    private final int ORDER_ERROR_NUMBER = 1004;
    private final int TOKEN_ERROR_NUMBER = 1005;
    private final int ORDER_REPEAT_ERROR_NUMBER = 1006;
    private final int MEMBER_ERROR_NUMBER = 1007;
    private final int VENDOR_NULL_ERROR_NUMBER = 1008;
    private final int QUOTATION_ERROR_NUMBER = 1009;
    private final int MEMBER_NAME_ERROR_NUMBER = 1010;
    private final int VENDOR_NAME_ERROR_NUMBER = 1011;
    private final int VENDOR_NAME_NULL_ERROR_NUMBER = 1012;
    private final int VENDOR_ENTITY_NULL_ERROR_NUMBER = 1013;
    private final int ORDER_OPEN_ERROR_NUMBER = 1014;
    private final int ORDER_MONEY_ERROR_NUMBER = 1015;
    private final int AMOUNT_MONEY_ERROR_NUMBER = 1016;
    private final int INVOICE_ERROR_NUMBER = 1017;
    private final int INQUIRY_ERROR_NUMBER = 1018;
    private final String TOTAL_ERROR = "总价不正确"; //1001
    private final String PRICE_ERROR = "供应商单个商品金额不正确";  //1002
    private final String VENDOR_ERROR = "供应商合计商品金额不正确"; //1003
    private final String ORDER_ERROR = "信息不完整"; //1004
    private final String TOKEN_ERROR = "TOKEN信息不正确"; //1005
    private final String ORDER_REPEAT_ERROR = "订单信息重复"; //1006
    private final String MEMBER_ERROR = "会员信息不存在！"; //1007
    private final String VENDOR_NULL_ERROR = "供应商订单信息不正确！"; //1008
    private final String QUOTATION_ERROR = "报价订单信息不正确！"; //1009
    private final String MEMBER_NAME_ERROR = "会员名称不正确"; //1010
    private final String VENDOR_NAME_ERROR = "供应商名称不正确"; //1011
    private final String VENDOR_NAME_NULL_ERROR = "无供应商名称"; //1012
    private final String VENDOR_ENTITY_NULL_ERROR = "没找到相关供应商"; //1013
    private final String ORDER_OPEN_ERROR = "没有订单ID"; //1014
    private final String ORDER_MONEY_ERROR = "没有输入总价或邮费"; //1015
    private final String AMOUNT_MONEY_ERROR = "没有输入总价"; //1016
    private final String INVOICE_ERROR = "发票信息为空"; //1017
    private final String INQUIRY_ERROR = "询价单信息不正确"; //1018
    final Logger logger = LoggerFactory.getLogger(OrderBiz.class);

    public OrderBiz() {
    }

    @Override
    public ReturnMessage saveOrder(OrderEntity order) {

        ReturnMessage isOk = ReturnMessage.success();
        if (order == null) {
            isOk = ReturnMessage.message(ORDER_ERROR_NUMBER, ORDER_ERROR, null);
            return isOk;
        }

        if (!"Ivj6eZRx40MTx2ZvnG8nA".equals(order.getToken())) {
            isOk = ReturnMessage.message(TOKEN_ERROR_NUMBER, TOKEN_ERROR, null);
            return isOk;
        }


        // TODO: 2017/5/3 订单唯一性验证
        // 检验格式
        // 认证错误
        // 适配性错误
        // TODO: 2017/5/3  同步(唯一性约束)
        // 用户,供应商信息错误 计算总计错误
        if (order.getOpenOrderId() == null) {
            isOk = ReturnMessage.message(ORDER_OPEN_ERROR_NUMBER, ORDER_OPEN_ERROR, null);
            return isOk;
        }
        ReturnMessage<List<MemberOrderEntity>> listMemberOrderEntityReturnMessage = memberService.listExistOrder(order.getOpenOrderId());
        List<MemberOrderEntity> MemberOrderEntityEntityList = listMemberOrderEntityReturnMessage.getData();
        if (MemberOrderEntityEntityList.size() != 0) {
            isOk = ReturnMessage.message(ORDER_REPEAT_ERROR_NUMBER, ORDER_REPEAT_ERROR, null);
            return isOk;
        }

        //获取cid
        ReturnMessage<TempUserEntity> memberEntity = memberService.getTempMember(order.getMemberId());
        TempUserEntity member = memberEntity.getData();
        // TODO: 2017/5/3 客户名称
        if (member == null) {
            isOk = ReturnMessage.message(MEMBER_ERROR_NUMBER, MEMBER_ERROR, null);
            return isOk;
        }
        if (!member.getUsername().equals(order.getMemberUsername())) {
            isOk = ReturnMessage.message(MEMBER_NAME_ERROR_NUMBER, MEMBER_NAME_ERROR, null);
            return isOk;
        }

        if (order.getVendorVO() == null || order.getVendorVO().size() <= 0) {
            isOk = ReturnMessage.message(VENDOR_NULL_ERROR_NUMBER, VENDOR_NULL_ERROR, null);
            return isOk;
        }
        // TODO: 2017/5/3 计算 总计
        Double totalAdd = 0.0, freightAdd = 0.0;
        for (int i = 0; i < order.getVendorVO().size(); i++) {
            Double vendorTotal = 0.0, vendorFreight = 0.0;
            VendorVO vendorVO = order.getVendorVO().get(i);
            // TODO: 2017/5/3 供应商名称
            MemberEntity entity = memberService.getItem(vendorVO.getId()).getData();
            if (entity == null) {
                isOk = ReturnMessage.message(VENDOR_ENTITY_NULL_ERROR_NUMBER, VENDOR_ENTITY_NULL_ERROR, null);
                return isOk;
            }
            if (vendorVO.getVendorUsername() == null) {
                isOk = ReturnMessage.message(VENDOR_NAME_NULL_ERROR_NUMBER, VENDOR_NAME_NULL_ERROR, null);
                return isOk;
            }
            if (!entity.getUsername().equals(vendorVO.getVendorUsername())) {
                isOk = ReturnMessage.message(VENDOR_NAME_ERROR_NUMBER, VENDOR_NAME_ERROR, null);
                return isOk;
            }

            for (int j = 0; j < vendorVO.getVendorOrderGoods().size(); j++) {
                VendorOrderGoodsVO vendorOrderGoodsVO = order.getVendorVO().get(i).getVendorOrderGoods().get(j);
                if (!vendorOrderGoodsVO.getAmount().equals(vendorOrderGoodsVO.getPrice() * vendorOrderGoodsVO.getNum() + vendorOrderGoodsVO.getFreight())) {
                    isOk = ReturnMessage.message(PRICE_ERROR_NUMBER, PRICE_ERROR, null);
                    return isOk;
                }
                totalAdd += vendorOrderGoodsVO.getPrice() * vendorOrderGoodsVO.getNum();
                freightAdd += vendorOrderGoodsVO.getFreight();
                vendorTotal += vendorOrderGoodsVO.getPrice() * vendorOrderGoodsVO.getNum();
                vendorFreight += vendorOrderGoodsVO.getFreight();
            }
            if (!(vendorTotal.equals(vendorVO.getTotal()) && vendorFreight.equals(vendorVO.getPostAmount()) && vendorVO.getAmount().equals(vendorTotal + vendorFreight))) {
                isOk = ReturnMessage.message(VENDOR_ERROR_NUMBER, VENDOR_ERROR, null);
                return isOk;
            }
        }
        if (order.getTotal() == null || order.getPostAmount() == null) {
            isOk = ReturnMessage.message(ORDER_MONEY_ERROR_NUMBER, ORDER_MONEY_ERROR, null);
            return isOk;
        }
        if (!(totalAdd.equals(order.getTotal()) && freightAdd.equals(order.getPostAmount()))) {
            isOk = ReturnMessage.message(TOTAL_ERROR_NUMBER, TOTAL_ERROR, null);
            return isOk;
        }
        if (order.getAmount() == null) {
            isOk = ReturnMessage.message(AMOUNT_MONEY_ERROR_NUMBER, AMOUNT_MONEY_ERROR, null);
            return isOk;
        }
        if (!(order.getAmount().equals(totalAdd + freightAdd))) {
            isOk = ReturnMessage.message(TOTAL_ERROR_NUMBER, TOTAL_ERROR, null);
            return isOk;
        }
        List<InquiryGoodsEntity> inquiryGoodsList = new ArrayList<>();
        try {
            /**
             * 添加询价信息到本地询价单表
             */
            if (order.getInvoiceVO() == null) {
                isOk = ReturnMessage.message(INVOICE_ERROR_NUMBER, INVOICE_ERROR, null);
                return isOk;
            }
            List<InquiryVO> inquiryVO = order.getInquiryVO();
            for (int i = 0; i < inquiryVO.size(); i++) {
                InquiryEntity inquiryEntity = order.getInquiryVO().get(i).toInquiryEntity(order.getInquiryVO().get(i));
                ReturnMessage<InquiryEntity> inquiryEntityReturnMessage = inquiryBizLocal.saveInquiry(order.getMemberId(), inquiryEntity);
                /**
                 * 更新有映射关系的询价单商品
                 */
                inquiryEntity = inquiryEntityReturnMessage.getData();
                if (inquiryEntity == null) {
                    throw new Exception(INQUIRY_ERROR_NUMBER + ":" + inquiryEntityReturnMessage.getMsg());
                } else {
                    List<InquiryGoodsEntity> goods = inquiryEntity.getGoods();
                    for (InquiryGoodsEntity inquiryGoodsEntity : goods) {
                        inquiryEntity.setGoods(null);
                        inquiryGoodsEntity.setInquiry(inquiryEntity);
                        inquiryGoodsEntity.setQuotationCount(0);
                        inquiryGoodsEntity.setDel(false);
                        inquiryGoodsEntity.setStatus(InquiryBiz.GOODS_STATUS_NO_QUOTATION);
                        Date date = new Date();
                        inquiryGoodsEntity.setLast(date);
                        inquiryGoodsEntity.setTime(date);
                        ReturnMessage<InquiryGoodsEntity> inquiryGoodsEntityReturnMessage = inquiryBizLocal.saveInquiryGoodsList(inquiryGoodsEntity);
                        InquiryGoodsEntity data = inquiryGoodsEntityReturnMessage.getData();
                        if (data == null) {
                            throw new Exception(inquiryGoodsEntityReturnMessage.getMsg() + "询价单保存失败。");
                        }
                        inquiryGoodsList.add(data);
                    }
                }
            }

            /**
             * 设置会员订单参数
             */
            Double total = order.getTotal(), amount = order.getAmount(), postage = order.getPostAmount(), totalOriginal = order.getTotal(), amountOriginal = order.getAmount(), postageOriginal = order.getPostAmount(),
                    amountFinal = order.getAmount(), payment = order.getAmount();
            Integer cid = member.getCorporation().getId(), mid = order.getMemberId(), paymenttypeid = 4,
                    invoicetypeid = order.getInvoiceVO().getType(), isinvoice = 0, paymentoperator = 1001, paymentmethod = 2,
                    stateid = 2, type = 1, clientOperation = 2, invoiceid = null;
            String invoicename = order.getInvoiceVO().getInvoiceHead(), detailedaddress = order.getInvoiceVO().getAddress(),
                    receiver = order.getInvoiceVO().getReceiver(), receivemobile = order.getInvoiceVO().getMobile(),
                    receiveaddress = order.getInvoiceVO().getAddress(), memo = order.getMemo();
            Date paymenttime = DateUtils.getDateFromString(order.getDate());


            if (invoicetypeid != 0) {
                Integer ispass = 1;
                String invoiceaddress = order.getInvoiceVO().getContactInfo(), invoicephone = order.getInvoiceVO().getContactInfo(),
                        invoiceaccount = order.getInvoiceVO().getBankInfo(), invoicetax = order.getInvoiceVO().getTaxNumber(),
                        invoicebank = order.getInvoiceVO().getBankInfo(), invoicesendaddress = order.getInvoiceVO().getAddress(),
                        invoicesendcontact = order.getInvoiceVO().getReceiver(), invoicesendphone = order.getInvoiceVO().getMobile();
                Date passtime = paymenttime;
                MemberInvoicesEntity entity = new MemberInvoicesEntity(mid, invoicetypeid, invoicename, invoiceaddress,
                        invoicephone, invoiceaccount, invoicetax, invoicebank, invoicesendaddress,
                        invoicesendcontact, invoicesendphone, ispass, passtime);
                /**
                 * 查询是否已存在发票
                 */
                ReturnMessage<List<MemberInvoicesEntity>> listReturnMessage = memberInvoiceService.listExistInvoice(entity);
                List<MemberInvoicesEntity> memberInvoicesEntityList = listReturnMessage.getData();
                if (memberInvoicesEntityList.size() == 0) {
                    MemberInvoicesEntity data = memberInvoiceService.addItem(entity).getData();
                    invoiceid = data.getId();
                } else {
                    invoiceid = memberInvoicesEntityList.get(0).getId();
                }
            }

            MemberOrderEntity memberOrderEntity = new MemberOrderEntity(order.getOpenOrderId(), cid, mid, total, amount, postage, totalOriginal,
                    amountOriginal, postageOriginal, paymenttypeid, amountFinal, invoicetypeid, isinvoice, invoicename,
                    detailedaddress, receiver, receivemobile, receiveaddress, payment, paymenttime, paymentoperator,
                    paymentmethod, memo, stateid, type, clientOperation, invoiceid);

            ReturnMessage<MemberOrderEntity> memberOrder = memberOrderService.addItem(memberOrderEntity);
            /**
             * 客户订单id
             */
            Integer memberOrderId = memberOrder.getData().getId();

            /**
             * 设置供应商订单参数
             */
            List<VendorVO> vendorVOList = order.getVendorVO();
            if (vendorVOList == null || vendorVOList.size() <= 0) {
                throw new Exception(VENDOR_NULL_ERROR_NUMBER + ":" + VENDOR_NULL_ERROR);
            }
            for (VendorVO vendorVO : vendorVOList) {
                Integer vid = vendorVO.getId(), orderid = memberOrderId, posttype = 1;
                Double totalVendor = vendorVO.getTotal(), amountVendor = vendorVO.getAmount(), postageVendor = vendorVO.getPostAmount(),
                        totalOriginalVendor = vendorVO.getTotal(), amountOriginalVendor = vendorVO.getAmount(), postageOriginalVendor = vendorVO.getPostAmount();
                String memoVendor = vendorVO.getMemo();
                VendorOrderEntity vendorOrderEntity = new VendorOrderEntity(cid, vid, orderid, posttype, totalVendor, amountVendor,
                        postageVendor, totalOriginalVendor, amountOriginalVendor, postageOriginalVendor, paymenttypeid, detailedaddress, receiver,
                        receivemobile, receiveaddress, stateid, type, memoVendor);
                ReturnMessage<VendorOrderEntity> vendorOrder = vendorOrderService.addItem(vendorOrderEntity);
                /**
                 * 供应商订单id
                 */
                Integer vendorOrderId = vendorOrder.getData().getId();

                /**
                 * 设置供应商订单商品参数
                 */
                List<VendorOrderGoodsVO> vendorOrderGoods = vendorVO.getVendorOrderGoods();
                for (VendorOrderGoodsVO vendorOrderGoodsVO : vendorOrderGoods) {

                    Integer vorderid = vendorOrderId;
                    String name = vendorOrderGoodsVO.getName(), aname = vendorOrderGoodsVO.getSpec(), vpname = vendorOrderGoodsVO.getName(),
                            vaname = vendorOrderGoodsVO.getSpec(), model = vendorOrderGoodsVO.getModel(), amodel = vendorOrderGoodsVO.getModel(),
                            vmodel = vendorOrderGoodsVO.getModel(), vamodel = vendorOrderGoodsVO.getModel(), spec = vendorOrderGoodsVO.getSpec(),
                            ASpec = vendorOrderGoodsVO.getSpec(), vspec = vendorOrderGoodsVO.getSpec(), vASpec = vendorOrderGoodsVO.getSpec(),
                            deliverydate = String.valueOf(vendorOrderGoodsVO.getDelivery()),
                            pro_name = vendorOrderGoodsVO.getName(), fromDispath = vendorOrderGoodsVO.getPlace(), brand = vendorOrderGoodsVO.getBrand(),
                            unit = vendorOrderGoodsVO.getUnit(), specname = vendorOrderGoodsVO.getSpec(), memoVendorOrderGoodsVO = vendorOrderGoodsVO.getMemo();
                    Double price = vendorOrderGoodsVO.getPrice(), priceOriginal = vendorOrderGoodsVO.getPrice(), freight = vendorOrderGoodsVO.getFreight(),
                            number = vendorOrderGoodsVO.getNum();

                    Integer inquiryEntityId = null;
                    for (InquiryGoodsEntity goodsEntity : inquiryGoodsList) {
                        if (goodsEntity.getSourceId() == vendorOrderGoodsVO.getGoodsId()) {
                            inquiryEntityId = goodsEntity.getId();
                        }
                    }

                    if (inquiryEntityId == null) {
                        throw new Exception("询价商品id:" + inquiryEntityId + " 不正确！");
                    }

                    ReturnMessage<QuotationEntity> quotationEntityReturnMessage = iQuotationBiz.saveQuotaion(mid, inquiryEntityId, name, brand, model, vendorOrderGoodsVO.getDescription(),
                            fromDispath, vendorOrderGoodsVO.getDelivery(), price, number, unit, vendorOrderGoodsVO.getPackageNum(),
                            vendorOrderGoodsVO.getPackageUnit(), amount, freight, DateUtils.getDateFromString(vendorOrderGoodsVO.getExpire()).getTime());
                    QuotationEntity quotationEntity = quotationEntityReturnMessage.getData();
                    if (quotationEntity == null) {
                        throw new Exception(QUOTATION_ERROR_NUMBER + ":" + "报价单保存失败！");
                    }
                    Integer quotationId = quotationEntity.getId();

                    VendorOrderProductEntity vendorOrderProductEntity = new VendorOrderProductEntity(vid, memberOrderId, vorderid,
                            name, aname, vpname, vaname, model, amodel, vmodel, vamodel, spec, ASpec, vspec, vASpec, price, priceOriginal,
                            deliverydate, number, quotationId, pro_name, fromDispath, freight, brand, unit, specname, memoVendorOrderGoodsVO);

                    VendorOrderProductEntity vendorOrderProduct = vendorOrderProductService.addItem(vendorOrderProductEntity).getData();
                    //供应商订单商品id
                    Integer vendorOrderProductId = vendorOrderProduct.getId();
                    MemberOrderProductEntity memberOrderProductEntity = new MemberOrderProductEntity(vid, orderid, vorderid,
                            vendorOrderProductId, name, aname, vpname, vaname, model, amodel, vmodel, vamodel, spec, ASpec,
                            vspec, vASpec, price, priceOriginal, deliverydate, number, quotationId, pro_name, fromDispath, freight, brand, unit, specname, memo);
                    ReturnMessage<MemberOrderProductEntity> memberOrderProduct = memberOrderProductService.addItem(memberOrderProductEntity);
                    //会员订单商品id
                    Integer memberOrderProductId = memberOrderProduct.getData().getId();
                }
            }

            // TODO: 2017/4/26 扣减信用额度

            CreditExpenseEntity creditExpense = new CreditExpenseEntity();
            creditExpense.setMemo("汉得api接入下单扣减信用额度！");
            creditExpense.setMoney(order.getAmount());
            creditExpense.setOrder_id(memberOrderId);
            creditExpenseService.addItem(creditExpense);

            ReturnMessage<CompanyOrgEntity> companyOrgEntity = companyOrgService.getItem(member.getCorporation().getId());
            CompanyOrgEntity companyOrg = companyOrgEntity.getData();
            companyOrg.setCredit(companyOrg.getCredit() - order.getAmount()); //更新可用信用额度

            isOk = ReturnMessage.message(ORDER_NUMBER,"创建订单成功",memberOrderId);
        } catch (Exception e) {
            isOk = ReturnMessage.failed(e.getMessage());
            logger.error(e.getMessage());
            if (e.getCause() != null) {
                logger.error(e.getCause().getMessage());
            }
            ctx.setRollbackOnly();
        }

        return isOk;
    }
}
