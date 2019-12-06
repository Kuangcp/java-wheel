package com.github.kuangcp.spring.beans.factory;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.TreeService;
import com.github.kuangcp.spring.beans.exception.BeanDefinitionParseException;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.github.kuangcp.spring.core.io.ClassPathResource;
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
    reader.loadDefinition(new ClassPathResource("tree.xml"));

    BeanDefinition definition = factory.getBeanDefinition("treeService");
    assertThat(definition.getClassName(), equalTo("com.github.kuangcp.spring.beans.TreeService"));
    TreeService bean = (TreeService) factory.getBean("treeService");
    assertNotNull(bean);
  }

  @Test
  public void testInvalidXML() {
    try {
      reader.loadDefinition(new ClassPathResource("tree-invalid.xml"));
    } catch (BeanDefinitionParseException e) {
      return;
    }

    Assert.fail("except BeanDefinitionParseException ");
  }
  
  @Test
  public void testGetBeanWithScope() throws Exception {
    reader.loadDefinition(new ClassPathResource("tree.xml"));
    BeanDefinition definition = factory.getBeanDefinition("treeService");
    assertTrue(definition.isSingleton());
    assertFalse(definition.isPrototype());
    assertThat(definition.getScope(), equalTo(BeanDefinition.SCOPE_DEFAULT));

    assertThat(definition.getClassName(), equalTo("com.github.kuangcp.spring.beans.TreeService"));
    TreeService bean = (TreeService) factory.getBean("treeService");
    assertNotNull(bean);

    TreeService secondBean = (TreeService) factory.getBean("treeService");
    assertEquals(bean, secondBean);
  }
}