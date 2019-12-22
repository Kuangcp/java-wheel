package com.github.kuangcp.spring.beans.factory.config;

import com.github.kuangcp.spring.beans.exception.BeansException;

/**
 * @author https://github.com/kuangcp on 2019-12-21 21:14
 */
public interface BeanPostProcessor {

  Object beforeInitialization(Object bean, String beanName) throws BeansException;

  Object afterInitialization(Object bean, String beanName) throws BeansException;
}