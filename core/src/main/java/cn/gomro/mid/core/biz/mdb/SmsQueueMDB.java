package cn.gomro.mid.core.biz.mdb;


import cn.gomro.mid.core.service.sms.SmsMessage;
import cn.gomro.mid.core.service.sms.SmsService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.util.logging.Logger;

/**
 * Created by momo on 16/4/1.
 */
@MessageDriven(name = "SmsQueueMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue/gomroSmsQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class SmsQueueMDB implements MessageListener {

    private final static Logger LOGGER = Logger.getLogger(SmsQueueMDB.class.toString());

    @Override
    public void onMessage(Message message) {

        ObjectMessage msg = null;

        try {
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;

                if (msg != null) {

                    SmsMessage sms = (SmsMessage) msg.getObject();

                    LOGGER.info("Received sms message from queue, Mobile: " + sms.getMobile());

                    boolean statues = SmsService.send(sms.getMobile(), sms.getMsg());

                    if (statues) LOGGER.info("Mobile: " + sms.getMobile() + " sms send success");
                    else LOGGER.warning("Mobile: " + sms.getMobile() + " sms send failed");
                }


            } else {
                LOGGER.warning("Message of wrong type: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
