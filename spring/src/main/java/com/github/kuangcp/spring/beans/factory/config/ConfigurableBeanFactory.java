package com.github.kuangcp.spring.beans.factory.config;

import com.github.kuangcp.spring.beans.factory.BeanFactory;

/**
 * @author https://github.com/kuangcp on 2019-12-06 00:20
 */
public interface ConfigurableBeanFactory extends BeanFactory {

  void setBeanClassLoader(ClassLoader loader);

  ClassLoader getBeanClassLoader();
}
