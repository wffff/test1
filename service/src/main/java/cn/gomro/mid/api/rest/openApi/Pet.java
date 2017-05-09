package cn.gomro.mid.api.rest.openApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.FormParam;
import java.io.Serializable;

/**
 * Created by adam on 2017/4/27.
 */
public class Pet implements Serializable {

    @FormParam(value="name")
    private String name;
    @FormParam(value="age")
    private Integer age;

    public Pet() {
    }

    /*public static String valueOf(String pet) {
        return pet;
    }*/
    public static Pet valueOf(String pet) {
        return JSON.parseObject(pet, Pet.class);
    }

    public static void main(String[] args) {
        Pet abc = new Pet("abc", 12);
        String s = JSONObject.toJSONString(abc);
        System.out.println(s);
    }

    public Pet(String name, Integer age) {
        this.name = name;
        this.age = age;
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



    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
