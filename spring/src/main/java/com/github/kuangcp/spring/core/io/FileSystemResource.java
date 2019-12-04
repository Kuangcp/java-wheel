package com.github.kuangcp.spring.core.io;

import com.github.kuangcp.spring.util.Assert;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:18
 */
public class FileSystemResource implements Resource {

  private File file;

  public FileSystemResource(String path) {
    Assert.notNull(path, "file not found");

    this.file = new File(path);
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return new FileInputStream(this.file);
  }

  @Override
  public String getDescription() {
    return "file[" + this.file.getAbsolutePath() + "]";
  }
}
