package cn.gomro.mid.core.service.sms;

/**
 * Created by momo on 2016/5/31.
 */
public class SmsService {

    public static boolean send(String mobile, String msg) {
        return new LuosimaoSmsAdapter().send(mobile, msg);
    }
}
