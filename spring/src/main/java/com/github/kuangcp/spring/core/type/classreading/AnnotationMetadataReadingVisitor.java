package com.github.kuangcp.spring.core.type.classreading;

import com.github.kuangcp.spring.core.annotation.AnnotationAttributes;
import com.github.kuangcp.spring.core.type.AnnotationMetadata;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;

/**
 * @author https://github.com/kuangcp on 2019-12-14 20:33
 */
public class AnnotationMetadataReadingVisitor
    extends ClassMetadataReadingVisitor
    implements AnnotationMetadata {

  private final Set<String> annotationSet = new LinkedHashSet<>(4);
  private final Map<String, AnnotationAttributes> attributeMap = new LinkedHashMap<>(4);

  public AnnotationMetadataReadingVisitor() {

  }

  @Override
  public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {

    String className = Type.getType(desc).getClassName();
    this.annotationSet.add(className);
    return new AnnotationAttributesReadingVisitor(className, this.attributeMap);
  }

  public Set<String> getAnnotationTypes() {
    return this.annotationSet;
  }

  public boolean hasAnnotation(String annotationType) {
    return this.annotationSet.contains(annotationType);
  }

  public AnnotationAttributes getAnnotationAttributes(String annotationType) {
    return this.attributeMap.get(annotationType);
  }
}
