package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.spring.beans.BeanDefinition;

/**
 * @author https://github.com/kuangcp on 2019-12-04 22:44
 */
public interface BeanDefinitionRegistry {

  BeanDefinition getBeanDefinition(String beanName);

  void registerBeanDefinition(String beanName, BeanDefinition definition);
}
