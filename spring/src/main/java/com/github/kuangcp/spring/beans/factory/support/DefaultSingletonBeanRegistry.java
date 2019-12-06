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
  public void registerSingleton(String beanId, Object singletonObj) {
    Assert.notNull(beanId, "bean id may not be null");
    Object oldObject = this.singletonObjects.get(beanId);
    if (Objects.nonNull(oldObject)) {
      throw new IllegalStateException("Could not register object [" + singletonObj
          + "] under bean id " + beanId + ", there is already exist " + oldObject);
    }
    this.singletonObjects.put(beanId, singletonObj);
  }

  @Override
  public Object getSingleton(String beanId) {
    return this.singletonObjects.get(beanId);
  }
}
