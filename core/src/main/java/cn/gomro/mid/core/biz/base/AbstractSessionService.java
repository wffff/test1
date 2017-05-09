package cn.gomro.mid.core.biz.base;

import cn.gomro.mid.core.common.Constants;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by momo on 16/5/22.
 */
public abstract class AbstractSessionService<T extends AbstractEntity> {

    final Logger logger = Logger.getLogger(AbstractSessionService.class);

    protected static final String PERSISTENCE_UNIT_NAME = "gomro";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    protected EntityManager em;

    @Resource
    protected SessionContext ctx;

    public ReturnMessage<T> getItem(Integer id) {

        if (id != null) {
            Class<T> cls;
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            cls = (Class) params[0];

            try {
                Map<String, Object> hints = new HashMap<>();
                hints.put(Constants.JPA_CACHEABLE, true);
                T entity = em.find(cls, id, hints);
                return ReturnMessage.success(entity);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return ReturnMessage.failed();
    }

    public ReturnMessage<T> addItem(T entity) {

        try {
            Date date = new Date();
            entity.setLast(date);
            entity.setTime(date);
            entity.setDel(false);
            em.persist(entity);
            em.flush();
            return ReturnMessage.success(entity);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    public ReturnMessage editItem(T entity) {
        try {
            entity.setLast(new Date());
            em.merge(entity);
            em.flush();
            return ReturnMessage.success();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed();
    }

    public ReturnMessage delItem(T entity) {
        try {
            entity.setDel(true);
            entity.setLast(new Date());
            em.merge(entity);
            em.flush();
            return ReturnMessage.success(ReturnCode.DELETE_SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.failed(ReturnCode.DELETE_FAILED);
    }
}
