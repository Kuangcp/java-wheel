package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.ConstructorArgument;
import com.github.kuangcp.spring.beans.PropertyValue;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author https://github.com/kuangcp on 2019-12-01 22:43
 */
@Getter
@Setter
@NoArgsConstructor
public class GenericBeanDefinition implements BeanDefinition {

  private String beanName;
  private String className;
  private Class<?> beanClass;
  private boolean singleton;
  private boolean prototype;

  private Map<String, PropertyValue> propertyValueMap = new ConcurrentHashMap<>();
  private ConstructorArgument constructorArgument = new ConstructorArgument();

  public GenericBeanDefinition(String id, String className) {
    this.beanName = id;
    this.className = className;
  }

  /**
   * Bean 类型
   */
  private String scope = SCOPE_DEFAULT;

  public void setScope(String scope) {
    if (Objects.isNull(scope)) {
      scope = SCOPE_DEFAULT;
    }

    this.scope = scope;
    this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
    this.prototype = SCOPE_PROTOTYPE.equals(scope);
  }

  @Override
  public boolean hasConstructorValue() {
    return constructorArgument.getArgumentCount() > 0;
  }

  @Override
  public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
    String className = getClassName();
    if (className == null) {
      return null;
    }
    Class<?> resolvedClass = classLoader.loadClass(className);
    this.beanClass = resolvedClass;
    return resolvedClass;
  }

  public Class<?> getBeanClass() throws IllegalStateException {
    if (this.beanClass == null) {
      throw new IllegalStateException(
          "Bean class name [" + this.getClassName()
              + "] has not been resolved into an actual Class");
    }
    return this.beanClass;
  }

  @Override
  public boolean hasBeanClass() {
    return this.beanClass != null;
  }
}