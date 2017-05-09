package cn.gomro.mid.core.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by momo on 16/8/17.
 */
public class ResourcesUtils {

    private static final String RESOURCES_SETTING = "settings.properties";

    public static String loadGoodsIndexDir() throws IOException {

        PropertiesUtils.OS os = PropertiesUtils.detectOS();
        String propname = "";
        switch (os) {
            case WIN:
                propname = "GOODS_INDEX_DIR_DEBUG_WIN";
                break;
            case LINUX:
                propname = "GOODS_INDEX_DIR_RELEASE_LINUX";
                break;
            case MAC:
                propname = "GOODS_INDEX_DIR_DEBUG_MAC";
                break;
        }

        InputStream input = ResourcesUtils.class.getClassLoader().getResourceAsStream(RESOURCES_SETTING);
        Properties properties = loadProperties(input);

        return properties.get(propname).toString().trim();
    }

    public static String loadLexiconDir() throws IOException {

        PropertiesUtils.OS os = PropertiesUtils.detectOS();
        String propname = "";
        switch (os) {
            case WIN:
                propname = "LEXICON_DIR_DEBUG_WIN";
                break;
            case LINUX:
                propname = "LEXICON_DIR_RELEASE_LINUX";
                break;
            case MAC:
                propname = "LEXICON_DIR_DEBUG_MAC";
                break;
        }

        InputStream input = ResourcesUtils.class.getClassLoader().getResourceAsStream(RESOURCES_SETTING);
        Properties properties = loadProperties(input);

        return properties.get(propname).toString().trim();
    }

    public static String loadProLexiconDir() throws IOException {

        PropertiesUtils.OS os = PropertiesUtils.detectOS();
        String propname = "";
        switch (os) {
            case WIN:
                propname = "PROLEX_DIR_DEBUG_WIN";
                break;
            case LINUX:
                propname = "PROLEX_DIR_RELEASE_LINUX";
                break;
            case MAC:
                propname = "PROLEX_DIR_DEBUG_MAC";
                break;
        }

        InputStream input = ResourcesUtils.class.getClassLoader().getResourceAsStream(RESOURCES_SETTING);
        Properties properties = loadProperties(input);

        return properties.get(propname).toString().trim();
    }

    public static Properties loadProperties(InputStream input) throws IOException {
        Properties properties = new Properties();
        properties.load(input);
        return properties;
    }

    public static Object getProperty(Properties properties, Object key) {
        return properties.get(key);
    }

    public static String getPropertyValue(String propertiesName,String key){
        InputStream input = ResourcesUtils.class.getClassLoader().getResourceAsStream(propertiesName);
        Properties properties = null;
        try {
            properties = loadProperties(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

}
