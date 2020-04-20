# Lock
> 与synchronized相同，对共享资源提供了并发控制功能，但具备了synchronized一些没有的功能如:设置获取锁的超时时间，手动中断锁，无法知道锁是否被占用等等。
## 1.Lock接口
[源码分析](../Jdk_source_learn/util/locks/Lock.java) 
## 2. 内存可见性
> Lock的加解锁和synchronized有同样的内存语义，也就是说，下一个线程加锁后可以看到前一个线程解锁前发生的所有操作。