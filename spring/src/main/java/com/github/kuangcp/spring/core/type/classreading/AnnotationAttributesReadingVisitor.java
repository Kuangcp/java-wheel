package com.github.kuangcp.spring.core.type.classreading;

import com.github.kuangcp.spring.core.annotation.AnnotationAttributes;
import java.util.Map;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

/**
 * @author https://github.com/kuangcp on 2019-12-14 20:35
 */
final class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

  private final String annotationType;

  private final Map<String, AnnotationAttributes> attributesMap;

  private AnnotationAttributes attributes = new AnnotationAttributes();


  public AnnotationAttributesReadingVisitor(
      String annotationType, Map<String, AnnotationAttributes> attributesMap) {
    super(SpringAsmInfo.ASM_VERSION);

    this.annotationType = annotationType;
    this.attributesMap = attributesMap;

  }

  @Override
  public final void visitEnd() {
    this.attributesMap.put(this.annotationType, this.attributes);
  }

  public void visit(String attributeName, Object attributeValue) {
    this.attributes.put(attributeName, attributeValue);
  }
}
