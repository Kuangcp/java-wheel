package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.spring.beans.exception.BeanCreationException;
import com.github.kuangcp.spring.beans.factory.config.SingletonBeanRegistry;
import com.github.kuangcp.spring.util.Assert;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * @author https://github.com/kuangcp on 2019-12-06 08:03
 */
@Slf4j
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

  /**
   * 已经完成初始化的 单例Bean
   */
  private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

  /**
   * 进入实例化阶段 但未初始化的 提前曝光的 单例Bean
   */
  private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

  /**
   * Names of beans that are currently in creation.
   */
  private final Set<String> singletonsCurrentlyInCreation =
      Collections.newSetFromMap(new ConcurrentHashMap<>(16));

  @Override
  public void registerSingleton(String beanName, Object singletonObject) {
    Assert.notNull(beanName, "beanName may not be null");
    synchronized (this.singletonObjects) {
      Object oldObject = this.singletonObjects.get(beanName);
      if (Objects.nonNull(oldObject)) {
        throw new IllegalStateException("Could not register object [" + singletonObject
            + "] under bean id " + beanName + ", there is already exist " + oldObject);
      }
    }

    this.addSingleton(beanName, singletonObject);
  }

  /**
   * Add the given singleton object to the singleton cache of this factory.
   * <p>To be called for eager registration of singletons.
   *
   * @param beanName the name of the bean
   * @param singletonObject the singleton object
   */
  protected void addSingleton(String beanName, Object singletonObject) {
    synchronized (this.singletonObjects) {
      this.singletonObjects.put(beanName, singletonObject);
      this.earlySingletonObjects.remove(beanName);
    }
  }

  @Override
  public Object getSingleton(String beanName) {
    return getSingleton(beanName, true);
  }

  protected Object getSingleton(String beanName, boolean allowEarlyReference) {
    Object singletonObject = this.singletonObjects.get(beanName);
    if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
      synchronized (this.singletonObjects) {
        singletonObject = this.earlySingletonObjects.get(beanName);
        if (Objects.isNull(singletonObject) && allowEarlyReference) {
          throw new BeanCreationException("early bean not found");
        }
      }
    }
    return singletonObject;
  }

  public boolean isSingletonCurrentlyInCreation(String beanName) {
    return this.singletonsCurrentlyInCreation.contains(beanName);
  }

  public void addCurrentlyInCreation(String beanName) {
    this.singletonsCurrentlyInCreation.add(beanName);
  }

  public void addEarlyInCreation(String beanName, Object value) {
    this.earlySingletonObjects.put(beanName, value);
  }
}
