package com.github.kuangcp.spring.context.support;

import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.github.kuangcp.spring.context.ApplicationContext;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:01
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {

  private DefaultBeanFactory factory;

  public ClassPathXmlApplicationContext(String configFile) {
    factory = new DefaultBeanFactory();
    XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
    reader.loadDefinition(configFile);
  }

  // delegate
  @Override
  public Object getBean(String beanId) {
    return factory.getBean(beanId);
  }
}
