package com.github.kuangcp.spring.util;

import java.util.Objects;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:24
 */
public final class Assert {

  private Assert() {
  }

  public static void notNull(Object target, String msg) {
    if (Objects.isNull(target)) {
      throw new IllegalArgumentException(msg);
    }
  }
}
