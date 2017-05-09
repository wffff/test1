package cn.gomro.mid.core.common.message;

import java.io.Serializable;

/**
 * Created by momo on 16/5/23.
 */
public class ReturnMessage<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public ReturnMessage(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <V> ReturnMessage<V> message(int code, String msg, V data) {
        return new ReturnMessage<>(code, msg, data);
    }

    public static <V> ReturnMessage<V> success(String msg, V data) {
        return new ReturnMessage<>(ReturnCode.SUCCESS, msg, data);
    }


    public static <V> ReturnMessage<V> success(V data) {
        return new ReturnMessage<>(ReturnCode.SUCCESS, "", data);
    }

    public static ReturnMessage success(String msg) {
        return new ReturnMessage<>(ReturnCode.SUCCESS, msg, null);
    }

    public static <V> ReturnMessage<V> success() {
        return new ReturnMessage<>(ReturnCode.SUCCESS, "", null);
    }

    public static <V> ReturnMessage<V> failed(String msg, V data) {
        return new ReturnMessage<>(ReturnCode.FAILED, msg, data);
    }

    public static <V> ReturnMessage<V> failed(V data) {
        return new ReturnMessage<>(ReturnCode.FAILED, "", data);
    }

    public static ReturnMessage failed(String msg) {
        return new ReturnMessage<>(ReturnCode.FAILED, msg, null);
    }

    public static <V> ReturnMessage<V> failed() {
        return new ReturnMessage<>(ReturnCode.FAILED, "", null);
    }
}