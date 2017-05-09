package cn.gomro.mid.core.common.utils.brandLex;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeremy on 2016/10/18.
 */
public class BrandDictUtil
{
    public static String generate(String brand){
        char[] ch = brand.toCharArray();

        String brandZH = "";
        String brandEN = "";

        List<String> zh = new ArrayList<String>();
        List<String> en = new ArrayList<String>();

        String pinyin = "";
        String dict = "";

        for (int i = 0; i < ch.length; i++){
            //System.out.println(ch[i]);
            if (StringAssistant.isChinese(ch[i])){
                zh.add(String.valueOf(ch[i]));
            }
            //处理例如: P&G WD-40 3M
            else if (StringAssistant.isLetter(ch[i]) || ch[i] == '-' || ch[i] == '&' || StringAssistant.isNumber(ch[i]))
            {
                en.add(String.valueOf(ch[i]));
            }
        }

        if (zh.size() !=0){
            for (String cn : zh){
                brandZH +=cn;
            }

            //拼音格式
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

            char[] py = brandZH.toCharArray();
            for (int i = 0; i < py.length; i++){
                try
                {
                    pinyin += PinyinHelper.toHanyuPinyinStringArray(py[i], format)[0]+" ";
                }
                catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination)
                {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                    System.out.println(py[i]);
                }
            }
        }

        if (en.size() != 0){
            for (String e : en){
                brandEN += e;
            }
            brandEN = brandEN.toLowerCase();
        }

        //去掉字符串末尾空格
        pinyin = pinyin.trim();

        if(pinyin.equals("")){
            pinyin = "null";
            brandZH = brand.toLowerCase();
        }
        if (brandEN.equals("")){
            brandEN = "null";
        }

        dict = brandZH+"/n/"+pinyin+"/"+brandEN;
        return dict;
    }
}
