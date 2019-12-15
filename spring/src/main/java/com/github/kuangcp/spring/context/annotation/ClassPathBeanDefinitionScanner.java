package com.github.kuangcp.spring.context.annotation;

import com.github.kuangcp.spring.beans.BeanDefinition;
import com.github.kuangcp.spring.beans.exception.BeanDefinitionStoreException;
import com.github.kuangcp.spring.beans.factory.support.BeanDefinitionRegistry;
import com.github.kuangcp.spring.beans.factory.support.BeanNameGenerator;
import com.github.kuangcp.spring.core.io.Resource;
import com.github.kuangcp.spring.core.io.support.PackageResourceLoader;
import com.github.kuangcp.spring.core.type.classreading.MetadataReader;
import com.github.kuangcp.spring.core.type.classreading.SimpleMetadataReader;
import com.github.kuangcp.spring.stereotype.Component;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author https://github.com/kuangcp on 2019-12-15 10:08
 */
@Slf4j
public class ClassPathBeanDefinitionScanner {

  private final BeanDefinitionRegistry registry;
  private PackageResourceLoader resourceLoader = new PackageResourceLoader();
  private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

  public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
    this.registry = registry;
  }

  public Set<BeanDefinition> doScan(String packagesToScan) {
    String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan, ",");

    Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
    for (String basePackage : basePackages) {
      Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
      for (BeanDefinition candidate : candidates) {
        beanDefinitions.add(candidate);
        registry.registerBeanDefinition(candidate.getId(), candidate);
      }
    }
    return beanDefinitions;
  }

  public Set<BeanDefinition> findCandidateComponents(String basePackage) {
    Set<BeanDefinition> candidates = new LinkedHashSet<>();
    try {
      Resource[] resources = this.resourceLoader.getResources(basePackage);

      for (Resource resource : resources) {
        try {
          MetadataReader metadataReader = new SimpleMetadataReader(resource);
          if (metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
            ScannedGenericBeanDefinition definition =
                new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());

            String beanName = this.beanNameGenerator.generateBeanName(definition, this.registry);
            definition.setId(beanName);
            candidates.add(definition);
          }
        } catch (Throwable ex) {
          log.error("", ex);
          throw new BeanDefinitionStoreException(
              "Failed to read candidate component class: " + resource, ex);
        }
      }
    } catch (IOException ex) {
      throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
    }
    return candidates;
  }
}
