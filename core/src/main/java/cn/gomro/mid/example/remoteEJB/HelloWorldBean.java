package cn.gomro.mid.example.remoteEJB;

/**
 * Created by adam on 16-11-15.
 */

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Local(HelloWorld.class)
@Remote(HelloWorldRemote.class)
public class HelloWorldBean implements HelloWorldRemote {
    public String sayHello() {
        return "Hello World!";
    }
}