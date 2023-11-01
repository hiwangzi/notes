# 接口

## 概念

* 接口不是类，而是对类的一组需求描述，这些类要遵从接口描述的统一格式进行定义。
    * 要注意「对称性」规则，例如假设`Employee`实现了`Comparable<Employee>`接口，同时`Manager`可以转换为`Employee`：
        * 此时应该考虑到可能会出现经理与雇员的比较，在实现子类的比较方法时，不能将父类直接转为子类进行比较。
          ```java
          class Manager extends Employee {
              public int compareTo(Employee other) {
                  Manager otherManager = (Manager) other; // ❌不要这样
              }
          }
          ```
        * 此例中，当 `manager.compareTo(employee)` 会抛出 `ClassCastException`
        * 对此，类似于`equals`，有两种不同的处理方式：
            * 如果子类之间的比较含义不同，那就属于不同类对象的非法比较，在比较前，应当使用 `getClass()` 检测
            * 如果存在一种通用的比较方法，则应在超类之中提供一个 `compareTo` 方法，并将其声明为 `final`

## 特性

* 接口可以被扩展为另一个接口，并且支持多继承
* 接口中不能包含实例域或静态方法（Java 8之前），但可以包含常量（`public static final`）
* Java SE 8中，接口可以增加静态方法（`public static`）
* 可以为接口中的方法（非静态）提供一个默认实现（`default`）
    * 如果在一个接口中将一个方法定义为默认方法，又在超类或其他接口中定义了同样的方法，处理规则如下：
        * 超类优先：如果超类提供了一个具体方法，同名且同参类型的默认方法会被忽略
        * 接口冲突时，只要其中一个接口提供了默认方法，则必须覆盖这个方法来解决冲突
