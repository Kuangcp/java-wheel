package com.github.kuangcp.spring.beans;

import com.github.kuangcp.spring.bean.TreeService;
import com.github.kuangcp.spring.beans.factory.annotation.AutowiredFieldElement;
import com.github.kuangcp.spring.beans.factory.annotation.InjectionElement;
import com.github.kuangcp.spring.beans.factory.annotation.InjectionMetadata;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import com.github.kuangcp.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.github.kuangcp.spring.core.io.ClassPathResource;
import com.github.kuangcp.spring.core.io.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-18 07:52
 */
public class InjectionMetadataTest {

  @Test
  public void testInjection() throws Exception {
    DefaultBeanFactory factory = new DefaultBeanFactory();
    XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
    Resource resource = new ClassPathResource("tree-with-annotation.xml");
    reader.loadBeanDefinitions(resource);

    Class<?> clz = TreeService.class;
    List<InjectionElement> elements = new ArrayList<>();

    {
      Field f = TreeService.class.getDeclaredField("treeDao");
      InjectionElement injectionElem = new AutowiredFieldElement(f, true, factory);
      elements.add(injectionElem);
    }

    InjectionMetadata metadata = new InjectionMetadata(clz, elements);
    TreeService petStore = new TreeService();
    metadata.inject(petStore);
    Assert.assertNotNull(petStore.getTreeDao());
  }
}
