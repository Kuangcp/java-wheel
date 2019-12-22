package com.github.kuangcp.spring.beans.factory;

/**
 * @author https://github.com/kuangcp on 2019-12-01 16:39
 */
public interface BeanFactory {

  Object getBean(String beanName);
}