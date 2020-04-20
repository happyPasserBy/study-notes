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

## 3. ThreadLocal常见问题
### 3.1 内存泄漏
> ThreadLocal中的内部类ThreadLocalMap中每个元素都是Entry类型，而Entry继承自WeakReference,Entry在创建时会执行以下代码
```
Entry(ThreadLocal<?> k, Object v) {
    super(k);
    value = v;
}
```
> 可以看待super(k)调用的是WeakReference的构造，也就是说k是一个弱引用也就是随时可以被GC回收，而value确是强引用，这就会出现k被回收而value继续存活导致的内存泄漏。在ThreadLocalMap中的resize等方法中对此种情况作出的了处理，
```
private void resize() {
    ......
    for (int j = 0; j < oldLen; ++j) {
        Entry e = oldTab[j];
        if (e != null) {
            ThreadLocal<?> k = e.get();
            if (k == null) {
                e.value = null; // Help the GC <-------------
            } else {
                int h = k.threadLocalHashCode & (newLen - 1);
                while (newTab[h] != null)
                    h = nextIndex(h, newLen);
                newTab[h] = e;
                count++;
            }
        }
    }
    ......
}
```
> if (k == null) e.value = null;清理了无用的value。在实际开发中应及时调用remove清理无用对象


### 3.2 NullPointerException
> 如果在set之前进行get则get返回一个null，null很容易引发空指针，比如说基本类型的自动拆装箱等操作

