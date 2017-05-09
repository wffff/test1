package cn.gomro.mid.core.biz.student.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.student.entity.StudentEntity;
import cn.gomro.mid.core.biz.student.service.IStudentSerivce;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
@Stateless
public class StudentService extends AbstractSessionService<StudentEntity> implements IStudentSerivce {
    @Override
    public ReturnMessage getName(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<StudentEntity>> getItemsPaged(StudentEntity entity, Integer page, Integer size) {
        return null;
    }
}
