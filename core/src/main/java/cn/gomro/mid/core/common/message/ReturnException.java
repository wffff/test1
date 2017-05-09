package cn.gomro.mid.core.common.message;

import cn.gomro.mid.core.CoreException;

/**
 * Created by momo on 2016/7/31.
 */
public class ReturnException extends CoreException {

    public ReturnException() {
    }

    public ReturnException(String message) {
        super(message);
    }

    public ReturnException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReturnException(Throwable cause) {
        super(cause);
    }

    public ReturnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
