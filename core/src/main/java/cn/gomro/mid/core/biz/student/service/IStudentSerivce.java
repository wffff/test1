package cn.gomro.mid.core.biz.student.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.student.entity.StudentEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
public interface IStudentSerivce extends IService<StudentEntity>{
    ReturnMessage<List<StudentEntity>> getStudentByPage(int page, int rows);

    int getStudentTotal();
}
