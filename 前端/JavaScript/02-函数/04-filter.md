# filter

* 类似于 `map()`，但 `filter()` 把传入的函数依次作用于每个元素，然后根据返回值是 `true`还是 `false` 决定保留还是丢弃该元素（`true`保留）。

## 练习

### 筛出素数

```javascript
function get_primes(arr) {
    return arr.filter(function(element){
        if(element <= 1){
            return false;
        }
        for(let i = 2; i <= element/2; i++){
            if(element % i === 0){
                return false;
            }
        }
        return true;
    });
}
```