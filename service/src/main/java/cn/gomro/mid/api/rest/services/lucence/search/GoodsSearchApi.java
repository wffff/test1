package cn.gomro.mid.api.rest.services.lucence.search;

import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.goods.service.IGoodsService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.search.lucene.IGoodsSearchBiz;
import org.apache.commons.lang.StringUtils;

import javax.ejb.AccessTimeout;
import javax.ejb.EJB;
import javax.ws.rs.*;
import java.io.IOException;

/**
 * Created by momo on 2016/5/31.
 */
@Path("/search")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.JSON_HEADER)
public class GoodsSearchApi {

    @EJB
    IGoodsSearchBiz searchBiz;

    public GoodsSearchApi() {
    }

    @GET
    @Path("/init")
    public ReturnMessage initialize(@QueryParam("step") int step, @QueryParam("size") int size) {
        size = size == 0 ? 800 : size;
        step = step == 0 ? 20 : step;
        ReturnMessage initialize = searchBiz.initialize(size, step);
        return initialize;
    }

    @GET
    @Path("/del")
    public ReturnMessage add(@QueryParam("admin") int admin) {
        //临时定义 清空索引必须简单验证。
        if (admin == 999)
            return searchBiz.del();
        else
            return ReturnMessage.failed();
    }

    @GET
    @Path("/{size}/{page}")
    public ReturnMessage searchGoodsMarket(@PathParam("page") int page, @PathParam("size") int size, @QueryParam("q") String q, @QueryParam("s") int sort) {
        return searchBiz.search(page, size, q, sort);
    }

    @GET
    @Path("/analysis")
    public ReturnMessage analyzerTest(@QueryParam("q") String q) throws IOException {
        if (StringUtils.isNotBlank(q))
            return searchBiz.analysisTest(q);
        else
            return ReturnMessage.failed();
    }

    @GET
    @Path("/addBrandDic")
    public ReturnMessage analyzerTest() {
        return searchBiz.addBrandDictionary();
    }
}
