package com.github.kuangcp.spring.context;

import static org.junit.Assert.assertNotNull;

import com.github.kuangcp.spring.beans.TreeService;
import com.github.kuangcp.spring.context.support.ClassPathXmlApplicationContext;
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
}