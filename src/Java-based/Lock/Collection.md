# 并发同步容器
## 1. ConcurrentHashMap
> ConcurrentHashMap与HashMap的底层数据结构一致单不允许插入Null键。存在多线程操作资源的时候，Hashtable与包装后的HashMap效率低，synchronized以当前对象作为锁，同一时间只能有一个线程在执行。
> ConcurrentHashMap将锁住的资源进行更细化的拆分，优化后数组中每个数据有拥有自己的锁，加锁机制采用CAS+synchronized。
### 1.1 加锁过程
1. 数组中的每个元素保存的是链表或红黑树的头节点
2. 当调用ConcurrentHashMap存储方法时，判断插入的是否是头结点
    * 是则采用CAS插入，失败则循环重试
    * 不是则采用synchronized加锁进行操作
### 1.2 源码简单分析
```
final V putVal(K key, V value, boolean onlyIfAbsent) {
    if (key == null || value == null) throw new NullPointerException();
    int hash = spread(key.hashCode());
    int binCount = 0;
    for (Node<K,V>[] tab = table;;) {
        Node<K,V> f; int n, i, fh;
        // 底层容器为空则进行初始化
        if (tab == null || (n = tab.length) == 0)
            tab = initTable();
        // 当前hash位置为null则直接cas操作增加节点
        else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
            if (casTabAt(tab, i, null,
                            new Node<K,V>(hash, key, value, null)))
                break;                   
        }
        // MOVED代表当前节点增在扩容
        else if ((fh = f.hash) == MOVED)
            // helpTransfer作用是辅助扩容
            tab = helpTransfer(tab, f);
        else {
            V oldVal = null;
            // 以当前节点(Node)加锁
            synchronized (f) {
                if (tabAt(tab, i) == f) {
                    // 链表增加节点
                    if (fh >= 0) {
                        binCount = 1;
                        for (Node<K,V> e = f;; ++binCount) {
                            K ek;
                            if (e.hash == hash &&
                                ((ek = e.key) == key ||
                                    (ek != null && key.equals(ek)))) {
                                oldVal = e.val;
                                if (!onlyIfAbsent)
                                    e.val = value;
                                break;
                            }
                            Node<K,V> pred = e;
                            if ((e = e.next) == null) {
                                pred.next = new Node<K,V>(hash, key,
                                                            value, null);
                                break;
                            }
                        }
                    }
                    // 红黑树的增加
                    else if (f instanceof TreeBin) {
                        Node<K,V> p;
                        binCount = 2;
                        if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                        value)) != null) {
                            oldVal = p.val;
                            if (!onlyIfAbsent)
                                p.val = value;
                        }
                    }
                }
            }
            // 判断是否需要树化
            if (binCount != 0) {
                if (binCount >= TREEIFY_THRESHOLD)
                    treeifyBin(tab, i);
                if (oldVal != null)
                    return oldVal;
                break;
            }
        }
    }
    addCount(1L, binCount);
    return null;
}
```
### 1.3 1.7版本的ConcurrentHashMap
#### 1.7
* 底层容器采用数组加链表
* 并发采用分段锁的形式(segment)，每个Segment都使用ReentrantLock加锁，默认有16个Segment,只有可以初始化可以变更。
## 2. CopyOnWriteArrayList
> 数组类型的并发容器，保证了高效的读操作，在读读与读写时均不会阻塞，为了保证读的效率CopyOnWriteArrayList在写操作时将原始数据拷贝出来，在操作完拷贝数据后将CopyOnWriteArrayList的数据指针指向拷贝数据放弃原始数据，换句话说就是在写的操作发生时CopyOnWriteArrayList会维护两份数据，一份用于读一份用于写。
### 2.1 源码解析
#### 2.1.1 add操作
```
public boolean add(E e) {
    // 加锁
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        // 获取容器的原始数据
        Object[] elements = getArray();
        int len = elements.length;
        // 复制原始数据
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        // 增加新值
        newElements[len] = e;
        // 将新的数据设置到当前成员变量上
        setArray(newElements);
        return true;
    } finally {
        lock.unlock();
    }
}
```
#### 2.1.3 get操作
```
private E get(Object[] a, int index) {
    // 直接下标访问并返回
    return (E) a[index];
}

```
### 2.2 缺点
* 只能保证数据的最终一致性，无法保证实时一致性。
* 内存占用过多，在写入时复制就会导致占用过大内存。
## 3. 并发队列

## 参考
1. https://www.cnblogs.com/yangfeiORfeiyang/p/9694383.html