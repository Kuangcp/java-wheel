package com.github.kuangcp.spring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:16
 */
public interface Resource {

  InputStream getInputStream() throws IOException;

  String getDescription();
}
