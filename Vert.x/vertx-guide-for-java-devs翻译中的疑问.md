## 0

* Every event loop is attached to a thread. By default Vert.x attaches 2 event loops per CPU core thread. The direct consequence is that a regular verticle always processes events on the same thread. 是因为一个CPU核里双线程？
* verticle 与 event bus 之间如何关联起来？https://zhuanlan.zhihu.com/p/37922864
