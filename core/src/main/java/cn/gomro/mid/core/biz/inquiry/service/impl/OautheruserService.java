package cn.gomro.mid.core.biz.inquiry.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.goods.service.impl.GoodsSpecService;
import cn.gomro.mid.core.biz.inquiry.entity.OautherUser;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

/**
 * Created by Adam on 2017/3/23.
 */
@Stateless
public class OautheruserService extends AbstractSessionService<OautherUser> implements cn.gomro.mid.core.biz.inquiry.service.OautheruserService {

    final Logger logger = LoggerFactory.getLogger(GoodsSpecService.class);

    public OautheruserService(){}

    @Override
    public ReturnMessage<OautherUser> getById(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<OautherUser>> queryOautherUserlist(Integer Id, String Name, Integer page, Integer rows) {
        String where = " WHERE del=false ";
        String order = " ORDER BY id DESC";
        if (Id != null && Id > 0){ where+=" AND id="+Id;}
        if (!Name.equals("")){ where+=" AND username like LOWER('%" + Name + "%')";}
        return  JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM OautherUser" + where,
                "FROM OautherUser" + where + order, page, rows);
    }

    @Override
    public Integer getOautherUserupdate(OautherUser entity) {

        OautherUser OautherUser = JpaUtils.querySingleResult(em, " FROM OautherUser where id=" + entity.getId());
        String order = " ORDER BY id DESC";
        Integer returned=1;
        Date date = new Date();

        String where=" where del=false  ";
        String and=" and id="+entity.getId();
        entity.setLast(date);
        entity.setTime(OautherUser.getTime());

        List<OautherUser> OautherUserListgetbyid =  JpaUtils.queryShortResultList(em, "FROM OautherUser"+where+and+order);
        OautherUser merge = em.merge(entity);
        if (merge.equals(OautherUserListgetbyid))
        {
            returned=1;
        }else
        {
            returned=2;
        }
        List<OautherUser> brandEntityList =  JpaUtils.queryShortResultList(em, "FROM OautherUser"+where+and+order);

        return returned;
    }

    @Override
    public Integer getOautherUsersave(OautherUser entity) {
        Integer returned=1;
        Date date = new Date();
        entity.setLast(null);
        entity.setTime(date);
        em.persist(entity);
        Integer id = em.find(entity.getClass(), entity.getId()).getId();
        if (id==0)
        {
            returned=1;
        }else
        {
            returned=2;
        }
        return returned;
    }

    @Override
    public Integer getOautherUserdelete(Integer id) {
        String where = " WHERE del=false  and  id="+id;
        String order = " ORDER BY id DESC";
        List<OautherUser> OautherUserEntityList =  JpaUtils.queryShortResultList(em, "FROM OautherUser"+where+order);
        Integer returned=1;
        OautherUser goodsBrandEntity=new OautherUser();
        for (OautherUser b:OautherUserEntityList)
        {
            b.setDel(true);
            goodsBrandEntity=em.merge(b);
        }
        for (OautherUser b:OautherUserEntityList)
        {
            if (b.equals(OautherUserEntityList))
            {
                returned=2;
            }else
            {
                returned=1;
            }
        }
        return returned;
    }

    @Override
    public ReturnMessage getName(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<OautherUser>> getItemsPaged(OautherUser entity, Integer page, Integer size) {
        String where = " WHERE del=false";
        String order = " ORDER BY id DESC";
        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM OautherUser" + where,
                "FROM OautherUser" + where + order, page, size);
    }
}
