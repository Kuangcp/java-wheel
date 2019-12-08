package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.spring.beans.factory.config.RuntimeBeanReference;
import com.github.kuangcp.spring.beans.factory.config.TypedStringValue;

/**
 * @author https://github.com/kuangcp on 2019-12-08 12:43
 */
public class BeanDefinitionValueResolver {

  private final DefaultBeanFactory beanFactory;

  public BeanDefinitionValueResolver(DefaultBeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  public Object resolverValueIfNecessary(Object value) {
    if (value instanceof RuntimeBeanReference) {
      RuntimeBeanReference ref = (RuntimeBeanReference) value;
      String beanId = ref.getBeanId();
      return this.beanFactory.getBean(beanId);
    } else if (value instanceof TypedStringValue) {
      return value;
    } else {
      throw new RuntimeException("the value " + value + " has not implement");
    }
  }
}
