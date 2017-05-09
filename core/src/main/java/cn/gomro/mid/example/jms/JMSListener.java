package cn.gomro.mid.example.jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by momo on 16/3/23.
 */
public class JMSListener implements MessageListener {
    private static final Logger log = Logger.getLogger(JMSListener.class.getName());

    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/test";
    private static final String DEFAULT_USERNAME = "ivan";
    private static final String DEFAULT_PASSWORD = "echoshen";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "remote://127.0.0.1:4447";


    ConnectionFactory connectionFactory = null;
    Connection connection = null;
    Session session = null;
    MessageConsumer consumer = null;
    Destination destination = null;
    TextMessage message = null;
    Context context = null;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;

            try {
                log.info("<<<===========\nMSG: " + msg.getText());
            } catch (JMSException e) {
                log.severe(e.getMessage());
            }
        }
    }

    public void destory() throws JMSException {
        consumer.close();
        session.close();
        connection.close();

        log.info("监听停止");
    }

    private void init() throws Exception {

        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, PROVIDER_URL);
        env.put(Context.SECURITY_PRINCIPAL, DEFAULT_USERNAME);
        env.put(Context.SECURITY_CREDENTIALS, DEFAULT_PASSWORD);
        context = new InitialContext(env);

        connectionFactory = (ConnectionFactory) context.lookup(DEFAULT_CONNECTION_FACTORY);
        destination = (Destination) context.lookup(DEFAULT_DESTINATION);

        connection = connectionFactory.createConnection(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        consumer = session.createConsumer(destination);
        connection.start();

        log.info("监听开始");
    }

    public void consumeMessage() throws JMSException, Exception {
        init();
        consumer.setMessageListener(this);
    }

    public static void main(String[] args) throws Exception {

        JMSListener listener = new JMSListener();

        listener.consumeMessage();

//        try {
//            Thread.sleep(5000);
//        } catch (Exception e) {
//        }
//
//        listener.destory();
    }
}
