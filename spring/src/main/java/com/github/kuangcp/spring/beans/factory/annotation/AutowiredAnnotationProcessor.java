package com.github.kuangcp.spring.beans.factory.annotation;

import com.github.kuangcp.spring.beans.annotation.AnnotationConstant;
import com.github.kuangcp.spring.beans.annotation.Autowired;
import com.github.kuangcp.spring.beans.exception.BeanCreationException;
import com.github.kuangcp.spring.beans.exception.BeansException;
import com.github.kuangcp.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.github.kuangcp.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

/**
 * @author https://github.com/kuangcp on 2019-12-21 21:13
 */
@Slf4j
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

  private AutowireCapableBeanFactory beanFactory;
  private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>();

  public AutowiredAnnotationProcessor() {
    this.autowiredAnnotationTypes.add(Autowired.class);
  }

  public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
    LinkedList<InjectionElement> elements = new LinkedList<>();
    Class<?> targetClass = clazz;

    do {
      LinkedList<InjectionElement> currElements = new LinkedList<>();
      for (Field field : targetClass.getDeclaredFields()) {
        Annotation annotation = this.findAutowiredAnnotation(field);
        if (annotation != null) {
          if (Modifier.isStatic(field.getModifiers())) {
            continue;
          }

          boolean required = this.determineRequiredStatus(annotation);
          currElements.add(new AutowiredFieldElement(field, required, beanFactory));
        }
      }

      //TODO 处理方法注入
      for (Method method : targetClass.getDeclaredMethods()) {

      }
      elements.addAll(0, currElements);
      targetClass = targetClass.getSuperclass();
    } while (targetClass != null && targetClass != Object.class);

    return new InjectionMetadata(clazz, elements);
  }

  protected boolean determineRequiredStatus(Annotation annotation) {
    try {
      Method method = ReflectionUtils.findMethod(annotation.annotationType(),
          AnnotationConstant.AUTOWIRED_REQUIRED);
      if (method == null) {
        // Annotations like @Inject and @Value don't have a method (attribute) named "required"
        // -> default to required status
        return true;
      }

      return (AnnotationConstant.AUTOWIRED_REQUIRED_DEFAULT == (Boolean) ReflectionUtils
          .invokeMethod(method, annotation));
    } catch (Exception ex) {
      // An exception was thrown during reflective invocation of the required attribute
      // -> default to required status
      return true;
    }
  }

  private Annotation findAutowiredAnnotation(AccessibleObject object) {
    for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
      Annotation annotation = AnnotationUtils.getAnnotation(object, type);
      if (annotation != null) {
        return annotation;
      }
    }
    return null;
  }

  public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  public Object beforeInitialization(Object bean, String beanName) throws BeansException {
    log.debug("AutowiredAnnotationProcessor.beforeInitialization {}", beanName);
    return bean;
  }

  public Object afterInitialization(Object bean, String beanName) throws BeansException {
    log.debug("AutowiredAnnotationProcessor.afterInitialization {}", beanName);
    return bean;
  }

  public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
    log.debug("AutowiredAnnotationProcessor.beforeInstantiation {}", beanName);
    return null;
  }

  public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
    log.debug("AutowiredAnnotationProcessor.afterInstantiation {}", beanName);
    return true;
  }

  public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
    log.debug("AutowiredAnnotationProcessor.postProcessPropertyValues {}", beanName);
    InjectionMetadata metadata = this.buildAutowiringMetadata(bean.getClass());
    try {
      metadata.inject(bean);
    } catch (Throwable ex) {
      throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
    }
  }
}
