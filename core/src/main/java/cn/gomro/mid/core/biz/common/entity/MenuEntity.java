package cn.gomro.mid.core.biz.common.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/12/26.
 */
@Entity
@Table(name = "t_pm_menu")
public class MenuEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "parent_id")
    private Integer parentId;
    private String url;
    @Column(name = "image_url")
    private String imageUrl;
    private Integer sort;
    private Integer status;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "parentId")
    List<MenuEntity> children;

    public MenuEntity() {
    }

    public MenuEntity(String name, Integer parentId, String url, String imageUrl, Integer sort, Integer status) {
        this.name = name;
        this.parentId = parentId;
        this.url = url;
        this.imageUrl = imageUrl;
        this.sort = sort;
        this.status = status;
    }

    public MenuEntity(Boolean del, Date last, Date time, String name, Integer parentId, String url, String imageUrl, Integer sort, Integer status) {
        super(del, last, time);
        this.name = name;
        this.parentId = parentId;
        this.url = url;
        this.imageUrl = imageUrl;
        this.sort = sort;
        this.status = status;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<MenuEntity> getChildren() {
        return children;
    }

    public void setChildren(List<MenuEntity> children) {
        this.children = children;
    }
}
