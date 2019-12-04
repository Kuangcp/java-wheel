package com.github.kuangcp.spring.beans.factory.xml;

import com.github.kuangcp.aop.util.ClassUtil;
import com.github.kuangcp.io.ResourceTool;
import com.github.kuangcp.spring.beans.exception.BeanDefinitionParseException;
import com.github.kuangcp.spring.beans.factory.support.BeanDefinitionRegistry;
import com.github.kuangcp.spring.beans.factory.support.GenericBeanDefinition;
import com.github.kuangcp.spring.util.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author https://github.com/kuangcp on 2019-12-04 23:28
 */
@Slf4j
public class XMLBeanDefinitionReader {

  public static final String ID_ATTRIBUTE = "id";
  public static final String CLASS_ATTRIBUTE = "class";

  BeanDefinitionRegistry registry;

  public XMLBeanDefinitionReader(BeanDefinitionRegistry registry) {
    this.registry = registry;
  }

  @SuppressWarnings("unchecked")
  public void loadDefinition(String configFile) {
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

        if (StringUtil.isBlank(id) || StringUtil.isBlank(className)) {
          throw new BeanDefinitionParseException();
        }

        GenericBeanDefinition definition = new GenericBeanDefinition(id, className);
        registry.registerBeanDefinition(id, definition);
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

}
