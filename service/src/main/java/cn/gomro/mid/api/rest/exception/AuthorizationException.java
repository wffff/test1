package cn.gomro.mid.api.rest.exception;

/**
 * Created by Adam on 2017/4/12.
 */
public class AuthorizationException extends RuntimeException {
    private String response = ErrorCode.NOT_AUTHED.getMsg();
    public String getResponse(){
        return this.response;
    }
}
