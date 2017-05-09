package cn.gomro.mid.core.biz.common.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yaoo on 2016/10/25.
 */
@Entity
@Table(name = "t_pm_role")
public class RoleEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    public RoleEntity() {
    }


    public RoleEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RoleEntity(Boolean del, Date last, Date time, String name, String description) {
        super(del, last, time);
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
