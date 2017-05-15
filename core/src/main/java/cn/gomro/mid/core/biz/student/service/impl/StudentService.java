package cn.gomro.mid.core.biz.student.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.student.entity.StudentEntity;
import cn.gomro.mid.core.biz.student.service.IStudentSerivce;
import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.Stateless;
import javax.persistence.Query;
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

    @Override
    public ReturnMessage<List<StudentEntity>> getStudent() {

        String jpql = "select i from StudentEntity i";
        Query query = em.createQuery(jpql);
        query.setHint(Constants.JPA_CACHEABLE, true);
        List<StudentEntity> resultList=query.getResultList();
        return ReturnMessage.success(resultList);
    }
}
