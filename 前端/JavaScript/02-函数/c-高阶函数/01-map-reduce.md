# map/reduce

## map

![map示例](./resources/map.png)

## reduce

```javascript
[x1, x2, x3, x4].reduce(f) = f(f(f(x1, x2), x3), x4)
```

## 练习

### 利用 `reduce` 求积

```javascript
function product(arr) {
        return arr.reduce(function (x, y) {
        return x * y;
    });
};
console.log(product([1, 2, 3, 4]) === 24);
```

### 利用 `map` 和 `reduce` 实现字符串转数值

```javascript
function string2int(s) {
    return s.split('')
        .map(function (x) {
            return x - 0;
        })
        .reduce(function (x, y) {
            return x * 10 + y;
        });
};
console.log(string2int('12345') === 12345);
```

### 规范化名字（首字母大写）

```javascript
function normalize(arr) {
    return arr.map(function (x) {
        xs = x.toLowerCase().split('');
        xs[0] = xs[0].toUpperCase();
        return xs.join('');
    });
};
console.log(normalize(['adam', 'LISA', 'barT']).toString() === ['Adam', 'Lisa', 'Bart'].toString());
```

### `arr.map(parseInt)` 不能正确输出的原因

```javascript
var arr = ['1', '2', '3'];
var r;
r = arr.map(parseInt);
console.log(r);
// 1,NaN,NaN
```

* [原因](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/map#Tricky_use_case)
    ```javascript
    var new_array = arr.map(function callback(currentValue[, index[, array]]) {
        // Return element for new_array
    }[, thisArg])
    ```
* 尝试1：
    ```javascript
    var x = [10, 20, 30, 40];
    x.map(function(x, y){
        return x + y;
    });
    // [10, 21, 32, 43]
    ```

* 尝试2：
    ```javascript
    var x = [10, 20, 30, 40];
    x.map(function(x, y, z){
        return z
    });
    // [Array(4), Array(4), Array(4), Array(4)]
    // 其中每一个Array均为[10, 20, 30, 40]
    ```
