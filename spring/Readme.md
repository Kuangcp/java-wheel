# Spring

## v1.0
- 读取XML 实例化对应的Bean

## v1.1
- 将操作XML的代码解耦 职责分离
  - 进一步抽象为资源 从类路径或文件系统中加载，还可以扩展为 URI 等等
- 包设计的思想: 实现相关的类放到 实现包下即使是接口 例如 BeanDefinitionRegistry接口 放到 support 包下
- scope 功能的实现
- context 的实现

## v1.2
- Bean属性注入 字面值属性注入

