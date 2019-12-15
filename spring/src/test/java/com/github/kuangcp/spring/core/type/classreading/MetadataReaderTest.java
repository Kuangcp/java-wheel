package com.github.kuangcp.spring.core.type.classreading;

import com.github.kuangcp.spring.bean.TreeService;
import com.github.kuangcp.spring.core.annotation.AnnotationAttributes;
import com.github.kuangcp.spring.core.io.ClassPathResource;
import com.github.kuangcp.spring.core.type.AnnotationMetadata;
import com.github.kuangcp.spring.stereotype.Component;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-15 09:37
 */
public class MetadataReaderTest {

  String classPath = "com/github/kuangcp/spring/bean/TreeService.class";

  @Test
  public void testGetMetadata() throws IOException {
    ClassPathResource resource = new ClassPathResource(classPath);

    MetadataReader reader = new SimpleMetadataReader(resource);
    //注意：不需要单独使用ClassMetadata
    //ClassMetadata clzMetadata = reader.getClassMetadata();
    AnnotationMetadata amd = reader.getAnnotationMetadata();

    String annotation = Component.class.getName();

    Assert.assertTrue(amd.hasAnnotation(annotation));
    AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
    Assert.assertEquals("treeService", attributes.get("value"));

    //注：下面对class metadata的测试并不充分
    Assert.assertFalse(amd.isAbstract());
    Assert.assertFalse(amd.isFinal());
    Assert.assertEquals(TreeService.class.getName(), amd.getClassName());
  }
}
