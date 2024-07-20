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

## 泛型
* Java的泛型是在编译时实现的，被称为类型擦除（Type Erasure）。意味着泛型信息只存在于编译时期，编译完成后所有关于泛型的信息都会被擦除，替换为它们的原生类型或者指定的边界类型。
* 关于擦除：
    * 如果类型参数是无界的，生成的字节码使用`Object`替代泛型。
    * 如有必要，插入类型铸件（第一个边界类）以保持类型安全。
### 泛型边界
* 有时可能希望限制可在参数化类型中用作类型参数的类型。例如，对数字进行操作的方法可能只想接受Number或其子类的实例。这时就需要用到有界类型参数。
* 除了限制可用于实例化泛型类型的类型之外，有界类型参数还允许调用边界中定义的方法：
  ```java
  public class NaturalNumber<T extends Integer> {
    private T n;
    public NaturalNumber(T n) { this.n = n; }
    public boolean isEven() { return n.intValue() % 2 ==0; } // isEven方法通过n调用Integer类中定义的intValue方法
  }
  ```
* 类型参数是可以有多个边界的，例如 `<T extends B1 & B2 & B3>`。但要注意，具有多个边界的类型变量是绑定中列出的所有类型的子类型。如果其中一个边界是类，就必须首先指定它。例如：
  ```java
  Class A { ... }
  interface B { ... }
  interface C { ... }
  class D <T extends A & B & C> { ... } // ✅
  class D <T extends B & A & C> { ... } // ❌会出现编译时错误
  ```
* 另外，有界类型参数中的extends既可以表示“extends”（类中的继承），也可以表示“implements”（接口中的实现）。
### 泛型的继承和子类型
![继承对比](./assets/generics-and-extending.png)
```java
public void someMethod(Number n) { ... }
someMethod(new Integer(10)); // ✅
someMethod(new Double(1.0)); // ✅
```
```java
Box<Number> box = new Box<Number>();
box.add(new Integer(10)); // ✅
box.add(new Double(1.0)); // ✅
```
```java
public void someMethod(Box<Number> n) { ... }
someMethod(new Box<Number>()); // ✅
someMethod(new Box<Integer>()); // ❌
someMethod(new Box<Double>()); // ❌
```
但是可以扩展或实现泛型类、泛型接口，例如下图2-5表示的Java的`Collection`接口，右边的图2-6是如下代码自定义的`PayloadList`：
![Collection与泛型](./assets/generics-for-collection.png)
```java
interface PayloadList<E, P> extends List<E> {
    void setPayload(int index, P val);
}
```
### 通配符
泛型和通配符是两个相关但独立的概念，但它们都是Java泛型系统的一部分，都用于增强代码的类型安全性和重用性。**通配符**是 Java 泛型的一部分，用于表示未知类型。它们主要用在变量声明和方法签名中，表示可以接受泛型参数的不同类型。例如，`List<?>` 表示 "一个未知类型的元素的列表"，可以是 `List<String>`、`List<Integer>` 等。

通配符在 Java 泛型中的使用并不仅限于方法。它们主要用于类型参数化，如在变量声明、方法参数、方法返回类型以及类型转换中。以下是一些例子：

1. **变量声明**：你可以使用通配符来声明一个变量，表示这个变量可以持有任何类型的对象。

```java
List<?> myList; // myList 可以持有任何类型的 List
```

2. **方法参数**：你可以在方法参数中使用通配符，表示这个方法可以接受任何类型的参数。

```java
public void printList(List<?> list) { // 可以接受任何类型的 List
    for (Object item : list) {
        System.out.println(item);
    }
}
```

3. **方法返回类型**：你可以在方法返回类型中使用通配符，表示这个方法可以返回任何类型的对象。

```java
public List<?> getUnknownList() {
    // 返回任意类型的 List
}
```

4. **类型转换**：你可以在类型转换中使用通配符，表示你可以将一个对象转换为任何类型的对象。

