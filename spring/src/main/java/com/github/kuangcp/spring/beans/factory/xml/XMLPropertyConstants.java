package com.github.kuangcp.spring.beans.factory.xml;

/**
 * @author https://github.com/kuangcp on 2019-12-06 07:56
 */
public interface XMLPropertyConstants {

  String ID_ATTRIBUTE = "id";
  String CLASS_ATTRIBUTE = "class";
  String SCOPE_ATTRIBUTE = "scope";
  String PROPERTY_ATTRIBUTE = "property";
  String REF_ATTRIBUTE = "ref";
  String VALUE_ATTRIBUTE = "value";
  String NAME_ATTRIBUTE = "name";
  String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
  String TYPE_ATTRIBUTE = "type";

  String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
  String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
  String BASE_PACKAGE_ATTRIBUTE = "base-package";
}
