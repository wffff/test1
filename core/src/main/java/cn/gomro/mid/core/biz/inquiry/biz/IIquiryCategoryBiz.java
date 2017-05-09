package cn.gomro.mid.core.biz.inquiry.biz;


import cn.gomro.mid.core.biz.inquiry.entity.InquiryCategoryEntity;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by 烦 on 2017/4/11.
 */
public interface IIquiryCategoryBiz {

    ReturnMessage<List<InquiryCategoryEntity>> getItemsPaged(InquiryCategoryEntity entity, Integer page, Integer size);

    ReturnMessage<List<InquiryCategoryEntity>>  queryInquiryCategoryList(Integer Id,String Name,Integer page,Integer rows);

    Integer getInquiryCategoryupdate(InquiryCategoryEntity entity);

    Integer getInquiryCategorysave(InquiryCategoryEntity entity);

    Integer getInquiryCategorydelete(Integer id);
}
