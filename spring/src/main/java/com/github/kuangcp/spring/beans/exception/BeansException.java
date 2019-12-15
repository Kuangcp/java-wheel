package com.github.kuangcp.spring.beans.exception;

/**
 * @author https://github.com/kuangcp on 2019-12-15 10:14
 */
public class BeansException extends RuntimeException {

  private static final long serialVersionUID = -697409736087745963L;

  public BeansException(String msg) {
    super(msg);
  }

  public BeansException(String msg, Throwable cause) {
    super(msg, cause);
  }
}