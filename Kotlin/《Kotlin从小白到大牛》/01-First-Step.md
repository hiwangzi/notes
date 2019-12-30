# First Step

## 对比 Java

* Kotlin 彻底支持函数式编程：可以定义高阶函数
* Java 把异常分为受检查异常和运行期异常，而 Kotlin 把所有的异常都看做是运行期异常
* Kotlin 数据类型默认不能接收空值

## Kotlin 的应用场景

* 基于 JVM
  * 服务器端编程
  * Android 应用开发
* 处于探索阶段
  * 编译成 JavaScript 代码，应用于 Web 前端开发。
  * 编译成本地（Native）代码，本地代码运行不再需要 Java 虚拟机，类似于 C 语言。

## 扩展学习

* Kotlin 源代码网址：https://github.com/JetBrains/kotlin
* Kotlin 官网：https://kotlinlang.org/
* Kotlin 官方参考文档：https://kotlinlang.org/docs/reference/
* Kotlin 标准库：https://kotlinlang.org/api/latest/jvm/stdlib/index.html

> Kotlin 标准库是由 Kotlin 官方开发的，Kotlin 语言是基于 Java 的，能够与 Java 完全地互操作，所以 Kotlin 可以调用 Java 对象，反之亦然。所以，Kotlin 语言尽可能利用 Java 自带库，然后在这些库上进行一些扩展（Extension）和必要的封装，这就是 Kotlin 标准库所包含的内容。

## HelloWorld

```kotlin
fun main(args: Array<String>) {
    println("Hello, world!")
}
```

* Kotlin中有一些函数不属于任何类，这些函数是顶层函数。上述示例中`println`函数对应Java中的`System.out.println`函数。

### 编译

* 方式一：直接使用 `kotlinc HelloWorld.kt` 编译，通过 `kotlin HelloWorld` 运行
* 方式二：可以将kotlin依赖一同打包成为jar文件
  * `kotlinc HelloWorld.kt -include-runtime -d HelloWorld.jar`
  * `java -jar HelloWorld.jar`
