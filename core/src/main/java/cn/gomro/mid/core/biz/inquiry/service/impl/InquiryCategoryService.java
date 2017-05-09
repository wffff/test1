package cn.gomro.mid.core.biz.inquiry.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.goods.service.impl.GoodsSpecService;
import cn.gomro.mid.core.biz.inquiry.entity.InquiryCategoryEntity;
import cn.gomro.mid.core.biz.inquiry.service.IInquiryCategoryService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.JpaUtils;
import jdk.nashorn.internal.objects.NativeUint8Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

/**
 * Created by Adam on 2017/3/23.
 */
@Stateless
public class InquiryCategoryService  extends AbstractSessionService<InquiryCategoryEntity> implements IInquiryCategoryService {

    final Logger logger = LoggerFactory.getLogger(GoodsSpecService.class);

    public InquiryCategoryService(){}

    @Override
    public ReturnMessage getName(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<InquiryCategoryEntity>> getItemsPaged(InquiryCategoryEntity entity, Integer page, Integer size) {
        String where = " WHERE del=false";
        String order = " ORDER BY id DESC";
        return JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM InquiryCategoryEntity" + where,
                "FROM InquiryCategoryEntity" + where + order, page, size);
    }

    public ReturnMessage<InquiryCategoryEntity> getById(Integer id) {
        return super.getItem(id);
    }

    public Integer getInquiryCategoryupdate(InquiryCategoryEntity entity)
    {
        InquiryCategoryEntity inquiryCategoryEntity = JpaUtils.querySingleResult(em, " FROM InquiryCategoryEntity where id=" + entity.getId());
        String order = " ORDER BY id DESC";
        Integer returned=1;
        Date date = new Date();

        String where=" where del=false  ";
        String and=" and id="+entity.getId();
        entity.setLast(date);
        entity.setTime(inquiryCategoryEntity.getTime());

        List<InquiryCategoryEntity> brandEntityListgetbyid =  JpaUtils.queryShortResultList(em, "FROM InquiryCategoryEntity"+where+and+order);
        InquiryCategoryEntity merge = em.merge(entity);
        if (merge.equals(brandEntityListgetbyid))
        {
            returned=1;
        }else
        {
            returned=2;
        }
        List<InquiryCategoryEntity> brandEntityList =  JpaUtils.queryShortResultList(em, "FROM InquiryCategoryEntity"+where+and+order);

        return returned;
    }

    public ReturnMessage<List<InquiryCategoryEntity>>  queryInquiryCategoryList(Integer Id,String Name,Integer page,Integer rows)
    {
        String where = " WHERE del=false ";
        String order = " ORDER BY id DESC";
        if (Id != null && Id > 0){ where+=" AND id="+Id;}
        if (!Name.equals("")){ where+=" AND name like LOWER('%" + Name + "%')";}
        return  JpaUtils.queryPaged(em, "SELECT COUNT(id) FROM InquiryCategoryEntity" + where,
                "FROM InquiryCategoryEntity" + where + order, page, rows);
    }

    public Integer getInquiryCategorysave(InquiryCategoryEntity entity)
    {
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
        { returned=2;

        }
        return returned;
    }

    public Integer getInquiryCategorydelete(Integer id)
    {

        String where = " WHERE del=false  and  id="+id;
        String order = " ORDER BY id DESC";
        List<InquiryCategoryEntity> brandEntityList =  JpaUtils.queryShortResultList(em, "FROM InquiryCategoryEntity"+where+order);
        Integer returned=1;
        InquiryCategoryEntity goodsBrandEntity=new InquiryCategoryEntity();
        for (InquiryCategoryEntity b:brandEntityList)
        {
            b.setDel(true);
            goodsBrandEntity=em.merge(b);
        }
        for (InquiryCategoryEntity b:brandEntityList)
        {
            if (b.equals(goodsBrandEntity))
            {
                returned=2;
            }else
            {
                returned=1;
            }
        }
        return returned;
    }


}
