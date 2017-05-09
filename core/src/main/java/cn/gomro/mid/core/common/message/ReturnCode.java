package cn.gomro.mid.core.common.message;

/**
 * Created by momo on 2016/7/28.
 */
public class ReturnCode {

    public static final String FAILED_SYSTEM = "系统处理错误！";
    public static final String DATA_FORMAT_ERROR = "数据格式不正确！";
    public static String DELETE_FAILED = "删除失败！";
    public static String DELETE_SUCCESS = "删除成功！";

    public static final int SUCCESS = 0;
    public static final int FAILED = -1;

    public static boolean isSuccess(int code) {
        return code >= SUCCESS;
    }

    public static boolean isFailed(int code) {
        return code <= FAILED;
    }
}
