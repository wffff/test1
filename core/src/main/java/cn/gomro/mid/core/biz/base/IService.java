package cn.gomro.mid.core.biz.base;

import cn.gomro.mid.core.common.message.ReturnMessage;

import java.io.Serializable;
import java.util.List;

/**
 * Created by momo on 2016/6/1.
 */
public interface IService<T> extends Serializable {

    ReturnMessage getName(Integer id);

    ReturnMessage<List<T>> getItemsPaged(T entity, Integer page, Integer size);

    ReturnMessage<T> getItem(Integer id);

    ReturnMessage<T> addItem(T entity);

    ReturnMessage editItem(T entity);

    ReturnMessage delItem(T entity);
}
