# build.gradle.kts的理解
先看一个典型的Java项目例子的`build.gradle.kts`，如下：

```kotlin
plugins {
    java
    id("com.example.someplugin") version "1.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.1")
}
```

## plugins
下面4段代码是等价的：

```kotlin
plugins {
    java
    id("com.example.someplugin") version "1.0.0"
}
```
```kotlin
// 其实圆括号里的这个是一个“带接收者的函数类型”（Function Types With Receiver），但在很多时候在被需要的地方他和lambda可以相互替代
plugins(fun PluginDependenciesSpec.() { 
    this.java
    this.id("com.example.someplugin").version("1.0.0") 
})
```
```kotlin
// 这才是带接收者的 lambda 表达式 (Lambdas with Receiver)
val config: PluginDependenciesSpec.() -> Unit = {
    this.java
    this.id("com.example.someplugin").version("1.0.0") 
}
plugins(config)
```
```kotlin
plugins({ // 这里去掉圆括号也是一样的啦
    this.java
    this.id("com.example.someplugin").version("1.0.0") 
})
```

* `plugins` 是个函数，其接收的参数类型是 `PluginDependenciesSpec.() -> Unit`，这是一个带接收者的 lambda 表达式 (Lambdas with Receiver)。（补充：它和扩展函数是不一样的，详细见下方）
* 其中`java`、`id`都是 `PluginDependenciesSpec` 的属性或方法。这个 lambda 是一个定义，真正的调用执行是在 `plugins` 函数内部，加载插件的原理就在 `java` 和 `id` 的实现里，调用他们就加载了插件。

### 补充：Kotlin中函数和lambda的区别
在 Kotlin 中，Lambda 表达式和函数在概念上是类似的，因为它们都代表了一段可以被执行的代码，并且可能接受参数并返回结果。然而，在语言实现和使用方式上，它们有一些重要的不同。

首先，让我们看一下这两者的定义方式：

**函数：**

```kotlin
fun add(a: Int, b: Int): Int {
  return a + b
}
```

**Lambda 表达式：**

```kotlin
val add = { a: Int, b: Int -> a + b }
```

* 在上面的例子中，函数 `add` 是明确命名的，而 Lambda 表达式 `add` 是匿名的并赋值给一个变量。
* 从实现角度来看，函数在 JVM 字节码中通常被编译为**静态方法或者类的成员方法**，取决于它们是顶层函数还是类的成员函数。而 Lambda 表达式则被编译为实现了**特定函数接口的匿名类的实例**。
* 从使用方式来看，函数可以直接通过其名称来调用，而 Lambda 表达式需要通过变量来调用。 
* 因此，虽然 Lambda 表达式和函数在功能上是类似的，但在实现和使用方式上是有区别的。
* 这两种方式在大多数情况下都可以互换使用。不过，它们在某些细节上有所不同，例如 return 语句的行为。在 lambda 表达式中，return 语句会从包含 lambda 的最近的封闭函数返回，这被称为【非局部返回】。而在匿名函数中，return 语句只会从匿名函数本身返回，这被称为【局部返回】。以下是一个例子来说明这两者的差异：
  ```kotlin
  fun main() {
      listOf(1, 2, 3, 4).forEach { if (it == 3) return }; println("This won't be printed")
      listOf(1, 2, 3, 4).forEach(fun(element) { if (element == 3) return }); println("This will be printed")
  }
  ```
  * 在上述代码中，第一个 forEach 调用使用的是 lambda 表达式。当 it == 3 时，return 语句会导致 main 函数返回，所以 "This won't be printed" 不会被打印。而第二个 forEach 调用使用的是匿名函数。当 element == 3 时，return 语句只会从匿名函数返回，所以 "This will be printed" 会被打印。

## repositories
除了 mavenCentral()，Gradle 还支持其他几种类型的仓库用于查找和下载依赖。以下是一些常见的选择：

1. `jcenter()`：JCenter 是 Bintray 提供的 Maven 仓库，它包含了 Maven Central 的所有内容，同时还有一些额外的库。然而需要注意的是，2021 年开始，JCenter 已经宣布停止服务，因此新的项目应该避免使用它。
2. `google()`：这个仓库包含了 Android 的所有官方库，如 Android Support Library 和 Google Play services。如果你正在开发 Android 应用，那么你可能需要添加这个仓库。
3. `maven { url "..." }`：你可以使用这种形式来添加任何自定义的 Maven 仓库。只需要将 ... 替换为仓库的 URL 即可。
4. `ivy { url "..." }`：你也可以添加 Ivy 仓库，使用方法和 Maven 仓库类似。

