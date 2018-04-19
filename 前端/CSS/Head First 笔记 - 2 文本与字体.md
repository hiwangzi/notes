# Head First HTML 与 CSS 笔记

## 文本与字体

* font-family：指定字体（不仅仅限于使用计算机上安装的字体）
* font-size：控制字体大小
* font-weight：影响字体粗细（ligher, normal, bold, bolder）
* font-style：字体的风格
* color：控制文本颜色
* text-decoration：对文本增加一些装饰（none, underline, overline, line-through）

### 1) font-family

* 字体可以分为以下5个系列
  * sans-serif：无衬线字体
  * serif：衬线字体
  * monospace：等宽字体
  * cursive：包括看似手写的字体，有时会在标题中使用
  * fantasy：包含有某种风格的装饰性字体

* 一个 font-family 包含一组有共同特征的字体
  * 浏览器会依次查找 font-family 中设置的字体在用户计算机中是否存在，有则使用
  * 最后一项总是放一个通用的字体系列名，例如`serif`, `sans-serif`, `cursive`, `monospace`。如果前面的字体均未匹配，浏览器会选用一个该系列默认的字体。

* 使用 Web 字体：`@font-face` 规则
  * woff：Web open font format
  * `@font-face` CSS 规则基本上已经成为了所有现代浏览器的一个标准，但存储字体采用格式还不是一个标准。以下是常用格式：
    * TrueType字体：.ttf
    * OpenType字体：.otf（建立在TrueType基础之上）
    * Embedded OpenType：.eot（OpenType的一种压缩格式，微软专有，仅IE可用）
    * SVG字体：.svg
    * Web开放字体格式：woff（建立在TrueType基础之上，目前支持较好）
  * CSS 中增加 `@font-face` 规则示例（CSS文件最上方）：
    ```css
    @font-face {
      font-family: "Emblema One"; /*自定字体名称*/
        src: url("http://demo.com/EmblemaOne-Regular.woff"),
             url("http://demo.com/EmblemaOne-Regular.ttf");
    }
    ```
    * 然后同普通字体一样使用即可。

### 2) font-size

* 可以使用 *像素* 或 *百分比* 或 *em* 或 *关键词* 来指定大小。（百分比、em是相较于父元素的字体大小）
    ```css
    body {
      font-size: 14px;
    }
    h1 {
      font-size: 150%;  /*父元素的150%*/
    }
    h2 {
      font-size: 1.2em; /*父元素的1.2倍*/
    }
    h3 {
      font-size: small; /*xx-small, x-small, small, medium, large, x-large, xx-large*/
    }
    ```

* 指定字体大小的比较好的实践：
  * 选择一个关键字（推荐small或medium），指定其为body规则中的字体大小，作为页面的默认字体大小
  * 使用em或百分比来为body中的子元素指定大小

### 3) font-weight

* 可以使用 ligher, normal, bold, bolder 指定字体粗细。
* 也可以将其设置为100至900之间的一个数（100的整倍数），但该特性未得到字体和浏览器的广泛支持，所以通常不使用。

### 4) font-style

* 除非确实需要区分斜体与倾斜文本，否则二者均可（PS: 我的认知，`italic`更加常见一些）
  * `italic`: 字体斜体风格
  * `oblique`: 浏览器将正常文字倾斜

### 5）color

* CSS 定义了大约150个颜色名
* 其他颜色可以通过 rgb 值来制定
  * `rgb(80%, 40%, 0%)`
  * `rgb(204, 102, 0)`
  * `#CC6600`

### 6) text-decoration

* 一次可以设置多个装饰。
* 如果文本继承了不想要的装饰，设置值为 `none` 可以去除装饰。
