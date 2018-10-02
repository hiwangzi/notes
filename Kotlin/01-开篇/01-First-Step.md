# 第一步

## Kotlin 的应用场景

* 基于JVM
  * 服务器端编程
  * Android应用开发
* 处于探索阶段
  * 编译成JavaScript代码，应用于Web前端开发。
  * 编译成本地（Native）代码，本地代码运行不再需要Java虚拟机，类似于C语言。

## HelloWorld

```kotlin
fun main(args: Array<String>) {
    println("Hello, world!")
}
```

* Kotlin中有一些函数不属于任何类，这些函数是顶层函数。上述示例中`println`函数对应Java中的`System.out.println`函数。

### 编译

* 方式一：直接使用`kotlinc HelloWorld.kt‵编译，通过‵kotlin HelloWorld‵运行
* 方式二：可以将kotlin依赖一同打包成为jar文件
  * `kotlinc HelloWorld.kt -include-runtime -d HelloWorld.jar`
  * `java -jar HelloWorld.jar`
