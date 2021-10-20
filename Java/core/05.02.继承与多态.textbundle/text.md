# 05.02 继承与多态

* Java 中只允许单继承。
* 一个对象变量可以指示多种实际类型的现象被称为多态（polymorphism）。
* 子类不能覆盖父类中 `final` 修饰的方法（`final` 类中所有方法自动地成为 `final` 方法，并且 `final` 类不能被继承）。
* 在父类转子类的类型转换前，可以先借助 `instanceof` 操作符，查看是否能够转换成功。
  ```java
  if (staff instanceof Manager) {
      boss = (Manager) staff;
  }
  ```
* 在超类（父类）设计合理的情况下，很少会使用到类型转换和 `instanceof` 运算符。

## 动态绑定、静态绑定
* 在运行时能够自动地选择调用哪个方法的现象称为**动态绑定**（dynamic binding）。
* 如果是 `private` 方法、`static` 方法、`final` 方法或者构造器，编译器可以准确知道应该调用哪个方法，此种调用方式称为**静态绑定**（static binding）。

## 抽象类
* 包含一个或多个抽象方法的类本身必须被声明为抽象的。
  * 除了抽象方法，抽象类还可以包含具体数据和具体方法。
  * 扩展抽象类有两种选择：一是部分实现抽象方法，此时子类仍需标记为抽象类；二是定义全部抽象方法。
  * 类即使不包含抽象方法，也可以将类声明为抽象类。
  * 抽象类不能被实例化。

## 访问控制
  * 仅对本类可见—— `private`
  * 对本包可见——默认，不需要修饰符（package private）
  * 对本包和所有子类可见—— `protected`
  * 对所有类可见—— `public`
