package cn.gomro.mid.core.biz.mdb;


import cn.gomro.mid.core.search.lucene.GoodsSearch;
import cn.gomro.mid.core.search.lucene.SearchGoodsVO;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.logging.Logger;

/**
 * Created by momo on 16/5/16.
 */
@MessageDriven(name = "GoodsAnalyzerQueueMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue/gomroGoodsAnalyzerQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class GoodsAnalyzerQueueMDB implements MessageListener {

    private final static Logger LOGGER = Logger.getLogger(GoodsAnalyzerQueueMDB.class.toString());

    @Inject
    private GoodsSearch goodsSearch;

    @Override
    public void onMessage(Message message) {

        SearchGoodsVO svo = null;

        try {
            Thread.sleep(10000);

            if (message instanceof SearchGoodsVO) {
                svo = (SearchGoodsVO) message;
                goodsSearch.add(svo);
            } else {
                LOGGER.warning("GoodsAnalyzer Queue: message isn't ObjectMessage ");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("===>");
    }
}
