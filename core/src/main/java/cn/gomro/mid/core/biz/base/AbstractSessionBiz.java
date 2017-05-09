package cn.gomro.mid.core.biz.base;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by momo on 16/5/22.
 */
public abstract class AbstractSessionBiz {

    protected static final String PERSISTENCE_UNIT_NAME = "gomro";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    protected EntityManager em;

    @Resource
    protected SessionContext ctx;

}
