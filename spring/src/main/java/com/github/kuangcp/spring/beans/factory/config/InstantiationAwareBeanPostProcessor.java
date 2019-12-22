package com.github.kuangcp.spring.beans.factory.config;

import com.github.kuangcp.spring.beans.exception.BeansException;

/**
 * @author https://github.com/kuangcp on 2019-12-21 21:14
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

  Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

  boolean afterInstantiation(Object bean, String beanName) throws BeansException;

  void postProcessPropertyValues(Object bean, String beanName) throws BeansException;
}