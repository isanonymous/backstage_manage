package cn.z.common.simplify;

import cn.z.common.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class DBMap extends HashMap<String,Object> {
  private static final long serialVersionUID = -1686791724060119662L;

  public boolean getStrIsNotEmpty(String key) {
    return StrUtil.isNotEmpty(getStr(key));
  }

  public String getStr(String k) {
    Object v = get(k);
    return v == null ? "" : v.toString();
  }
  public int getInt(String k) {
    return Integer.parseInt(getStr(k));
  }
  public long getLong(String k) {
    return Long.parseLong(getStr(k));
  }

  public DBMap(HttpServletRequest req) {
    Map<String, String[]> map = req.getParameterMap();
    for(String key : map.keySet()){
      String val = req.getParameter(key);
      if ("current".equals(key) || "size".equals(key))  //这两个放page里
        continue;
      put(key, val);
    }
  }
  // /**
  //  * 如果{@link HttpServletRequest#getParameter(String name)}返回值为null或空白字符, 就不会添加到map中
  //  * @param req
  //  * @param excludeParamOnValueIsBlank
  //  */
  // public DBMap(HttpServletRequest req, boolean excludeParamOnValueIsBlank) {
  //   Map<String, String[]> map = req.getParameterMap();
  //   for(String key : map.keySet()){
  //     String val = req.getParameter(key);
  //     if ("current".equals(key) || "size".equals(key))  //这两个放page里
  //       continue;
  //     if (excludeParamOnValueIsBlank && StrUtil.isBlank(val))
  //       continue;
  //     put(key, val);
  //   }
  // }
  public DBMap() {
  }

  /**
   * 注意确认类型,无法转换的会报错
   * @param key Map的key
   * @return
   */
  @SuppressWarnings("unchecked")
  public <T> T getValue(String key) {
    Object val = super.get(key);
    return (T) val;
  }
}
