package cn.gomro.mid.core.common.utils.brandLex;

import java.util.regex.Pattern;

public class StringAssistant {
    //单个字符 判断是否中文
//    public static boolean isChinese(char c){
//
//        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
//
//        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
//                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
//                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
//                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
//                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
//                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
//            return true;
//        }
//
//        return false;
//    }

    public static boolean isChinese(char c) {
        String regex = "^[\u4E00-\u9FA5]+$";
        boolean isZH = Pattern.matches(regex, String.valueOf(c));
        if (isZH) {
            return true;
        }
        return false;
    }


    //是否字母(大小写不区别)
    public static boolean isLetter(char l) {
        String regex = "[a-zA-Z]";
        boolean isEN = Pattern.matches(regex, String.valueOf(l));
        {
            if (isEN) {
                return true;
            }
        }
        return false;
    }

    //是否为数字
    public static boolean isNumber(char n) {
        String regex = "[0-9]*";
        boolean isNo = Pattern.matches(regex, String.valueOf(n));
        {
            if (isNo) {
                return true;
            }
        }
        return false;
    }

}
