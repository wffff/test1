package cn.gomro.mid.core.biz.common.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by momo on 2016/8/3.
 */
@Entity
@Table(name = "t_area_2")
@Cacheable
public class AreaEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50, nullable = false, unique = true)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private AreaEntity parent;
    @OneToMany(targetEntity = AreaEntity.class, cascade = {CascadeType.ALL}, mappedBy = "parent")
    private List<AreaEntity> childs;

    public AreaEntity() {
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

    public AreaEntity getParent() {
        return parent;
    }

    public void setParent(AreaEntity parent) {
        this.parent = parent;
    }

    public List<AreaEntity> getChilds() {
        return childs;
    }

    public void setChilds(List<AreaEntity> childs) {
        this.childs = childs;
    }
}
