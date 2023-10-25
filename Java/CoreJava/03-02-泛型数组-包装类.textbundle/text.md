# 03.02 泛型数组、包装类

## 泛型数组列表

* ArrayList 是一个采用类型参数（type parameter）的泛型类（generic class）。
    ```java
    ArrayList<Employee> staff = new ArrayList<Employee>();    
    ArrayList<Employee> staff = new ArrayList<>(); // JavaSE 7 之后可以简写
    ```
* JavaSE 5.0 以前的版本没有提供泛型类，而是有一个 ArrayList 类，其中保存类型为 Object 的元素。

* 如果已经清楚或者能够估计数组可能的存储元素数量，就可以在填充数组之前调用 ```ensureCapacity``` 方法
    ```java
    staff.ensureCapacity(100);
    ArrayList<Employee> staff = new ArrayList<>(100); // 或者在构造时就传入初始容量（超过100个之后自动扩展）
    ```
  这样做可以预先分配100个内存空间，而不用在每次 add 的时候再去分配。

* 一旦可以确认数组列表的大小不再发生变化，就可以调用 ```trimToSize``` 方法，其可以将存储区域的大小调整为当前元素数量所需要的存储空间数目，垃圾回收器将回收多余的存储空间。但要注意，在整理了存储空间大小之后，添加新元素就需要再次花费时间移动存储块。

* 常用方法
    * `boolean add(E obj)` // 在数组列表尾端添加一个元素，永远返回 true
    * `void add(int index, E obj)`
    * `void set(int index, E obj)` // 替换已经存在的元素内容
    * `E get(int index)`
    * `E remove(int index)` // 删除一个元素，并返回这个被删除的元素
    * `int size()` // 返回数组列表中当前元素数量

## 对象包装器与自动装箱

* 所有的基本类型都有一个与之对应的类。这些类称为包装器（wrapper）。
    * `Integer`, `Long`, `Float`, `Double`, `Short`, `Byte`, `Character`, `Void`, `Boolean`（前6个类派生于公共的超类Number）
    * 对象包装器类是不可变的（一旦构造了包装器，就不允许更改包装在其中的值）（个人认为类似 `String`）
    * 对象包装器类是 `final`。

### 自动装箱
```java
// 下面这个调用
list.add(3);
// 将自动地变换为
list.add(Integer.valueOf(3));
```

### 自动拆箱
```java
// 编译器会将下面这个调用
int n = list.get(i);
// 翻译成
int n = list.get(i).intValue();
```

* 注意，对于包装类，不要使用 `==` ，要使用 `equals` 进行比较。
* 如果一个条件表达式中混合使用 `Integer` 和 `Double` 类型，`Integer` 值就会拆箱，***提升为 `double`，然后再装箱为 `Double`***。
    ```java
    Integer i = 1;
    Double d = 2.0;
    System.out.println(true ? i : x); // 结果是 1.0
    ```
* 装箱和拆箱是***编译器认可***的，而不是虚拟机。编译器在生成类的字节码时，插入必要的方法调用。而虚拟机只是执行这些字节码。
