package cn.gomro.mid.api.rest.services.base;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.goods.entity.FreightTemplateEntity;
import cn.gomro.mid.core.biz.goods.service.IFreightTemplateService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by yaodw on 2016/7/18.
 */
@Path("/freight")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.JSON_HEADER)
public class FreightApi extends AbstractApi {

    @EJB
    private IFreightTemplateService freightTemplateService;

    public FreightApi() {
    }

    /**
     * 根据运费模板ID得到运费模板名称
     *
     * @param id 供应商id
     * @return 运费模板名称
     */
    @GET
    @Path("/template/name/{id}")
    public ReturnMessage getTemplateName(@PathParam("id") Integer id) {
        return freightTemplateService.getName(id);
    }

    /**
     * 根据运费模板ID得到运费模板名称
     *
     * @param id 供应商id
     * @return 运费模板名称
     */
    @GET
    @Path("/template/{id}")
    public ReturnMessage<FreightTemplateEntity> getTemplate(@PathParam("id") Integer id) {
        return freightTemplateService.getItem(id);
    }

    /**
     * 根据供应商ID得到供应商运费模板列表
     *
     * @param vid 供应商id
     * @return 供应商运费模板列表
     */
    @GET
    @Path("/template/list/{vid}")
    public ReturnMessage<List<FreightTemplateEntity>> getVendorTemplateList(@PathParam("vid") Integer vid) {
        return freightTemplateService.getMemberFreightTemplateList(vid);
    }
}