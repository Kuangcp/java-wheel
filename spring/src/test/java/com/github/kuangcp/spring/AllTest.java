package com.github.kuangcp.spring;

import com.github.kuangcp.spring.context.ApplicationContextTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author https://github.com/kuangcp on 2019-12-05 00:12
 */
@RunWith(Suite.class)
@SuiteClasses({
    ApplicationContextTest.class,
    BeansSuiteTest.class
})
public class AllTest {

}
