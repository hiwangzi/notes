# Head First HTML 与 CSS 笔记

## 介绍

* HTML 中的 CSS

  ```html
  <!-- 位于<head>元素之中 -->
  <style>
      p {
        background-color: red;
      }
      em {
        background-color: green;
      }
      em {
        font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
      }
  </style>
  ```

* 单独的 CSS 文件

  ```css
      p {
        background-color: red;
      }
      em {
        background-color: green;
      }
      em {
        font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
      }
  ```

  * 需要在 HTML 的 `<head>` 中加入
    ```html
    <!-- HTML5 中可以去除 type -->
    <link type="text/css" rel="stylesheet" href="lounge.css">
    ```

* 子元素可以继承父元素的样式（部分），同样也可以覆盖父元素的样式。
  * 一般来讲，如果样式可以影响文本外观，则可以继承。
  * 像是边框这种，是不可以继承的。（这是可以理解的）

* 类选择器使用 `.` 表示。

* 选择器样式冲突时：
  * “更特定（更具体）”的规则胜出
  * 相同时，选择最后列出的规则（注意：**不是**`class`属性中的顺序）。

* CSS 的验证工具：https://jigsaw.w3.org/css-validator/
