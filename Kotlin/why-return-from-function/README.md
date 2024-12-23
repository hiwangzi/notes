# 在 lambda 中直接 return

* 从Java 里面 lambda 表达式完全是匿名对象的设计思路，在其中的 `return` 不会直接影响 lambda 代码块外部的代码执行，而 Kotlin 在这方面做了改变，`return` 是会影响外部代码执行的（但也可以使用 @label 的语法取消对外部代码的影响）
* 一个合理的应用场景：
  ![for](resources/for.jpeg)
  ![forEach](resources/forEach.jpeg)