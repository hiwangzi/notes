# 03. 运算符

## 逻辑运算符

|名称|符号|
|------|------|
|逻辑非|!|
|逻辑与|&|
|逻辑或|\||
|短路与|&&|
|短路或|\|\||
|异或|^|

例：「短路与」示例 （若使用“与”，则会报错(10/0)，使用「短路与」则可以正常运行）

```java
public class Demo{
    public static void main(String[] args){
        int n = 10, m = 2;
        boolean k = false;
        if(n != 10 && 10/0 == 9){
            System.out.println(!k);
        }
        else{
            System.out.println(k);
        }
    }
}
```

## 位运算符

```
&     ("and")
|     ("or")
^     ("xor")
~     ("not")
<<    左移
>>    右移（用符号位填充高位）
>>>   (用0填充高位)
```

## 运算符优先级

![运算符优先级](./assets/operator-priority.jpg)