```java
List<?> myList = (List<?>) someObject; // 将 someObject 转换为任意类型的 List
```

然而，虽然通配符提供了很大的灵活性，但它也有一些限制。例如，你不能用通配符来创建一个新的实例（如 `new List<?>()` 是不合法的）。另外，因为通配符表示未知类型，所以你通常不能将对象添加到 `List<?>` 中（除了 `null` 之外）。

#### 三种通配符示例
Java 泛型中的通配符 `?` 主要用于增加代码的灵活性，并允许泛型类型的变化。

有三种主要的通配符：

1. **无界通配符（Unbounded Wildcard）**： `?`。表示可以接受任何类型。这在你需要操作但不关心实际类型的集合时非常有用。

```java
public void printList(List<?> list) {
    for (Object item : list) {
        System.out.println(item);
    }
}
```

2. **上界通配符（Upper Bounded Wildcard）**： `? extends T`。表示可以接受任何 T 类型或其子类。这在你需要使用特定类及其子类的通用方法时非常有用。

```java
public double sumOfList(List<? extends Number> list) {
    double sum = 0.0;
    for (Number n : list) {
        sum += n.doubleValue();
    }
    return sum;
}
```

在这个例子中，我们可以传递任何 `Number` 的子类的 `List`（例如 `Integer`、`Double` 等）到 `sumOfList` 方法。

3. **下界通配符（Lower Bounded Wildcard）**： `? super T`。表示可以接受 T 类型或其父类。这在你需要将对象写入具有特定类型或其父类型的集合时很有用。

```java
public void addNumbers(List<? super Integer> list) {
    for (int i = 1; i <= 10; i++) {
        list.add(i);
    }
}
```

在这个例子中，我们可以将 `Integer` 对象添加到任何 `Integer` 的父类（如 `Number` 或 `Object`）的 `List` 中。

总的来说，Java 泛型中的通配符为我们提供了更大的灵活性，使我们能够编写更加通用和可复用的代码。
#### 使用通配符处理继承
```java
List<Integer> intList = new ArrayList<>();
List<Number> numList = intList; // ❌

List<? extends Integer> intChildList = new ArrayList<>();
List<? extends Number> numChildList = intChildList; // ✅

intChildList = intList; // ✅
List<Number> numList2 = new ArrayList<>();
numChildList = numList2; // ✅

List<? super Integer> intParentList = intList; // ✅
```
![list-number-and-list-integer](assets/list-number-and-list-integer.png)
![list-extends-super](assets/list-extends-super.png)

### 使用泛型的一些限制
1. 无法创建类型参数的实例
    ```java
    public static <E> void append(List<E> list) {
        E element = new E(); // ❌ 编译时错误
        list.add(element);
    }
    ```
    ```java
    public static <E> void append(List<E> list, Class<E> cls) throws Exception {
        E element = cls.newInstance(); // ✅
        list.add(element);
    }
    ```
2. 无法声明类型为类型参数的静态字段
    ```java
    class MobileDevice<T> {
        private static T device; // ❌ 因为不确定T是什么类型，无法静态
    }
    ```
3. 因为类型擦除，无法强制转换类型参数，或者使用 `instanceOf`
    ```java
    public static <E> void instanceCheck(List<E> list) {
        System.out.println(list instanceof List<Integer>); // ❌
        System.out.println(list instanceof List<?>); // ✅
        System.out.println(list instanceof ArrayList<Integer>); // ❌
        System.out.println(list instanceof ArrayList<?>); // ✅
    }

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>();
        System.out.println(intList instanceof ArrayList<Number>);
        List<Number> numList = (List<Number>) intList; // ❌
        ArrayList<Integer> intArrayList = (ArrayList<Integer>) intList; // ✅
    }
    ```

### 编写泛型数组代码
#### 例子目标：实现一个通用的数组拷贝方法
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

#### 限制下的 `badCopyOf`
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

#### 利用反射实现 `goodCopyOf`

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
