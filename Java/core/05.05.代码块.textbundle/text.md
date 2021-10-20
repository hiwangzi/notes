# 05.05 代码块

代码块就是使用 `{}` 括起来的一段代码，根据位置不同，可分为四类： 

* 普通代码块
* 构造代码块
* 静态代码块
* 同步代码块

## 普通代码块

直接定义在方法中的代码块称为普通代码块。

```java
public class Demo {
    public static void main(String args[]) {
        { // 普通代码块
            int x = 30; // 属于局部变量
            System.out.println("普通代码块 --> x = " + x);
        }
        int x = 100; // 与局部变量名称相同
        System.out.println("代码块之外 --> x = " + x);
    }
}
// 输出
// 普通代码块 --> x = 30
// 代码块之外 --> x = 100
```

## 构造代码块

直接在类中定义的代码块，称为构造块。

```java
public class Demo {
    public static void main(String args[]) {
        new JustClass(); // 实例化对象
        new JustClass(); // 实例化对象
        new JustClass(); // 实例化对象
    }
}

class JustClass {
    { // 直接在类中编写代码块，称为构造块
        System.out.println("1. 构造块。");
    }

    public JustClass() { // 定义构造方法
        System.out.println("2. 构造方法。");
    }
}
// 输出
// 1. 构造块。
// 2. 构造方法。
// 1. 构造块。
// 2. 构造方法。
// 1. 构造块。
// 2. 构造方法。
```

可以发现，

1. **构造块优先于构造方法执行**（*代码先后顺序无影响*），且执行多次。
2. **只要有**实例化对象产生，**就执行**构造块中的语句。 

## 静态代码块

使用 `static` 关键字声明的代码块就称为静态代码块。

```java
public class Demo {
    static { // 在主方法所在的类中定义静态块
        System.out.println("在主方法所在类中定义的代码块");
    }

    public static void main(String args[]) {
        new JustClass(); // 实例化对象
        new JustClass(); // 实例化对象
        new JustClass(); // 实例化对象
    }
}

class JustClass {
    { // 直接在类中编写代码块，称为构造块
        System.out.println("1. 构造块。");
    }

    static { // 使用static，称为静态代码块
        System.out.println("0. 静态代码块");
    }

    public JustClass() { // 定义构造方法
        System.out.println("2. 构造方法。");
    }
}
// 输出
// 在主方法所在类中定义的代码块
// 0. 静态代码块
// 1. 构造块。
// 2. 构造方法。
// 1. 构造块。
// 2. 构造方法。
// 1. 构造块。
// 2. 构造方法。
```

注意：

* 静态块优先于主方法执行。
* 不管有多少个实例化对象产生，静态代码块**只执行一次**。
* 静态代码块的主要功能是为静态属性初始化。