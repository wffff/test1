package cn.gomro.mid.api.rest.services;

import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.oss.IOssFileUploadBizLocal;
import cn.gomro.mid.core.oss.OssFileUpload;
import com.aliyun.oss.model.UploadFileRequest;
import com.sun.istack.NotNull;
import com.sun.jersey.core.header.FormDataContentDisposition;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import java.io.InputStream;

/**
 * Created by Adam on 2017/3/24.
 */
@Path("/oss")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.MULTIPART_FORM_DATA)
public class OssApi {
    @EJB
    IOssFileUploadBizLocal ossFileUpload;

    public OssApi() {
    }
    @POST
    @Path("upload")
    public ReturnMessage uploadFile(UploadFileRequest request){
//        ossFileUpload.putObject(file,"test",)
        return ReturnMessage.failed("test");
    }
}
