package com.github.kuangcp.spring.beans.factory.annotation;

import com.github.kuangcp.spring.bean.TreeDao;
import com.github.kuangcp.spring.bean.TreeService;
import com.github.kuangcp.spring.beans.factory.config.DependencyDescriptor;
import com.github.kuangcp.spring.beans.factory.support.DefaultBeanFactory;
import java.lang.reflect.Field;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author https://github.com/kuangcp on 2019-12-21 21:11
 */
public class AutowiredAnnotationProcessorTest {

  // mock
  private DefaultBeanFactory beanFactory = new DefaultBeanFactory() {
    public Object resolveDependency(DependencyDescriptor descriptor) {
      if (descriptor.getDependencyType().equals(TreeDao.class)) {
        return new TreeDao();
      }
      throw new RuntimeException("can't support types except AccountDao and ItemDao");
    }
  };

  @Test
  public void testGetInjectionMetadata() {
    AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
    processor.setBeanFactory(beanFactory);
    InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(TreeService.class);

    Collection<InjectionElement> elements = injectionMetadata.getInjectionElements();
    Assert.assertEquals(1, elements.size());
    assertFieldExists(elements, "treeDao");

    TreeService petStore = new TreeService();
    injectionMetadata.inject(petStore);

    Assert.assertNotNull(petStore.getTreeDao());
  }

  private void assertFieldExists(Collection<InjectionElement> elements, String fieldName) {
    for (InjectionElement ele : elements) {
      AutowiredFieldElement fieldEle = (AutowiredFieldElement) ele;
      Field f = fieldEle.getField();
      if (f.getName().equals(fieldName)) {
        return;
      }
    }
    Assert.fail(fieldName + "does not exist!");
  }
}
