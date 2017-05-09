package cn.gomro.mid.core.biz.inquiry.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.inquiry.biz.IIquiryCategoryBizLocal;
import cn.gomro.mid.core.biz.inquiry.biz.IIquiryCategoryBizRemote;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryCategoryEntity;
import cn.gomro.mid.core.biz.inquiry.service.IInquiryCategoryService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by 烦 on 2017/4/11.
 */
@Stateless
public class IquiryCategoryBiz extends AbstractSessionBiz implements IIquiryCategoryBizLocal,IIquiryCategoryBizRemote {

    @EJB
    IInquiryCategoryService iInquiryCategoryService;

    @Override
    public ReturnMessage<List<InquiryCategoryEntity>> getItemsPaged(InquiryCategoryEntity entity, Integer page, Integer size) {
        return iInquiryCategoryService.getItemsPaged(entity,page,size);
    }

    public  ReturnMessage<List<InquiryCategoryEntity>>  queryInquiryCategoryList(Integer Id,String Name,Integer page,Integer rows)
    {
        return iInquiryCategoryService.queryInquiryCategoryList(Id,Name,page,rows);
    }

    public Integer getInquiryCategoryupdate(InquiryCategoryEntity entity)
    {
        return iInquiryCategoryService.getInquiryCategoryupdate(entity);
    }
    public Integer getInquiryCategorysave(InquiryCategoryEntity entity)
    {
        return iInquiryCategoryService.getInquiryCategorysave(entity);
    }
    public  Integer getInquiryCategorydelete(Integer id)
    {
        return iInquiryCategoryService.getInquiryCategorydelete(id);
    }
}
