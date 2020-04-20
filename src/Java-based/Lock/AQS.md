# AQS
> 并发同步框架，当多个线程访问共享资源时，如果获取到锁继续操作，反之将进入队列进行排队,AQS的两个核心:资源(state)与FIFO队列，AQS还定义了两种资源共享方式: 独占(ReentrantLock)与共享(CountDownLatch)
3. 公平锁: 当线程等待的时间越久获取到锁的概率越大
4. 使用lock()后必须释放锁unlock();锁需要执行的代码尽量放入try catch中，防止死锁
5. synchronized与ReentrantLock最本质的区别:
    * synchronized和ReentrantLock的区别synchronized基于对象头加锁，而ReentrantLock基于Unsafe.pack()
## ReentrantLock(再入锁)
1. 特点
    * 基于AQS实现，能够实现比synchronized更细粒度的控制，比如控制公平性
    * 性能方面与JDK1.8版本中的synchronized是不相上下并且都是可重入
    * 能够判断是否有线程或者某个特定的线程在排队等待锁
    * 可以设置获取锁的超时时间，也就是说在一段时间后没有获得锁将不在尝试获取锁
    * 感知有没有成功获取锁
## CountDownLatch
> 某个线程等待一个或多个线程达到某个状态后继续执行
```java
public class Test {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);
        new Thread(){
            public void run() {
                System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                latch.countDown();
            };
        }.start();
        new Thread(){
            public void run() {
                System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                latch.countDown();
            };
        }.start();
        try {
            System.out.println("等待2个子线程执行完毕...");
            latch.await();
            System.out.println("2个子线程已经执行完毕");
            System.out.println("继续执行主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
//等待2个子线程执行完毕...
//子线程Thread-0正在执行
//子线程Thread-0执行完毕
//子线程Thread-1正在执行
//子线程Thread-1执行完毕
//2个子线程已经执行完毕
//继续执行主线程
```
## CyclicBarrier
> 某组线程都达到固定状态后执行各自任务
```java
public class Test2 {
    public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N);
        for(int i=0;i<N;i++)
            new Writer(barrier).start();
    }
    static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;
        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try {
                Thread.sleep(3000);      //以睡眠来模拟写入数据操作
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(BrokenBarrierException e){
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}
//线程Thread-0正在写入数据...
//线程Thread-1正在写入数据...
//线程Thread-2正在写入数据...
//线程Thread-3正在写入数据...
//线程Thread-0写入数据完毕，等待其他线程写入完毕
//线程Thread-1写入数据完毕，等待其他线程写入完毕
//线程Thread-2写入数据完毕，等待其他线程写入完毕
//线程Thread-3写入数据完毕，等待其他线程写入完毕
//所有线程写入完毕，继续处理其他任务...
//所有线程写入完毕，继续处理其他任务...
//所有线程写入完毕，继续处理其他任务...
//所有线程写入完毕，继续处理其他任务...
```
## Semaphore
> 控制某个资源的访问量，由Semaphore控制与颁发访问令牌
```java
public class Test {
    public static void main(String[] args) {
        int N = 8; 
        Semaphore semaphore = new Semaphore(5);
        for(int i=0;i<N;i++)
            new Worker(i,semaphore).start();
    }
    static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;
        public Worker(int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(this.num+"正在执行");
                Thread.sleep(2000);
                System.out.println(this.num+"执行完毕，释放");
                semaphore.release();           
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```