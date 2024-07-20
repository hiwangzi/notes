# 03.01 数组的定义及使用

## 声明及开辟数组空间

1. 声明数组

    ![声明数组](./assets/declare-array.jpg)
    ```java
    int score [] = null; // null表示引用数据类型的默认值
    int [] score = null; // 与上一句等价
    ```

2. 为数组开辟空间

    ![声明数组](./assets/allocate-memory-to-array.jpg)

* 补充：堆栈内存解释
  * 数组操作中，在栈内存中保存的永远是数组的名称，只开辟了栈内存空间的数组是无法使用的，必须有指向的堆内存才可以使用；
  * 要想开辟新的堆内存，则必须使用 `new` 关键字，之后只是将此内存的使用权交给了对应的栈内存空间，而且一个堆内存可以被多个栈内存空间指向。

## 数组的静态初始化

### 一维数组

```java
int score[] = {1, 2, 3, 4, 5};
```

### 二维数组

* 声明之后再赋值
    ```java
    int arr[][] = new int[4][3]; // 声明并实例化二维数组
    arr[0][0] = 201604;
    arr[0][1] = 1;
    arr[1][1] = 11;
    arr[2][2] = 22;
    arr[3][1] = 31;
    ```
* 声明同时赋值

    ![声明二维数组同时赋值](./assets/init-2d-array.jpg)
    ```java
    // 每行的数组元素个数不一样，分配空间不同
    public class ArrayDemo {
        public static void main(String args[]){
            int score[][] = {
                {67,61},{78,89,83},{99,100,98,66,95}
            };
            for(int i=0; i<score.length; i++){
                for(int j=0; j<score[i].length; j++){
                    System.out.print(score[i][j] + "\t");
                }
                System.out.println("");
            }
        }
    };
    ```

## 数组的传递引用

* Java程序设计语言总是采用**按值调用**。也就是说，方法得到的是所有参数值的一个拷贝，特别是，方法不能修改传递给它的任何参数变量的内容。

### 要点

1. 数组引用传递的是 **堆内存** 的使用权，可以将数组传递到方法之中，传递时不需要写 `[]`，直接写数组名。
2. 方法中对数组的修改都会保留下来。
3. Java 提供了一些对数组进行操作的方法，例如数组排序，数组拷贝。

### 接收和返回数组

一个方法可以接收一个数组，也可以返回一个数组。

* 接收数组：如果方法接收一个数组的话，则在此方法中对数组做的操作都将被保留下来。
* 返回数组：方法除了可以接受数组之外，也可以通过方法返回一个数组，只需要在返回值类型上，明确的声明出返回的类型是数组即可。

### 应用举例

#### 1. 数组排序

