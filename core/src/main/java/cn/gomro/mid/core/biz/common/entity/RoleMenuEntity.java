package cn.gomro.mid.core.biz.common.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2016/12/26.
 */
@Entity
@Table(name = "t_pm_role_menu")
public class RoleMenuEntity extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private RoleEntity role;
    @OneToOne
    @JoinColumn(name = "menu_id",referencedColumnName = "id")
    private MenuEntity menu;

    public RoleMenuEntity() {
    }

    public RoleMenuEntity(RoleEntity role, MenuEntity menu) {
        this.role = role;
        this.menu = menu;
    }

    public RoleMenuEntity(Boolean del, Date last, Date time, RoleEntity role, MenuEntity menu) {
        super(del, last, time);
        this.role = role;
        this.menu = menu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }
}
