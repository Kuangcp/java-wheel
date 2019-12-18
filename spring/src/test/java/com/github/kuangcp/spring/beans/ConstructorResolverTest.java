package com.github.kuangcp.spring.beans;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.github.kuangcp.spring.bean.TreeService;
import com.github.kuangcp.spring.beans.factory.support.ConstructorResolver;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.github.kuangcp.spring.core.io.ClassPathResource;
import org.junit.Before;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-09 22:53
 */
public class ConstructorResolverTest {


  DefaultBeanFactory factory;
  XMLBeanDefinitionReader reader;

  @Before
  public void setUp() {
    factory = new DefaultBeanFactory();
    reader = new XMLBeanDefinitionReader(factory);
  }

  @Test
  public void testAutowireConstructor() throws Exception {
    reader.loadBeanDefinitions(new ClassPathResource("tree-with-constructor.xml"));

    BeanDefinition definition = factory.getBeanDefinition("treeService");
    assertThat(definition.getClassName(), equalTo("com.github.kuangcp.spring.bean.TreeService"));

    ConstructorResolver constructorResolver = new ConstructorResolver(factory);

    TreeService bean = (TreeService) constructorResolver.autowireConstructor(definition);
    assertNotNull(bean);
    assertNotNull(bean.getTreeDao());
    assertThat(bean.getVersion(), equalTo("version"));
  }
}