以下是一个示例：以下代码会使 Gradle 在查找和下载依赖时，依次检查 Maven Central、Google 和 JitPack 仓库。

```kotlin
repositories {
    mavenCentral() // 添加 Maven Central 仓库
    google() // 添加 Google 仓库
    maven { url = uri("https://jitpack.io") } // 添加 JitPack 仓库
}
```

需要注意的是，添加的仓库越多，查找依赖的时间可能会越长，因此最好只添加实际需要的仓库。

## dependencies
```kotlin
dependencies {
    implementation("com.example:library:1.0.0") // 在编译和运行阶段都需要这个库
    compileOnly("com.example:compileOnlyLibrary:1.0.0") // 只在编译阶段需要这个库
    runtimeOnly("com.example:runtimeOnlyLibrary:1.0.0") // 只在运行阶段需要这个库
    testImplementation("junit:junit:4.12") // 只在编译和运行测试代码时需要这个库
    testCompileOnly("com.example:testCompileOnlyLibrary:1.0.0") // 只在编译测试代码时需要这个库
    testRuntimeOnly("com.example:testRuntimeOnlyLibrary:1.0.0") // 只在运行测试时需要这个库
}
```

在 Maven 中，你可以使用 `<scope>` 元素来控制依赖的使用范围，这和 Gradle 中的依赖配置类似。以下是几种常见的 Maven 依赖范围：
* `compile`（对应 Gradle 的 `implementation`）: 这是默认的范围，如果没有指定 <scope>，那么就会使用这个范围。在编译、测试和运行阶段都需要这种依赖。
* `provided`（对应 Gradle 的 `compileOnly`）: 这种依赖在编译和测试阶段需要，但在运行阶段不需要，因为这个依赖项会由 JDK 或容器提供。
* `runtime`（对应 Gradle 的 `runtimeOnly`）: 这种依赖在运行和测试阶段需要，但在编译阶段不需要。
* `test`（对应 Gradle 的 `testImplementation`）: 这种依赖只在编译和运行测试时需要。

```xml
<dependencies>
    <dependency>
        <groupId>com.example</groupId>
        <artifactId>library</artifactId>
        <version>1.0.0</version>
        <!-- 在编译、测试和运行阶段都需要这个依赖 -->
    </dependency>
    <dependency>
        <groupId>com.example</groupId>
        <artifactId>compileOnlyLibrary</artifactId>
        <version>1.0.0</version>
        <scope>provided</scope> <!-- 只在编译和测试阶段需要这个依赖 -->
    </dependency>
    <dependency>
        <groupId>com.example</groupId>
        <artifactId>runtimeOnlyLibrary</artifactId>
        <version>1.0.0</version>
        <scope>runtime</scope> <!-- 只在运行和测试阶段需要这个依赖 -->
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope> <!-- 只在编译和运行测试时需要这个依赖 -->
    </dependency>
</dependencies>
```

### 传递依赖

是的，Gradle 和其他构建工具一样，也有传递依赖的问题。传递依赖是指一个项目 A 依赖于项目 B，而项目 B 又依赖于项目 C，那么项目 A 将直接或间接地依赖于项目 C。在一些情况下，这可能会导致一些问题：
1. **依赖冲突**：如果项目 A 和项目 B 都依赖于项目 C，但是依赖的版本不同，那么可能会发生依赖冲突。Gradle 有一个内置的策略来处理这种冲突，即选择最新的版本。然而，这并不总是最佳的解决方案，因为新版本可能不兼容旧版本。你可以通过配置 `resolutionStrategy` 来自定义冲突解决策略。
2. **未必要的依赖**：由于传递依赖的存在，项目可能会包含一些实际上并不需要的依赖。这不仅会增加构建和运行项目的时间，还可能引入不必要的复杂性和潜在的错误。你可以使用 `implementation` 依赖配置来限制依赖的传递，或者使用 `exclude` 关键字来排除特定的传递依赖。例如：
    ```kotlin
    dependencies {
        implementation("com.example:library:1.0.0") {
            exclude(group = "com.example", module = "unnecessary-library") // 排除不必要的传递依赖
       }
    }
    ```
3. **难以追踪的错误**：如果项目包含了许多传递依赖，那么当出现错误时，可能会很难追踪到具体是哪个依赖引起的。你可以使用 Gradle 的依赖报告来查看项目的完整依赖图，这可以帮助你理解和解决依赖相关的问题。 例如：
    ```shell
    ./gradlew dependencies // 查看项目的依赖报告
    ```

总的来说，虽然 Gradle 提供了一些工具和策略来处理传递依赖的问题，但是最佳的实践仍然是尽量减少依赖的数量和复杂性，以及定期检查和更新依赖。
