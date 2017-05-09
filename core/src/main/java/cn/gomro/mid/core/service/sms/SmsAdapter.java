package cn.gomro.mid.core.service.sms;

/**
 * Created by momo on 2016/5/31.
 */
public interface SmsAdapter {

    boolean send(String mobile, String msg);

}
