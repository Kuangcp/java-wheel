package com.github.kuangcp.spring.beans.factory;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-01 16:43
 */
public class BeanFactoryTest {

  @Test
  public void testGetBean() {
    BeanFactory factory = new DefaultBeanFactory("tree.xml");

    BeanDefinition definition = factory.getBeanDefinition("treeService");
    assertThat(definition.getClassName(), equalTo("com.github.kuangcp.spring.beans.TreeService"));
    Object bean = factory.getBean("treeService");
    assertNotNull(bean);
  }
}