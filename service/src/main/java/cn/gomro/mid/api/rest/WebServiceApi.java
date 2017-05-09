package cn.gomro.mid.api.rest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;

@Path("/")
public class WebServiceApi {

    @Context
    HttpServletRequest req;

    @Context
    ServletConfig servletConfig;

    @Context
    ServletContext servletContext;

    public WebServiceApi() {
    }

    @GET
    @Produces(RestMediaType.JSON_HEADER)
    public Object get(@Context HttpHeaders hh,@Context HttpServletRequest req) {
        MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
        Map<String, Cookie> pathParams = hh.getCookies();
        return req.getParameterMap();//headerParams;
    }
}
