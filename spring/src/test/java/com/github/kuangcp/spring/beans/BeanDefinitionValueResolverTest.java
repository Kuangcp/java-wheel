package com.github.kuangcp.spring.beans;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.github.kuangcp.spring.bean.TreeDao;
import com.github.kuangcp.spring.beans.factory.config.RuntimeBeanReference;
import com.github.kuangcp.spring.beans.factory.support.BeanDefinitionValueResolver;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.github.kuangcp.spring.core.io.ClassPathResource;
import org.junit.Before;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-08 12:40
 */
public class BeanDefinitionValueResolverTest {

  DefaultBeanFactory factory;
  XMLBeanDefinitionReader reader;

  @Before
  public void setUp() {
    factory = new DefaultBeanFactory();
    reader = new XMLBeanDefinitionReader(factory);
  }

  @Test
  public void testGetBean() {
    reader.loadBeanDefinitions(new ClassPathResource("tree-with-property.xml"));
    BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(factory);

    RuntimeBeanReference ref = new RuntimeBeanReference("treeDao");
    Object value = valueResolver.resolverValueIfNecessary(ref);
    assertNotNull(value);
    assertTrue(value instanceof TreeDao);
  }
}
