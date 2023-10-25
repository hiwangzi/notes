# 01 Java介绍

## 「白皮书」关键术语

* 简单性 C++--
* 面向对象
* 分布式
* 健壮性
* 安全性
* 体系结构中立
* 可移植性
* 解释型
* 高性能
* 多线程
* 动态性

## Java 发展历程

* SUN公司——Stanford University Network
* 主设计者：James Gosling
* 1996年初 - 发布 Java 1.0
* Java 2, JDK 1.2 有了很大的改变 - 更加接近“一次编写，随处运行”的承诺
* J2SE 5.0 得到了进一步改进 - 泛型、(以下几项受 C# 启发) foreach、自动装箱、注解
* 2014年 - 发布 Java 8 - 重大改变：提供了一种「**函数式**」编程方式，可以容易地表述并发执行的计算

![Java的版本](./assets/java-version.jpg)

## Java 主要技术分支

* Java SE
  Java 2 Platform, Standard Edition
  前身：J2SE，2005年更名为Java SE
* Java EE（主要应用）
  Java 2 Platform, Enterprise Edition
  前身：J2EE，2005年更名为Java EE
* Java ME（嵌入式）
  Java 2 Platform, Micro Edition
  前身：J2ME，2005年更名为Java ME

## JVM, JRE, JDK

* JVM(Java Virtual Machine) < JRE(Java Runtime Environment) < JDK(Java Development Kit)

## “跨平台” 与 “解释执行”

* 早期的 Java是解释型的。现在 Java 虚拟机使用了即时编译器，因此采用 Java 编写的“热点”代码其运行速度与 C++ 相差无几，有些情况下甚至更快。

![Java的跨平台性](./assets/java-cross-platform.png)

## Java 开发环境

1. Path 的主要功能是设置 JDK 的可执行命令
2. classpath 主要是在执行的时候起作用，告诉JVM类的保存路径，一般（默认）设置为```.```（cmd设置命令：```set classpath=.```）
    * 注意：classpath 只在 java 命令时起作用，对 javac 无作用

## 一些术语

![Java的一些术语](./assets/java-terms.jpg)

* 当Oracle为解决一些紧急问题做出某些微小的版本改变时，将其称为更新。例如：Java SE 8u31是Java SE 8的第31次更新，它的内部版本号是1.8.0_31。更新不需要安装在前一个版本上，它会包含整个JDK的最新版本。
