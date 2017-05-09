package cn.gomro.mid.core.biz.inquiry.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Adam on 2017/3/23.
 */
@Entity
@Table(name = "t_inquiry_category_2")
public class InquiryCategoryEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public InquiryCategoryEntity() {
    }

    public InquiryCategoryEntity(Boolean del, Date last, Date time, String name) {
        super(del, last, time);
        this.name = name;
    }

    @Override
    public String toString() {
        return "InquiryCategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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
