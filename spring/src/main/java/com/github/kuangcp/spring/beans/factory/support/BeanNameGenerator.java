package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.spring.beans.BeanDefinition;

/**
 * @author https://github.com/kuangcp on 2019-12-15 10:10
 */
public interface BeanNameGenerator {

  /**
   * Generate a bean name for the given bean definition.
   *
   * @param definition the bean definition to generate a name for
   * @param registry the bean definition registry that the given definition
   * is supposed to be registered with
   * @return the generated bean name
   */
  String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);
}
