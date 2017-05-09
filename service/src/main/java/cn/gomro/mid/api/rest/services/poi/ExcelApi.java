package cn.gomro.mid.api.rest.services.poi;

import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.goods.biz.GoodsVO;
import cn.gomro.mid.core.biz.goods.biz.IGoodsBizLocal;
import cn.gomro.mid.core.biz.goods.service.GoodsType;
import cn.gomro.mid.core.biz.goods.service.impl.GoodsSpecService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by yaoo on 2016/8/24.
 */
@Path("/excel")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.JSON_HEADER)
public class ExcelApi {
    final Logger logger = LoggerFactory.getLogger(GoodsSpecService.class);

    @EJB
    private IGoodsBizLocal goodsBiz;

    public ExcelApi() {
    }

    /**
     * 处理excel文件并返回解析数据
     *
     * @param memberId 供应商Id
     * @param url      Excel地址
     * @return GoodsVO列表
     */
    @GET
    @Path("/check")
    public ReturnMessage<List<GoodsVO>> checkExcelData(@QueryParam("memberId") Integer memberId, @QueryParam("url") String url) {
        if (url == null || memberId == null) {
            return ReturnMessage.failed("Excel Url or Data is null", null);
        }
        GoodsType type = GoodsType.MARKET;
        try {
            List<GoodsVO> goodsVOList = new MarketGoodsInventoryExcelReader().read(url);
            return goodsBiz.checkGoodsVOList(type, memberId, goodsVOList);
        } catch (Exception e) {
            logger.error("Method: checkExcelData() {}",e);
        }
        return ReturnMessage.failed();
    }
}
