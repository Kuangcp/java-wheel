package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.aop.util.ClassUtil;
import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.PropertyValue;
import com.github.kuangcp.spring.beans.exception.BeanCreationException;
import com.github.kuangcp.spring.beans.factory.config.BeanPostProcessor;
import com.github.kuangcp.spring.beans.factory.config.ConfigurableBeanFactory;
import com.github.kuangcp.spring.beans.factory.config.DependencyDescriptor;
import com.github.kuangcp.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
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
  private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
  private ClassLoader loader;

  @Override
  public Object getBean(String beanName) {
    BeanDefinition definition = this.getBeanDefinition(beanName);
    if (Objects.isNull(definition)) {
      return null;
    }

    if (definition.isSingleton()) {
      Object bean = this.getSingleton(beanName);
      if (Objects.isNull(bean)) {
        bean = this.createBean(definition);
        this.registerSingleton(beanName, bean);
      }

      return bean;
    }

    return this.createBean(definition);
  }

  private Object createBean(BeanDefinition definition) {
    Object bean = this.instantiateBean(definition);
    this.populateBean(bean, definition);
    this.initializeBean(bean, definition);
    return bean;
  }

  private void initializeBean(Object bean, BeanDefinition definition) {
    for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
      if (beanPostProcessor != null) {
        beanPostProcessor.beforeInitialization(bean, definition.getId());
      }
    }

    for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
      if (beanPostProcessor != null) {
        beanPostProcessor.afterInitialization(bean, definition.getId());
      }
    }
  }

  private void populateBean(Object bean, BeanDefinition definition) {
    // annotation inject
    for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
      if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
        ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
            .postProcessPropertyValues(bean, definition.getId());
      }
    }

    // field inject
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
      throw new BeanCreationException("failed to create bean: " + definition.getClassName());
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

      for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
        if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
          ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
              .beforeInstantiation(target, definition.getId());
        }
      }

      Object result = constructor.newInstance();

      for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
        if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
          ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
              .afterInstantiation(result, definition.getId());
        }
      }
      return result;
    } catch (Exception e) {
      log.error("", e);
    }
    return null;
  }

  @Override
  public BeanDefinition getBeanDefinition(String beanName) {
    return this.definitionMap.get(beanName);
  }

  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition definition) {
    this.definitionMap.put(beanName, definition);
  }

  @Override
  public void setBeanClassLoader(ClassLoader loader) {
    this.loader = loader;
  }

  @Override
  public ClassLoader getBeanClassLoader() {
    return Objects.nonNull(this.loader) ? this.loader : ClassUtil.getDefaultClassLoader();
  }

  @Override
  public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
    beanPostProcessors.add(postProcessor);
  }

  @Override
  public List<BeanPostProcessor> getBeanPostProcessors() {
    return beanPostProcessors;
  }

  /**
   * resolveBeanClass must invoke before resolveBeanClass
   */
  @Override
  public Object resolveDependency(DependencyDescriptor descriptor) {
    Class<?> typeToMatch = descriptor.getDependencyType();
    for (BeanDefinition bd : this.definitionMap.values()) {
      this.resolveBeanClass(bd);
      Class<?> beanClass = bd.getBeanClass();
      if (typeToMatch.isAssignableFrom(beanClass)) {
        return this.getBean(bd.getId());
      }
    }
    return null;
  }

  public void resolveBeanClass(BeanDefinition bd) {
    if (!bd.hasBeanClass()) {
      try {
        bd.resolveBeanClass(this.getBeanClassLoader());
      } catch (ClassNotFoundException e) {
        throw new RuntimeException("can't load class:" + bd.getClassName());
      }
    }
  }
}
