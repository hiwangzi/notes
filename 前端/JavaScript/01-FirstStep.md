# 快速入门

* 简史：
  * 网景公司于 1995 年发布 JavaScript；
  * ECMAScript 是标准，JavaScript 是网景公司对标准的一种具体实现；
  * ES6 于 2015.6 发布

* 网页中 JavaScript 的引入：
  * 直接包含在 ```<script>...</script>``` 之中
  * 代码放于单独的 ```.js``` 文件，通过 ```<script src="..."></script>``` 引入

* JavaScript 不强制要求在每个语句的结尾加 `;`，浏览器可以自动追补，但这样可能会改变程序语义。

## 数据类型与变量

* Number、字符串、布尔值、undefined、null、对象（数组、函数……）

### Number

JavaScript 不区分整数和浮点数，统一用 Number 表示，以下都是合法的 Number 类型。

```javascript
123; // 整数123
0.456; // 浮点数0.456
1.2345e3; // 科学计数法表示1.2345x1000，等同于1234.5
-99; // 负数
NaN; // NaN表示Not a Number，当无法计算结果时用NaN表示。判断一个数字是否是NaN的唯一方法：isNaN(NaN)
Infinity; // Infinity表示无限大，当数值超过了JavaScript的Number所能表示的最大值时，就表示为Infinity
0xff00; // 十六进制数
```

### 字符串

* 使用单引号或双引号括起来。
* 多行字符串
    ```javascript
    `这是一个
    多行
    字符串`;
    ```
* 模板字符串
    ```javascript
    var name = '小明';
    var age = 20;
    var message = `你好, ${name}, 你今年${age}岁了!`;
    alert(message);
    ```
* 字符串是不可变的，如果对字符串的某个索引赋值，不会有任何错误，但也没有效果。
* 常用方法（返回一个新字符串，不会改变原有字符串）
  * `toUpperCase()`
  * `toLowerCase()`
  * `indexOf()`
  * `substring()`

### 布尔值

* `&&` 与运算
* `||` 或运算
* `!` 非运算

### 比较运算符

* 避免使用 `==`，坚持使用 `===`
    ```javascript
    false == 0; // true 自动转换数据类型再比较
    false === 0; // false 不会自动转换数据类型，如果数据类型不一致，返回false，如果一致，再比较。
    ```

* `NaN` 与所有值都不相等，包括自身，只能通过 `isNaN()` 函数判断

* 由于浮点数计算的误差，需要注意浮点数的相等比较
    ```javascript
    1 / 3 === (1 - 2 / 3); // false
    Math.abs(1 / 3 - (1 - 2 / 3)) < 0.0000001; // true 要比较两个浮点数是否相等，只能计算它们之差的绝对值，看是否小于某个阈值
    ```

* `null` 与 `undefined`
  * JavaScript的设计者希望用`null`表示一个空的值，而`undefined`表示值未定义。事实证明，这并没有什么卵用，区分两者的意义不大。大多数情况下，我们都应该用`null`。`undefined`仅仅在判断函数参数是否传递的情况下有用。

### 数组

```javascript
var arr = [1, 2, 3.14, 'Hello', null, true];
arr[0]; // 返回索引为0的元素，即1
arr[5]; // 返回索引为5的元素，即true
arr[6]; // 索引超出了范围，返回undefined
```

* 使用 `length` 可以得到数组大小。
* 可以通过索引赋值，如果超出范围，不会报错，会改变 `Array` 的大小（但不建议这样做）
    ```javascript
    var arr = [1, 2, 3];
    arr[5] = 'x';
    arr; // arr变为[1, 2, 3, undefined, undefined, 'x']
    ```
* 多维数组
    ```javascript
    var arr = [[1, 2, 3], [400, 500, 600], '-'];
    ```
* 常用方法
  * `indexOf()`
  * `slice()`
    ```javascript
    var arr = ['A', 'B', 'C', 'D', 'E', 'F', 'G'];
    arr.slice(0, 3); // 从索引0开始，到索引3结束，但不包括索引3: ['A', 'B', 'C']
    arr.slice(3); // 从索引3开始到结束: ['D', 'E', 'F', 'G']
    arr.slice(); // 截取所有元素，相当于复制了一个数组
    ```
  * `push()`向`Array`的末尾添加若干元素，`pop()`则把`Array`的最后一个元素删除掉
    * 空数组 `pop` 不会报错，返回 `undefined`
  * `unshift()`向`Array`的头部添加若干元素，`shift()`则把`Array`的第一个元素删除掉
  * `sort()`
  * `reverse()`
  * `splice()`：它可以从指定的索引开始删除若干元素，然后再从该位置添加若干元素
    ```javascript
    var arr = ['Microsoft', 'Apple', 'Yahoo', 'AOL', 'Excite', 'Oracle'];
    // 从索引2开始删除3个元素,然后再添加两个元素:
    arr.splice(2, 3, 'Google', 'Facebook'); // 返回删除的元素 ['Yahoo', 'AOL', 'Excite']
    arr; // ['Microsoft', 'Apple', 'Google', 'Facebook', 'Oracle']
    // 只删除,不添加:
    arr.splice(2, 2); // ['Google', 'Facebook']
    arr; // ['Microsoft', 'Apple', 'Oracle']
    // 只添加,不删除:
    arr.splice(2, 0, 'Google', 'Facebook'); // 返回[],因为没有删除任何元素
    arr; // ['Microsoft', 'Apple', 'Google', 'Facebook', 'Oracle']
    ```
  * `concat()`
    * `concat()`方法并没有修改当前`Array`，而是返回了一个新的`Array`
    * `concat()`方法可以接收任意个元素和 `Array`，并且自动把`Array`拆开，然后全部添加到新的`Array`里
      ```javascript
      var arr = ['A', 'B', 'C'];
      arr.concat(1, 2, [3, 4]); // ['A', 'B', 'C', 1, 2, 3, 4]
      ```
  * `join()`：如果`Array`的元素不是字符串，将自动转换后再连接。
    ```javascript
    var arr = ['A', 'B', 'C', 1, 2, 3];
    arr.join('-'); // 'A-B-C-1-2-3'
    ```

