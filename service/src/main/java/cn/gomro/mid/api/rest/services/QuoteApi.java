package cn.gomro.mid.api.rest.services;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.inquiry.biz.IQuotationBizLocal;
import cn.gomro.mid.core.biz.inquiry.entity.QuotationEntity;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.DateUtils;
import cn.gomro.mid.core.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

/**
 * Created by adam on 2017/1/17.
 */
@Path("/quote")
@Produces(RestMediaType.JSON_HEADER)
//@Consumes(RestMediaType.JSON_HEADER)
public class QuoteApi extends AbstractApi {

    final Logger logger = LoggerFactory.getLogger(QuoteApi.class);

    @EJB
    private IQuotationBizLocal quotationBiz;

    public QuoteApi() {
    }

    /**
     * 新建报价 针对询价商品添加报价
     */
    @POST
    @Path("/save")
    public ReturnMessage saveQuotation(@FormParam("memberId") Integer memberId,
                                       @FormParam("inquiryGoodsId") Integer inquiryGoodsId,
                                       @FormParam("name") String name,
                                       @FormParam("brand") String brand,
                                       @FormParam("model") String model,
                                       @FormParam("description") String description,
                                       @FormParam("place") String place,
                                       @FormParam("delivery") Integer delivery,
                                       @FormParam("price") Double price,
                                       @FormParam("num") Double num,
                                       @FormParam("unit") String unit,
                                       @FormParam("packageNum") Double packageNum,
                                       @FormParam("packageUnit") String packageUnit,
                                       @FormParam("amount") Double amount,
                                       @FormParam("freight") Double freight,
                                       @FormParam("expire") Long expire) {
        return quotationBiz.saveQuotaion(memberId,inquiryGoodsId,name,brand,model,description,place,delivery,price,num,
                unit,packageNum,packageUnit,amount,freight,expire);
    }

    /**
     * 根据id 获取一条报价商品
     */
    @GET
    @Path("/id/{id}")
    public ReturnMessage<QuotationEntity> getQuotationGoods(@PathParam("id") Integer id) {
        return quotationBiz.getById(id);
    }

    /**
     * 根据寻价商品id 获取报价商品列表
     */
    @GET
    @Path("/list/inquiry/goods/{id}")
    public ReturnMessage<List<QuotationEntity>> getQuotaionListByInquiryGoodsId(@PathParam("id") Integer id) {
        return quotationBiz.listByInquiryGoodsId(id);
    }

    /**
     * 根据寻价单id 获取报价商品列表
     */
    @GET
    @Path("/list/inquiry/{id}")
    @Consumes(MediaType.WILDCARD)
    public ReturnMessage<List<QuotationEntity>> getQuotaionListByInquiryId(@PathParam("id") Integer id,
                                                                           @QueryParam("page") Integer page,
                                                                           @QueryParam("size") Integer size) {
        page = Utils.reqInt(page, 1);
        size = Utils.reqInt(size, Constants.DEFAULT_PAGE_SIZE);
        return quotationBiz.listByInquiryId(id, page, size);
    }

    /**
     * 根据寻价单id 获取报价商品列表
     */
    @GET
    @Path("/list/quotation")

    public ReturnMessage<List<QuotationEntity>> listQuotaionListByQuotation(@QueryParam("id") Integer id,
                                                                            @QueryParam("page")Integer page,
                                                                            @QueryParam("size")Integer size,
                                                                            @QueryParam("start") Date start,
                                                                            @QueryParam("end") Date end) {

        page = Utils.reqInt(page, 1);
        size = Utils.reqInt(size, Constants.DEFAULT_PAGE_SIZE);
        QuotationEntity quotation=new QuotationEntity(id);
        return quotationBiz.listByConditions(quotation,page, size, start, end);
    }


    /**
     * 根据报价商品id删除报价商品
     */
    @DELETE
    @Path("/del/{id}")
    public ReturnMessage delQuotation(@PathParam("id") Integer id) {
        return quotationBiz.removeQuotaion(id);
    }

    /**
     * 修改报价商品
     *
     * @paramquotation
     * @return
     */
    @PUT
    @Path("/edit")
    @Consumes(APPLICATION_FORM_URLENCODED)
    public ReturnMessage auditingInquiry(@QueryParam("id") Integer id,
                                         @QueryParam("name") String name,
                                         @QueryParam("brand") String brand,
                                         @QueryParam("model") String model,
                                         @QueryParam("description") String description,
                                         @QueryParam("place") String place,
                                         @QueryParam("delivery") Integer delivery,
                                         @QueryParam("price") Double price,
                                         @QueryParam("num") Double num,
                                         @QueryParam("unit") String unit,
                                         @QueryParam("packageNum") Double packageNum,
                                         @QueryParam("packageUnit") String packageUnit,
                                         @QueryParam("freight") Double freight,
                                         @QueryParam("expire") String expire){
        QuotationEntity quotation =new QuotationEntity(id,name,brand,model,description,place,delivery,price,num,unit,packageNum,packageUnit,freight, DateUtils.getDateFromString(expire));
        return quotationBiz.updateQuotation(quotation);
    }
    /**
    *
    ** 根据报价单ID和订单ID 修改报价单状态 同时修改询价单商品状态
     */
    @PUT
    @Path("/motify")
    @Consumes(RestMediaType.FORM_HEADER)
    public ReturnMessage<QuotationEntity> purchaseOrder(@QueryParam("quotationid") Integer quotationId,
                                                        @QueryParam("orderid") Integer orderId){
        return quotationBiz.purchaseOrder(quotationId,orderId);
    }
}
