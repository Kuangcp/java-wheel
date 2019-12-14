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
- Bean 生命周期管理

## v1.4
- AOP
