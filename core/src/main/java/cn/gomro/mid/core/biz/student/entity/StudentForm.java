package cn.gomro.mid.core.biz.student.entity;

import javax.ws.rs.FormParam;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/9.
 */
public class StudentForm implements Serializable{
    @FormParam(value = "name")
    private String name;
    public StudentForm() {
    }
    public StudentForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StudentForm{" +
                "name='" + name + '\'' +
                '}';
    }
}
