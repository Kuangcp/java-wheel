package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.spring.beans.BeanDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author https://github.com/kuangcp on 2019-12-01 22:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericBeanDefinition implements BeanDefinition {

  private String id;

  private String className;

}
