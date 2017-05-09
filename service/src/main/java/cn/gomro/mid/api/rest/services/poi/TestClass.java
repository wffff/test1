package cn.gomro.mid.api.rest.services.poi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yaodw on 2016/7/20.
 */
public class TestClass {
    public static void main(String[] args) {

        String str = "38";
        String string = "38.22";
        double v1 = Double.parseDouble(str);
        Double aDouble = Double.valueOf(string);
        System.out.println(v1+"---->>"+aDouble);

       /* Pattern p = Pattern.compile(" (\\d+)\\.?[0]* ");
        String s = "1.0";
        String[] split = s.split("\\.");
        System.out.println(split.length);*/

    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("(\\d*\\.)?\\d+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
