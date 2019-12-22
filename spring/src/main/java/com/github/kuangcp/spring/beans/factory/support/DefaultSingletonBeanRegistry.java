package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.spring.beans.factory.config.SingletonBeanRegistry;
import com.github.kuangcp.spring.util.Assert;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author https://github.com/kuangcp on 2019-12-06 08:03
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

  private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

  @Override
  public void registerSingleton(String beanName, Object singletonObj) {
    Assert.notNull(beanName, "beanName may not be null");
    Object oldObject = this.singletonObjects.get(beanName);
    if (Objects.nonNull(oldObject)) {
      throw new IllegalStateException("Could not register object [" + singletonObj
          + "] under bean id " + beanName + ", there is already exist " + oldObject);
    }
    this.singletonObjects.put(beanName, singletonObj);
  }

  @Override
  public Object getSingleton(String beanName) {
    return this.singletonObjects.get(beanName);
  }
}
