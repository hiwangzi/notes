* Happens-Before 的定义：在JMM中，如果操作A Happens-Before 操作B（记作hb(A, B)），则意味着：
  * 操作A的结果对操作B是可见的。
  * 操作A的执行顺序在操作B之前。
  * **重点即：前面一个操作的结果对后续操作是可见的**。
* 几项基本原则：
  * 顺序性规则：“程序前面对某个变量的修改一定是对后续操作可见的。”
  * 对 `volatile` 变量的 **写** 操作 happens before于后续的 **读** 操作。
  * 传递性：如果 A happens before B，且 B happens before C，那么 A happens before C。
  * 锁的规则：对一个锁的解锁 happens before 于后续对同一锁的加锁操作。
  * 线程启动规则：调用线程的 start() 方法 happens before 该线程的任何其他操作。
  * 线程终止规则：线程中所有的操作 happens before 对线程的终止检测。线程中执行的所有操作都会在其他线程可以检测到该线程已终止之前完成。
    * 假设线程A执行一些操作并修改共享变量，然后终止。线程B调用threadA.join()等待线程A终止。根据线程终止规则，线程A的所有操作（包括对共享变量的修改）在线程B从join()方法返回时都是可见的。
  * 线程中断规则：对线程 interrupt() 方法的调用 happens before 被中断线程检测到中断事件的代码。
  * 对象终结规则：一个对象的初始化完成（构造函数结束）先行发生于其 finalize() 方法的开始。
