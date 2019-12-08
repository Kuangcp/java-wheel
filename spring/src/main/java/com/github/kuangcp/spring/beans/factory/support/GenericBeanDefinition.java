package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.ConstructorArgument;
import com.github.kuangcp.spring.beans.PropertyValue;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author https://github.com/kuangcp on 2019-12-01 22:43
 */
@Data
@NoArgsConstructor
public class GenericBeanDefinition implements BeanDefinition {

  private String id;

  private String className;

  private boolean singleton;

  private boolean prototype;

  private Map<String, PropertyValue> propertyValueMap = new ConcurrentHashMap<>();

  private ConstructorArgument constructorArgument = new ConstructorArgument();

  public GenericBeanDefinition(String id, String className) {
    this.id = id;
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
}