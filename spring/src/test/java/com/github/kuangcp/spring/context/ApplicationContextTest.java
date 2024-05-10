package com.github.kuangcp.spring.context;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.github.kuangcp.spring.bean.TreeService;
import com.github.kuangcp.spring.bean.loop.CurrencyService;
import com.github.kuangcp.spring.bean.loop.ItemService;
import com.github.kuangcp.spring.bean.loop.PlayerService;
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
    public void testGetBeanWithPropertyInject() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("tree-with-property.xml");
        TreeService bean = (TreeService) context.getBean("treeService");
        assertNotNull(bean);
        assertNotNull(bean.getTreeDao());
    }

    @Test
    public void testGetBeanWithConstructorInject() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("tree-with-constructor.xml");
        TreeService bean = (TreeService) context.getBean("treeService");
        assertNotNull(bean);
        assertNotNull(bean.getTreeDao());
        assertThat(bean.getVersion(), equalTo("version"));
    }

    @Test
    public void testGetBeanFromFileSystem() {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                "/home/kcp/Code/Java/java-wheel/spring/src/test/resources/tree.xml");
        TreeService bean = (TreeService) context.getBean("treeService");
        assertNotNull(bean);
    }

    @Test
    public void testGetBeanViaAnnotation() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("tree-with-annotation.xml");
        TreeService bean = (TreeService) context.getBean("treeService");
        assertNotNull(bean);
        assertNotNull(bean.getTreeDao());
    }

    // 属性循环依赖
    @Test
    public void testGetBeanWithLoop() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("tree-with-loop.xml");

        CurrencyService currencyService = (CurrencyService) context.getBean("currencyService");
        assertNotNull(currencyService);
        assertNotNull(currencyService.getItemService());

        PlayerService playerService = (PlayerService) context.getBean("playerService");
        assertNotNull(playerService);
        assertNotNull(playerService.getItemService());

        ItemService itemService = (ItemService) context.getBean("itemService");
        assertNotNull(itemService);
        assertNotNull(itemService.getPlayerService());
        assertThat(itemService, equalTo(playerService.getItemService()));
    }

    // TODO 构造器循环依赖
    //  https://www.baeldung.com/circular-dependencies-in-spring

}