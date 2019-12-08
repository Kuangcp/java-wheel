package com.github.kuangcp.spring.beans.factory.config;

import lombok.Data;

/**
 * @author https://github.com/kuangcp on 2019-12-08 12:00
 */
@Data
public class RuntimeBeanReference {

  private final String beanId;

  public RuntimeBeanReference(String beanId) {
    this.beanId = beanId;
  }
}
