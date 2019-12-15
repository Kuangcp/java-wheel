package com.github.kuangcp.spring.beans.factory.config;

/**
 * @author https://github.com/kuangcp on 2019-12-06 00:20
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

  void setBeanClassLoader(ClassLoader loader);

  ClassLoader getBeanClassLoader();
}
