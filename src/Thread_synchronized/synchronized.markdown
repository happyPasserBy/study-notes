# synchronized
## 特性
1. 互斥性: 在同一时间值允许一个线程持有某个对象锁，通过这种特性来实现多线程的协调机制，这样在同一时间只有一个线程对需要同步的代码块进行访问。互斥性也成为操作的原子性
2. 可见性: 必须保证在锁释放之前，对共享变量所做的修改对随后获得该锁的另一个线程是可见的，否则会导致数据不一致
## 锁的分类
1. 获取对象锁
    * 同步代码块，锁是小括号()中的实例对象
    * 同步非静态方法，锁是当前对象的实例 
2. 获取类锁
    * 同步代码块，锁是小括号()中的类对象
    * 同步静态方法，锁是当前的类对象
## synchronized的基础
1. Java对象头
    * 对象在内存中的布局
        1. 对象头: 它是实现synchronized的锁对象的基础，一般而言，synchronized使用的锁对象是存储在Java对象头里的，JVM中采用2个字来存储对象头(如果对象是数组则会分配3个字，多出来的1个字记录的是数组长度)，其主要结构是由Mark Word 和 Class Metadata Address 组成
        2. 实例数据: 存放类的属性数据信息，包括父类的属性信息，如果是数组的实例部分还包括数组的长度，这部分内存按4字节对齐
        3. 对其填充: 由于虚拟机要求对象起始地址必须是8字节的整数倍。填充数据不是必须存在的，仅仅是为了字节对齐，这点了解即可
    *  对象头结构(32位虚拟机)
        1. Mark Word: 默认存储对象的hashCode,分代年龄，锁类型，锁标志位等信息
        2. Class MateData Address: 类型指针指向对象的类元数据，JVM通过这个指针确定该对象是哪个类的数据
2. Monitor
    * 每个对象都存在着一个Monitor与之相关联，对象与其Monitor存在多种实现方式，如Monitor可以与对象一起创建或销毁，当一个Monitor被某个线程持有后，他便处于锁定状态，在Java虚拟机(HotSpot)中，monitor是由ObjectMonitor实现的（位于HotSpot虚拟机源码ObjectMonitor.hpp文件，C++实现的）
    * ObjectMonitor中有两个队列，_WaitSet 和 _EntryList，用来保存ObjectWaiter对象列表( 每个等待锁的线程都会被封装成ObjectWaiter对象)，_owner指向持有ObjectMonitor对象的线程，当多个线程同时访问一段同步代码时，首先会进入 _EntryList 集合，
        当线程获取到对象的monitor 后进入 _Owner 区域并把monitor中的owner变量设置为当前线程同时monitor中的计数器count加1，若对象调用 wait() 方法，将释放当前持有的monitor，owner变量恢复为null，count自减1，同时该线程进入 WaitSet集合中等待被唤醒。若当前线程执行完毕也将释放monitor(锁)并复位变量的值，以便其他线程进入获取monitor(锁)。如下图所示
