package cn.gomro.mid.core.biz.inquiry.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;

/**
 * Created by Adam on 2017/4/17.
 */
@Entity
@Table(name = "t_company_org")
public class TempCorpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public TempCorpEntity() {
    }

    public TempCorpEntity(String name) {
        this.name = name;
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
}