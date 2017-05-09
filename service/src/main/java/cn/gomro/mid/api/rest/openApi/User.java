package cn.gomro.mid.api.rest.openApi;

import javax.ws.rs.FormParam;
import java.io.Serializable;
import java.util.List;

/**
 * Created by adam on 2017/4/27.
 */
public class User implements Serializable {
    @FormParam(value="name")
    private String name;
    @FormParam(value="age")
    private Integer age;
    @FormParam( "pet")
    private List<Pet> pet;

    public User() {
    }

    public User(String name, Integer age, List<Pet> pet) {
        this.name = name;
        this.age = age;
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", pet=" + pet +
                '}';
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Pet> getList() {
        return pet;
    }

    public void setList(List<Pet> pet) {
        this.pet = pet;
    }
}
