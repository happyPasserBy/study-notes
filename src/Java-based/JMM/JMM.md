# Java内存模型JMM 
> Java内存模型本身是一种抽象的概念，并不真实存在，它描述的是一组规则或者规范，通过这个规范定义了程序各个变量(实例字段，静态字段和高层数组对象的元素)的访问方式

![](./jmm.png)

## 1. JMM中的主内存
* 存储Java实例对象
* 包括成员变量，类信息，常量，静态变量等
* 主内存为共享数据，当多个线程访问通一个数据时可能引发安全问题
## 2. JMM中的工作内存
* 存储当前方法的所有本地变量信息，本地变量对其它线程不可见
* 共享变量的副本
* 字节码行号指示器，Native方法信息
* 属于线程私有数据区域，不存在线程安全问题
## 3. 指令排序
### 3.1 什么是指令排序
> 编译器和处理器为了提高单线程下的执行效率从而进行的指令排序
### 3.2 栗子
```
int one = 1; // 操作A
int two = 2; // 操作B
int three = one + two; // 操作C
```
> 我们以为机器会按照ABC的顺序执行，其实不然，经过指令排序后执行的顺序可能是ABC也可能是BAC，但不可能是C第一个执行，因为会破坏程序的数据依赖性既C依赖AB
### 3.2 不会重排序的情况
* 存在数据依赖性如上边的栗子C依赖于AB。
* as-if-serial规则，保证单线程下结果的不变性。
* 防止重排序的指令如volatile。
## 3. happens-before
> happens-before是保证了多线程下的内存可见性，如A线程某个操作 happens-before B线程的某个操作则A的操作结果对B可见。
### 3.2 规则
* 程序顺序规则：一个线程中的每个操作，happens-before 于该线程中的任意后续操作。
* 监视器锁规则：对一个锁的解锁，happens-before 于随后对这个锁的加锁。
* volatile变量规则：对一个volatile域的写，happens-before 于任意后续对这个volatile域的读。
* 传递性：如果A happens-before B，且B happens-before C，那么A happens-before C。
* start()规则：如果线程A执行操作ThreadB.start()（启动线程B），那么A线程的ThreadB.start()操作 happens-before 于线程B中的任意操作。
* join()规则：如果线程A执行操作ThreadB.join()并成功返回，那么线程B中的任意操作 happens-before 于线程A从ThreadB.join()操作成功返回。
* 程序中断规则：对线程interrupted()方法的调用先行于被中断线程的代码检测到中断时间的发生。
* 对象finalize规则：一个对象的初始化完成（构造函数执行结束）先行于发生它的finalize()方法的开始。

## 4. volatile
* 读取volatile修饰的变量时将当前线程的工作区内存致为无效，也就是说不读取本地的共享变量副本
* 写入volatile修饰的变量时将工作区的共享变量刷新到主内存中
* volatile可以禁止JVM优化-指令重排序
* volatile与synchronized的区别
    1. volatile是告诉JVM变量在寄存器中是不确定的，需要从主存中读取，synchronized则是锁定当前变量，只有当前线程可以访问
    2. volatile仅能实现变量的可见性，不能保证原子性，而synchronized都可以
    3. volatile不会阻塞线程，synchronized是可以的
    4. volatile标记的变量不会被编译器优化，而synchronized会
* volatile导致的总线风暴
## 参考
1. https://juejin.im/post/5ae6d309518825673123fd0e
2. https://www.freesion.com/article/78361480659/
