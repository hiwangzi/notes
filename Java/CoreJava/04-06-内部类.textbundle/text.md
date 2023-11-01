# 04.06 内部类

* 在类的内部还可以定义另一个类。如果在类 `Outer` 的内部再定义一个类 `Inner` ，此时类 `Inner` 就称为内部类，而类 `Outer` 则称为外部类。
* 内部类可声明为 `public` 或 `private`。当内部类声明为 `public` 或 `private` 时，对其访问的限制与成员变量和成员方法完全相同。
* 内部类存在的特点：
  * 缺点：破坏了程序的结构
  * 优点：可以方便的访问外部类中的私有属性
* 四类内部类：
  * 成员内部类
  * 静态内部类
  * 局部内部类
  * 匿名内部类

## 在其他类中调用内部类（成员内部类）

一个内部类除了可以通过外部类访问，也可以直接在其他类中调用（这里指的是在声明了外部类对象后，通过外部类对象调用内部类构造方法来实例化一个内部类对象），调用的格式如下：

```java
外部类.内部类 内部类对象 = 外部类实例.new 内部类();
```

```java
class Outer {                               // 定义外部类
    private String info = "hello world";    // 定义外部类的私有属性
    class Inner {                           // 定义内部类
        public void print() {               // 定义内部类的方法
            System.out.println(info);       // 直接访问外部类的私有属性
        }
    }
    public void fun() {                     // 定义外部类的方法
        new Inner().print();                // 通过内部类的实例化对象调用方法
    }
}
public class InnerClassDemo04 {
    public static void main(String args[]) {
        Outer out = new Outer();            // 外部类实例化对象
        Outer.Inner in = out.new Inner();   // 实例化内部类对象
        in.print();                         // 调用内部类的方法
        // 上面代码的另一种写法
        new Outer().new Inner().print();
    }
}
```

## 使用 static 声明内部类（静态内部类）

* 如果一个内部类使用 `static` 关键字声明，则此内部类称为静态内部类（内部类访问控制是 public 的情况下相当于一个外部类，可以直接通过外部类.内部类进行访问）
* 静态内部类是不依赖于外部类的，也就说可以在不创建外部类对象的情况下创建内部类的对象。另外，静态内部类不持有指向外部类对象的引用（可以反编译代码查看）。

```java
class Outer {                                   // 定义外部类
    private static String info = "hello world"; // 定义外部类的私有属性
    static class Inner {                        // 使用static定义内部类为外部类
        public void print() {                   // 定义内部类的方法
          System.out.println(info);             // 直接访问外部类的静态私有属性
      }
    }
}
public class InnerClassDemo03 {
    public static void main(String args[]) {
      new Outer.Inner().print();
    }
}
```

## 在方法中定义内部类（局部内部类、匿名内部类）

* 一个内部类可以在任意的位置定义，下面在方法中定义内部类，称为**局部**内部类。
  ```java
  class Outer {                                           // 定义外部类
      private String info = "hello world";                // 定义外部类的私有属性
      public void fun(final int temp) {                   // 定义外部类的方法
          class Inner {                                   // 在方法中定义的内部类
              public void print() {                       // 定义内部类的方法
                  System.out.println("类中属性：" + info);  // 直接访问外部类的私有属性
                  System.out.println("方法中参数：" + temp);
              }
          }
          new Inner().print();                            // 通过内部类的实例化对象调用方法
      }
  }
  public class InnerClassDemo05{
      public static void main(String args[]){
          new Outer().fun(30) ;                           // 调用外部类的方法
      }
  }
  ```

* 以上代码还可以写作「匿名内部类」的形式（
  * Java 1.8 后还可以基于函数式接口，用 lambda 表达式形式定义。不过此处Object不是函数式接口，所以无法改写。
  ```java
  class Outer {                                                // 定义外部类
      private String info = "hello world" ;                    // 定义外部类的私有属性
      public void fun(final int temp) {                        // 定义外部类的方法
          new Object() {                                       // 匿名内部类实例话
              public void print() {                            // 定义内部类的方法
                  System.out.println("类中的属性：" + info);     // 直接访问外部类的私有属性
                  System.out.println("方法中的参数：" + temp);
              }
          }.print();
      }
  }
  ```

## 如何实现内部类访问外部类数据的呢？
### 成员内部类
* 成员内部类可以无条件访问外部类的成员。原理如下：
* 编译后会发现，会生成 `Outer.class`, `Outer$Inner.class` 两个文件，反编译 `Outer$Inner.class` 后会发现，它会在构造方法中传入一个 `Outer` 类的对象引用。
### 静态内部类
* 实际上，Java的静态内部类不能直接访问外部类的非静态成员（包括私有成员）。静态内部类只能直接访问外部类的静态成员。
* 这是因为静态内部类是属于外部类本身的，而不是属于外部类的某个实例的。所以静态内部类没有引用到外部类的实例，也就不能访问外部类的实例成员。
* 在Java中，私有成员确实是不可以被其他类访问的。但是，这个规则有一个例外，那就是内部类可以访问其外部类的所有成员，包括私有成员。
* 这是因为内部类被视为其外部类的一部分，所以它可以访问外部类的所有成员。这个特性使得内部类可以方便地访问外部类的状态，而不需要任何特别的访问方法或者修改访问权限。
### 局部内部类、匿名内部类
* 它们也都可以访问外部类的所有成员，包括私有成员，但有一个重要的限制，局部内部类只能访问它所在方法中的 final 或者是有效 final 的局部变量。
* 所谓的effectively final 变量，是指在初始化后就不再改变的变量。这是因为局部内部类的实例可能会在其外部方法已经返回之后还存在，而局部变量在方法返回后就不再存在，所以为了避免数据不一致，Java设计者决定只允许访问 final 或者是有效 final 的局部变量。
* 反编译之后会发现，Java采用了**复制**的方法实现这一点，如果允许修改这个值，就会造成数据不一致，所以要求用`final`限定。
### 参考
* [参考：Java内部类详解 - Matrix海子 - 博客园](https://www.cnblogs.com/dolphin0520/p/3811445.html)
