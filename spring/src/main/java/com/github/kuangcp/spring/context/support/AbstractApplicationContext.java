package com.github.kuangcp.spring.context.support;

import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.github.kuangcp.spring.context.ApplicationContext;
import com.github.kuangcp.spring.core.io.Resource;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:51
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

  private DefaultBeanFactory factory;

  abstract Resource getResourceByPath(String configFile);

  public AbstractApplicationContext(String configFile) {
    factory = new DefaultBeanFactory();
    XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
    Resource source = this.getResourceByPath(configFile);
    reader.loadDefinition(source);
  }

  // delegate
  @Override
  public Object getBean(String beanId) {
    return factory.getBean(beanId);
  }
}
