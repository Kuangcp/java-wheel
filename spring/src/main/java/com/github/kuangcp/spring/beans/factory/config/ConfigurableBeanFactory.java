package com.github.kuangcp.spring.beans.factory.config;

import java.util.List;

/**
 * @author https://github.com/kuangcp on 2019-12-06 00:20
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

  void setBeanClassLoader(ClassLoader loader);

  ClassLoader getBeanClassLoader();

  void addBeanPostProcessor(BeanPostProcessor postProcessor);

  List<BeanPostProcessor> getBeanPostProcessors();
}
