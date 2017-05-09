package cn.gomro.mid.core.biz.inquiry.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.inquiry.biz.OautheruserBizLocal;
import cn.gomro.mid.core.biz.inquiry.biz.OautheruserBizRemote;
import cn.gomro.mid.core.biz.inquiry.entity.OautherUser;
import cn.gomro.mid.core.biz.inquiry.service.OautheruserService;
import cn.gomro.mid.core.common.message.ReturnMessage;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by 烦 on 2017/4/11.
 */
@Stateless
public class OautherUserBiz extends AbstractSessionBiz implements OautheruserBizLocal,OautheruserBizRemote {

    @EJB
    OautheruserService oautheruserService;

    @Override
    public ReturnMessage<List<OautherUser>> getItemsPaged(OautherUser entity, Integer page, Integer size) {
        return oautheruserService.getItemsPaged(entity,page,size);
    }

    @Override
    public ReturnMessage<List<OautherUser>> queryOautherUserlist(Integer Id, String Name, Integer page, Integer rows) {
        return oautheruserService.queryOautherUserlist(Id,Name,page,rows);
    }

    @Override
    public Integer getOautherUserupdate(OautherUser entity) {
        return oautheruserService.getOautherUserupdate(entity);
    }

    @Override
    public Integer getOautherUsersave(OautherUser entity) {
        return oautheruserService.getOautherUsersave(entity);
    }

    @Override
    public Integer getOautherUserdelete(Integer id) {
        return oautheruserService.getOautherUserdelete(id);
    }
}
