# 04.02 继承与多态

* Java 中只允许单继承。
    ![多重继承 vs 多层继承](./assets/1.png)
* 继承的UML类图表示，[关于UML](../04-07-UML.textbundle/text.md)
    ![继承的UML类图表示](./assets/2.png)
* 在使用继承的时候应该注意的是：子类不能直接访问父类中的私有成员，但是子类可以调用父类中的非私有方法。（详见下文[访问控制](#访问控制)）
* 使用`super()`方法调用父类构造函数
    ```java
    class A {
        A(String str) {
            System.out.println(str);
        }
    }
    class B extends A {
        B() {
            // 1. 因为A类缺少默认构造方法，所以必须显示调用父类A的构造函数，
            //    否则无法编译通过。
            // 2. 另外，使用super显式调用父类构造函数时，必须在方法体首行。
            super("default"); 

            System.out.println("Hello");
            System.out.println("World");
        }
        B(String str) {
            // 此处调用本类的无参构造函数，已经传递调用了父类A的构造函数。
            this();
        }
    }
    public class SuperTest {
        public static void main(String [] args) {
            B b = new B("Hi");
        }
        // 输出结果如下：
        // default
        // Hello
        // World
    }
    ```
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
* 包含一个或多个抽象方法的类本身必须被声明为抽象的（abstract）。
    * 除了抽象方法，抽象类还可以包含具体数据和具体方法。
    * 扩展抽象类有两种选择：
      * 一是部分实现抽象方法，此时子类仍需标记为抽象类；
      * 二是定义全部抽象方法。
    * 类即使不包含抽象方法，也可以将类声明为抽象类。
    * 抽象类不能被实例化。

## 访问控制
* 仅对本类可见—— `private`
* 对本包可见——默认，不需要修饰符（package private）
* 对本包和所有子类可见—— `protected`
* 对所有类可见—— `public`
* 如果没有明确地指出父类，则Object就被认为是这个类的超类，其常见几个方法：
    * [equals 方法](#equals-方法)
    * [hashcode 方法](#hashcode-方法)
    * [toString 方法](#tostring-方法)
* 关于 `protected` 的补充：
    * 即使两个子类继承自同一个父类，也不能在一个子类中访问另一个子类的`protected`方法
    * > Protected access requires a little more elaboration. Suppose class A declares a protected field x and is extended by a class B, which is defined in a different package (this last point is important). Class B inherits the protected field x, and its code can access that field in the current instance of B or in any other instances of B that the code can refer to. This does not mean, however, that the code of class B can start reading the protected fields of arbitrary instances of A! If an object is an instance of A but is not an instance of B, its fields are obviously not inherited by B, and the code of class B cannot read them.
      >
      > ——《Java in a Nutshell》
      * 这里的关键是理解Java如何处理在继承关系中的protected访问级别。当你有一个继承了类A的类B，并且类B想要访问一个protected字段（在这个例子中是x），那么这个访问只能在类B的实例上进行。这是Java的设计决定，用于提供更严格的数据封装。 
      * 以下面的例子解释一下：
        1. 类B可以访问其自身的`x`字段，因为它继承了类A的`x`字段。
        2. 类B的实例也可以访问其他B实例的`x`字段，因为这些字段都是从类A继承来的。
        3. 类B的实例不能访问仅仅是类A实例的`x`字段。这是因为从面向对象的角度来看，类B的实例和类A的实例是不同的实体，即使它们有共享的基类。在Java中，一个子类的实例不能访问另一个类实例中的`protected`字段，除非那个类实例也是子类的实例。
      * 这个规则可能看起来有点违反直觉，因为从逻辑上讲，我们可能会认为，“因为B继承了A，那么B应该可以访问任何A的实例的x字段”。但在Java中，访问控制的规则更加严格，并且重在保护数据的封装性。 
      * 这是Java设计者做出的决定，可能与其他编程语言（如C++）的做法有所不同。在C++中，一个子类可以访问任何父类实例的protected字段，而不仅仅是子类实例的。但在Java中，这样的访问是被禁止的。
      * ```java
        // 在包 com.example 中
        package com.example;
        
        public class A {
            protected int x;
        }
        
        // 在包 com.example.sub 中
        package com.example.sub;
        
        import com.example.A;
        
        public class B extends A {
            void method(A a, B b) {
                a.x = 1;  // 这将会出错，因为 a 不一定是B的实例
                b.x = 2;  // 这是允许的，因为 b 是B的实例
            }
        }
        ```
      * 但是如果类A和类B位于同一个包中，那么即使字段是`protected`，类B也可以访问任何A的实例的该字段。这是因为在Java中，`protected`权限实际上允许同一包内的所有类访问该字段，**无论它们是否具有继承关系**。
### `equals` 方法

* equals 方法示例
```java
// 代码来自《Java核心技术 卷I 第十版》P167
// Parent Class
public class Employee {
    ...
    public boolean equals(Object otherObject) {
        // a quick test to see if the objects are identical
        if (this == otherObject) return true;

        // must return false if the explicit parameter is null
        if (otherObject == null) return false;

        // if the classes don't match, they can't be equal
        // 笔者注：子类Manager通过super.equals方法调用到此处时，
        //        getClass()的结果是子类，这是动态绑定（多态）的体现
        if (getClass() != otherObject.getClass())
            return false;

        // now we know otherObject is a non-null Employee
        Employee other = (Employee) otherObject;

        // test whether the fields hava identical values
        // 笔者注：此处使用Objects.equals方法是为了防备name或hireDay可能为null的情况
        return Objects.equals(name, other.name) &&
                salary == other.salary &&
                Objects.equals(hireDate, other.hireDate);
    }
}
// Child Class
// 1. 先调用父类的equals，如果返回false，对象则不可能相等
// 2. 如果父类中的域都相等，再比较子类中新增的实例域
public class Manager extends Employee {
    ...
    public boolean equals(Object otherObject) {
        if (!super.equals(otherObject)) return false;
        // super.equals checked that this and otherObject belong to the same class
        Manager other = (Manager) otherObject;
        return bonus == other.bonus;
    }
}
```

* Java 语言规范要求 equals 方法具有以下特性：
    * 自反性：`x.equals(x)` 应当返回 `true`
    * 对称性：`x.equals(y)` 与 `y.equals(x)` 返回应当相同
    * 传递性：如果 `x.equals(y)` 返回 true，且 `y.equals(z)` 也返回 `true`，则 `x.equals(z)` 也应返回 `true`
    * 一致性：如果 `x` 与 `y` 引用的对象没有发生变化，则 `x.eqauls(y)` 也不应变化
    * 对于任意的非空引用 `x`，`x.equals(null)` 应当返回 false

* 在上面的例子中，`if(getClass() != otherObject.getClass()) return false;` 如果发现类型不一致，就返回 false。
    * 谨慎使用 `instanceof` 检测，例子如下。
    * 但有一些程序员喜欢采用以下代码进行检测 `if(!(otherObject instanceof Employee)) return false;` 但这样存在问题，父类对象与子类对象比较时，不满足对称性，如下示例代码：
        ```java
        import java.util.Objects;

        public class SuperTest {
            public static void main(String[] args) {
                Parent parent = new Parent("Hi");
                Child child = new Child("Hi");
                System.out.println(parent.equals(child));
                System.out.println(child.equals(parent));
            }
        }

        class Parent {
            private final String strParent;

            Parent(String str) {
                this.strParent = str;
            }

            @Override
            public boolean equals(Object otherObject) {
                if (this == otherObject) return true;
                if (otherObject == null) return false;

                // ⬇️ 最终输出会得到 true、java.lang.ClassCastException，不满足对称性 ❌
                // child instanceof Parent -> true
                // parent instanceof Parent -> true
                if (!(otherObject instanceof Parent)) return false;

                // ⬇️ 使用该种判断，最终输出会得到 false、false ✅
                // if (getClass() != otherObject.getClass()) return false;
                Parent other = (Parent) otherObject;
                return Objects.equals(strParent, other.strParent);
            }
        }

        class Child extends Parent {

            private final String strChild;

            Child(String str) {
                super(str);
                this.strChild = str;
            }

            @Override
            public boolean equals(Object otherObject) {
                // 执行 child.equals(parent) 时，super的比较 parent instanceof Parent -> true
                // 因此这里的检测失灵，导致后面的类型转换异常
                if (!super.equals(otherObject)) return false;
                Child other = (Child) otherObject;
                return Objects.equals(strChild, other.strChild);
            }
        }
        ```

* 关于 `getClass` 与 `instanceof` 两种检测方法：
    * 如果 Child Class 能够拥有自己的相等概念，则对称性需求将强制采用 `getClass` 进行检测。
    * 如果由 Parent Class 决定相等的概念，那么就可以使用 `instanceof` 进行检测，这样可以在不同子类的对象之间进行相等的比较。
        ```java
        // 为了实现「由Parent Class决定相等的概念」，可以直接移除 Child Class 的 equals 重写，
        // 并将 Parent Class 中 equals 方法标记为 final。
        // 因为既然都是Parent Class决定，Child Class没有必要进行重写操作，推荐这种方式 👍
      
        @Override
        public boolean equals(Object otherObject) {
            if (!super.equals(otherObject)) return false;
            // 妄图重写后，通过在子类 equals 中增加 instance 判断（下面这行代码），是不行的，
            // 这样 childObj.equals(parentObj) 就不符合「由Parent Class决定相等的概念」要求了
            if (!(otherObject instanceof Child)) return true;
            Child other = (Child) otherObject;
            return Objects.equals(strChild, other.strChild);
            // 如果重写了，就什么不要做，直接 return super.equals(otherObject)
            // 但是这和没有这个方法的效果是一样的，干嘛要画蛇添足呢 😁
        }
        ```
    * 造成二者之间差异的**根本原因**：
        * `childObj instanceof ParentClass`结果为true，`parentObj instanceof ChildClass`结果为false
        * `getClass`得到的结果是动态绑定后的子类

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
       如果在子类中重新定义 `equals`，就要在其中包含调用 `super.equals(other)`。

### `hashCode` 方法

* 散列码（hash code）是由对象导出的一个整形值（可以是负数）。是***没有规律***的，如果`x`与`y`是两个不同的对象，则`x.hashCode()`与`y.hashCode()`基本上不会相同。
* `hashCode` 方法定义在 `Object` 类中，因此每个对象都有一个默认的散列码方法，其返回结果是对象的**存储地址**。
* 一个例子：
  * 代码：
      ```java
      String string1 = "hiwangzi";
      StringBuilder stringBuilder1 = new StringBuilder(string1);
      System.out.println(string1.hashCode() + " " + stringBuilder1.hashCode());

      String string2 = new String("hiwangzi");
      StringBuilder stringBuilder2 = new StringBuilder(string2);
      System.out.println(string2.hashCode() + " " + stringBuilder2.hashCode());
      ```
  * 输出结果：
      ```plain
      -1232882509 1975012498
      -1232882509 1808253012
      ```
  * 可以看到，String 对象的散列码是相同的，这是因为字符串的散列码方法override过，是基于字符串的内容生成的；而 StringBuffer 对象散列码不同，这是因为 StringBuffer 类没有定义 `hashCode()` 方法，它的散列码是由默认的 Object 类的默认 `hashCode()` 方法导出的对象存储地址。
* 如果重新定义 `equals` 方法，就***必须***重新定义 `hashCode` 方法，以便于可以将对象插入到散列表中。
* 可以调用 `Objects.hash` 方法并提供多个参数得到散列码（这种做法比较好）：
    ```java
    public int hashCode(){
        return Objects.hash(name, salary, hireDay);
    }
    ```
* `equals` 与 `hashCode` 行为***必须***一致，即 x.equals(y) 与 x.hashCode() == y.hashCode() 结果一致。

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
