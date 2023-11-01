# lambda 表达式

* lambada 表达式是一个可传递的代码块，重点是延迟执行（deferred execution）。
* 对于***只有一个抽象方法***的接口，需要这种接口的对象时，就可以提供一个 lambda 表达式。这种接口称为函数式接口(functional interface)。
* java.util.function 包中定义了很多非常通用的函数式接口，可以用来保存 lambda 表达式。

## 方法引用
* 使用 `::` 操作符分隔对象(或类)名与方法名，主要有以下3种情况：
    * `object::instanceMethod`，例如：`System.out::println`等价于`x -> System.out.println(x)`
    * `Class::staticMethod`，例如：`Math::pow`等价于`(x,y) -> Math.pow(x, y)`
    * `Class::instanceMethod`，例如：`String::compareToIgnoreCase`等价于`(x, y) -> x.compareToIgnoreCase(y)`

## 构造器引用
* `Person::new` 指的是 `Person` 的哪一个构造器呢? 这取决于上下文。
* 可以用数组类型建立构造器引用。例如，`int[]::new` 是一个构造器引用，它有一个参数表示数组的长度。这等价于 lambda 表达式 `x -> new int[x]`。
    * 实例：Stream 接口有一个 `toArray` 方法可以返回 Object 数组: `Object[] people = stream.toArray()`，但将 `Person[]::new` 传入 `toArray` 方法，即使用 `Perso[] people = stream.toArray(Person[]::new)` 更佳，可以得到一个正确类型的数组。

## 变量作用域
* lambda 表达式中的3个部分：一个代码块、参数、自由变量的值（非参数且不在代码中定义的变量）
* 因为并发执行多个动作时的安全性，所以在lambda中自由变量的值不允许被改变。在外部可能发生改变同样不合法。
* 在lambda表达式中`this`表示的是创建这个lambda表达式方法的`this`参数。

## 处理 lambda 表达式
### 常用函数式接口

|函数式接口|参数类型|返回类型|抽象方法名|描述|其他方法|
|---------|-------|-------|--------|---|-------|
|`Runnable`|无|`void`|`run`|作为无参数或无返回值的动作运行||
|`Supplier<T>`|无|`T`|`get`|提供一个`T`类型的值||
|`Consumer<T>`|`T`|`void`|`accept`|处理一个`T`类型的值|`andThen`|
|`BiConsumer<T, U>`|`T, U`|`void`|`accept`|处理`T`和`U`类型的值|`andThen`|
|`Function<T, R>`|`T`|`R`|`apply`|有一个`T`类型参数的函数，返回`R`类型值|`compose`, `andThen`, `identity`|
|`BiFunction<T, U, R>`|`T, U`|`R`|`apply`|有`T`和`U`类型参数的函数，返回`R`类型值|`andThen`|
|`UnaryOperator<T>`|`T`|`T`|`apply`|类型`T`上的一元操作符|`compose`, `andThen`, `identity`|
|`BinaryOperator<T>`|`T`|`T`|`apply`|类型`T`上的二元操作符|`andThen`, `maxBy`, `minBy`|
|`Predicate<T>`|`T`|`boolean`|`test`|判断是否为真|`and`, `or`, `negate`, `isEqual`|
|`BiPredicate<T, U>`|`T, U`|`boolean`|`test`|判断是否为真|`and`, `or`, `negate`|

### 基本类型的函数式接口
* 以`int`为例，其他基本类型同理

    |函数式接口|参数类型|返回类型|抽象方法名|
    |---------|-------|-------|--------|
    |`IntSupplier`|无|`int`|`getAsInt`|
    |`IntConsumer`|`int`|`void`|`accept`|
    |`ObjIntConsumer<T>`|`T, int`|`void`|`accept`|
    |`IntFunction<T>`|`int`|`T`|`apply`|
    |`IntToDoubleFunction<T>`|`int`|`double`|`applyAsDouble`|
    |`ToIntFunction<T>`|`T`|`int`|`applyAsInt`|
    |`ToIntBiFunction<T, U>`|`T, U`|`int`|`applyAsInt`|
    |`IntUnaryOperator`|`int`|`int`|`applyAsInt`|
    |`IntBinaryOperator`|`int, int`|`int`|`applyAsInt`|
    |`IntPredicate`|`int`|`boolean`|`test`|
