# 方法

* 将函数绑定到对象就可以称之为方法。
* JavaScript 的函数内部调用 `this`
  * 以对象的方法形式调用，该函数的 `this` 指向被调用的对象
  * 单独调用函数，`this` 指向全局对象，也就是 `window`（strict模式下，指向`undefined`）
  * 在函数内部定义的函数，`this` 指向 `window`（strict模式下，指向`undefined`）（解决办法：在第一层函数里通过变量捕获`this`，在第二层中使用）
* `apply` 与 `call`
  * 要指定函数的 `this` 指向哪个对象，可以用函数本身的 `apply` 方法，它接收两个参数，第一个参数就是需要绑定的 `this` 变量，第二个参数是 `Array`，表示函数本身的参数。
  * `call` 方法同样目的，只是函数本身参数不通过 `Array` 传入，直接按顺序传入
  * 例如：
    ```javascript
    Math.max.apply(null, [3, 5, 4]); // 5
    Math.max.call(null, 3, 5, 4); // 5
    ```