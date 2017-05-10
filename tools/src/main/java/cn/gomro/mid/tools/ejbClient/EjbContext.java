package cn.gomro.mid.tools.ejbClient;


import cn.gomro.mid.example.remoteEJB.HelloWorld;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Adam on 2016/12/8.
 */
public class EjbContext {

    private static Context context;

    private EjbContext() {
    }

    //测试通过版本
    public static Context initialContext() {
        if (context == null) {
            Properties properties = getProperties("jndi.properties");
            try {
                context = new InitialContext(properties);
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return context;
    }

    private static Properties getProperties(String propertiesName) {
        InputStream inputStream;
        ClassLoader cl = EjbContext.class.getClassLoader();
        if (cl != null) {
            inputStream = cl.getResourceAsStream(propertiesName);
        } else {
            inputStream = ClassLoader.getSystemResourceAsStream(propertiesName);
        }
        Properties dbProps = new Properties();
        try {
            dbProps.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dbProps;
    }


    public static void main(String[] args) {
        try {
            Context initialContext = EjbContext.initialContext();
            HelloWorld helloWorldRemote = (HelloWorld) initialContext.lookup("gomro.mid/core/HelloWorldBean!cn.gomro.mid.example.remoteEJB.HelloWorldRemote");
            String sayHello = helloWorldRemote.sayHello();
            System.out.println(sayHello);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
