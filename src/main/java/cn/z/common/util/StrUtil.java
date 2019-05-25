package cn.z.common.util;

/** copyFrom-->org.apache.commons.lang3*/
public class StrUtil {
  /**
   * The empty String {@code ""}.
   * @since 2.0
   */
  public static final String EMPTY = "";

  // Empty checks
  //-----------------------------------------------------------------------
  /**
   * <p>Checks if a CharSequence is empty ("") or null.</p>
   *
   * <pre>
   * StringUtils.isEmpty(null)      = true
   * StringUtils.isEmpty("")        = true
   * StringUtils.isEmpty(" ")       = false
   * StringUtils.isEmpty("bob")     = false
   * StringUtils.isEmpty("  bob  ") = false
   * </pre>
   *
   * <p>NOTE: This method changed in Lang version 2.0.
   * It no longer trims the CharSequence.
   * That functionality is available in isBlank().</p>
   *
   * @param cs  the CharSequence to check, may be null
   * @return {@code true} if the CharSequence is empty or null
   * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
   */
  public static boolean isEmpty(final CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

  /**
   * <p>Checks if a CharSequence is not empty ("") and not null.</p>
   *
   * <pre>
   * StringUtils.isNotEmpty(null)      = false
   * StringUtils.isNotEmpty("")        = false
   * StringUtils.isNotEmpty(" ")       = true
   * StringUtils.isNotEmpty("bob")     = true
   * StringUtils.isNotEmpty("  bob  ") = true
   * </pre>
   *
   * @param cs  the CharSequence to check, may be null
   * @return {@code true} if the CharSequence is not empty and not null
   * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
   */
  public static boolean isNotEmpty(final CharSequence cs) {
    return !isEmpty(cs);
  }


  /**
   * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
   *
   * <pre>
   * StringUtils.isBlank(null)      = true
   * StringUtils.isBlank("")        = true
   * StringUtils.isBlank(" ")       = true
   * StringUtils.isBlank("bob")     = false
   * StringUtils.isBlank("  bob  ") = false
   * </pre>
   *
   * @param cs  the CharSequence to check, may be null
   * @return {@code true} if the CharSequence is null, empty or whitespace
   * @since 2.0
   * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
   */
  public static boolean isBlank(final CharSequence cs) {
    int strLen;
    if (cs == null || (strLen = cs.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if (Character.isWhitespace(cs.charAt(i)) == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
   *
   * <pre>
   * StringUtils.isNotBlank(null)      = false
   * StringUtils.isNotBlank("")        = false
   * StringUtils.isNotBlank(" ")       = false
   * StringUtils.isNotBlank("bob")     = true
   * StringUtils.isNotBlank("  bob  ") = true
   * </pre>
   *
   * @param cs  the CharSequence to check, may be null
   * @return {@code true} if the CharSequence is
   *  not empty and not null and not whitespace
   * @since 2.0
   * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
   */
  public static boolean isNotBlank(final CharSequence cs) {
    return !isBlank(cs);
  }

  // Defaults
  //-----------------------------------------------------------------------
  /**
   * <p>Returns either the passed in String,
   * or if the String is {@code null}, an empty String ("").</p>
   *
   * <pre>
   * StringUtils.defaultString(null)  = ""
   * StringUtils.defaultString("")    = ""
   * StringUtils.defaultString("bat") = "bat"
   * </pre>
   *
   * @see Object//my//Utils#toString(Object)
   * @see String#valueOf(Object)
   * @param str  the String to check, may be null
   * @return the passed in String, or the empty String if it
   *  was {@code null}
   */
  public static String defaultString(final String str) {
    return str == null ? EMPTY : str;
  }

  /**
   * <p>Returns either the passed in String, or if the String is
   * {@code null}, the value of {@code defaultStr}.</p>
   *
   * <pre>
   * StringUtils.defaultString(null, "NULL")  = "NULL"
   * StringUtils.defaultString("", "NULL")    = ""
   * StringUtils.defaultString("bat", "NULL") = "bat"
   * </pre>
   *
   * @see Object//my//Utils#toString(Object,String)
   * @see String#valueOf(Object)
   * @param str  the String to check, may be null
   * @param defaultStr  the default String to return
   *  if the input is {@code null}, may be null
   * @return the passed in String, or the default if it was {@code null}
   */
  public static String defaultString(final String str, final String defaultStr) {
    return str == null ? defaultStr : str;
  }

  /**
   * <p>Returns either the passed in CharSequence, or if the CharSequence is
   * whitespace, empty ("") or {@code null}, the value of {@code defaultStr}.</p>
   *
   * <pre>
   * StringUtils.defaultIfBlank(null, "NULL")  = "NULL"
   * StringUtils.defaultIfBlank("", "NULL")    = "NULL"
   * StringUtils.defaultIfBlank(" ", "NULL")   = "NULL"
   * StringUtils.defaultIfBlank("bat", "NULL") = "bat"
   * StringUtils.defaultIfBlank("", null)      = null
   * </pre>
   * @param <T> the specific kind of CharSequence
   * @param str the CharSequence to check, may be null
   * @param defaultStr  the default CharSequence to return
   *  if the input is whitespace, empty ("") or {@code null}, may be null
   * @return the passed in CharSequence, or the default
   * @see String//my//Utils#defaultString(String, String)
   */
  public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
    return isBlank(str) ? defaultStr : str;
  }

  /**
   * <p>Returns either the passed in CharSequence, or if the CharSequence is
   * empty or {@code null}, the value of {@code defaultStr}.</p>
   *
   * <pre>
   * StringUtils.defaultIfEmpty(null, "NULL")  = "NULL"
   * StringUtils.defaultIfEmpty("", "NULL")    = "NULL"
   * StringUtils.defaultIfEmpty(" ", "NULL")   = " "
   * StringUtils.defaultIfEmpty("bat", "NULL") = "bat"
   * StringUtils.defaultIfEmpty("", null)      = null
   * </pre>
   * @param <T> the specific kind of CharSequence
   * @param str  the CharSequence to check, may be null
   * @param defaultStr  the default CharSequence to return
   *  if the input is empty ("") or {@code null}, may be null
   * @return the passed in CharSequence, or the default
   * @see String//my//Utils#defaultString(String, String)
   */
  public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr) {
    return isEmpty(str) ? defaultStr : str;
  }

}
