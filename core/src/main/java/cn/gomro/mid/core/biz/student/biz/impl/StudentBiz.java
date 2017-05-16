package cn.gomro.mid.core.biz.student.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.student.biz.IStudentBiz;
import cn.gomro.mid.core.biz.student.biz.IStudentBizLocal;
import cn.gomro.mid.core.biz.student.biz.IStudentBizRemote;
import cn.gomro.mid.core.biz.student.entity.StudentEntity;
import cn.gomro.mid.core.biz.student.entity.StudentForm;
import cn.gomro.mid.core.biz.student.service.IStudentSerivce;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
@Stateless
public class StudentBiz extends AbstractSessionBiz implements IStudentBizLocal,IStudentBizRemote {
    @EJB
    IStudentSerivce studentSerivce;
    @Override
    public ReturnMessage saveOrder(StudentForm sf) {
        StudentEntity se=new StudentEntity(sf.getName());
        return studentSerivce.addItem(se);
    }

    @Override
    public ReturnMessage<List<StudentEntity>> getStudent() {
        return studentSerivce.getStudent();
    }

    @Override
    public ReturnMessage<List<StudentEntity>> getStudentByPage(int page, int rows) {
        return studentSerivce.getStudentByPage(page,rows);
    }

    @Override
    public int getStudentTotal() {
        return studentSerivce.getStudentTotal();
    }
}
