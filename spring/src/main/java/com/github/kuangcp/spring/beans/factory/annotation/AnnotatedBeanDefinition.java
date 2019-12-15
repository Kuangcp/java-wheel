package com.github.kuangcp.spring.beans.factory.annotation;

import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.core.type.AnnotationMetadata;

/**
 * @author https://github.com/kuangcp on 2019-12-15 10:11
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {

  AnnotationMetadata getMetadata();
}
