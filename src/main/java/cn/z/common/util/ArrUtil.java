package cn.z.common.util;

import java.util.Arrays;

public class ArrUtil {
  public static void outln(Object obj) {
    if (obj.getClass().isArray()) {
      System.out.println("\n----ArrUtil.isArray---: " + Arrays.toString((Object[])obj));
      return;
    }
    System.out.println("\n----ArrUtil.obj---: "+ obj);
  }

  public static boolean isEmpty(Object[] objs) {
    return objs==null || objs.length==0;
  }

  public static boolean isNotEmpty(Object[] objs) {
    return !isEmpty(objs);
  }
}
