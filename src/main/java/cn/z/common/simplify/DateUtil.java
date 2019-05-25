package cn.z.common.simplify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateUtil {
  String FULL_DASH="yyyy-MM-dd HH:mm:ss";
  String FULL_SLASH="yyyy/MM/dd HH:mm:ss";
  String DATE_HM_DASH="yyyy-MM-dd HH:mm";
  String DATE_HM_SLASH="yyyy/MM/dd HH:mm";
  String DATE_DASH="yyyy-MM-dd";
  String DATE_SLASH="yyyy/MM/dd";

  static String fmt(String fmt, Date date) {
    return new SimpleDateFormat(fmt).format(date);
  }

  static Date parse(String fmt, String dest) {
    try {
      return new SimpleDateFormat(fmt).parse(dest);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
}
