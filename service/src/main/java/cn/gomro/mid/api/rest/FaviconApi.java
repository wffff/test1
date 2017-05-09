package cn.gomro.mid.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by admin on 2016/9/12.
 * 解决：ERROR [cn.gomro.mid.api.rest.exception.ExceptionMapper]
 * (default task-12) Message : RESTEASY003210: Could not find resource
 * for full path: http://x.service.gomro.cn:8899/favicon.ico

 */
@Path("")
@Produces("image/x-icon")
public class FaviconApi {
    @GET
    @Path("/favicon.ico")
    public String favicon() {
        return "favicon.ico";
    }
}
