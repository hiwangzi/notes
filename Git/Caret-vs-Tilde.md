# Caret v.s. Tilde

* Caret: ^
* Tilde: ~

## Rules of thumb

* Use ~ most of the time — to go back a number of generations, usually what you want
* Use ^ on merge commits — because they have two or more (immediate) parents

Mnemonics:
* Tilde ~ is almost linear in appearance and wants to go backward in a straight line
* Caret ^ suggests an interesting segment of a tree or a fork in the road

## 例子
```
// 自上向下为起点到终点
A   B   C   D
 \ /     \ /
  E       F
   \     / \
    \   /   |
     \ /    |
      G     H
       \   /
        \ /
         I
```
```
A = A~0 = A^0 = E~ = E~1 = E^ = E^1 = G~2  = G^^                 = I~3    = I^^^
B                             = E^2 = G~^2 = G^^2                = I~2^2  = I^^^2
C             = F~ = F~1 = F^ = F^1        = G^2^  = H~2         = I^2~2  = I^2^^
D                             = F^2        = G^2^2 = H~^2 = H^^2 = I^2~^2 = I^2^^2
```

## 万能的`^`

通过使用`^`操作符，可以完成使用`~`操作符的所有场景，包括引用一个提交的父级提交和跳过父级提交访问更高级的父级提交，以及处理合并提交的情况。因此，使用`^`操作符可以覆盖`~`操作符的所有功能。

然而，只使用`~`操作符无法完全实现使用`^`操作符的所有场景。特别是在需要引用第二个或更高级的父级提交时，`~`操作符无法直接实现。因此，只使用`~`操作符无法覆盖`^`操作符的所有功能。

总结起来就是：
- `^`操作符可以完成使用`~`操作符的所有场景。
- 只使用`~`操作符无法完全实现使用`^`操作符的所有场景。
