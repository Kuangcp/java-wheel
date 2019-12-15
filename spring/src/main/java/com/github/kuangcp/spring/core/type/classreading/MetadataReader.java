package com.github.kuangcp.spring.core.type.classreading;

import com.github.kuangcp.spring.core.io.Resource;
import com.github.kuangcp.spring.core.type.AnnotationMetadata;
import com.github.kuangcp.spring.core.type.ClassMetadata;

/**
 * @author https://github.com/kuangcp on 2019-12-15 09:38
 */
public interface MetadataReader {

  /**
   * Return the resource reference for the class file.
   */
  Resource getResource();

  /**
   * Read basic class metadata for the underlying class.
   */
  ClassMetadata getClassMetadata();

  /**
   * Read full annotation metadata for the underlying class,
   * including metadata for annotated methods.
   */
  AnnotationMetadata getAnnotationMetadata();
}
