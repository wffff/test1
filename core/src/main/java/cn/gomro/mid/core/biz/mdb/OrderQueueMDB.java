package cn.gomro.mid.core.biz.mdb;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.logging.Logger;

/**
 * Created by momo on 16/5/16.
 */
@MessageDriven(name = "OrderQueueMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue/gomroOrderQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class OrderQueueMDB implements MessageListener {

    private final static Logger LOGGER = Logger.getLogger(OrderQueueMDB.class.toString());

//    @Inject
//    private OrderServices orderBean;

    @Override
    public void onMessage(Message message) {

        ObjectMessage msg = null;

        try {
            Thread.sleep(10000);

            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
//                if (msg != null) {
//                    OrderEntity order = (OrderEntity) msg.getObject();
//                    orderBean.addOrder(order);
//                    LOGGER.info("Order Queue: ok, " + order.getName() + " ** " + getClass().getName());
//                } else {
//                    LOGGER.warning("Order Queue: msg is null");
//                }
            } else {
                LOGGER.warning("Order Queue: message isn't ObjectMessage");
            }
//        } catch (JMSException e) {
            //throw new RuntimeException(e);
//            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("===>");
    }
}
