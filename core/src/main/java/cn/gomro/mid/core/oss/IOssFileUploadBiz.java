package cn.gomro.mid.core.oss;

import cn.gomro.mid.core.common.message.ReturnMessage;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Created by Adam on 2017/3/15.
 */
public interface IOssFileUploadBiz {
    ReturnMessage putObject(byte[] bytes, String path, String name, String suffix);
}
