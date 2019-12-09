package com.github.kuangcp.spring.beans;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author https://github.com/kuangcp on 2019-12-08 21:35
 */
@Data
public class ConstructorArgument {

  private List<ValueHolder> valueHolders = new ArrayList<>();

  public int getArgumentCount() {
    return CollectionUtils.size(valueHolders);
  }

  @Data
  @NoArgsConstructor
  public static class ValueHolder {

    private Object value;
    /**
     * TODO
     */
    private String type;
    /**
     * TODO
     */
    private Integer index;
    /**
     * TODO
     */
    private String name;

    public ValueHolder(Object value) {
      this.value = value;
    }
  }

  public ConstructorArgument() {

  }
}
