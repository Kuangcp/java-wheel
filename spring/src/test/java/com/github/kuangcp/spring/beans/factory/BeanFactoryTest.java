package com.github.kuangcp.spring.beans.factory;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.exception.BeanDefinitionParseException;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.beans.factory.xml.XMLBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-01 16:43
 */
public class BeanFactoryTest {

  DefaultBeanFactory factory;
  XMLBeanDefinitionReader reader;

  @Before
  public void setUp() {
    factory = new DefaultBeanFactory();
    reader = new XMLBeanDefinitionReader(factory);
  }

  @Test
  public void testGetBean() {
    reader.loadDefinition("tree.xml");

    BeanDefinition definition = factory.getBeanDefinition("treeService");
    assertThat(definition.getClassName(), equalTo("com.github.kuangcp.spring.beans.TreeService"));
    Object bean = factory.getBean("treeService");
    assertNotNull(bean);
  }

  @Test
  public void testInvalidXML() {
    try {
      reader.loadDefinition("tree-invalid.xml");
    } catch (BeanDefinitionParseException e) {
      return;
    }

    Assert.fail("except BeanDefinitionParseException ");
  }
}