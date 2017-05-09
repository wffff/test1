package cn.gomro.mid.core.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yaodw on 2016/6/29.
 */
public class DateUtils {

    public static String getFormatedDate(Date date){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
    }

    public static Date getDateFromString(String datePattern){
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(datePattern);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0l);
        }
    }
}
