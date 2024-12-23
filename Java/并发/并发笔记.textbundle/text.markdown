* 并发的三个核心问题
    * 分工：高效的拆解问题
        * Java API中的Executor、Fork/Join、Future就是用于解决分工问题
        * 设计模式：生产者-消费者、Thread-per-Message、Worker Thread
    * 同步：线程之间如何协作
        * Executor、Fork/Join、Future同时也可以解决同步（例如可以使用Future发起一个异步调用，主线程使用get()获取结果时，主线程会等待，异步执行有结果了，get()方法会自动返回，这里主线程和异步线程之间的协作，就是由Future解决的）
        * Java SDK中的CountDownLatch
        * 处理并发的方式
            * 信号量
            * 管程 monitor（Java中解决同步和互斥的核心技术）
    * 互斥：同一时刻只有一个线程能够访问某一共享资源
        * 多个线程同时访问同一个共享资源时，会出现不确定性问题，其源头来自：
            * 缓存导致的可见性问题
            * 线程切换带来的原子性问题
            * 编译优化带来的有序性问题
        * Java引入了内存模型来解决可见性和有序性问题
        * 通过锁来实现互斥，即同一时刻只能有一个线程访问共享资源。比如synchronized及SDK里各种Lock。
            * 为了解决性能问题，需要分场景优化：
                * 例如读多写少的场景，使用ReadWriteLock、StampedLock；
                * AtomicInterger等原子类采用无锁技术；
                * 不共享变量或者限定只读：ThreadLocal和final；
                * Copy-on-Write模式。

![](assets/17315657756474.png)
* 计算机的矛盾：CPU、内存、IO设备三者速度差异巨大