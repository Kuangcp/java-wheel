package com.github.kuangcp.spring.core.type.classreading;

import com.github.kuangcp.spring.core.io.Resource;
import com.github.kuangcp.spring.core.type.AnnotationMetadata;
import com.github.kuangcp.spring.core.type.ClassMetadata;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import org.springframework.asm.ClassReader;

/**
 * @author https://github.com/kuangcp on 2019-12-15 09:39
 */
@Getter
public class SimpleMetadataReader implements MetadataReader {

  private final Resource resource;
  private final ClassMetadata classMetadata;
  private final AnnotationMetadata annotationMetadata;

  public SimpleMetadataReader(Resource resource) throws IOException {
    InputStream is = new BufferedInputStream(resource.getInputStream());
    ClassReader classReader;

    try {
      classReader = new ClassReader(is);
    } finally {
      is.close();
    }

    AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
    classReader.accept(visitor, ClassReader.SKIP_DEBUG);

    this.annotationMetadata = visitor;
    this.classMetadata = visitor;
    this.resource = resource;
  }
}
