package cn.gomro.mid.core.biz.student.biz;

import cn.gomro.mid.core.biz.student.entity.StudentEntity;
import cn.gomro.mid.core.biz.student.entity.StudentForm;
import cn.gomro.mid.core.common.message.ReturnMessage;

/**
 * Created by Administrator on 2017/5/9.
 */
public interface IStudentBiz {
    ReturnMessage saveOrder(StudentForm sf);
}
