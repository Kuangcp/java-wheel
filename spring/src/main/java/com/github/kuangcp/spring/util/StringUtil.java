package com.github.kuangcp.spring.util;

/**
 * @author https://github.com/kuangcp on 2019-12-04 23:40
 */
public final class StringUtil {

  private StringUtil() {

  }

  public static boolean isBlank(final CharSequence cs) {
    int strLen;
    if (cs == null || (strLen = cs.length()) == 0) {
      return true;
    }

    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(cs.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public static boolean nonBlank(final CharSequence cs) {
    return !isBlank(cs);
  }
}
