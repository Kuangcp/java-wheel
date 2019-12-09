package com.github.kuangcp.spring.beans;

import java.util.Map;

/**
 * @author https://github.com/kuangcp on 2019-12-01 16:36
 */
public interface BeanDefinition {

  String SCOPE_SINGLETON = "singleton";
  String SCOPE_PROTOTYPE = "prototype";
  String SCOPE_DEFAULT = "";

  String getId();

  String getClassName();

  boolean isSingleton();

  boolean isPrototype();

  String getScope();

  void setScope(String scope);

  /**
   * @return propertyName -> PropertyValue
   */
  Map<String, PropertyValue> getPropertyValueMap();

  ConstructorArgument getConstructorArgument();

  boolean hasConstructorValue();
}
