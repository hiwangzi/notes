# sort

## 默认的排序方法

```javascript
// 看上去正常的结果:
['Google', 'Apple', 'Microsoft'].sort(); // ['Apple', 'Google', 'Microsoft'];

// apple排在了最后（字符串按照ASCII排序）
['Google', 'apple', 'Microsoft'].sort(); // ['Google', 'Microsoft", 'apple']

// 无法理解的结果（默认将所有元素转为String再排序）
[10, 20, 1, 2].sort(); // [1, 10, 2, 20]
```

## 自定义排序

* `sort()` 方法也是一个高阶函数，它还可以接收一个比较函数来实现自定义的排序。

```javascript
var arr = ['Google', 'apple', 'Microsoft'];
arr.sort(function (s1, s2) {
    x1 = s1.toUpperCase();
    x2 = s2.toUpperCase();
    if (x1 < x2) {
        return -1;
    }
    if (x1 > x2) {
        return 1;
    }
    return 0;
}); // ['apple', 'Google', 'Microsoft']
```
