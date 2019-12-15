package com.github.kuangcp.spring.core.type;

import com.github.kuangcp.spring.core.annotation.AnnotationAttributes;
import java.util.Set;

/**
 * @author https://github.com/kuangcp on 2019-12-14 20:34
 */
public interface AnnotationMetadata extends ClassMetadata {

  Set<String> getAnnotationTypes();

  boolean hasAnnotation(String annotationType);

  AnnotationAttributes getAnnotationAttributes(String annotationType);
}