* 如果没有明确地指出父类，则Object就被认为是这个类的超类，其常见几个方法：
  * [equals 方法](#equals方法)
  * [hashcode 方法](http://www.cnblogs.com/hiwangzi/p/7635819.html#hashcode-方法)
  * [toString 方法](http://www.cnblogs.com/hiwangzi/p/7635819.html#tostring-方法)

* 关于 `protected` 的补充：
  * 即使两个子类继承自同一个父类，也不能在一个子类中访问另一个子类的`protected`方法
  * > Protected access requires a little more elaboration. Suppose class A declares a protected field x and is extended by a class B, which is defined in a different package (this last point is important). Class B inherits the protected field x, and its code can access that field in the current instance of B or in any other instances of B that the code can refer to. This does not mean, however, that the code of class B can start reading the protected fields of arbitrary instances of A! If an object is an instance of A but is not an instance of B, its fields are obviously not inherited by B, and the code of class B cannot read them.
    >
    > ——《Java in a Nutshell》

### `equals` 方法

* equals 方法示例
```java
// 代码来自《Java核心技术 卷I》P167
// 父类
public class Employee{
    ...
    public boolean equals(Object otherObject){
        // a quick test to see if the objects are identical
        if(this == otherObject) return true;

        // must return false if the explicit parameter is null
        if(otherObject == null) return false;

        // if the classes don't match, they can't be equal
        // 笔者注：子类通过super.equals方法调用到此处时，getClass()的结果是子类
        if(getClass() != otherObject.getClass())
            return false;

        // now we know otherObject is a non-null Employee
        Employee other = (Employee) otherObject;

        // test whether the fields hava identicial values
        // 笔者注：此处使用Objects.equals方法是为了防备name或hireDay可能为null的情况
        return Objects.equals(name, other.name)
            && salary == other.salary
            && Objects.equals(hireDate, other.hireDate);
    }
}
// 子类
// 先调用超类的equals，如果返回false，对象则不可能相等
// 如果父类中的域都相等，再比较子类的实例域
public class Manager extends Employee{
    ...
    public boolean equals(Object otherObject){
        if(!super.equals(otherObject)) return false;
        // super.equals checked that this and otherObject belong to the same class
        Manager other = (Manager) otherObject;
        return bonus == other.bonus;
    }
}
```

* Java 语言规范要求 equals 方法具有以下特性：
    * 自反性：x.equals(x) 应当返回 true
    * 对称性：x.equals(y) 与 y.equals(x) 返回应当相同
    * 传递性：如果 x.equals(y) 返回 true，且 y.equals(z) 也返回 true，则 x.equals(z) 也应返回 true
    * 一致性：如果 x 与 y 引用的对象没有发生变化，则 x.eqauls(y) 也不应变化
    * 对于任意的非空引用 x，x.equals(null) 应当返回 false

* 在上面的例子中，如果发现类型不一致，就返回 false。但同时也有许多程序员喜欢采用以下代码进行检测 ```if(!(otherObject instanceof Employee)) return false;``` 但这样没有解决 otherObject 是子类的情况(父类对象.eqaules(子类对象))下的比较问题。

* 关于 getClass 与 instanceof 两种检测方法：
    * 如果子类能够拥有自己的相等概念，则对称性需求将强制采用 getClass 进行检测。
    * 如果由超类决定相等的概念，那么就可以使用 instanceof 进行检测，这样可以在不同子类的对象之间进行相等的比较。

* 编写完美的 equals 方法的建议：
    1. 显式参数命名为 otherObject，稍后需要将它转换为另一个叫做 other 的变量。
    2. 检测 this 与 otherObject 是否引用同一个对象：```return this == otherObject;```
    3. 检测 otherObject 是否为 null，是则返回 false。
    4. 比较 this 与 otherObject 是否属于同一个类：
        * 如果 equals 的语义在每个子类中有所改变，就使用 getClass 检测：```return getClass() != otherObject.getClass();```
        * 如果所有的子类都拥有统一的语义，就使用 instanceof 检测：```return (!(otherObject instanceof ClassName));```
    5. 将 otherObject 转换为相应的类类型变量：```ClassName other = (ClassName) otherObject```
    6. 对所有需要比较的域进行比较。使用 == 比较基本类型域，使用 equals 比较对象域。如果所有的域都匹配，则返回 true，否则返回false。
        ```java
        return field1 == other.field1
            && Objects.equals(field2, other.field2)
            && ...;
        ```
        如果在子类中重新定义 equals，就要在其中包含调用 super.equals(other)。
    
### `hashcode` 方法

* 散列码（hash code）是由对象导出的一个整形值（可以是负数）。其是没有规律的，如果x与y是两个不同的对象，则x.hashCode()与y.hashCode()基本上不会相同。
* hashCode 方法定义在 Object 类中，因此每个对象都有一个默认的散列码方法，其返回结果是对象的存储地址。
* 一个例子：
    ```java
    String string1 = "hiwangzi";
    StringBuilder stringBuilder1 = new StringBuilder(string1);
    System.out.println(string1.hashCode() + " " + stringBuilder1.hashCode());

    String string2 = new String("hiwangzi");
    StringBuilder stringBuilder2 = new StringBuilder(string2);
    System.out.println(string2.hashCode() + " " + stringBuilder2.hashCode());
    ```
    输出结果：
    ```
    -1232882509 1975012498
    -1232882509 1808253012
    ```
    可以看到，String对象的散列码是相同的，这是因为字符串的散列码是由内容导出的;而StringBuffer对象散列码不同，这是因为StringBuffer类没有定义hashCode()方法，它的散列码是由默认的Object类的默认hashCode()方法导出的对象存储地址。
* 如果重新定义 equals 方法，就必须重新定义 hashCode 方法，以便于可以将对象插入到散列表中。
* 可以调用 Objects.hash 方法并提供多个参数得到散列码（这种做法比较好）：
    ```java
    public int hashCode(){
        return Objects.hash(name, salary, hireDay);
    }
    ```
* equals 与 hashCode 定义必须一致，即 x.equals(y) 与 x.hashCode() == y.hashCode() 结果一致。

### `toString` 方法

* 绝大多数的 toString 方法都遵循这样的格式：类的名字，随后一对方括号括起来的域值。
    ```java
    public String toString(){
        return getClass().getName()
            + "[name=" + name
            + ",salary=" + salary
            + ",hireDay=" + hireDay
            + "]";
    }
    ```
