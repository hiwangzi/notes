# 函数

<!-- TOC -->

- [函数](#函数)
    - [定义方式](#定义方式)
    - [调用函数](#调用函数)
    - [变量的作用域](#变量的作用域)
        - [变量提升](#变量提升)
        - [全局作用域](#全局作用域)
        - [名字空间](#名字空间)
        - [局部作用域](#局部作用域)
        - [常量](#常量)
        - [解构赋值](#解构赋值)

<!-- /TOC -->

## 定义方式

* 以下为示例（多个参数以 `,` 分隔）
    ```javascript
    function abs(x) {
        if (x >= 0) {
            return x;
        } else {
            return -x;
        }
    }
    ```
* 如果没有 `return`，函数执行完毕后返回结果 `undefined`
* JS中函数也是一个对象，使用函数名`abs`可以视为指向该函数的变量。
  * 所以，也可以采用如下方式定义函数：
    ```javascript
    var abs = function (x) {
        if (x >= 0) {
            return x;
        } else {
            return -x;
        }
    }; // 注意此处有一个分号，表示赋值语句结束
    ```
  * 这种方式下，`function (x) {...}` 是一个匿名函数，它没有函数名。但该匿名函数被赋值给了变量 `abs`，所以可以通过变量 `abs` 调用该函数。
  * 上述两种定义完全等价。

## 调用函数

* JavaScript允许传入任意个参数而不影响调用，因此传入的参数比定义的参数多也没有问题（PS：这...😥）
    ```javascript
    abs(10, 'blablabla'); // 返回10
    abs(-9, 'haha', 'hehe', null); // 返回9
    ```
* 传入的参数比定义的少也没有问题
    ```javascript
    abs(); // 返回NaN
    ```
  * 此时 `x` 收到的是 `undefined`，因此计算结果为 `NaN`
  * 可以通过参数检查来避免接收 `undefined`
    ```javascript
    function abs(x) {
        if (typeof x !== 'number') {
            throw 'Not a number';
        }
        if (x >= 0) {
            return x;
        } else {
            return -x;
        }
    }
    ```
* `arguments`：只在函数内部起作用，永远指向当前函数的调用者传入的所有参数，**类似**`Array`但不是
  * 利用`arguments`，可以获得调用者传入的所有参数。也就意味着，即使函数不定义任何参数，还是可以拿到参数的值
  * `arguments` 常用作判断传入参数的个数，会有以下写法：
    ```javascript
    // foo(a[, b], c)
    // 接收2~3个参数，b是可选参数，如果只传2个参数，b默认为null：
    function foo(a, b, c) {
        if (arguments.length === 2) {
            // 实际拿到的参数是a和b，c为undefined
            c = b; // 把b赋给c
            b = null; // b变为默认值
        }
        // ...
    }
    ```

* `rest`：除了已定义参数之外的参数
  * 是个 `Array`
  * 如果已定义参数都未填满，`rest` 会接收空数组

## 变量的作用域

* 如果一个变量在函数体内部申明，则该变量的作用域为整个函数体，在函数体外不可引用该变量
* 函数嵌套时，内部函数可以访问外部函数定义的变量，反过来不可。
* 重名时，会覆盖。

### 变量提升

* 声明会被自动提升，但赋值不会。例：
  * 以下代码
    ```javascript
    'use strict';
    function foo() {
        var x = 'Hello, ' + y;
        console.log(x);
        var y = 'Bob';
    }
    foo(); // 此时不会报错，输出 Hello, undefined
    ```
  * 相当于
    ```javascript
    function foo() {
        var y; // 提升变量y的申明，此时y为undefined
        var x = 'Hello, ' + y;
        console.log(x);
        y = 'Bob';
    }
    ```

### 全局作用域

* 不在任何函数内定义的变量就具有全局作用域。
* 实际上全局作用域被绑定到 `window` 这个全局对象上。
* JS 实际上只有一个全局域。

### 名字空间

* 全局变量都绑定到 `window` 上，很容易冲突。
* 减少冲突的一个方法：
    ```javascript
    // 唯一的全局变量MYAPP:
    var MYAPP = {};

    // 其他变量:
    MYAPP.name = 'myapp';
    MYAPP.version = 1.0;

    // 其他函数:
    MYAPP.foo = function () {
        return 'foo';
    };

    // 这样 MYAPP 就可以被称作名字空间
    // 许多著名的 JS 库都采用此种做法：jQuery, YUI 等等
    ```

### 局部作用域

* 使用 `var` 声明的变量作用域实际上是整个函数内部，例如：`for`循环中定义的 `i` 在循环外函数内都是可以访问的
* ES6 引入了关键字 `let` 解决此问题，用 `let` 替代 `var` 可以申明一个块级作用域的变量
    ```javascript
    'use strict';

    function foo() {
        var sum = 0;
        for (let i=0; i<100; i++) {
            sum += i;
        }
        // SyntaxError:
        i += 1; // 但若是 var 声明的 i，此处不会报错
    }
    ```

### 常量

* ES6 引入了关键字 `const` 来定义常量，其也同 `let` 一样具有块级作用域

### 解构赋值

```javascript
let [x, [y, z]] = ['hello', ['JavaScript', 'ES6']];
x; // 'hello'
y; // 'JavaScript'
z; // 'ES6'

// TODO 如果不退出浏览器重开，以下会报错 Identifier 'z' has already been declared
// 目前没找到取消声明的方式
let [, , z] = ['hello', 'JavaScript', 'ES6']; // 忽略前两个元素，只对z赋值第三个元素
z; // 'ES6'
```

* 嵌套或要使用的变量名和属性名不一致时：
    ```javascript
    var person = {
        name: '小明',
        age: 20,
        gender: 'male',
        passport: 'G-12345678',
        school: 'No.4 middle school',
        address: {
            city: 'Beijing',
            street: 'No.1 Road',
            zipcode: '100001'
        }
    };
    var {name, address: {city, zip}} = person;
    name; // '小明'
    city; // 'Beijing'
    zip; // undefined, 因为属性名是zipcode而不是zip
    // 注意: address不是变量，而是为了让city和zip获得嵌套的address对象的属性:
    address; // Uncaught ReferenceError: address is not defined
    // 把passport属性赋值给变量id:
    let {name, passport:id} = person;
    name; // '小明'
    id; // 'G-12345678'
    // 注意: passport不是变量，而是为了让变量id获得passport属性:
    passport; // Uncaught ReferenceError: passport is not defined
    ```
* 解构赋值可以使用默认值：
    ```javascript
    var person = {
        name: '小明',
        age: 20,
        gender: 'male',
        passport: 'G-12345678'
    };

    // 如果person对象没有single属性，默认赋值为true:
    var {name, single=true} = person;
    name; // '小明'
    single; // true
    ```
* 使用场景
  * 交换变量
    ```javascript
    var x=1, y=2;
    [x, y] = [y, x]
    ```
  * 快速获取当前页面的域名和路径
    ```javascript
    var {hostname:domain, pathname:path} = location;
    ```
  * 在一个函数接收一个对象作为参数时候，解构函数将对象的属性绑定到变量
    ```javascript
    function buildDate({year, month, day, hour=0, minute=0, second=0}) {
        return new Date(year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second);
    }
    buildDate({ year: 2017, month: 1, day: 1 });
    // Sun Jan 01 2017 00:00:00 GMT+0800 (CST)
    buildDate({ year: 2017, month: 1, day: 1, hour: 20, minute: 15 });
    // Sun Jan 01 2017 20:15:00 GMT+0800 (CST)
    ```