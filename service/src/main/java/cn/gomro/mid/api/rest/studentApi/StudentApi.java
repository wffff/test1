package cn.gomro.mid.api.rest.studentApi;

import cn.gomro.mid.api.rest.AbstractApi;
import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.biz.student.biz.IStudentBizLocal;
import cn.gomro.mid.core.biz.student.entity.StudentForm;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ws.rs.*;

/**
 * Created by Administrator on 2017/5/9.
 */
@Path("/student")
@Produces(RestMediaType.JSON_HEADER)
public class StudentApi extends AbstractApi {
    @EJB
    private IStudentBizLocal studentBizLocal;

    public StudentApi() {

    }

    @POST
    @Path("/add")
    @Consumes(RestMediaType.FORM_HEADER)
    public ReturnMessage addStudent(@BeanParam StudentForm sf) {
        return studentBizLocal.saveOrder(sf);
    }
}
