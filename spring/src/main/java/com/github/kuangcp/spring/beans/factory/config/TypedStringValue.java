package com.github.kuangcp.spring.beans.factory.config;

import lombok.Data;

/**
 * @author https://github.com/kuangcp on 2019-12-09 01:20
 */
@Data
public class TypedStringValue {

  private final String value;

  public TypedStringValue(String value) {
    this.value = value;
  }
}
