package cn.gomro.mid.core.biz.inquiry.biz;


import cn.gomro.mid.core.biz.inquiry.entity.InquiryCategoryEntity;
import cn.gomro.mid.core.biz.inquiry.entity.OautherUser;
import cn.gomro.mid.core.common.message.ReturnMessage;

import java.util.List;

/**
 * Created by 烦 on 2017/4/11.
 */
public interface OautheruserBiz {

    ReturnMessage<List<OautherUser>> getItemsPaged(OautherUser entity, Integer page, Integer size);

    ReturnMessage<List<OautherUser>>  queryOautherUserlist(Integer Id, String Name, Integer page, Integer rows);

    Integer getOautherUserupdate(OautherUser entity);

    Integer getOautherUsersave(OautherUser entity);

    Integer getOautherUserdelete(Integer id);
}
