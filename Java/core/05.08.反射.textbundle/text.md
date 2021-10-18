# 反射

* 能够分析类能力的程序称为反射(reflective)，其可以：
  * 在运行中分析类
  * 在运行中查看对象
  * 编写泛型数组代码
  * 调用任意方法
* `java.lang.Class` 类用于描述 JVM 中使用的类。

## 在运行中分析类

* `java.lang.reflect`中三个重要的类`Field`、`Method`、`Constructor`分别用于描述类的域、方法、构造器。
* `Class`类对象的
    * `getFields`、`getMethods`、`getConstructors`方法将分别返回类提供的`public` 域、方法和构造器数组（包含超类的公有成员）
    * `getDeclaredFields`、`getDeclaredMethods`、`getDeclaredConstructors`方法将分别返回类中声明的全部域、方法和构造器数组（但不包含超类的成员）。

## 在运行中分析对象

* 在获取`Field`对象后，通过其`get`方法，可以得到某个对象该字段的值。
* 直接访问私有域，`get`方法会抛出`IllegalAccessException`异常。但如果一个Java程序没有受到安全管理器的控制，就可以通过`Field`对象的`setAccessible`方法覆盖访问控制（`Method`、`Constructor`同理）。
* 通过`get`方法得到的是`Object`。如果字段为基本类型，会将其打包为包装类型，并以`Object`形式返回。
* 通过`set`方法可以修改字段的值。

## 编写泛型数组代码

* 将一个 `Employee[]` 临时地转换成 `Object[]` 数组， 然后再把它转换回来是可以的， 但最开始就是 `Object[]` 的数组却永远不能转换成 `Employe[]` 数组。（但可以进行单个元素的转换）
    ```java
    Employee[] employees = new Employee[10];
    Object[] objects = employees;
    System.out.println((Employee[]) objects); // 可以转换

    objects = new Object[10];
    System.out.println((Employee[]) objects); // java.lang.ClassCastException
    ```
* 以下是利用反射实现任意类型数组拷贝的例子：
    ```java
    private static Object goodCopyOf(Object array, int newLength) {
        Objects.requireNonNull(array);
        Class clazz = array.getClass();
        if (clazz.isArray()) {
            Class componentTypeClazz = clazz.getComponentType();
            int length = Array.getLength(array);
            Object newArray = Array.newInstance(componentTypeClazz, newLength);
            System.arraycopy(array, 0, newArray, 0, Math.min(length, newLength));
            return newArray;
        } else {
            throw new IllegalArgumentException("First parameter should be an array.");
        }
    }
    ```

## 调用任意方法

```java
// `name` 为方法名，`parameterTypes`为该方法的参数类型（因为可能存在重载）

Method getMethod(String name, Class... parameterTypes) // 该方法属于`Class`类的实例对象
```

```java
// `obj` 为该方法所属的对象，如果为静态方法，传入`null`即可，后面的`args`为该方法所需的参数
// 返回值如果为基本类型，会自动包装后返回

public Object invoke(Object obj, Object... args) // 该方法属于 `Method` 类的实例对象
```
