package com.github.kuangcp.spring.core.io;

import com.github.kuangcp.aop.util.ClassUtil;
import com.github.kuangcp.spring.util.Assert;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:17
 */
public class ClassPathResource implements Resource {

  private String path;
  private ClassLoader loader;

  public ClassPathResource(String path) {
    this(path, null);
  }

  public ClassPathResource(String path, ClassLoader loader) {
    Assert.notNull(path, "file not found");
    this.path = path;
    this.loader = Objects.nonNull(loader) ? loader : ClassUtil.getDefaultClassLoader();
  }

  @Override
  public InputStream getInputStream() throws IOException {
    InputStream input = this.loader.getResourceAsStream(this.path);
    if (Objects.isNull(input)) {
      throw new FileNotFoundException(this.path + " can not be opened");
    }
    return input;
  }

  @Override
  public String getDescription() {
    return this.path;
  }
}
