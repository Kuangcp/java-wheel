package com.github.kuangcp.spring.context.support;

import com.github.kuangcp.spring.core.io.ClassPathResource;
import com.github.kuangcp.spring.core.io.Resource;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:01
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

  @Override
  Resource getResourceByPath(String configFile) {
    return new ClassPathResource(configFile);
  }

  public ClassPathXmlApplicationContext(String configFile) {
    super(configFile);
  }
}
