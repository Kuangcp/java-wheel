package com.github.kuangcp.spring.beans.factory.config;

import com.github.kuangcp.spring.util.Assert;
import java.lang.reflect.Field;
import lombok.Getter;

/**
 * @author https://github.com/kuangcp on 2019-12-15 12:22
 */
@Getter
public class DependencyDescriptor {

  private Field field;
  private boolean required;

  public DependencyDescriptor(Field field, boolean required) {
    Assert.notNull(field, "Field must not be null");
    this.field = field;
    this.required = required;
  }

  public Class<?> getDependencyType() {
    if (this.field != null) {
      return field.getType();
    }
    // TODO constructor
    throw new RuntimeException("only support field dependency");
  }
}
