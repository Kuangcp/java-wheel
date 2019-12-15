package com.github.kuangcp.spring.context.annotation;

import com.github.kuangcp.spring.beans.factory.annotation.AnnotatedBeanDefinition;
import com.github.kuangcp.spring.beans.factory.support.GenericBeanDefinition;
import com.github.kuangcp.spring.core.type.AnnotationMetadata;

/**
 * @author https://github.com/kuangcp on 2019-12-15 10:12
 */
public class ScannedGenericBeanDefinition
    extends GenericBeanDefinition
    implements AnnotatedBeanDefinition {

  private final AnnotationMetadata metadata;

  public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
    super();
    this.metadata = metadata;
    this.setClassName(this.metadata.getClassName());
  }

  public final AnnotationMetadata getMetadata() {
    return this.metadata;
  }
}