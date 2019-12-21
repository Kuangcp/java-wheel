package com.github.kuangcp.spring.beans.factory.annotation;

import com.github.kuangcp.spring.beans.factory.config.AutowireCapableBeanFactory;
import java.lang.reflect.Member;

/**
 * @author https://github.com/kuangcp on 2019-12-18 07:59
 */
public abstract class InjectionElement {

  protected Member member;

  /**
   * 接口越小越好 用于解析创建Bean
   */
  protected AutowireCapableBeanFactory factory;

  InjectionElement(Member member, AutowireCapableBeanFactory factory) {
    this.member = member;
    this.factory = factory;
  }

  public abstract void inject(Object target);
}
