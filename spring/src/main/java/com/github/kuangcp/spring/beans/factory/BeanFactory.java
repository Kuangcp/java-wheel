package com.github.kuangcp.spring.beans.factory;

import com.github.kuangcp.spring.beans.BeanDefinition;

/**
 * @author https://github.com/kuangcp on 2019-12-01 16:39
 */
public interface BeanFactory {

  Object getBean(String beanId);

  BeanDefinition getBeanDefinition(String beanId);
}