# 标准对象

* 在JavaScript的世界里，一切都是对象。

```js
typeof 123; // 'number'
typeof NaN; // 'number'
typeof 'str'; // 'string'
typeof true; // 'boolean'
typeof undefined; // 'undefined'
typeof Math.abs; // 'function'
typeof null; // 'object'
typeof []; // 'object'
typeof {}; // 'object'
```

* 注意：
  * `null` 的类型是 `object`
  * `Array` 的类型也是 `object`

## 包装对象

```js
var n = new Number(123); // 123, 生成了新的包装类型
var b = new Boolean(true); // true, 生成了新的包装类型
var s = new String('str'); // 'str', 生成了新的包装类型

// 虽然包装对象看上去和原来的值一模一样，但他们的类型已经变为object了
// 所以，包装对象和原始值用===比较会返回false：
typeof new Number(123); // 'object'
new Number(123) === 123; // false

typeof new Boolean(true); // 'object'
new Boolean(true) === true; // false

typeof new String('str'); // 'object'
new String('str') === 'str'; // false
```

* 不写 `new` 时，分别转换为 `number`, `boolean`, `string` 类型
    ```js
    var n = Number('123'); // 123，相当于parseInt()或parseFloat()
    typeof n; // 'number'

    var b = Boolean('true'); // true
    typeof b; // 'boolean'

    var b2 = Boolean('false'); // true! 'false'字符串转换结果为true！因为它是非空字符串！
    var b3 = Boolean(''); // false

    var s = String(123.45); // '123.45'
    typeof s; // 'string'
    ```

## 总结

* 建议不使用包装类型
* 用 `parseInt()` 或 `parseFloat()` 来转换任意类型到number；
* 用 `String()` 来转换任意类型到string，或者直接调用某个对象的 `toString()` 方法；
* `typeof` 操作符可以判断出 `number`、`boolean`、`string`、`function` 和 `undefined`；
* 判断 `Array` 要使用 `Array.isArray(arr)`；
* 判断 `null` 请使用 `myVar === null`；
* 判断某个全局变量是否存在用 `typeof window.myVar === 'undefined'`；
* 函数内部判断某个变量是否存在用 `typeof myVar === 'undefined'`。
