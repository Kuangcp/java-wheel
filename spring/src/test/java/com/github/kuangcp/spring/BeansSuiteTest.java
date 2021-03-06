package com.github.kuangcp.spring;

import com.github.kuangcp.spring.beans.BeanDefinitionTest;
import com.github.kuangcp.spring.beans.factory.BeanFactoryTest;
import com.github.kuangcp.spring.core.io.ResourceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:12
 */
@RunWith(Suite.class)
@SuiteClasses({
    BeanFactoryTest.class,
    BeanDefinitionTest.class,
    ResourceTest.class
})
public class BeansSuiteTest {

}
