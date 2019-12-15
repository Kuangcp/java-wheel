package com.github.kuangcp.spring.context.annotation;

import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.core.annotation.AnnotationAttributes;
import com.github.kuangcp.spring.core.type.AnnotationMetadata;
import com.github.kuangcp.spring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-15 10:15
 */

public class ClassPathBeanDefinitionScannerTest {

  String basePackages = "com.github.kuangcp.spring.bean";

  @Test
  public void testParseScannedBean(){
    DefaultBeanFactory factory = new DefaultBeanFactory();
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
    scanner.doScan(basePackages);

    String annotation = Component.class.getName();
    assertBeanDefinition(factory, annotation);
  }

  public static void assertBeanDefinition(DefaultBeanFactory factory, String annotation) {
    {
      BeanDefinition bd = factory.getBeanDefinition("treeService");
      Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
      ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
      AnnotationMetadata amd = sbd.getMetadata();


      Assert.assertTrue(amd.hasAnnotation(annotation));
      AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
      Assert.assertEquals("treeService", attributes.get("value"));
    }

    {
      BeanDefinition bd = factory.getBeanDefinition("treeDao");
      Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
      ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
      AnnotationMetadata amd = sbd.getMetadata();
      Assert.assertTrue(amd.hasAnnotation(annotation));
    }
  }
}