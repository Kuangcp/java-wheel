package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.ConstructorArgument;
import com.github.kuangcp.spring.beans.ConstructorArgument.ValueHolder;
import com.github.kuangcp.spring.beans.exception.BeanCreationException;
import com.github.kuangcp.spring.beans.factory.config.ConfigurableBeanFactory;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

/**
 * @author https://github.com/kuangcp on 2019-12-09 22:56
 */
@Slf4j
public class ConstructorResolver {

  private final ConfigurableBeanFactory beanFactory;

  public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  public Object autowireConstructor(final BeanDefinition definition) {
    Constructor<?> constructor = null;
    Object[] args = null;
    Class<?> beanClass;
    try {
      beanClass = this.beanFactory.getBeanClassLoader().loadClass(definition.getClassName());
    } catch (ClassNotFoundException e) {
      throw new BeanCreationException(
          definition.getId() + " instantiation of bean failed, can't load class ");
    }
    Constructor<?>[] candidates = beanClass.getConstructors();

    BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(beanFactory);
    ConstructorArgument argument = definition.getConstructorArgument();

    for (Constructor<?> candidate : candidates) {
      Class<?>[] parameterTypes = candidate.getParameterTypes();
      if (parameterTypes.length != argument.getArgumentCount()) {
        continue;
      }
      args = new Object[parameterTypes.length];
      boolean isMatch = this.valuesMatchType(valueResolver, argument.getValueHolders(),
          parameterTypes, args);
      if (isMatch) {
        constructor = candidate;
        break;
      }
    }

    if (Objects.isNull(constructor)) {
      throw new BeanCreationException(definition.getId() + " can't find correspond constructor");
    }

    try {
      return constructor.newInstance(args);
    } catch (Exception e) {
      throw new BeanCreationException("can't find a create instance using " + constructor);
    }
  }

  private boolean valuesMatchType(BeanDefinitionValueResolver valueResolver,
      List<ValueHolder> valueHolders,
      Class<?>[] parameterTypes,
      Object[] args) {

    for (int i = 0; i < parameterTypes.length; i++) {
      ValueHolder valueHolder = valueHolders.get(i);
      Object originValue = valueHolder.getValue();
      try {
        Object value = valueResolver.resolverValueIfNecessary(originValue);
        args[i] = value;
      } catch (Exception e) {
        log.error("", e);
        return false;
      }
    }
    return true;
  }
}
