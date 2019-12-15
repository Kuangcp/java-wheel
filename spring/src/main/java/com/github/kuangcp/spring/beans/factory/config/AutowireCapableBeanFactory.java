package com.github.kuangcp.spring.beans.factory.config;

import com.github.kuangcp.spring.beans.factory.BeanFactory;

/**
 * @author https://github.com/kuangcp on 2019-12-15 12:23
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

  Object resolveDependency(DependencyDescriptor descriptor);
}

