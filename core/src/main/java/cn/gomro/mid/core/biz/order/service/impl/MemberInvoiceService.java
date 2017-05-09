package cn.gomro.mid.core.biz.order.service.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionService;
import cn.gomro.mid.core.biz.order.entity.MemberInvoicesEntity;
import cn.gomro.mid.core.biz.order.service.IMemberInvoiceService;
import cn.gomro.mid.core.common.message.ReturnMessage;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by adam on 2017/4/27.
 */
@Stateless
public class MemberInvoiceService extends AbstractSessionService<MemberInvoicesEntity> implements IMemberInvoiceService {

    @Override
    public ReturnMessage getName(Integer id) {
        return null;
    }

    @Override
    public ReturnMessage<List<MemberInvoicesEntity>> getItemsPaged(MemberInvoicesEntity entity, Integer page, Integer size) {
        return null;
    }

    @Override
    public ReturnMessage<List<MemberInvoicesEntity>> listExistInvoice(MemberInvoicesEntity entity) {
        String jpql = "select i from MemberInvoicesEntity i where i.mid=" + entity.getMid() +
                " and i.invoicetypeid=" + entity.getInvoicetypeid() +
                " and i.invoicename='" + StringUtils.trim(entity.getInvoicename()) + "'" +
                " and i.invoiceaddress='" + StringUtils.trim(entity.getInvoiceaddress()) + "'" +
                " and i.invoicephone='" + StringUtils.trim(entity.getInvoicephone()) + "'" +
                " and i.invoiceaccount='" + StringUtils.trim(entity.getInvoiceaccount()) + "'" +
                " and i.invoicetax='" + StringUtils.trim(entity.getInvoicetax()) + "'" +
                " and i.invoicebank='" + StringUtils.trim(entity.getInvoicebank()) +"'"+
                " and i.invoicesendaddress='" + StringUtils.trim(entity.getInvoicesendaddress()) +"'"+
                " and i.invoicesendcontact='" + StringUtils.trim(entity.getInvoicesendcontact()) +"'"+
                " and i.invoicesendphone='" + StringUtils.trim(entity.getInvoicesendphone())+"'";
        Query query = em.createQuery(jpql,MemberInvoicesEntity.class);
        List<MemberInvoicesEntity> resultList = query.getResultList();
        return ReturnMessage.success(resultList);
    }
}
