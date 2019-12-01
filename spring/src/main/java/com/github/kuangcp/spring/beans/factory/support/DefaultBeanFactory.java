package com.github.kuangcp.spring.beans.factory.support;

import com.github.kuangcp.aop.util.ClassUtil;
import com.github.kuangcp.io.ResourceTool;
import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.factory.BeanFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author https://github.com/kuangcp on 2019-12-01 16:40
 */
@Slf4j
public class DefaultBeanFactory implements BeanFactory {

  public static final String ID_ATTRIBUTE = "id";
  public static final String CLASS_ATTRIBUTE = "class";

  private Map<String, BeanDefinition> definitionMap = new ConcurrentHashMap<>();

  public DefaultBeanFactory(String configFile) {
    loadDefinition(configFile);
  }

  @SuppressWarnings("unchecked")
  private void loadDefinition(String configFile) {
    InputStream in = null;
    try {
      ClassLoader loader = ClassUtil.getDefaultClassLoader();
      in = loader.getResourceAsStream(configFile);
      SAXReader saxReader = new SAXReader();
      Document doc = saxReader.read(in);
      Element root = doc.getRootElement();
      Iterator<Element> iterator = root.elementIterator();
      while (iterator.hasNext()) {
        Element element = iterator.next();
        String id = element.attributeValue(ID_ATTRIBUTE);
        String className = element.attributeValue(CLASS_ATTRIBUTE);

        GenericBeanDefinition definition = new GenericBeanDefinition(id, className);
        this.definitionMap.put(id, definition);
      }
    } catch (DocumentException e) {
      log.error("", e);
    } finally {
      try {
        ResourceTool.close(in);
      } catch (IOException e) {
        log.error("", e);
      }
    }
  }

  @Override
  public Object getBean(String beanId) {
    BeanDefinition definition = getBeanDefinition(beanId);
    if (Objects.isNull(definition)) {
      return null;
    }

    ClassLoader loader = ClassUtil.getDefaultClassLoader();
    String className = definition.getClassName();
    try {
      Class<?> target = loader.loadClass(className);
      Constructor<?> constructor = target.getConstructor();
      if (Objects.isNull(constructor)) {
        log.warn("not define empty constructor");
        return null;
      }
      return constructor.newInstance();
    } catch (Exception e) {
      log.error("", e);
    }
    return null;
  }

  @Override
  public BeanDefinition getBeanDefinition(String beanId) {
    return this.definitionMap.get(beanId);
  }
}
