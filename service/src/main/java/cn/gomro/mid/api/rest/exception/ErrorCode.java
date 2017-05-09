package cn.gomro.mid.api.rest.exception;

/**
 * Created by Adam on 2017/4/12.
 */
public class ErrorCode {

    public static class NOT_AUTHED {
        private static String msg = "权限不足！";
        public static String getMsg() {
            return msg;
        }
    }
}
