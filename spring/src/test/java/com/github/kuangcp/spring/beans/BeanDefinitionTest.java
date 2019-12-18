package com.github.kuangcp.spring.beans;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.github.kuangcp.spring.bean.TreeService;
import com.github.kuangcp.spring.beans.ConstructorArgument.ValueHolder;
import com.github.kuangcp.spring.beans.factory.config.RuntimeBeanReference;
import com.github.kuangcp.spring.beans.factory.config.TypedStringValue;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.github.kuangcp.spring.core.io.ClassPathResource;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-08 11:55
 */
public class BeanDefinitionTest {

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

    BeanDefinition definition = factory.getBeanDefinition("treeService");
    assertThat(definition.getClassName(), equalTo("com.github.kuangcp.spring.bean.TreeService"));
    TreeService bean = (TreeService) factory.getBean("treeService");
    assertNotNull(bean);

    Map<String, PropertyValue> propertyValueMap = definition.getPropertyValueMap();
    assertThat(propertyValueMap.size(), equalTo(1));

    {
      PropertyValue treeDao = propertyValueMap.get("treeDao");
      assertNotNull(treeDao);
      assertTrue(treeDao.getValue() instanceof RuntimeBeanReference);
    }
  }
  
  @Test
  public void testConstructorArgs() throws Exception {
    reader.loadBeanDefinitions(new ClassPathResource("tree-with-constructor.xml"));

    BeanDefinition definition = factory.getBeanDefinition("treeService");
    assertThat(definition.getClassName(), equalTo("com.github.kuangcp.spring.bean.TreeService"));

    ConstructorArgument argument = definition.getConstructorArgument();
    List<ValueHolder> valueHolders = argument.getValueHolders();
    assertThat(valueHolders.size(), equalTo(2));
    RuntimeBeanReference reference = (RuntimeBeanReference) valueHolders.get(0).getValue();
    assertThat(reference.getBeanId(), equalTo("treeDao"));
    TypedStringValue version  = (TypedStringValue) valueHolders.get(1).getValue();
    assertThat(version.getValue(), equalTo("version"));
  }
}