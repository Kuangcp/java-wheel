package com.github.kuangcp.spring.beans.factory.xml;

import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.context.annotation.ClassPathBeanDefinitionScannerTest;
import com.github.kuangcp.spring.core.io.ClassPathResource;
import com.github.kuangcp.spring.core.io.Resource;
import com.github.kuangcp.spring.stereotype.Component;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-15 10:23
 */
public class XMLBeanDefinitionReaderTest {

  @Test
  public void testParseScannedBean() {
    DefaultBeanFactory factory = new DefaultBeanFactory();
    XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
    Resource resource = new ClassPathResource("tree-with-annotation.xml");
    reader.loadDefinition(resource);
    String annotation = Component.class.getName();

    ClassPathBeanDefinitionScannerTest.assertBeanDefinition(factory, annotation);
  }
}