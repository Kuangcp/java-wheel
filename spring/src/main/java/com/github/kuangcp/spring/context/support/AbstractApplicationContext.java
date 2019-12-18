package com.github.kuangcp.spring.context.support;

import com.github.kuangcp.aop.util.ClassUtil;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.github.kuangcp.spring.context.ApplicationContext;
import com.github.kuangcp.spring.core.io.Resource;
import java.util.Objects;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:51
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

  private DefaultBeanFactory factory;
  // TODO loader 设计存在问题
  private ClassLoader loader;

  abstract Resource getResourceByPath(String configFile);

  public AbstractApplicationContext(String configFile) {
    factory = new DefaultBeanFactory();
    XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
    Resource source = this.getResourceByPath(configFile);
    reader.loadBeanDefinitions(source);
    factory.setBeanClassLoader(this.getBeanClassLoader());
  }

  // delegate
  @Override
  public Object getBean(String beanId) {
    return factory.getBean(beanId);
  }

  public void setBeanClassLoader(ClassLoader loader) {
    this.loader = loader;
  }

  public ClassLoader getBeanClassLoader() {
    return Objects.nonNull(this.loader) ? this.loader : ClassUtil.getDefaultClassLoader();
  }
}
