# 04.03 Override的补充

## 方法的覆写

* 当子类定义了与父类方法名称相同、参数的类型及个数、**返回值相同**的方法时，就被称为方法的覆写。

* 被覆写的方法不能拥有比父类方法更严格的访问权限。`private < default < protected < public`
  * 如果父类中的方法使用了 `private` 声明，而子类中同样的方法使用了 `public` 声明，这样不属于override。

    ```java
    class A {
        public void fun() {
            print();
        }
        /*①*/ void print() {
            System.out.println("父类中的 print() 方法");
        }
    }
    class B extends A {
        /*②*/ void print() { // 覆写的是 print() 方法
            System.out.println("子类中的 print() 方法");
        }
    }
    public class OverrideDemo {
        public static void main(String [] args) {
            B b = new B();
            b.print();
            b.fun();
        }
    }
    // ① public; ② public 属于重写，输出结果为“子类；子类”
    // ① public; ② private 不属于重写，因为不符合重写原则，无法编译通过
    // ① private; ② public 不属于重写，因为如果父类中方法使用了 private 声明，
    //                       那这个方法对于子类而言是不可见的，即使它符合了覆写的访问限制要求，
    //                       但仍然不能够动态的实现重写的效果，输出结果为“子类；父类”。
    //                       此时子类中的方法相当于子类新定义了一个方法，与父类中的同名方法无关
    ```

#### 关于 `this.方法()` 与 `super.方法()`

* 使用 `this.方法()` 会首先查找本类中是否存在有要调用的方法名称，如果存在则直接调用，如果不存在则查找父类中是否具有此方法，如果有就调用，如果没有，则会出现编译时错误。
* 使用 `super.方法()` 明确的表示调用的方法不是子类中的方法（不查找子类），而直接调用父类中的指定方法。

### 属性的“覆盖”（隐藏）

* 字段（也称为属性或成员变量）没有 "override" 的概念。只有方法可以被重写（override）。字段的 "隐藏" 是一个不同的概念。
* 如果在子类中声明了一个和父类中同名的字段，那么子类的这个字段会隐藏父类的同名字段。这并非重写（override），而是称为隐藏（hide）。这是因为字段的解析是静态的，也就是说，它是在编译时确定的，而不是在运行时确定的。
* 与方法的覆盖表现不同，不能实现「动态多态」。

```java
class A {
    String info = "Hello";
}
class B extends A {
    int info = 100; //名称与父类中变量相同
    public void print(){
        System.out.println(super.info);
        System.out.println(this.info);
    }
}
public class VarOverrideDemo {
    public static void main(String [] args) {
        B b = new B();
        b.print();
    }
}
// 输出
// Hello
// 100
```

```java
class Parent {
    public int x = 0;
}

class Child extends Parent {
    public int x = 1;
}

public static void main(String[] args) {
    Parent parent = new Parent();
    Child child = new Child();
    Parent parentChild = new Child();

    System.out.println("parent.x = " + parent.x); // 输出 "parent.x = 0"
    System.out.println("child.x = " + child.x); // 输出 "child.x = 1"
    System.out.println("parentChild.x = " + parentChild.x); // 输出 "parentChild.x = 0"
}
// 这里的 parentChild 实际上是一个 Child 对象，但是我们将它向上转型为 Parent 类型。
// 因此，当我们通过 parentChild 访问 x 字段时，我们实际上访问的是 Parent 类中的 x 字段，而不是 Child 类中的 x 字段。
```

其实在实际开发中，这种特性意义不是很大，因为绝大多数情况，属性都是被封装的，而被限制为 `private` 后，对于子类而言不可见，因此不会相互影响。

## 关于 `this` 与 `super` 的小总结

|区别|this|super|
|---|----|------|
|功能|调用本类构造、本类方法、本类属性|子类调用父类构造、父类方法、父类属性|
|形式|先查找本类中时候存在指定的调用结构，有则调用，无则调用父类定义|不查找子类，直接调用父类调用结构|
|特殊|表示本类的当前对象||

建议在开发中，加上 `this.` 或者 `super.`，这样便于区分。
