# HashMap部分源码笔记
## 1.计算底层容器容量方法
> 该方法主要将创建HashMap时传入的初始化容量转为大于cap且是最接近cap的2的n次幂
```
static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}

```

### 1.1 为什么是2的n次幂
* 效率高: 计算容器下标的操作就是key值 & 容器长度 取余的操作，但是使用&的操作效率高于%操作，而key.hashcode & (array.length - 1) = key.hashcode % array.length
* 计算的hash分布更均匀[链接](https://www.sohu.com/a/275783730_100212268)
* 扩容时方便定位: 当容器下标与hash & 的最高位结果为1时，当前元素下标 = 原下标 + 原容器长度，当最高位结果为0时，下标不变

```

// 假设容器长度 2^4 = 16,某个key(key1)的hash为101

1.先计算出key1的下标
0000 0000 0000 1111   2^4-1 
0000 1010 0101 0101   & key1
___________________

0000 0000 0000 0101   容器下标 0101 = 5
               |
             最高位

2.此时容器扩容为16*2 = 32，为保持hash落点均匀元素需重新计算下表

0000 0000 0001 1111   2^5-1 
0000 1010 0101 0101   & key1
___________________
0000 0000 0001 0101   容器下标 0001 0101 = 21
             |
           最高位


//21感觉有点奇怪，5(原下标)+16(原容器长度) = 21(现下标) 可能是巧合在试试

3.此时容器扩容为32*2 = 64

0000 0000 0011 1111   2^6-1 
0000 1010 0101 0101   & key1
___________________
0000 0000 0001 0101    容器下标 0001 0101 = 21 
            |
          最高位

// 没变！ 在扩容试下

4.此时容器扩容为64*2 = 128

0000 0000 0111 1111   2^7-1 
0000 1010 0101 0101   & key1
___________________
0000 0000 0101 0101    容器下标 0001 0101 = 85 
           |
        最高位

// (原下标)21+64(原容器长度) = 85(现下标) 发现规律没有，再次尝试

5.此时容器扩容为128*2 = 256

0000 0000 1111 1111   2^8-1 
0000 1010 0101 0101   & key1
___________________
0000 0000 0101 0101    容器下标 85没变
          |
        最高位

```


### 1.2 tableSizeFor 演示

* 假设tableSizeFor(211);
```
int n = cap - 1;       -1是防止cap本就是2的n次幂，-1后经过后续计算得到的还是数据本身


211二进制为 1101 0011
0000 0000 1101 0011    n |= n >>> 1;
0000 0000 0110 1001    | 操作
———————————————————
0000 0000 1111 1011    n |= n >>> 2;
0000 0000 0011 1110    | 操作 
———————————————————
0000 0000 1111 1111    n |= n >>> 4; 
0000 0000 0000 1111    | 操作
———————————————————
0000 0000 1111 1111    n |= n >>> 8; 
0000 0000 0000 0000    | 操作
———————————————————
0000 0000 1111 1111    n |= n >>> 16;
0000 0000 0000 0000    | 操作
———————————————————
0000 0000 1111 1111    结果 255

(n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1; 最后这步判断最关键的一句 n + 1

0000 0000 1111 1111  255
0000 0000 0000 0001  +1
———————————————————
0000 0001 0000 0000  = 256 = 2^8


```
### 1.3 总结：所有的位移与|都是为了将高位以下数据改为1(只要n>0必定有一位为1)，当所有位上的数据为1时，则+1以后得到的数必定大于cap且是最接近cap的2的n次幂

## 2.根据hash计算下标
> (h = key.hashCode()) ^ (h >>> 16) 主要是为了混淆高低位，一些相差较大的数组可能在二进制中出现高位不同但低位相同，而计算hashMap下标时会跟当前容器容量进行&操作，这样就会导致命名数字相差很大但计算出来的下标却一样，^ (h >>> 16)这个操作就是为了混淆高低位。具体请看参考第4条。
```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16); // 
}

```
## 3. 核心方法
### 3.1 map的put方法
```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
    // 底层容器
    Node<K,V>[] tab; 
    // 与待存储值冲突的元素
    Node<K,V> p; 
    // 容器容量
    int n, 
    // 待存储的hash值
    i;
    // 判断如果现有容器是空的则初始化容器
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    // 判断当前存储元素所对应的下标是否有元素，没有则继续存储
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else { // 计算出的下标有元素

        Node<K,V> e;

        // 冲突元素的key
        K k;

        // 判断存储的元素与冲突的元素是否是相同的key,是则只要替换值
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        else if (p instanceof TreeNode) // 如果是树型则调用树的存储方法
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {


            for (int binCount = 0; ; ++binCount) {

                // 如果链表的下个节点为null
                if ((e = p.next) == null) {
                    //  将存储元素转为链表节点
                    p.next = newNode(hash, key, value, null);
                    // 判断是否可以转换为红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }

                // 在链表中查找key相同的元素，这样只改动对应元素的值即可
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                // 移动链表
                p = e;
            }
        }

        // 找到了相同key的元素(existing mapping for key),
        if (e != null) { // 
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            // 更改值的回调
            afterNodeAccess(e);
            return oldValue;
        }
    }
    // 统计更改次数
    ++modCount;
    // 判断是否需要扩容
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

### 3.2 map的resize方法
```java
final Node<K,V>[] resize() {
        // 容器 
        Node<K,V>[] oldTab = table;
        // 保存现有容量
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        // 现有扩容的阈值
        int oldThr = threshold;
        // 新的容量
        int newCap,
        // 新的扩容阈值 
        newThr = 0;
        if (oldCap > 0) {
            // 达到最大值不在扩容
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            // 没超过最最大值，将容量与阈值扩充为原来的2倍
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            // 循环底层数组准备拷贝每个元素
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null) // 单个节点直接重新计算下标并存储
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode) // 红黑树的拷贝
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        // 保存不移动的链表
                        Node<K,V> loHead = null, loTail = null;
                        // 保存待移动的链表
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            // 注意此时的小标计算 & oldCap，没有oldCap-1是为了求出原有hash高位是否为1，具体请看参考第5条
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            // 高为为1，说明与原有的下标不符需要移动
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        // 原有位置上保存新的链表(下标在发生扩容时不需要移动)
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        // 存放扩容后需要移动的链表，因为扩容必定时原有容量的两倍，所以 newTab[j + oldCap]
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
```

### 3.3 map中的红黑树
### 3.3.1 将链表转为红黑树
```java
final void treeifyBin(Node<K,V>[] tab, int hash) {
    int n, index; Node<K,V> e;
    // 如果容器长度小于64s则无须树化，直接扩容即可
    if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
        resize();
    else if ((e = tab[index = (n - 1) & hash]) != null) {
        TreeNode<K,V> hd = null, tl = null;
        // 循环链表创建红黑树节点
        do {
            // 创建红黑树节点
            TreeNode<K,V> p = replacementTreeNode(e, null);
            if (tl == null)
                hd = p;
            else {
                p.prev = tl;
                tl.next = p;
            }
            tl = p;
        } while ((e = e.next) != null);
        if ((tab[index] = hd) != null)
            hd.treeify(tab);
    }
}
```
### 3.3.2 创建红黑树
```java
final void treeify(Node<K,V>[] tab) {
    // 根节点
    TreeNode<K,V> root = null;
    for (TreeNode<K,V> x = this, next; x != null; x = next) {
        next = (TreeNode<K,V>)x.next;
        x.left = x.right = null;
        if (root == null) {
            x.parent = null;
            x.red = false;
            root = x;
        }
        else {
            // 当前循环节点的key
            K k = x.key;
            // 当前循环节点的hash
            int h = x.hash;
            Class<?> kc = null;
            for (TreeNode<K,V> p = root;;) {
                int dir, ph;
                K pk = p.key;

                // 整个if代码块用来判断存储到左节点还是右节点
                if ((ph = p.hash) > h)
                    dir = -1;
                else if (ph < h)
                    dir = 1;
                else if ((kc == null &&
                            (kc = comparableClassFor(k)) == null) ||
                            (dir = compareComparables(kc, k, pk)) == 0)
                    dir = tieBreakOrder(k, pk);

                TreeNode<K,V> xp = p;
                // 判断要存储的节点是否为null
                if ((p = (dir <= 0) ? p.left : p.right) == null) {
                    x.parent = xp;
                    if (dir <= 0)
                        xp.left = x;
                    else
                        xp.right = x;
                    // 维护平衡
                    root = balanceInsertion(root, x);
                    break;
                }
            }
        }
    }
    // 将root保存到在容器里对应下标的第一个节点
    moveRootToFront(tab, root);
}
```

### 3.3.3 保持平衡的左旋转
```java
static <K,V> TreeNode<K,V> rotateLeft(TreeNode<K,V> root,
                                        TreeNode<K,V> p) {
    TreeNode<K,V> r, pp, rl;
    if (p != null && (r = p.right) != null) {
        if ((rl = p.right = r.left) != null)
            rl.parent = p;
        if ((pp = r.parent = p.parent) == null)
            (root = r).red = false;
        else if (pp.left == p)
            pp.left = r;
        else
            pp.right = r;
        r.left = p;
        p.parent = r;
    }
    return root;
}
```
### 3.3.4 保持平衡的右旋转
```java
static <K,V> TreeNode<K,V> rotateRight(TreeNode<K,V> root,
                                        TreeNode<K,V> p) {
    TreeNode<K,V> l, pp, lr;
    if (p != null && (l = p.left) != null) {
        if ((lr = p.left = l.right) != null)
            lr.parent = p;
        if ((pp = l.parent = p.parent) == null)
            (root = l).red = false;
        else if (pp.right == p)
            pp.right = l;
        else
            pp.left = l;
        l.right = p;
        p.parent = l;
    }
    return root;
}
```
### 3.3.5 插入时保持平衡
```java
static <K,V> TreeNode<K,V> balanceInsertion(TreeNode<K,V> root,
                                                    TreeNode<K,V> x) {
    // 新插入的节点颜色为红色
    x.red = true;
    // xp = x父节点，xpp = x祖先节点，xppl = x祖先的左节点，xppr = x祖先的右节点
    for (TreeNode<K,V> xp, xpp, xppl, xppr;;) {

        // 判断是否为根节点
        if ((xp = x.parent) == null) {
            x.red = false;
            return x;
        }
        // 判断父节点是否为根节点
        else if (!xp.red || (xpp = xp.parent) == null)
            return root;

        // x的父节点xp为祖先节点xpp的左节点
        if (xp == (xppl = xpp.left)) {
            // 祖先的右节点xppr不为空且是红色
            // 除了x节点为新节点其它节点必定属于红黑树特性,而x的祖先的右节点是红色则说明x祖先节点xpp必定为黑色，x的父节点xp必定为红色。此时只要变色就可以保证xpp开始的树的平衡，最后将x指向xpp准备下一次的平衡判断
            if ((xppr = xpp.right) != null && xppr.red) {
                xppr.red = false;
                xp.red = false;
                xpp.red = true;
                x = xpp;
            }
            // xppr为空或者是黑色
            else {
                // 如果在右侧则需先左旋转
                if (x == xp.right) {
                    root = rotateLeft(root, x = xp);
                    xpp = (xp = x.parent) == null ? null : xp.parent;
                }
                if (xp != null) {
                    xp.red = false;
                    if (xpp != null) {
                        xpp.red = true;
                        root = rotateRight(root, xpp);
                    }
                }
            }
        }
        else {
            if (xppl != null && xppl.red) {
                xppl.red = false;
                xp.red = false;
                xpp.red = true;
                x = xpp;
            }
            else {
                if (x == xp.left) {
                    root = rotateRight(root, x = xp);
                    xpp = (xp = x.parent) == null ? null : xp.parent;
                }
                if (xp != null) {
                    xp.red = false;
                    if (xpp != null) {
                        xpp.red = true;
                        root = rotateLeft(root, xpp);
                    }
                }
            }
        }
    }
}
```


## 参考
1. [hash相关解析](https://blog.csdn.net/huzhigenlaohu/article/details/51802457)
2. [源码分析](https://juejin.im/post/5d51884ee51d4561e0516ac9)
3. [源码分析](https://juejin.im/post/5d54300151882551d172f22d)
4. [hash算法](https://www.zhihu.com/question/20733617/answer/32513376)
5. [扩容](https://segmentfault.com/a/1190000015812438)
6. [红黑树插入](https://www.cnblogs.com/oldbai/p/9890808.html)