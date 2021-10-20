# 04.01 数组的定义及使用

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
            int score[][]={
                {67,61},{78,89,83},{99,100,98,66,95}
            };
            for(int i=0;i<score.length;i++){
                for(int j=0;j<score[i].length;j++){
                    System.out.print(score[i][j]+"\t");
                }
                System.out.println("");
            }
        }
    };
    ```
