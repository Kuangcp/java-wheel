package com.github.kuangcp.spring.beans;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author https://github.com/kuangcp on 2019-12-08 21:35
 */
@Data
public class ConstructorArgument {

  private List<ValueHolder> valueHolders = new ArrayList<>();


  @Data
  @NoArgsConstructor
  public static class ValueHolder {

    private Object value;
    private String type;
    private String name;

    public ValueHolder(Object value) {
      this.value = value;
    }
  }

  public ConstructorArgument() {

  }
}
