# 多线程
## 进程与线程
1. 进程的定义
    * 为了是计算机可以串行/并行的执行多个程序，进程独占自己的内存空间，保存各自的运行状态，互不干扰并可以相互切换，进程是资源分配的最小单位
2. 线程的定义
    * 更细粒度的执行与控制程序，一个进程下有多个线程，线程共享进程的内存资源，线程可相互切换，线程是CPU调度的最小单位
    * Java中线程的六个状态
        1. 新建(New):创建后尚未启动
        2. 运行(Runnable): 正在运行或者等待CPU调度
        3. 无限期等待状态(Waiting): 不会被分配CPU执行时间，需要显示被唤醒
            * 没有设置Timeout参数的Obejct.wait()
            * 没有设置Timeout参数的Thread.join()
            * lockSupport.park()方法
        4. 限期等待(Timed Waiting): 在一定时间后会由系统自动唤醒
            * Thread.sleep()方法
            * 设置了Timeout参数的Obejct.wait()
            * 设置了Timeout参数的Thread.join()
            * lockSupport.parkNanos()方法
            * lockSupport.parkUntil()方法
        5. 阻塞(Blocked): 等待获取排它锁
        6. 结束(Terminated): 已终止线程的状态，线程已经结束执行
3. Java中，线程主要分为两类，User Thread(用户线程)与Daemon Thread(守护线程)
            * 用户线程: 直行普通任务的线程
            * 守护线程: 主要服务于用户线程，如GC线程
4. Java进程和线程的关系
    * 每个进程对应一个JVM实例，多个线程共享JVM里的堆
## 并发与并行
1. 并发: Java中多个线程同时处于Runnable状态，单核CUP会根据时间分片算法调度不同的线程，也就是说，多个线程交替执行
2. 并行: 多线线程同时执行多个任务
## Thread中start与run的区别
1. run调用只是使用当前线程的调用自身的run方法
2. start会调用带有native关键字的方法，其内部含义是调用JVM指令创建子线程并执行run方法

## Thread与Runnable的区别
1. Runnable是一个接口,Thread是一个类并且它实现了Runnable
2. 都说Runnable和Thread是实现多线程的两种方式，但个人感觉最终创建线程并执行的还是Thread,只不过实现了Runnable说明该类是应用于多线程的，而Runnable是一个多线程标识
3. Runnable中run方法有两个缺陷：1.无法返回值,2.无法抛出异常。缺陷产生的原因是最终调用run方法的是JDK内部，我们无法直接接收或捕获返回值。

## 处理线程的返回值
1. 主线程等待法
* 缺点
    1. 无法做到精准控制，如果子线程已经处理完毕主线程依旧需要等待100ms
```java
public class CycleWait implements Runnable{
    private String value;
    public void run() {
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        value = "we have data now";
    }

    public static void main(String[] args) throws InterruptedException {
        CycleWait cw = new CycleWait();
        Thread t = new Thread(cw);
        t.start();
        while (cw.value == null){
            Thread.currentThread().sleep(100);
        }
        System.out.println("value : " + cw.value);
    }
}
```
2. 使用Thread类的join方法阻塞当前线程以等待子线程处理完毕
* 缺点
    1. 如果出现子线程1执行完在执行子线程2......最后在执行主线程，代码冗余，无法更细粒度的控制