```
ObjectMonitor() {
    _header       = NULL;
    _count        = 0; //记录个数
    _waiters      = 0,
    _recursions   = 0;
    _object       = NULL;
    _owner        = NULL;
    _WaitSet      = NULL; //处于wait状态的线程，会被加入到_WaitSet
    _WaitSetLock  = 0 ;
    _Responsible  = NULL ;
    _succ         = NULL ;
    _cxq          = NULL ;
    FreeNext      = NULL ;
    _EntryList    = NULL ; //处于等待锁block状态的线程，会被加入到该列表
    _SpinFreq     = 0 ;
    _SpinClock    = 0 ;
    OwnerIsThread = 0 ;
  }
```
![avatar](https://img-blog.csdn.net/20170604114223462?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvamF2YXplamlhbg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
* 由此看来，monitor对象存在于每个Java对象的对象头中(存储的指针的指向)，synchronized锁便是通过这种方式获取锁的，也是为什么Java中任意对象可以作为锁的原因，同时也是notify/notifyAll/wait等方法存在于顶级对象Object中的原因
3. synchronzied的四种状态
    * 无锁
        1. 无锁没有对资源进行锁定，所有的线程都能访问并修改同一个资源，但同时只有一个线程能修改成功
    * 偏向锁
        1. 大多数情况下，锁不存在多线程竞争，总是由同一线程多次获得，因此只要实现某种机制来确实是否是同一个线程来获取锁就可以避免一些有关锁的操作，偏向锁是乐观锁的实现
        2. 偏向锁的获取过程
            * 1.检查对象头中Mark Word的结构是否是为偏向锁，锁的标志位等于1则为偏向锁
            * 2.如果为偏向锁并且Mark World的ThreadID等于当前线程ID进入第五步，否则进入第三步
            * 3.如果线程ID不等于当前线程ID，通过CAS竞争锁，如果成功则将Mark Word中线程ID设置为当前线程ID，然后执行5，如果竞争失败则4
            * 4.如果CAS竞争失败，说明有多个线程在竞争锁，当到达全局安全点（safepoint）时获得偏向锁的线程被挂起，偏向锁升级为轻量级锁，
                然后被阻塞在安全点的线程继续往下执行同步代码。（撤销偏向锁的时候会导致stop the word）？？？？？
            * 5.执行同步代码
    * 轻量级锁
        1. 适用于线程交替执行同步块，若存在同一时间访问通一锁的情况，就会导致轻量级锁膨胀为重量级锁，轻量级锁属于乐观锁的实现
        2. 轻量级锁由偏向级所升级而来，偏向锁运行在一个线程进入同步块的情况下，当第二个线程加入锁的争用的时候，偏向锁就会升级为轻量级锁
    * 重量级锁
        1. 属于悲观锁的实现，阻塞没有获得锁的线程
4. 锁膨胀的方向
    * 无锁 》 偏向锁 》 轻量级锁 》 重量级锁

![](https://blog.dreamtobe.cn/img/java_synchronized.png)
## 自旋锁与自适应自旋锁
1. 自旋锁
    * 多数情况下，共享数据锁定的持续状态时间较短，阻塞或唤醒一个Java线程需要操作系统切换CPU状态来完成而切换CPU所耗费处理器的时间并不值得，
        通过让线程执行忙循环等待锁的释放，不让出CPU
2. 自适应自旋
    * 自旋次数不固定，由前一次在同一个锁上的自旋时间及锁的拥有者的状态来决定
## 锁消除
1.定义
    * JIT编译时，对运行上下文进行扫描，去除不可能存在的竞争的锁  
```java
public class StringBufferWithoutSync {
    public void add(String str1, String str2) {
        //StringBuffer是线程安全,由于sb只会在append方法中使用,不可能被其他线程引用
        //因此sb属于不可能共享的资源,JVM会自动消除内部的锁
        StringBuffer sb = new StringBuffer();
        sb.append(str1).append(str2);
    }

    public static void main(String[] args) {
        StringBufferWithoutSync withoutSync = new StringBufferWithoutSync();
        for (int i = 0; i < 1000; i++) {
            withoutSync.add("aaa", "bbb");
        }
    }
}
```
## 锁粗化
1. 定义: 通过扩大加锁范围，避免反复加锁和解锁
```java
class CoarseSync {
    static int num = 0;
    public static String copyString100Times(String target){
        StringBuffer sb = new StringBuffer();
        synchronized (CoarseSync.class){
            while (num<100){
                sb.append(target); //反复调用append,则会扩大锁的范围
                num++;
            }
        }
        return sb.toString();
    }

}

```
## JUC下的常用并发工具类
### AQS
> 并发同步框架，当多个线程访问共享资源时，如果获取到锁继续操作，反之将进入队列进行排队,AQS定义了两种资源共享方式: 独占(ReentrantLock)与共享(CountDownLatch)
3. 公平锁: 当线程等待的时间越久获取到锁的概率越大
4. 使用lock()后必须释放锁unlock();锁需要执行的代码尽量放入try catch中，防止死锁
5. synchronized与ReentrantLock最本质的区别:
    * synchronized和ReentrantLock的区别synchronized基于对象头加锁，而ReentrantLock基于Unsafe.pack()
### ReentrantLock(再入锁)
1. 特点
    * 基于AQS实现，能够实现比synchronized更细粒度的控制，比如控制公平性
    * 性能方面与JDK1.8版本中的synchronized是不相上下并且都是可重入
    * 能够判断是否有线程或者某个特定的线程在排队等待锁
    * 可以设置获取锁的超时时间，也就是说在一段时间后没有获得锁将不在尝试获取锁
    * 感知有没有成功获取锁
### CountDownLatch
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
### CyclicBarrier
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
### Semaphore
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


## Java内存模型JMM 
1. 定义: Java内存模型本身是一种抽象的概念，并不真实存在，它描述的是一组规则或者规范，通过这个规范定义了程序各个变量(实例字段，静态字段和高层数组对象的元素)的访问方式

![](./jmm.png)

2. JMM中的主内存
    * 存储Java实例对象
    * 包括成员变量，类信息，常量，静态变量等
    * 主内存为共享数据，当多个线程访问通一个数据时可能引发安全问题
3. JMM中的工作内存
    * 存储当前方法的所有本地变量信息，本地变量对其它线程不可见
    * 共享变量的副本
    * 字节码行号指示器，Native方法信息
    * 属于线程私有数据区域，不存在线程安全问题
4. volatile
    * 读取volatile修饰的变量时将当前线程的工作区内存致为无效，也就是说不读取本地的共享变量副本
    * 写入volatile修饰的变量时将工作区的共享变量刷新到主内存中
    * volatile可以禁止JVM优化-指令重排序
    * volatile与synchronized的区别
        1. volatile是告诉JVM变量在寄存器中是不确定的，需要从主存中读取，synchronized则是锁定当前变量，只有当前线程可以访问
        2. volatile仅能实现变量的可见性，不能保证原子性，而synchronized都可以
        3. volatile不会阻塞线程，synchronized是可以的
        4. volatile标记的变量不会被编译器优化，而synchronized会
    
## CAS (Compare and Swap)
1. 一种高效实现线程安全性的方法，支持原子更新操作，适用于计数器，序列发生器等场景，CAS属于乐观锁机制，CAS操作失败后由开发人员决定后续操作
2. CAS 操作包含三个操作数 —— 内存位置（V）、预期原值（A）和新值(B)。 如果内存位置的值与预期原值相匹配，那么处理器会自动将该位置值更新为新值 。否则，处理器不做任何操作。无论哪种情况，它都会在 CAS 指令之前返回该 位置的值。（在 CAS 的一些特殊情况下将仅返回 CAS 是否成功，而不提取当前 值。）CAS 有效地说明了“我认为位置 V 应该包含值 A；如果包含该值，则将 B 放到这个位置；否则，不要更改该位置，只告诉我这个位置现在的值即可
3. CAS存在的问题
    * ABA问题: 因为CAS需要在操作值的时候检查下值有没有发生变化，如果没有发生变化则更新，但是如果一个值原来是A，变成了B，又变成了A，那么使用CAS进行检查时会发现它的值没有发生变化，但是实际上却变化了。ABA问题的解决思路就是使用版本号。在变量前面追加上版本号，每次变量更新的时候把版本号加一，那么A－B－A 就会变成1A-2B－3A
    * 自旋CAS: 自旋CAS如果长时间不成功，会给CPU带来非常大的执行开销
    * 对一个共享变量执行操作时，CAS能够保证原子操作，但是对多个共享变量操作时，根据CAS特性是无法保证操作的原子性的。
        Java从1.5开始JDK提供了AtomicReference类来保证引用对象之间的原子性，可以把多个变量放在一个对象里来进行CAS操作
## 参考
1. https://coding.imooc.com/class/303.html
2. https://blog.csdn.net/javazejian/article/details/72828483
3. https://blog.csdn.net/ls5718/article/details/52563959
4. https://juejin.im/post/5bee576fe51d45710c6a51e0#comment
5. https://blog.csdn.net/zqz_zqz/article/details/70233767
6. https://blog.dreamtobe.cn/2015/11/13/java_synchronized/
7. http://www.importnew.com/21889.html














   
   
   
   
   





