package com.github.kuangcp.spring.beans;

import lombok.Data;

/**
 * @author https://github.com/kuangcp on 2019-12-08 11:58
 */
@Data
public class PropertyValue {

  private final String name;
  private final Object value;
  private Object convertedValue;

  public PropertyValue(String name, Object value) {
    this.name = name;
    this.value = value;
  }
}
