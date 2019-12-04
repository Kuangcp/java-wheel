package com.github.kuangcp.spring.beans.factory;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.TreeService;
import com.github.kuangcp.spring.beans.exception.BeanDefinitionParseException;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.github.kuangcp.spring.core.io.ClassPathResource;
import com.github.kuangcp.spring.core.io.Resource;
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
    Resource source = new ClassPathResource("tree.xml");
    reader.loadDefinition(source);

    BeanDefinition definition = factory.getBeanDefinition("treeService");
    assertThat(definition.getClassName(), equalTo("com.github.kuangcp.spring.beans.TreeService"));
    TreeService bean = (TreeService) factory.getBean("treeService");
    assertNotNull(bean);
  }

  @Test
  public void testInvalidXML() {
    try {
      Resource source = new ClassPathResource("tree-invalid.xml");
      reader.loadDefinition(source);
    } catch (BeanDefinitionParseException e) {
      return;
    }

    Assert.fail("except BeanDefinitionParseException ");
  }
}