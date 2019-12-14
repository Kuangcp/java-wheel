package com.github.kuangcp.spring.core.io.support;

import com.github.kuangcp.spring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-14 17:20
 */
public class PackageResourceLoaderTest {

  @Test
  public void testGetResource() throws Exception {
    PackageResourceLoader loader = new PackageResourceLoader();
    Resource[] resources = loader.getResources("com.github.kuangcp.spring.bean");
    Assert.assertEquals(2, resources.length);
  }
}