package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.factory.BeanFactory;

/**
 * @author https://github.com/kuangcp on 2019-12-01 16:40
 */
public class DefaultBeanFactory implements BeanFactory {

  public DefaultBeanFactory(String configFile) {
  }

  @Override
  public Object getBean(String id) {
    return null;
  }

  @Override
  public BeanDefinition getBeanDefinition(String beanId) {
    return null;
  }
}
