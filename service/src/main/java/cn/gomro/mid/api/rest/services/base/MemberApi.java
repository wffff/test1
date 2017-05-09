package cn.gomro.mid.api.rest.services.base;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.goods.entity.CorporationEntity;
import cn.gomro.mid.core.biz.goods.entity.MemberEntity;
import cn.gomro.mid.core.biz.goods.service.IMemberService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.Utils;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.Date;

/**
 * Created by yaodw on 2016/7/18.
 */
@Path("/member")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.JSON_HEADER)
public class MemberApi extends AbstractApi {

    @EJB
    private IMemberService memberService;

    public MemberApi() {
    }

    /**
     * 根据会员ID得到会员
     *
     * @param id 会员id
     * @return 会员对象
     */
    @GET
    @Path("/id/{id}")
    public ReturnMessage<MemberEntity> getVendor(@PathParam("id") Integer id) {

        id = Utils.reqInt(id, 0);
        return memberService.getItem(id);
    }

    /**
     * 根据会员ID得到会员名称
     *
     * @param id 会员id
     * @return 会员名称
     */
    @GET
    @Path("/name/{id}")
    public ReturnMessage getName(@PathParam("id") Integer id) {

        id = Utils.reqInt(id, 0);
        if(id==0) return ReturnMessage.failed();
        return memberService.getName(id);
    }

    /**
     * 增加会员
     * @param name      会员名
     * @param isCustomer    是客户
     * @param isGroupCustomer   是集团客户
     * @param isVendor  是供应商
     * @param isGroupVendor 是信息供应商
     * @param companyName   公司名
     * @param act   是否启用
     * @param isshowname    是否显示名
     * @return
     */
    @POST
    @Path("/add")
    public ReturnMessage addMember(String name,Boolean isCustomer,Boolean isGroupCustomer,Boolean isVendor,Boolean isGroupVendor,String companyName,Boolean act,Integer isshowname) {
        Date date = new Date();
        CorporationEntity corporationEntity = new CorporationEntity(name, act, isshowname, false, date, date);
        MemberEntity entity = new MemberEntity(false,date,date,corporationEntity);
        return memberService.addItem(entity);
    }

    /**
     * 根据id删除会员
     * @param id
     * @return
     */
    @GET
    @Path("/del/id/{id}")
    public ReturnMessage delMember(@PathParam("id") Integer id) {

        id = Utils.reqInt(id, 0);
        if(id==0) return ReturnMessage.failed();
        MemberEntity memberEntity = memberService.getItem(id).getData();
        return memberService.delItem(memberEntity);
    }

    /**
     * 根据id编辑会员信息
     * @param id
     * @param name      会员名
     * @param isCustomer    是客户
     * @param isGroupCustomer   是集团客户
     * @param isVendor  是供应商
     * @param isGroupVendor 是信息供应商
     * @param companyName   公司名
     * @param act   是否启用
     * @param isshowname    是否显示名
     * @return
     */
    @POST
    @Path("/edit/id/{id}")
    public ReturnMessage editMember(@PathParam("id") Integer id,String name,Boolean isCustomer,Boolean isGroupCustomer,Boolean isVendor,
                                    Boolean isGroupVendor,String companyName,Boolean act,Integer isshowname) {

        /*
        * 因领导要求修改增加corporation对象关联，此段需要重新做！
        id = Utils.reqInt(id, 0);
        if(id==0) return ReturnMessage.failed();
        MemberEntity memberEntity = memberService.getItem(id).getData();
        if(memberEntity!=null){
            if(StringUtils.isNotBlank(name)) memberEntity.getCorporation().setName(name);
            if(StringUtils.isNotBlank(companyName)) memberEntity.getCorporation().setCompanyName(companyName);
            if(act!=null) memberEntity.getCorporation().setAct(act);
            if(isshowname!=null && (isshowname==0||isshowname==1)) memberEntity.getCorporation().setIsshowname(isshowname);
            return memberService.editItem(memberEntity);
        }*/
        return ReturnMessage.failed();
    }


}