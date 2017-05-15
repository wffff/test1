package cn.gomro.mid.core.biz.student.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;
import com.alibaba.fastjson.JSON;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/5/9.
 */
@Entity
@Table(name = "student")
public class StudentEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public StudentEntity() {
    }

    public StudentEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
