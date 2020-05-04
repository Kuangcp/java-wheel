[Lombok原理分析](https://blog.mythsman.com/post/5d2c11c767f841464434a3bf/)

[手写Lombok](https://www.cnblogs.com/vipstone/p/12597756.html)

1. javac -cp $JAVA_HOME/lib/tools.jar Getter* -d .
1. javac -processor com.github.kuangcp.GetterProcessor Config.java
1. javap -p Config.class

- 实现步骤：
1. 利用 roundEnv 的 getElementsAnnotatedWith 方法过滤出被Getter这个注解标记的类，并存入set
1. 遍历这个set里的每一个元素，并生成 jCTree 这个语法树
1. 创建一个 TreeTranslator，并重写其中的 visitClassDef 方法，这个方法处理遍历语法树得到的类定义部分 jcClassDecl
1. 创建一个 jcVariableDeclList 保存类的成员变量
1. 遍历 jcTree 的所有成员(包括成员变量和成员函数和构造函数)，过滤出其中的成员变量，并添加进 jcVariableDeclList
1. 将 jcVariableDeclList 的所有变量转换成需要添加的getter方法，并添加进 jcClassDecl 的成员中
1. 调用默认的遍历方法遍历处理后的jcClassDecl
1. 利用上面的 TreeTranslator 去处理 jcTree
1. 接下来再实现 makeGetterMethodDeclare 方法:
