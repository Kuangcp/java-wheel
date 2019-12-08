package com.github.kuangcp.spring.beans.factory.xml;

import static com.github.kuangcp.spring.beans.factory.xml.XMLPropertyConstants.CLASS_ATTRIBUTE;
import static com.github.kuangcp.spring.beans.factory.xml.XMLPropertyConstants.ID_ATTRIBUTE;
import static com.github.kuangcp.spring.beans.factory.xml.XMLPropertyConstants.NAME_ATTRIBUTE;
import static com.github.kuangcp.spring.beans.factory.xml.XMLPropertyConstants.PROPERTY_ATTRIBUTE;
import static com.github.kuangcp.spring.beans.factory.xml.XMLPropertyConstants.REF_ATTRIBUTE;
import static com.github.kuangcp.spring.beans.factory.xml.XMLPropertyConstants.SCOPE_ATTRIBUTE;

import com.github.kuangcp.io.ResourceTool;
import com.github.kuangcp.spring.beans.PropertyValue;
import com.github.kuangcp.spring.beans.exception.BeanDefinitionParseException;
import com.github.kuangcp.spring.beans.factory.config.RuntimeBeanReference;
import com.github.kuangcp.spring.beans.factory.support.BeanDefinitionRegistry;
import com.github.kuangcp.spring.beans.factory.support.GenericBeanDefinition;
import com.github.kuangcp.spring.core.io.Resource;
import com.github.kuangcp.spring.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Objects;
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

  private BeanDefinitionRegistry registry;

  public XMLBeanDefinitionReader(BeanDefinitionRegistry registry) {
    this.registry = registry;
  }

  @SuppressWarnings("unchecked")
  public void loadDefinition(Resource resource) {
    InputStream in = null;
    try {
      in = resource.getInputStream();
      SAXReader saxReader = new SAXReader();
      Document doc = saxReader.read(in);
      Element root = doc.getRootElement();
      Iterator<Element> iterator = root.elementIterator();
      while (iterator.hasNext()) {
        Element element = iterator.next();
        String id = element.attributeValue(ID_ATTRIBUTE);
        String className = element.attributeValue(CLASS_ATTRIBUTE);
        String scope = element.attributeValue(SCOPE_ATTRIBUTE);

        if (StringUtils.isBlank(id) || StringUtils.isBlank(className)) {
          throw new BeanDefinitionParseException();
        }

        GenericBeanDefinition definition = new GenericBeanDefinition(id, className);
        definition.setScope(scope);
        this.parsePropertyElement(element, definition);
        registry.registerBeanDefinition(id, definition);
      }
    } catch (DocumentException | IOException e) {
      log.error("", e);
    } finally {
      try {
        ResourceTool.close(in);
      } catch (IOException e) {
        log.error("", e);
      }
    }
  }

  private void parsePropertyElement(Element element, GenericBeanDefinition definition) {
    Iterator iterator = element.elementIterator(PROPERTY_ATTRIBUTE);
    while (iterator.hasNext()) {
      Element property = (Element) iterator.next();
      String propertyName = property.attributeValue(NAME_ATTRIBUTE);
      if (!StringUtils.hasLength(propertyName)) {
        log.error("property must have name value");
        return;
      }

      Object value = this.parsePropertyValue(property, propertyName);
      PropertyValue propertyValue = new PropertyValue(propertyName, value);
      definition.getPropertyValueMap().put(propertyName, propertyValue);
    }
  }

  private Object parsePropertyValue(Element property, String propertyName) {
    String refBeanId = property.attributeValue(REF_ATTRIBUTE);
    if (Objects.nonNull(refBeanId)) {
      if (StringUtils.isBlank(refBeanId)) {
        log.error("{} contains empty value ", propertyName);
      }
      return new RuntimeBeanReference(refBeanId);
    } else {
      throw new RuntimeException("must specify a ref ");
    }
  }
}
