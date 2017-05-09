package cn.gomro.mid.core.biz.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2016/8/2.
 */
@MappedSuperclass
@Cacheable
public abstract class AbstractEntity implements Serializable {

    @Column(columnDefinition = "boolean default false")
    protected Boolean del;
    @Column(columnDefinition = "TIMESTAMP(0) default null")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date last;
    @Column(columnDefinition = "TIMESTAMP(0) default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
     protected Date time;

    public AbstractEntity() {
    }

    public AbstractEntity(Boolean del, Date last, Date time) {
        this.del = del;
        this.last = last;
        this.time = time;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public Date getLast() {
        return this.last;
    }

    public void setLast(Date last) {
        this.last = last;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
