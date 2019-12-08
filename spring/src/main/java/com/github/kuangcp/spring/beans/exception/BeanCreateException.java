package com.github.kuangcp.spring.beans.exception;

/**
 * @author https://github.com/kuangcp on 2019-12-08 16:48
 */
public class BeanCreateException extends RuntimeException {

  public BeanCreateException() {
  }

  public BeanCreateException(String message) {
    super(message);
  }
}
