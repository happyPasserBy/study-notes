# Lock 
> Juc包下的锁接口
```
public interface Lock {
    // 加锁
    void lock();

    void lockInterruptibly() throws InterruptedException;
    // 尝试加锁
    boolean tryLock();
    // 在指定时间内尝试加锁
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
    // 解锁
    void unlock();

    Condition newCondition();
}
```
