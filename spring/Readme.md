# Spring
- [从零开始造Spring](https://chuansongme.com/n/2373343751011)

## v1.0
- 读取XML 实例化对应的Bean

## v1.1
- 将操作XML的代码解耦 职责分离
  - 进一步抽象为资源 从类路径或文件系统中加载，还可以扩展为 URI 等等
- 包设计的思想: 实现相关的类放到 实现包下即使是接口 例如 BeanDefinitionRegistry接口 放到 support 包下
- scope 功能的实现
- context 的实现

## v1.2
- Bean属性注入 字面值属性注入(x)
- 构造器注入

## v1.3
- 注解实现: 指定package 进行 scan 找到Component标记的类 据此创建 BeanDefinition
  1. 把一个package 下的类 变成 Resource
  1. 使用 ASM 读取 resource 的注解
  1. 创建 BeanDefinition
  1. Bean 注入
    1. AutowiredAnnotationProcessor 将Class转换成 InjectMetadata
    1. 调用 InjectMetadata inject() 完成注入， 因为 AutowireCapableBeanFactory 的 resolveDependency()

- Bean 生命周期管理 (自定义的方法都可以看作 Hook 也就是 CallBack 回调)
  1. `实例化` 通过无参或含参构造器实例化Bean
  1. `初始化` 调用用户自定义初始化方法
  1. `运行`
  1. `销毁` DisposableBean.destroy() 调用自定义的销毁方法


1. InstantiationAwareBeanPostProcessor.beforeInstantiation()
1. 实例化
1. InstantiationAwareBeanPostProcessor.afterInstantiation()
1. InstantiationAwareBeanPostProcessor.postProcessPropertyValues() 实现Autowired
1. BeanNameAware.setBeanName()
1. BeanFactoryAware.setBeanFactory()
1. BeanPostProcessor.beforeInitialization()
1. 初始化
1. BeanPostProcessor.afterInitialization()

> 如何使用 BeanPostProcessor
1. AbstractApplicationContext 里创建 BeanPostProcessor
1. ConfigurableBeanFactory 里往 processor 注入 factory
1. DefaultBeanFactory.populateBean() 中使用这些 processor

> 处理循环依赖问题 [4.2之前不支持构造器循环注入的解决方案](https://www.baeldung.com/circular-dependencies-in-spring)
1. 首先明确注入方式有: 1. set方式 2. 构造器
1. 这里只用了提前曝光的设计(只有两层) 仅适用于 set方法形式的注入
1. 处理构造器注入的形式
  - 使用 ObjenesisCglibAopProxy 生成代理(由第三方打破死循环) 解决构造器 循环依赖 [objenesis](http://objenesis.org/)

> 三级缓存处理set方式循环依赖
- DefaultSingletonBeanRegistry 类
  1. `singletonFactories`： 进入实例化阶段的单例对象工厂的cache （二级缓存的工厂类）
    - 三级缓存 存在的意义: [参考：非要三级缓存么？](https://www.cnblogs.com/youzhibing/p/14337244.html)
  1. `earlySingletonObjects`： 完成实例化但是尚未初始化的，提前曝光的单例对象的Cache （二级缓存）
  1. `singletonObjects`： 完成初始化的单例对象的cache（一级缓存）

```java
	protected Object getSingleton(String beanName, boolean allowEarlyReference) {
		Object singletonObject = this.singletonObjects.get(beanName);

		if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
			synchronized (this.singletonObjects) {
				singletonObject = this.earlySingletonObjects.get(beanName);

				// 如果二级缓存获取不到就去对应的工厂方法构建出来 并加入二级缓存
				if (singletonObject == null && allowEarlyReference) {
					ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
					if (singletonFactory != null) {
						singletonObject = singletonFactory.getObject();
						this.earlySingletonObjects.put(beanName, singletonObject);
						this.singletonFactories.remove(beanName);
					}
				}
			}
		}
		return singletonObject;
	}
```

## v1.4
- AOP
