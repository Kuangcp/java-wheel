package com.github.kuangcp.spring.beans.factory.annotation;

import java.util.Collection;
import java.util.List;

/**
 * @author https://github.com/kuangcp on 2019-12-18 08:03
 */
public class InjectionMetadata {

  private final Class<?> targetClass;

  /**
   * 需要被注入的成员属性的Bean
   */
  private final Collection<InjectionElement> injectionElements;

  public InjectionMetadata(Class<?> targetClass, List<InjectionElement> injectionElements) {
    this.targetClass = targetClass;
    this.injectionElements = injectionElements;
  }

  public Collection<InjectionElement> getInjectionElements() {
    return injectionElements;
  }

  /**
   * 通过 target 成员属性 找到是否有已经注册的 InjectionElement
   * 并利用 InjectionElement内factory属性解析实例化Bean 实现反射注入
   *
   * @param target 被注入属性的对象
   */
  public void inject(Object target) {
    if (injectionElements == null || injectionElements.isEmpty()) {
      return;
    }

    for (InjectionElement ele : injectionElements) {
      ele.inject(target);
    }
  }
}
