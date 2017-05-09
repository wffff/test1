package cn.gomro.mid.core.service.sms;

import java.io.Serializable;

/**
 * Created by momo on 2016/5/31.
 */
public class SmsMessage implements Serializable {

    private String mobile;
    private String msg;

    public SmsMessage() {
    }

    public SmsMessage(String mobile, String msg) {
        this.mobile = mobile;
        this.msg = msg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
