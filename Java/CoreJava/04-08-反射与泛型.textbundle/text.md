# 04.08 反射与泛型

## 反射
* 反射库（reflection library）提供了一个非常丰富且精心设计的工具集，以便编写能够动态操纵 Java 代码的程序。
* 能够分析类能力的程序称为反射(reflective)，其可以：
  * 在运行中分析类
  * 在运行中查看对象
  * 编写泛型数组代码
  * 调用任意方法
* `java.lang.Class` 类用于描述 JVM 中使用的类。
* 不要过多地使用反射，其不适合编写应用程序。反射是很脆弱的，编译器很难帮助人们发现程序中的错误，因此很有可能会导致运行时出现异常。

### 在运行中分析类
* 获取`Class`对象的三种方式（JVM为每个类型管理一个 Class 对象）：
    ```java
    // 1. 通过实例对象获取
    Random random = new Random();
    Class cl = random.getClass();
    System.out.println(cl.getName()); // 输出为 java.util.Random

    // 2. 通过静态方法 forName 获得类名对应的 Class 对象
    Class cl = Class.forName("java.util.Random");
    
    // 3. 通过`类.class`获取（基本类型也可以，例如下面的cl2）
    Class cl1 = Random.class;
    Class cl2 = int.class;
    Class cl3 = Double[].class;

    // 历史原因，getName 方法有时会返回一个奇怪的名字
    Double[].class.getName(); // 返回结果为 "[Ljava.lang.Double;"
    int[].class.getName(); // 返回结果为 "[I"
    ```
* `java.lang.reflect`中三个重要的类`Field`、`Method`、`Constructor`分别用于描述类的域、方法、构造器。
* `Class`类对象的
  * `getFields`、`getMethods`、`getConstructors`方法将分别返回类提供的`public` 属性、方法和构造器数组（包含超类的公有成员）
  * `getDeclaredFields`、`getDeclaredMethods`、`getDeclaredConstructors`方法将分别返回类中声明的全部域、方法和构造器数组（但不包含超类的成员）。
* 可以通过 newInstance 方法动态地创建一个类的实例
    ```java
    e.getClass().newInstance(); // 调用默认的无参构造器
    ```
  * 如果没有无参构造，则会抛出异常; 而对于基本类型来说，例如：`int.class.newInstance()` 会抛出 `InstantiationException` 异常
  * 如果希望提供参数，则应使用 Constructor 类中的 newInstance 方法。

### 在运行中分析对象
* 在获取`Field`对象后，通过其`get`方法，可以得到某个对象该字段的值。
* 直接访问私有域，`get`方法会抛出`IllegalAccessException`异常。但如果一个Java程序没有受到安全管理器的控制，就可以通过`Field`对象的`setAccessible`方法覆盖访问控制（`Method`、`Constructor`同理）。
* 通过`get`方法得到的是`Object`。如果字段为基本类型，会将其打包为包装类型，并以`Object`形式返回。
* 通过`set`方法可以修改字段的值。
* 在编码，如果知道想要查看的对象的属性名称，查看指定的属性值非常容易，而利用反射机制可以查看及修改在编译时还不清楚的对象属性。
    ```java
    Employee tank = new Employee("Peppa", 35000, 6, 4, 1989);
    Class cl = tank.getClass();
    Field[] fields = cl.getDeclaredFields();
    if (fields.length > 0) {
        Field f = fields[0];
        f.setAccessible(true); // 使得可以访问私有域
        System.out.println(f.getName() + ": " + f.get(tank)); // 结果为"name: man"
        f.set(tank, "hero");   // 修改域
        System.out.println(f.getName() + ": " + f.get(tank)); // 结果为"name: hero"
    }
    ```
### 调用任意方法
```java
// `name` 为方法名，`parameterTypes`为该方法的参数类型（因为可能存在重载）

Method getMethod(String name, Class... parameterTypes) // 该方法通过「Class对象.getMethod」调用
```

```java
// `obj` 为该方法所属的对象，如果为静态方法，传入`null`即可，后面的`args`为该方法所需的参数
// 返回值如果为基本类型，会自动包装后返回

public Object invoke(Object obj, Object... args) // 该方法通过「Method对象.invoke」调用
```

## 编写泛型数组代码
### 例子目标：实现一个通用的数组拷贝方法
```java
public static void main(String[] args) {
    int[] a = {1, 2, 3};
    a = (int[]) goodCopyOf(a, 10);
    System.out.println(Arrays.toString(a));
    
    String[] b = {"Andy", "Haley", "Tom", "Jerry"};
    b = (String[]) goodCopyOf(b, 10);
    System.out.println(Arrays.toString(b));
    
    b = (String[]) badCopyOf(b, 10); // 将抛出异常，原因见下面
}
```

### 限制下的 `badCopyOf`
* 将一个对象数组（`String[]`）临时地转换成 `Object[]` 数组， 然后再把它转换回来是可以的。
* 但***最开始***就是 `Object[]` 的数组却永远不能转换成对象数组。
* 不过可以通过进行单个元素的转换，循环处理得到目标类型数组。
    ```java
    import java.util.Arrays;

    String[] strArray = {"Tom", "Jerry"};
    Object[] objArray = (Object[]) strArray;
    Arrays.toString(objArray);                  // ✅ "[Tom, Jerry]"
    Arrays.toString((String[]) objArray);       // ✅ "[Tom, Jerry]"

    Object[] objArrayCopy = new Object[2];
    System.arraycopy(strArray, 0, objArrayCopy, 0, strArray.length);
    Arrays.toString(objArrayCopy);              // ✅ "[Tom, Jerry]"
    Arrays.toString((String[]) objArrayCopy);   // ❌ java.lang.ClassCastException: class [Ljava.lang.Object; cannot be cast to class [Ljava.lang.String; ([Ljava.lang.Object; and [Ljava.lang.String; are in module java.base of loader 'bootstrap')

    String[] strArrayCopy = new String[2];
    for (int i = 0; i < objArrayCopy.length; i++) {
        strArrayCopy[i] = (String) objArrayCopy[i];
    }
    Arrays.toString(strArrayCopy);              // ✅ "[Tom, Jerry]"
    ```
* 因此，下面方法的返回值的数组类型 `Object []` 不能直接被转换为目标类型数组：
    ```java
    public static Object[] badCopyOf(Object[] a, int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(a, 0, newArray, 0, Math.min(a.length, newLength));
        return newArray;
    }
    ```

### 利用反射实现 `goodCopyOf`

```java
// 1. 参数 array 的类型为 Object，而不是 Object[] 的原因：整型数组类型int[]可以被转换成Object，但不能转换成对象数组。
// 2. 返回值的类型为 Object，而不是 Object[] 的原因：最开始就定义为 `Object []` 的数组不能直接被转换为目标类型数组。
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
        throw new IllegalArgumentException("The first parameter should be an array.");
    }
}
```
