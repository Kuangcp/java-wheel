package com.github.kuangcp.spring.context.support;

import com.github.kuangcp.spring.core.io.FileSystemResource;
import com.github.kuangcp.spring.core.io.Resource;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:42
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

  @Override
  Resource getResourceByPath(String configFile) {
    return new FileSystemResource(configFile);
  }

  public FileSystemXmlApplicationContext(String configFile) {
    super(configFile);
  }
}
