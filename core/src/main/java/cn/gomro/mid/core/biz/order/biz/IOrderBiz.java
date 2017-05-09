package cn.gomro.mid.core.biz.order.biz;

import cn.gomro.mid.core.biz.order.entity.vo.OrderEntity;
import cn.gomro.mid.core.common.message.ReturnMessage;

/**
 * Created by Administrator on 2017/4/25.
 */
public interface IOrderBiz {
    ReturnMessage saveOrder(OrderEntity order);
}
