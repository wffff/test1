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

    @Override
    public ReturnMessage<List<StudentEntity>> getStudentByPage(int page, int rows) {
        String jpql = "select i from StudentEntity i";
        String jpqlCount = "select count(i) from StudentEntity i";
        Query query = em.createQuery(jpql);
        Query countQuery = em.createQuery(jpqlCount);
        int count = new Long((long) countQuery.getSingleResult()).intValue();
        int pages = rows == 0 ? 0 : (count / rows);
        if (count % rows != 0) pages++;
        if (page < 1) page = 1;
        if (page > pages) page = pages;

        int start = (page - 1) * rows;
        if (count > 0 && start < count) {
            query.setFirstResult(start);
            query.setMaxResults(rows);
        }
        List<StudentEntity> resultList=query.getResultList();
        return ReturnMessage.success(resultList);
    }

    @Override
    public int getStudentTotal() {
        String jpqlCount = "select count(i) from StudentEntity i";
        Query countQuery = em.createQuery(jpqlCount);
        int count = new Long((long) countQuery.getSingleResult()).intValue();
        return count;
    }
}
