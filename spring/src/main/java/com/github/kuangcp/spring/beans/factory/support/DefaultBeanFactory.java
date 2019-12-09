package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.aop.util.ClassUtil;
import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.PropertyValue;
import com.github.kuangcp.spring.beans.exception.BeanCreateException;
import com.github.kuangcp.spring.beans.factory.config.ConfigurableBeanFactory;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

/**
 * @author https://github.com/kuangcp on 2019-12-01 16:40
 */
@Slf4j
@NoArgsConstructor
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
    implements ConfigurableBeanFactory, BeanDefinitionRegistry {

  private Map<String, BeanDefinition> definitionMap = new ConcurrentHashMap<>();
  private ClassLoader loader;

  @Override
  public Object getBean(String beanId) {
    BeanDefinition definition = this.getBeanDefinition(beanId);
    if (Objects.isNull(definition)) {
      return null;
    }

    if (definition.isSingleton()) {
      Object bean = this.getSingleton(beanId);
      if (Objects.isNull(bean)) {
        bean = this.createBean(definition);
        this.registerSingleton(beanId, bean);
      }

      return bean;
    }

    return this.createBean(definition);
  }

  private Object createBean(BeanDefinition definition) {
    Object bean = instantiateBean(definition);
    this.populateBean(bean, definition);
    return bean;
  }

  private void populateBean(Object bean, BeanDefinition definition) {
    Map<String, PropertyValue> propertyValueMap = definition.getPropertyValueMap();
    if (Objects.isNull(propertyValueMap) || propertyValueMap.isEmpty()) {
      return;
    }

    BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
    try {
      for (Entry<String, PropertyValue> entry : propertyValueMap.entrySet()) {
        String propertyName = entry.getKey();
        Object propertyValue = entry.getValue().getValue();
        Object resolvedValue = valueResolver.resolverValueIfNecessary(propertyValue);

        // inject bean as property to target bean
        BeanUtils.setProperty(bean, propertyName, resolvedValue);
      }
    } catch (Exception e) {
      throw new BeanCreateException("failed to create bean: " + definition.getClassName());
    }
  }

  private Object instantiateBean(BeanDefinition definition) {
    if (definition.hasConstructorValue()) {
      ConstructorResolver constructorResolver = new ConstructorResolver(this);
      return constructorResolver.autowireConstructor(definition);
    }

    ClassLoader loader = this.getBeanClassLoader();
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

  @Override
  public void setBeanClassLoader(ClassLoader loader) {
    this.loader = loader;
  }

  @Override
  public ClassLoader getBeanClassLoader() {
    return Objects.nonNull(this.loader) ? this.loader : ClassUtil.getDefaultClassLoader();
  }
}