> 冒泡排序算法的运作如下：
>
> a. 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
>
> b. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
>
> c. 针对所有的元素重复以上的步骤，除了最后一个。
>
> d. 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
>
> [参考：冒泡排序](https://zh.wikipedia.org/wiki/%E5%86%92%E6%B3%A1%E6%8E%92%E5%BA%8F)

```java
// 标准冒泡排序法
public static void sort(int temp[]) {
    for (int i = 0; i < temp.length - 1; i++) {
        for (int j = 0; j < temp.length - 1 - i; j++) {
            if (temp[j] > temp[j + 1]) {
                int t = temp[j];
                temp[j] = temp[j + 1];
                temp[j + 1] = t;
            }
        }
    }
}

// 助记码
// 参考 https://zh.wikipedia.org/wiki/%E5%86%92%E6%B3%A1%E6%8E%92%E5%BA%8F#JAVA
i∈[0,N-1)               //循环N-1遍
    j∈[0,N-1-i)         //每遍循环要处理的无序部分
        swap(j,j+1)     //两两排序（升序/降序）

// Java 本身也提供了数组排序的方法（不局限于 int 数组）
int score[]={67,89,87,69,90,100,75,90}; // 定义整型数组
java.util.Arrays.sort(score); // 数组排序
```

#### 2. 数组拷贝

```java
public class Test {
    public static void main(String args[]) {
        int i1[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};             // 源数组
        int i2[] = {11, 22, 33, 44, 55, 66, 77, 88, 99};    // 目标数组
        copy(i1, 3, i2, 1, 3);                              // 调用拷贝方法(正常拷贝)
        //copy(i1,0,i2,8,2);                                // 数组越界调用
        //System.arraycopy(i1,0,i2,8,2);                    // 系统提供的方法，同上数组越界调用
        print(i2);
    }

    // 源数组名称，源数组开始点，目标数组名称，目标数组开始点，拷贝长度
    public static void copy(int s[], int s1, int o[], int s2, int len) {
        for (int i = 0; i < len; i++) {
            o[s2 + i] = s[s1 + i]; // 进行拷贝操作
        }
    }

    public static void print(int temp[]) { // 输出数组内容
        for (int i = 0; i < temp.length; i++) {
            System.out.print(temp[i] + "\t");
        }
    }
}
```

#### 3. 使用方法交换变量值

* Java 总是采用「**按值调用(call by value)**」，即方法得到的是参数值的一个拷贝。

```java
public class Swap {
    public static void main(String[] args) {
        int a[] = {1};
        int b[] = {2};
        System.out.println("Swap 1:");
        System.out.println("--Before: " + a[0] + ", " + b[0]);
        Swap1(a, b);
        System.out.println("--After: " + a[0] + ", " + b[0] + "\n");

        a[0] = 1;
        b[0] = 2;
        System.out.println("Swap 2:");
        System.out.println("--Before: " + a[0] + ", " + b[0]);
        Swap2(a, b);
        System.out.println("--After: " + a[0] + ", " + b[0]);
    }

    /*使用数组实现交换数值*/
    /*改变的是对应的堆空间里面的值*/
    public static void Swap1(int num1[], int num2[]) {
        int temp = num1[0];
        num1[0] = num2[0];
        num2[0] = temp;
    }

    /*改变（交换）的只是num1与num2对堆空间值的引用*/
    /*a与b对堆内存的引用并没有改变*/
    public static void Swap2(int num1[], int num2[]) {
        int temp[];
        temp = num1;
        num1 = num2;
        num2 = temp;
    }
}
```

![交换后结果](./assets/var-swap.png)

### 可变参数

即方法中，可以接收的参数不再是固定的，而是随着需要传递。

格式如下：

```java
返回值类型 方法名称(类型 … 参数名称) {
    //函数体
}
```

例子：

```java
public class Demo {
    public static void main(String args[]) {
        System.out.print("不传递参数（fun()）：");
        fun(); // 不传递参数
        System.out.print("\n传递一个参数（fun(1)）：");
        fun(1); // 传递一个参数
        System.out.print("\n传递五个参数（fun(1,2,3,4,5)）：");
        fun(1, 2, 3, 4, 5);
    }

    public static void fun(int... arg) { // 可变参数
        for (int i = 0; i < arg.length; i++) { // 循环输出
            System.out.print(arg[i] + " ");
        }
    }

    // 输出：
    // 不传递参数（fun()）：
    // 传递一个参数（fun(1)）：1 
    // 传递五个参数（fun(1,2,3,4,5)）：1 2 3 4 5 
}
```

### foreach

数组的输出，一般都会使用 `for` 循环输出。在 `JDK 1.5` 之后，提出一种 `foreach` 语法。 格式如下：

```java
for(数据类型 变量名称:数组名称){
    //函数体
}
```

例子：

```java
public class Demo {
    public static void main(String args[]) {
        System.out.print("不传递参数（fun()）：");
        fun(); // 不传递参数
        System.out.print("\n传递一个参数（fun(1)）：");
        fun(1); // 传递一个参数
        System.out.print("\n传递五个参数（fun(1,2,3,4,5)）：");
        fun(1, 2, 3, 4, 5);
    }

    public static void fun(int... args) { // 可变参数
        for (int arg : args) { // 使用foreach输出输出
            System.out.print(arg + " ");
        }
    }
}
```