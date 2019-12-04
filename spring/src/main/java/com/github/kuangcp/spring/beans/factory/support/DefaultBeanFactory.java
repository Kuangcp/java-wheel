package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.aop.util.ClassUtil;
import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.factory.BeanFactory;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author https://github.com/kuangcp on 2019-12-01 16:40
 */
@Slf4j
@NoArgsConstructor
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {

  private Map<String, BeanDefinition> definitionMap = new ConcurrentHashMap<>();

  @Override
  public Object getBean(String beanId) {
    BeanDefinition definition = getBeanDefinition(beanId);
    if (Objects.isNull(definition)) {
      return null;
    }

    ClassLoader loader = ClassUtil.getDefaultClassLoader();
    String className = definition.getClassName();
    try {
      Class<?> target = loader.loadClass(className);
      Constructor<?> constructor = target.getConstructor();
      if (Objects.isNull(constructor)) {
        log.warn("not define empty constructor");
        return null;
      }
      return constructor.newInstance();
    } catch (Exception e) {
      log.error("", e);
    }
    return null;
  }

  @Override
  public BeanDefinition getBeanDefinition(String beanId) {
    return this.definitionMap.get(beanId);
  }

  @Override
  public void registerBeanDefinition(String beanId, BeanDefinition definition) {
    this.definitionMap.put(beanId, definition);
  }
}
