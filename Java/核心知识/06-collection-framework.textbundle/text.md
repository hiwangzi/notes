# 6. 集合框架

集合框架（Collections Framework）是用于表示和操作集合的统一体系结构。所有集合框架都包含以下内容：
* 接口：表示集合的抽象数据类型。接口允许独立于其表示的细节来操纵集合。在面向对象语言中，接口通常形成层次结构。
* 实现：集合接口的具体实现。实质上，它们是可重用的数据结构。
* 算法：对实现集合接口的对象执行有用计算（如搜索和排序）的方法。算法被认为是多态的，也就是说，相同的方法可以用于适当的集合接口的许多不同实现。实质上，算法是可重用的功能。

除了Java集合框架之外，最著名的集合框架示例是C++标准模板库（STL）和Smalltalk的集合层次结构。从历史上看，集合框架相当复杂，这使得它们具有陡峭的学习曲线的声誉。我们相信Java集合框架打破了这一传统。

Java中集合框架主要分为两大类：

## `Collection`
* 表示一组对象，主要的子接口有`Set`、`List`和`Queue`。
* `List`：是一个有序的 Collection，它可以包含重复的元素。用户可以精确地控制每个元素插入的位置。用户可以根据元素的整数索引（位置）访问元素，并搜索元素在 List 中的位置。主要实现类有 `ArrayList`，`LinkedList` 等。
* `Set`：是一个不包含重复的元素的 Collection。主要实现类有 `HashSet`，`TreeSet` 等。
* `Queue`：通常但不一定）以 FIFO（先进先出）的方式排序元素。主要实现类有 `LinkedList`，`PriorityQueue` 等。

![Collection相关接口、类](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/hiwangzi/notes/dev/Java/CoreJava/06-collection-framework.textbundle/assets/collection.puml)

## `Map`
* `Map` 是一个对象，保存键/值对。键和值都是对象。在映射中，键和值都是对象。映射中的键是唯一的，但是值可以重复。主要实现类有 `HashMap`，`TreeMap`，`LinkedHashMap` 等。

![Collection相关接口、类](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/hiwangzi/notes/dev/Java/CoreJava/06-collection-framework.textbundle/assets/map.puml)
