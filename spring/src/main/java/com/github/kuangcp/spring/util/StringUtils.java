package com.github.kuangcp.spring.util;

import java.util.Objects;

/**
 * @author https://github.com/kuangcp on 2019-12-04 23:40
 */
public final class StringUtils {

  private StringUtils() {

  }

  public static boolean hasLength(final CharSequence cs) {
    return Objects.nonNull(cs) && cs.length() > 0;
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
