package com.github.kuangcp.spring.context;

import static org.junit.Assert.assertNotNull;

import com.github.kuangcp.spring.beans.TreeService;
import com.github.kuangcp.spring.context.support.ClassPathXmlApplicationContext;
import com.github.kuangcp.spring.context.support.FileSystemXmlApplicationContext;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:05
 */
public class ApplicationContextTest {

  @Test
  public void testGetBean() {
    ApplicationContext context = new ClassPathXmlApplicationContext("tree.xml");
    TreeService bean = (TreeService) context.getBean("treeService");
    assertNotNull(bean);
  }

  @Test
  public void testGetBeanFromFileSystem() {
    ApplicationContext context = new FileSystemXmlApplicationContext("/home/kcp/Code/Java/java-wheel/spring/src/test/resources/tree.xml");
    TreeService bean = (TreeService) context.getBean("treeService");
    assertNotNull(bean);
  }
}