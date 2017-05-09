package cn.gomro.mid.api.rest.services;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.goods.entity.CorporationEntity;
import cn.gomro.mid.core.biz.inquiry.biz.IInquiryBizLocal;
import cn.gomro.mid.core.biz.inquiry.entity.*;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adam on 2017/1/17.
 */
@Path("/inquiry")
@Produces(RestMediaType.JSON_HEADER)
public class InquiryApi extends AbstractApi {

    final Logger logger = LoggerFactory.getLogger(InquiryApi.class);

    @EJB
    private IInquiryBizLocal inquiryBiz;

    public InquiryApi() {
    }

    /**
     * 新增询价单 同时添加询价商品列表
     */
    @POST
    @Path("/add")
    public ReturnMessage addInquiry(
            @FormParam("memberId") Integer memberId,
            @FormParam("contacts") String contacts,
            @FormParam("qq") String qq,
            @FormParam("inquiryDescription") String inquiryDescription,
            @FormParam("inquiryExpire") Long inquiryExpire,
            @FormParam("name") List<String> name,
            @FormParam("brand") List<String> brand,
            @FormParam("model") List<String> model,
            @FormParam("description") List<String> description,
            @FormParam("address") List<String> address,
            @FormParam("delivery") List<Integer> delivery,
            @FormParam("num") List<Double> num,
            @FormParam("unit") List<String> unit,
            @FormParam("packageNum") List<Double> packageNum,
            @FormParam("packageUnit") List<String> packageUnit,
            @FormParam("expire") List<Long> expire,
            @FormParam("attachment") List<String> attachment,
            @FormParam("url") List<String> url,
            @FormParam("memo") List<String> memo) {

        boolean complete = memberId != null &&
                contacts != null &&
                qq != null &&
                inquiryDescription != null &&
                inquiryExpire != null &&
                name != null && name.size() > 0 &&
                brand != null && brand.size() > 0 &&
                model != null && model.size() > 0 &&
                description != null && description.size() > 0 &&
                address != null && address.size() > 0 &&
                delivery != null && delivery.size() > 0 &&
                num != null && num.size() > 0 &&
                unit != null && unit.size() > 0 &&
                packageNum != null && packageNum.size() > 0 &&
                packageUnit != null && packageUnit.size() > 0 &&
                expire != null && expire.size() > 0 &&
                attachment != null && attachment.size() > 0 &&
                url != null && url.size() > 0 &&
                memo != null && memo.size() > 0;
        if (!complete) {
            return ReturnMessage.failed();
        }
        List<InquiryGoodsEntity> goods = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < name.size(); i++) {
            if (expire.get(i) > currentTimeMillis) {
                currentTimeMillis = expire.get(i);
            }
            InquiryGoodsEntity inquiryGoodsEntity = new InquiryGoodsEntity(
                    name.get(i), brand.get(i), model.get(i), description.get(i), address.get(i), delivery.get(i), num.get(i),
                    unit.get(i), packageNum.get(i), packageUnit.get(i), new Date(expire.get(i)), attachment.get(i), url.get(i), memo.get(i));
            goods.add(inquiryGoodsEntity);
        }
        InquiryEntity inquiryEntity = new InquiryEntity(contacts, qq, 0, inquiryDescription, new Timestamp(currentTimeMillis), goods);
        ReturnMessage<InquiryEntity> inquiryEntityReturnMessage = inquiryBiz.saveInquiry(memberId, inquiryEntity);
        if (inquiryEntityReturnMessage.getCode() >= 0) {
            return ReturnMessage.success();
        } else {
            return ReturnMessage.failed();
        }
    }

    /**
     * [批量] 审核询价商品 批量审核询价商品
     */
    @PUT
    @Path("goods/auditing")
    @Consumes(MediaType.WILDCARD)
    public ReturnMessage auditing(@QueryParam("id") List<Integer> id,
                                  @QueryParam("reject") Boolean reject,
                                  @QueryParam("reason") String reason,
                                  @QueryParam("categoryId") Integer categoryId) {
        return inquiryBiz.auditing(id, reject, reason, categoryId);
    }

    /**
     * [批量] 关闭询价商品 同时更新询价单状态
     */
    @PUT
    @Path("goods/abort")
    public ReturnMessage closeInquiryGoods(@QueryParam("id") List<Integer> id) {
        return inquiryBiz.closeInquiryGoods(id);
    }

    /**
     * [批量] 删除一条寻价商品（未报价）
     */
    @DELETE
    @Path("goods/remove")
    public ReturnMessage removeInquiryGoods(@QueryParam("id") List<Integer> id) {
        return inquiryBiz.removeInquiryGoods(id);
    }


    /**
     * 修改询价商品 未报价时可以修改
     */
    @POST
    @Path("goods/update")
    public ReturnMessage updateInquiryGoods(
            @FormParam("id") Integer id,
            @FormParam("name") String name,
            @FormParam("brand") String brand,
            @FormParam("model") String model,
            @FormParam("description") String description,
            @FormParam("address") String address,
            @FormParam("delivery") Integer delivery,
            @FormParam("num") Double num,
            @FormParam("unit") String unit,
            @FormParam("packageNum") Double packageNum,
            @FormParam("packageUnit") String packageUnit,
            @FormParam("expire") Long expire,
            @FormParam("attachment") String attachment,
            @FormParam("url") String url,
            @FormParam("memo") String memo
    ) {
        return inquiryBiz.updateInquiryGoods(id, name, brand, model, description, address, delivery, num, unit,
                packageNum, packageUnit, expire, attachment, url, memo);
    }

    /**
     * 修改询价单 所有询价商品未有报价时可修改
     */
    @POST
    @Path("update")
    public ReturnMessage updateInquiryGoods(
            @FormParam("id") Integer id,
            @FormParam("contacts") String contacts,
            @FormParam("qq") String qq,
            @FormParam("inquiryDescription") String inquiryDescription,
            @FormParam("inquiryExpire") Long inquiryExpire) {
        return inquiryBiz.updateInquiry(id, contacts, qq, inquiryDescription, inquiryExpire);
    }

    /**
     * [批量] 根据id 获取询价单
     */
    @GET
    @Path("id")
    public ReturnMessage<List<InquiryEntity>> getInquiry(@QueryParam("id") List<Integer> id) {
        return inquiryBiz.getInquiryById(id);
    }

    /**
     * 根据询价单属性条件 分页查询 询价单列表
     */
    @GET
    @Path("list")
    public ReturnMessage<List<InquiryEntity>> getInquiryList(@QueryParam("id") Integer id,
                                                             @QueryParam("contacts") String contacts,
                                                             @QueryParam("memberName") String memberName,
                                                             @QueryParam("status") Integer status,
                                                             @QueryParam("page") Integer page,
                                                             @QueryParam("size") Integer size,
                                                             @QueryParam("start") Date start,
                                                             @QueryParam("end") Date end) {
        page = Utils.reqInt(page, 1);
        size = Utils.reqInt(size, Constants.DEFAULT_PAGE_SIZE);
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setId(id);
        inquiryEntity.setContacts(contacts);
        inquiryEntity.setStatus(status);
        return inquiryBiz.listInquiry(inquiryEntity, memberName, page, size, start, end);
    }

    /**
     * 获取询价商品（id）
     */
    @GET
    @Path("/goods/id")
    public ReturnMessage<List<InquiryGoodsEntity>> getInquiryGoods(@QueryParam("id") List<Integer> id) {
        return inquiryBiz.getInquiryGoodsById(id);
    }

    /**
     * 根据询价商品属性及询价单属性条件 分页查询 询价商品列表
     */
    @GET
    @Path("/goods/list")
    public ReturnMessage<List<InquiryGoodsEntity>> listInquiryGoods(
            @QueryParam("id") Integer id,
            @QueryParam("contacts") String contacts,
            @QueryParam("name") String name,
            @QueryParam("companyName") String companyName,
            @QueryParam("brand") String brand,
            @QueryParam("model") String model,
            @QueryParam("address") String address,
            @QueryParam("status") Integer status,
            @QueryParam("categoryId") Integer categoryId,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("start") Date start,
            @QueryParam("end") Date end) {

        page = Utils.reqInt(page, 1);
        size = Utils.reqInt(size, Constants.DEFAULT_PAGE_SIZE);

        TempCorpEntity tempCorpEntity = new TempCorpEntity(companyName);
        TempUserEntity tempUserEntity = new TempUserEntity(tempCorpEntity);
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setMember(tempUserEntity);
        inquiryEntity.setContacts(contacts);
        InquiryCategoryEntity inquiryCategoryEntity = new InquiryCategoryEntity();
        inquiryCategoryEntity.setId(categoryId);
        InquiryGoodsEntity goods = new InquiryGoodsEntity();
        goods.setInquiry(inquiryEntity);
        goods.setCategory(inquiryCategoryEntity);
        goods.setName(name);
        goods.setBrand(brand);
        goods.setModel(model);
        goods.setAddress(address);
        goods.setStatus(status);
        return inquiryBiz.listInquiryGoods(goods, page, size, start, end);
    }


}
