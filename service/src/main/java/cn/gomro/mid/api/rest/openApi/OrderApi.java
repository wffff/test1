package cn.gomro.mid.api.rest.openApi;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.order.biz.IOrderBizLocal;
import cn.gomro.mid.core.biz.order.entity.vo.*;
import cn.gomro.mid.core.common.message.ReturnMessage;
import com.alibaba.fastjson.JSON;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2017/4/11.
 * 第三方开放平台接口部分
 */
@Path("/order")
@Produces(RestMediaType.JSON_HEADER)
public class OrderApi extends AbstractApi {

    @EJB
    private IOrderBizLocal OrderBiz;

    public OrderApi() {
    }

    @POST
    @Path("/add")
    @Consumes(RestMediaType.FORM_HEADER)
    public ReturnMessage addVendorOrder(@BeanParam OrderEntity order) {
        return OrderBiz.saveOrder(order);
    }

    @GET
    @Path("/form")
    @Consumes(RestMediaType.FORM_HEADER)
    public String getOrderForm() {

        /**
         * 发票信息
         */
        InvoiceVO invoiceVO = new InvoiceVO(2, "上海固买电子商务有限公司", "310002232",
                "北青公路1068号 021-54321", "1231235", 149.0, "上海", "上海市",
                "青浦区", "双联路388弄8号", "发票收件人", "12396392922");

        /**
         * 询价单及询价商品信息
         */
        InquiryGoodsVO inquiryGoodsVO = new InquiryGoodsVO(1, "工具箱", "世达", "a-102", "小号",
                "多合一工具箱空间要大", "上海市", 5, 2.0, "只", 1.0, "只",
                "2017-05-27 00:00:00", "附件信息", "www.gomro.cn", "备注信息");
        List<InquiryGoodsVO> inquiryGoodsVOList = new ArrayList();
        inquiryGoodsVOList.add(inquiryGoodsVO);
        InquiryVO inquiryVO = new InquiryVO(1, "李磊", "1239875374", "询价单描述或备注", inquiryGoodsVOList);
        List<InquiryVO> inquiryVOList = new ArrayList();
        inquiryVOList.add(inquiryVO);

        /**
         * 供应商及子订单（供应商订单）列表
         */
        VendorOrderGoodsVO vendorOrderGoodsVO = new VendorOrderGoodsVO(1, 1, "工具箱", "世达",
                "a-102", "小号", "多合一工具箱，内空间大", 50.00, "收货地址：上海市黄浦区南京东路1号", "发货地：上海",
                5, 2.0, "只", 1.0, "只", 49.0, 149.0,
                "2017-05-27 00:00:00", "商品备注信息内容");
        List<VendorOrderGoodsVO> vendorOrderGoodsVOList = new ArrayList();
        vendorOrderGoodsVOList.add(vendorOrderGoodsVO);

        VendorVO vendorVO = new VendorVO("guangtong_admin",1001, 100.0, 49.0, 149.0, "子订单（供应商订单）备注内容", vendorOrderGoodsVOList);
        List<VendorVO> vendorVOList = new ArrayList();
        vendorVOList.add(vendorVO);

        /**
         * 订单实体Bean
         */
        OrderEntity o = new OrderEntity("13918025359","Ivj6eZRx40MTx2ZvnG8nA", 1888, 1001, inquiryVOList, vendorVOList,
                "上海", "上海市", "青浦区", "双联路388弄8号", "收货人", "15501234567",
                100.00, 49.00, 149.00, "2017-05-27 00:00:00",
                invoiceVO, "备注");

        return JSON.toJSONString(o);
    }

}
