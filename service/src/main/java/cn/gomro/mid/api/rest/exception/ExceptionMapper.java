package cn.gomro.mid.api.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Created by admin on 2016/9/8.
 */
@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {
    final Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);
    public Response toResponse(Exception exception) {
        if(exception instanceof NotAllowedException){
            logger.error("Message : {} ",exception.getMessage());
            if(exception.getCause()!=null) {
                logger.error("Cause : {} ",exception.getCause().getMessage());
            }
            return Response.status(405).build();
        }else {
            logger.error("Message : {} ",exception.getMessage());
            if(exception.getCause()!=null) {
                logger.error("Cause : {} ",exception.getCause().getMessage());
            }
            return Response.status(500).build();
        }
    }
}