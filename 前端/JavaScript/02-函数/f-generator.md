# generator

* 生成器是ES6引入的新的数据类型。
* 看上去像一个函数，但是可以返回多次。
* 注意定义有一个 `*` 号。

```js
// 例子 1
function* foo(x) {
    yield x + 1;
    yield x + 2;
    return x + 3;
}
let f = foo(100);
f.next(); // {value: 101, done: false}
f.next(); // {value: 102, done: false}
f.next(); // {value: 103, done: true}
f.next(); // {value: undefined, done: true}
```

```js
// 例子 2
function* fib(max) {
    var
        t,
        a = 0,
        b = 1,
        n = 0;
    while (n < max) {
        yield a;
        [a, b] = [b, a + b];
        n ++;
    }
    return 'haha';
}
for (var x of fib(5)) {
    console.log(x); // 依次输出0, 1, 1, 2, 3, ...
}
```

```js
// 例子 3
'use strict';
function* next_id() {
    let init_id = 0;
    while (true) {
        yield ++init_id;
    }
}
// 测试:
var
    x,
    pass = true,
    g = next_id();
for (x = 1; x < 100; x ++) {
    if (g.next().value !== x) {
        pass = false;
        console.log('测试失败!');
        break;
    }
}
if (pass) {
    console.log('测试通过!');
}
```