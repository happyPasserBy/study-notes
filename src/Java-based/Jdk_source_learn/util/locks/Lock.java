public interface Lock {
    // 加锁
    void lock();
    // 尝试加锁但出现一只休眠的状况，可被中断
    void lockInterruptibly() throws InterruptedException;
    // 尝试加锁并返回加锁是否成功
    boolean tryLock();
    // 在指定时间内尝试加锁
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
    // 解锁
    void unlock();

    Condition newCondition();
}
/*
* 参考
* 1. https://blog.csdn.net/u013851082/article/details/70140223
*/  
