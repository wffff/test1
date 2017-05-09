package cn.gomro.mid.core.service.sms;

/**
 * Created by momo on 2016/5/31.
 */
public class GomroSmsMessageTemplate {

    public static String registerValidateCode(String code, String expire) {
        return "固买网提醒您本次注册的手机验证码为：" + code + " ，有效期至：" + expire + " ；如果非您本人操作，请忽略此信息或登录 www.gomro.cn 反馈 。【固买工业品网】";
    }

    public static String registerValidate(String msg) {
        return msg+"【固买工业品网】";
    }
}