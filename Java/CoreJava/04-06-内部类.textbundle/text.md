# 04.06 内部类

在类的内部还可以定义另一个类。如果在类 `Outter` 的内部再定义一个类 `Inner` ，此时类 `Inner` 就称为内部类，而类 `Outter` 则称为外部类。
内部类可声明为 `public` 或 `private`。当内部类声明为 `public` 或 `private` 时，对其访问的限制与成员变量和成员方法完全相同。

```java
class Outer {                               // 定义外部类
    private String info = "hello world";    // 定义外部类的私有属性
    class Inner {                           // 定义内部类
        public void print(){                // 定义内部类的方法
            System.out.println(info);       // 直接访问外部类的私有属性
        }
    }
    public void fun() {                     // 定义外部类的方法
        new Inner().print();                // 通过内部类的实例化对象调用方法
    }
}
public class InnerClassDemo01 {
    public static void main(String args[]){
        new Outer().fun();                  // 调用外部类的fun()方法
    }
}

// 以上程序中，Inner 类作为 Outter 的内部类存在，
// 并且在外部类的 fun() 方法之中直接实例化内部类的对象并调用 print() 方法。
```

内部类存在的特点：

* 缺点：破坏了程序的结构
* 优点：可以方便的访问外部类中的私有属性

## 在其他类中调用内部类

一个内部类除了可以通过外部类访问，也可以直接在其他类中调用（这里指的是在声明了外部类对象后，通过外部类对象调用内部类构造方法来实例化一个内部类对象），调用的格式如下：

```java
外部类.内部类 内部类对象 = 外部类实例.new 内部类();
```

```java
class Outer {                               // 定义外部类
    private String info = "hello world";    // 定义外部类的私有属性
    class Inner{                            // 定义内部类
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
        new Outer().new Inner().print();    // 与上面的代码等效
    }
}
```

## 使用 static 声明内部类

如果一个内部类使用 `static` 关键字声明，则此内部类称为静态内部类（内部类访问控制是 public 的情况下相当于一个外部类，可以直接通过外部类.内部类进行访问）

```java
class Outer{                                    // 定义外部类
    private static String info = "hello world"; // 定义外部类的私有属性
    static class Inner {                        // 使用static定义内部类为外部类
        public void print() {                   // 定义内部类的方法
            System.out.println(info);           // 直接访问外部类的私有属性
        }
    }
}
public class InnerClassDemo03 {
    public static void main(String args[]) {
        new Outer.Inner().print();
    }
}
```

## 在方法中定义内部类

一个内部类可以在任意的位置定义，下面在方法中定义内部类。

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
        new Inner().print() ;                           // 通过内部类的实例化对象调用方法
    }
}
public class InnerClassDemo05{
    public static void main(String args[]){
        new Outer().fun(30) ;                           // 调用外部类的方法
    }
}
```

以上代码还可以写作「匿名对象」的形式：

```java
class Outer{                                                // 定义外部类
    private String info = "hello world" ;                   // 定义外部类的私有属性
    public void fun(final int temp){                        // 定义外部类的方法
        new Object(){
            public void print(){                            // 定义内部类的方法
                System.out.println("类中的属性：" + info);    // 直接访问外部类的私有属性
                System.out.println("方法中的参数：" + temp);
            }
        }.print();
    }
}
```

补充：Java 1.8 后还可以考虑使用 lambda 表达式。