```java
public class CycleWait implements Runnable{
    private String value;
    public void run() {
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        value = "we have data now";
    }

    public static void main(String[] args) throws InterruptedException {
        CycleWait cw = new CycleWait();
        Thread t = new Thread(cw);
        t.start();
        t.join();
        System.out.println("value : " + cw.value);
    }
}
```
3. 通过Callable接口实现，通过Future Task 或者 线程池获取
```java
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception{
        String value="test";
        System.out.println("Ready to work");
        Thread.currentThread().sleep(5000);
        System.out.println("task done");
        return  value;
    }
}
```
* Future Task
```java
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException {
        FutureTask<String> task = new FutureTask<String>(new MyCallable());
        new Thread(task).start();
        if(!task.isDone()){
            System.out.println("task has not finished, please wait!");
        }
        System.out.println("task return: " + task.get());
    }
}
```
* 线程池
```java
public class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        Future<String> future = newCachedThreadPool.submit(new MyCallable());
        if(!future.isDone()){
            System.out.println("task has not finished, please wait!");
        }
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            newCachedThreadPool.shutdown();
        }
    }
}
```
## sleep与wait的区别
1. sleep是Thread方法，wait是Object方法
2. sleep可以在任何地方使用，wait只可以在 synchronized方法或synchronized块中使用
3. sleep让出CPU但不会释放锁，wait让出CPU，释放共享锁
```java
public class WaitSleepDemo {
    public static void main(String[] args) {
        final Object lock = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread A is waiting to get lock");
                synchronized (lock){
                    try {
                        System.out.println("thread A get lock");
                        Thread.sleep(20);
                        System.out.println("thread A do wait method");
                        lock.wait();
                        System.out.println("thread A is done");
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        try{
            Thread.sleep(10);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread B is waiting to get lock");
                synchronized (lock){
                    try {
                        System.out.println("thread B get lock");
                        System.out.println("thread B is sleeping 10 ms");
                        Thread.sleep(10);
                        lock.notifyAll();
                        Thread.yield();
                        Thread.sleep(2000);
                        System.out.println("thread B is done");
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
```
## notify与notifyAll的区别
1. 锁池(EntryList)与等待池(WaitSet)
    * 锁池: 没有竞争到对象锁的线程被放入锁池
    * 等待池: 当一个拥有锁的线程内调用wait后，当前线程会释放锁并进入等待池，等待被唤醒，如果带参数调用wait，在到达指定参数时间后将线程将竞争锁对象，如没有竞争到将进入锁池
2. notify将随机从等待池唤醒一个线程，让其去竞争锁，如未竞争到将进入锁池
3. notifyAll将唤醒等待池中所有线程，让其去竞争锁，线程的优先级越高获取锁的几率越大，如未竞争到将进入锁池
```java
import java.util.logging.Level;
import java.util.logging.Logger;
public class NotificationDemo {
    private volatile boolean go = false;

    public static void main(String args[]) throws InterruptedException {
        final NotificationDemo test = new NotificationDemo();

        Runnable waitTask = new Runnable(){

            @Override
            public void run(){
                try {
                    test.shouldGo();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " finished Execution");
            }
        };

        Runnable notifyTask = new Runnable(){

            @Override
            public void run(){
                test.go();
                System.out.println(Thread.currentThread().getName() + " finished Execution");
            }
        };

        Thread t1 = new Thread(waitTask, "WT1"); //will wait
        Thread t2 = new Thread(waitTask, "WT2"); //will wait
        Thread t3 = new Thread(waitTask, "WT3"); //will wait
        Thread t4 = new Thread(notifyTask,"NT1"); //will notify

        //starting all waiting thread
        t1.start();
        t2.start();
        t3.start();

        //pause to ensure all waiting thread started successfully
        Thread.sleep(200);

        //starting notifying thread
        t4.start();

    }
    /*
     * wait and notify can only be called from synchronized method or bock
     */
    private synchronized void shouldGo() throws InterruptedException {
        while(go != true){
            System.out.println(Thread.currentThread()
                    + " is going to wait on this object");
            wait(); //release lock and reacquires on wakeup
            System.out.println(Thread.currentThread() + " is woken up");
        }
        go = false; //resetting condition
    }

    /*
     * both shouldGo() and go() are locked on current object referenced by "this" keyword
     */
    private synchronized void go() {
        while (go == false){
            System.out.println(Thread.currentThread()
                    + " is going to notify all or one thread waiting on this object");

            go = true; //making condition true for waiting thread
            //notify(); // only one out of three waiting thread WT1, WT2,WT3 will woke up
            notifyAll(); // all waiting thread  WT1, WT2,WT3 will woke up
        }

    }
}
```
## yield
1. 定义: Thread.yield暗示调度器当前线程可以让出资源，当不会对锁造成影响，yield方法只会给相同优先级或更高优先级的线程以运行的机会

## 参考
1. [进程与线程](http://www.ruanyifeng.com/blog/2013/04/processes_and_threads.html)
2. https://coding.imooc.com/lesson/303.html#mid=21216
3. http://silencedut.com/2016/06/25/%E4%BB%8E%E4%BD%BF%E7%94%A8%E5%88%B0%E5%8E%9F%E7%90%86%E5%AD%A6%E4%B9%A0Java%E7%BA%BF%E7%A8%8B%E6%B1%A0/
4. [并行与并发](https://juejin.im/post/5bdf0667e51d450b267fe3e3#comment)
5. [线程状态](https://juejin.im/post/5e208da16fb9a02fdd389934   )
## 遗留问题
1.慕课-实战-java第八章第一小节
    * java是单线程模型？





    
    
    
    
    