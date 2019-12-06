package com.github.kuangcp.spring.beans.factory.config;

/**
 * @author https://github.com/kuangcp on 2019-12-06 08:01
 */
public interface SingletonBeanRegistry {

  void registerSingleton(String beanId, Object singletonObj);

  Object getSingleton(String beanId);
}
