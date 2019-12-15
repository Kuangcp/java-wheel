package com.github.kuangcp.spring.core.type.classreading;

import com.github.kuangcp.spring.bean.TreeService;
import com.github.kuangcp.spring.core.annotation.AnnotationAttributes;
import com.github.kuangcp.spring.core.io.ClassPathResource;
import com.github.kuangcp.spring.stereotype.Component;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.asm.ClassReader;

/**
 * @author https://github.com/kuangcp on 2019-12-14 17:34
 */
public class ClassReaderTest {

  String classPath = "com/github/kuangcp/spring/bean/TreeService.class";

  @Test
  public void testGetClassMetaData() throws IOException {

    ClassPathResource resource = new ClassPathResource(classPath);
    ClassReader reader = new ClassReader(resource.getInputStream());

    ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();

    reader.accept(visitor, ClassReader.SKIP_DEBUG);

    Assert.assertFalse(visitor.isAbstract());
    Assert.assertFalse(visitor.isInterface());
    Assert.assertFalse(visitor.isFinal());
    Assert.assertEquals(TreeService.class.getName(), visitor.getClassName());
    Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
    Assert.assertEquals(0, visitor.getInterfaceNames().length);
  }

  @Test
  public void testGetAnnotation() throws Exception {
    ClassPathResource resource = new ClassPathResource(classPath);
    ClassReader reader = new ClassReader(resource.getInputStream());

    AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();

    reader.accept(visitor, ClassReader.SKIP_DEBUG);

    String annotation = Component.class.getName();
    Assert.assertTrue(visitor.hasAnnotation(annotation));

    AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);

    Assert.assertEquals("treeService", attributes.get("value"));
  }
}
