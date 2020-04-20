# ThreadLocal
> 用于提供线程的局部变量，也就是说让对象在线程之间隔离，ThreadLocal与同步机制有些差异，同步机制保证的是数据的一致性，
> 而ThreadLocal保证的是多线程环境的下数据独立性,每个线程都有一个ThreadLocalMap用来存储线程变量。
## 1.简单使用
```
public class TestThreadLocal {

    private static ThreadLocal threadLocal = new ThreadLocal();

    @Test
    public void teat(){
        for (int i = 0; i < 1000; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 存值
                    threadLocal.set(finalI);
                    print();
                }
            }).start();
        }

    }

    public void print(){
        // 取值
        System.out.println(threadLocal.get());
    }
}
```
## 2. 深入分析
[请看我ThreadLocal源码分析笔记](../Jdk_source_learn/lang/ThreadLocal.java)
