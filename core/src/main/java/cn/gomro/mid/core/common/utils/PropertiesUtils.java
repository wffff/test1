package cn.gomro.mid.core.common.utils;

import java.util.Properties;

/**
 * Created by momo on 16/8/17.
 */
public class PropertiesUtils {

    public enum OS {
        UNKNOWN, WIN, LINUX, MAC
    }

    public static OS detectOS() {

        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");

        if (os.toUpperCase().startsWith("WIN")) return OS.WIN;
        else if (os.toUpperCase().startsWith("LINUX")) return OS.LINUX;
        else if (os.toUpperCase().startsWith("MAC")) return OS.MAC;
        else return OS.UNKNOWN;
    }
}
