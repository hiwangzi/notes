# 原理

## 重要的接口

* 重要概念：接口`ApplicationContext`继承了`BeanFactory`。`BeanFactory`才是Spring的核心容器，`ApplicationContext`的大多数实现类都**组合**了`BeanFactory`的功能。
* `BeanFactory`作用
    * 表面上只有`getBean`
    * 实际上IoC、DI、Bean的生命周期，都由其实现类提供