### 对象

* 对象是一组由“键-值”组成的**无序**集合。
* 通过 `对象变量.属性名` 获取一个对象的属性。
  * 前提：标准属性名，不包含特殊符号
  * 否则：只能通过 `['xxx']` 这种形式访问
* 可以使用 `in` 操作符检测对象是否拥有某一属性
    ```javascript
    'toString' in xiaoming; // true 继承自 object
    ```
* 对象可以继承
* 使用 `hasOwnProperty()` 检测属性是否是对象自身拥有的
    ```javascript
    var xiaoming = {
        name: '小明'
    };
    xiaoming.hasOwnProperty('name'); // true
    xiaoming.hasOwnProperty('toString'); // false
    ```

### 变量

* 可以把任意数据类型赋值给变量，同一个变量可以反复赋值，而且可以是不同类型的变量。
* 但是要注意只能用var申明一次。
* 使用 `delete` 可以删除对象的属性
    ```javascript
    delete xiaoming.school; // 删除一个不存在的school属性也不会报错
    ```

### strict 模式

* 如果一个变量没有通过 `var` 申明就被使用，那么该变量就自动被申明为全局变量
* 使用 `var` 申明的变量则不是全局变量，它的范围被限制在该变量被申明的函数体内
* 在strict模式下运行的JavaScript代码，强制通过`var`申明变量，未使用`var`申明变量就使用的，将导致运行错误
  * 在 JavaScript 代码第一行写上 `'use strict';` 即可启用 strict 模式。

## 条件判断

* JavaScript把 `null`、`undefined`、`0`、`NaN` 和空字符串 `''` 视为 `false`，其他值一概视为 `true`。

## 循环

* for .. in（PS：得到的是“下标”）
    ```javascript
    var o = {
        name: 'Jack',
        age: 20,
        city: 'Beijing'
    };
    for (var key in o) {
        console.log(key); // 'name', 'age', 'city'
    }
    ```
    ```javascript
    var a = ['A', 'B', 'C'];
    for (var i in a) {
        console.log(i); // '0', '1', '2'
        console.log(a[i]); // 'A', 'B', 'C'
    }
    ```

## `Map` 与 `Set`

* 对象`{}`也可以视作其他语言中的`Map`，但 key 必须为字符串
* 因此 ES6 引入了 `Map`

### `Map`

* 初始化方式一
    ```javascript
    var m = new Map([['Michael', 95], ['Bob', 75], ['Tracy', 85]]);
    m.get('Michael'); // 95
    ```
* 初始化方式二
    ```javascript
    var m = new Map(); // 空Map
    m.set('Adam', 67); // 添加新的key-value
    m.set('Bob', 59);
    m.has('Adam'); // 是否存在key 'Adam': true
    m.get('Adam'); // 67
    m.delete('Adam'); // 删除key 'Adam'
    m.get('Adam'); // undefined
    ```
* 多次对一个 key 放入 value，后面的值会覆盖之前的值。

### `Set`

```javascript
var s1 = new Set(); // 空Set
var s2 = new Set([1, 2, 3]); // 含1, 2, 3
```

* `add(key)` 添加元素
* `delete(key)` 删除元素

## iterable

遍历`Array`可以采用下标循环，遍历`Map`和`Set`就无法使用下标。为了**统一集合类型**，ES6标准引入了新的`iterable`类型，`Array`、`Map`和`Set`都属于`iterable`类型。

* ES6 引入 `for ... of` 语法（PS：JS真的🙈是...）

    ```javascript
    var a = ['A', 'B', 'C'];
    a.name = 'Hello';
    for (var x in a) {
        console.log(x); // '0', '1', '2', 'name'
    }
    ```

    ```javascript
    var a = ['A', 'B', 'C'];
    a.name = 'Hello';
    for (var x of a) {
        console.log(x); // 'A', 'B', 'C'
    }
    ```

* 遍历更好的方式是使用 `iterable` 内置的 `forEach` 方法（ES5.1引入），它接收一个函数，每次迭代就自动回调该函数。

    ```javascript
    var a = ['A', 'B', 'C'];
    a.forEach(function (element, index, array) {
        // element: 指向当前元素的值
        // index: 指向当前索引
        // array: 指向Array对象本身
        console.log(element + ', index = ' + index);
    });
    ```