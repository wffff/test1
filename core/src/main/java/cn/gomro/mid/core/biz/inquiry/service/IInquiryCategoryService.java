package cn.gomro.mid.core.biz.inquiry.service;

import cn.gomro.mid.core.biz.base.IService;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryCategoryEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by Adam on 2017/3/23.
 */
public interface IInquiryCategoryService extends IService<InquiryCategoryEntity> {

    ReturnMessage<InquiryCategoryEntity> getById(Integer id);

    ReturnMessage<List<InquiryCategoryEntity>>  queryInquiryCategoryList(Integer Id,String Name,Integer page,Integer rows);

    Integer getInquiryCategoryupdate(InquiryCategoryEntity entity);

    Integer getInquiryCategorysave(InquiryCategoryEntity entity);

    Integer getInquiryCategorydelete(Integer id);
}
