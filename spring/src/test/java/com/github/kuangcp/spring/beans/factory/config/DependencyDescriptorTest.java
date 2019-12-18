package com.github.kuangcp.spring.beans.factory.config;

import com.github.kuangcp.spring.bean.TreeDao;
import com.github.kuangcp.spring.bean.TreeService;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.github.kuangcp.spring.core.io.ClassPathResource;
import com.github.kuangcp.spring.core.io.Resource;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-15 12:34
 */
public class DependencyDescriptorTest {

  @Test
  public void testResolveDependency() throws Exception {
    DefaultBeanFactory factory = new DefaultBeanFactory();
    XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
    Resource resource = new ClassPathResource("tree-with-annotation.xml");
    reader.loadBeanDefinitions(resource);

    Field f = TreeService.class.getDeclaredField("treeDao");
    DependencyDescriptor descriptor = new DependencyDescriptor(f, true);
    Object o = factory.resolveDependency(descriptor);
    Assert.assertTrue(o instanceof TreeDao);
  }
}