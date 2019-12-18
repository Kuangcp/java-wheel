package com.github.kuangcp.spring.beans.factory.annotation;

import java.util.Collection;
import java.util.List;

/**
 * @author https://github.com/kuangcp on 2019-12-18 08:03
 */
public class InjectionMetadata {

  private final Class<?> targetClass;

  private Collection<InjectionElement> injectionElements;

  public InjectionMetadata(Class<?> targetClass, List<InjectionElement> injectionElements) {
    this.targetClass = targetClass;
    this.injectionElements = injectionElements;
  }

  public Collection<InjectionElement> getInjectionElements() {
    return injectionElements;
  }

  public void inject(Object target) {
    if (injectionElements == null || injectionElements.isEmpty()) {
      return;
    }

    for (InjectionElement ele : injectionElements) {
      ele.inject(target);
    }
  }
}
