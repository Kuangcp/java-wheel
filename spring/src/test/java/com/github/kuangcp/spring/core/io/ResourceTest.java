package com.github.kuangcp.spring.core.io;

import static org.junit.Assert.assertNotNull;

import com.github.kuangcp.io.ResourceTool;
import java.io.InputStream;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:28
 */
public class ResourceTest {


  @Test
  public void testClassPath() throws Exception {
    Resource source = new ClassPathResource("tree.xml");

    InputStream inputStream = null;
    try {
      inputStream = source.getInputStream();
      assertNotNull(inputStream);
    } finally {
      ResourceTool.close(inputStream);
    }
  }

  // TODO not hard code
  @Test
  public void testFileSystem() throws Exception {
    Resource source = new FileSystemResource(
        "/home/kcp/Code/Java/java-wheel/spring/src/test/resources/tree.xml");

    InputStream inputStream = null;
    try {
      inputStream = source.getInputStream();
      assertNotNull(inputStream);
    } finally {
      ResourceTool.close(inputStream);
    }
  }

}