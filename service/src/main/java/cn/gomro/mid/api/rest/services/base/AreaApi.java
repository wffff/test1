package cn.gomro.mid.api.rest.services.base;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.common.biz.IAreaBiz;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ws.rs.*;

/**
 * Created by yaodw on 2016/7/18.
 */
@Path("/area")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.JSON_HEADER)
public class AreaApi extends AbstractApi {

    @EJB
    IAreaBiz areaBiz;

    public AreaApi() {
    }

    @GET
    @Path("/{id}")
    public ReturnMessage getAreaItem(@PathParam("id") Integer id) {
       // return areaBiz.getAreaItem(id);
        return ReturnMessage.success("ok");
    }
}