package cn.gomro.mid.core.oss;

import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.OssUtils;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import java.io.InputStream;

/**
 * Created by Adam on 2017/3/15.
 */
@Stateless
public class OssFileUpload implements IOssFileUploadBizRemote,IOssFileUploadBizLocal {

    final Logger logger = Logger.getLogger(OssFileUpload.class);

    @Override
    public ReturnMessage putObject(byte[] bytes, String path, String name, String suffix) {
        try {
            String s = OssUtils.putObject(bytes, path, name, suffix);
            return ReturnMessage.success(s);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }
}
