# 03.03 方法的声明及使用

## 方法的重载

方法名称相同，但 **参数的类型** 或 **参数的个数** 不同，通过传递参数的个数或类型的不同完成不同功能的方法调用。

例如：```System.out.println()```

## 使用 return 结束一个方法

`return` 除了可以用来返回内容，也可以结束方法。

例如：

```java
public class ExampleReturn{
    public static void main(String args[]){
        System.out.println("1. 调用fun()方法之前。");
        fun(10);
        System.out.println("2. 调用fun()方法之后。");
    }
    public static void fun(int x){
        System.out.println("3. 进入fun()方法。");
        if(x == 10){
            return;//结束方法，返回被调用处
        }
        System.out.println("4. 正常执行完fun()方法。");
    }
}
// 结果
// 1. 调用fun()方法之前。
// 3. 进入fun()方法。
// 2. 调用fun()方法之后。
```