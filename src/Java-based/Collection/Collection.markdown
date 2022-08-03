# Java集合浅析
1. 定义: 集合是由多个工具类组成，方便我们存储操作一组数据
2. 集合体系
    * 大致分为Collection与Map
![collections.png](collection.png)
3. Collection体系 
    1. List
        1. 特点: 值可重复，给每个值分配索引，常用子类有ArrayList,LinkedList
        2. ArrayList
            * 底层使用数组实现，所以查询快，增删慢
            * 查询快: 数组存储在内存中是连续的内存块，可根据连续的下标进行访问
            * 增删慢: 因为是连续的内存块，如果在数组中间的位置删除一个元素，则从删除的下标位置开始，
                右侧元素依次向左移动补齐(存储方向是从左到右，从0开始)，增加则是从插入下标开始元素依次向右移动
        3. LinkedList
            * 基于链表实现，所以增删快，查询慢，线程不安全
            * 增删快: 链表为不连续内存块，每个内存块保存着上一个与下一个元素的指针，如果删除当中某个元素只需更改指针即可
            * 查询慢: 因为链表的不连续性，查找只能从头或者尾
        4. Vector
            * 基于数组，线程安全
    2. Set
        1. 特点: 无下标，元素不可重复，常用子类有HashSet，LinkedHashSet，TreeSet
        2. HashSet
            * 基于哈希表实现，允许null值，但只能出现一次
            * 当对象存入HashSet中，先调用对象的hashCode获得hash值，与集合中元素进项比较
                1. 如果没有hash值相同的元素则存入
                2. 如果存在hash值相同元素，则在调用equals与之比较，结果为true则不存入，结果为false则存入
            * HashSet底层使用的是HashMap
            ```java
               package java.util;
               public class HashSet<E> extends AbstractSet<E> implements Set<E>, Cloneable, java.io.Serializable {
                   static final long serialVersionUID = -5024744406713321676L;
                   private transient HashMap<E,Object> map;
                   // Dummy value to associate with an Object in the backing Map
                   private static final Object PRESENT = new Object();
                   //........
                   // 注意这里add方法
                   public boolean add(E e) {
                       return map.put(e, PRESENT)==null;
                   }
                   //.......
               }
            ```
        3. LinkedHashSet
            * 基于链表实现，能保证元素的存取顺序，元素不可重复，属于HashSet子类，判断元素是否唯一与HashSet相同
        4. TreeSet
            * 基于二叉树实现，默认采用自然排序，
            * 自然排序: 调用TreeSet的add方法时，会默认把对象提升为Comparable,然后调用compareTo进行比较
                1. 比较返回0: 不存储
                2. 比较返回负数: 存放在树的左边
                3. 比较返回正数: 存放在树的右边
            * 比较器排序
                1. 实例化TreeSet时需要传入实现了Comparator的子类并重写compare方法
    3. Queue
5. Map体系
    1. 特点: 采用双列集合，其中保存的是键值对，键值要求唯一(部分Map值可以使null),值可以重复,键值一一对应
    2. HashMap
        1. 使用Hash算法+equals比较键，null可以作为键也可作为值
        2. 线程不安全
        3. Java8以前，HashMap基于数组+链表
        4. Java8及以后，HashMap基于数字+链表+红黑树，当出现Hash碰撞后，如果元素不相等则使用链表存储(默认是链表)，当链表存储的元素达到一定阈值(TREEIFY_THRESHOLD:8)后转为红黑树存储,当红黑树的容量小于某个阈值(UNTREEIFY_THRESHOLD:6)则转为链表
        5. HashMap在实例化时并不会初始化数组的长度，而是采用懒加载的方式，在存储元素的时候进行初始化
        6. HashMap自动扩容的时机: 当前容量 > 总容量 * 负载因子(0.75)就需要扩容，HashMap初始化容量为16
    3.LinkedHashMap
        1. 基于哈希Hash与链表实现，存取有序
    3. TreeMap 
        1. 基于二叉树实现,传入的键需要进行比较与TreeSet相似
            * 自然排序: 作为键的对象需实现Comparable接口
            * 自定义排序: 实例化TreeSet时需要传入实现了Comparator的子类并重写compare方法
    4. Hashtable
        * 基于数组+链表实现，线程安全
        * null不可作为键亦不可作为值
6. Map与Collection接口的区别
    1. Map是双列集合的根接口，Collection是单列集合的根接口
    2. Map的数据结构是针对键而Collection是针对元素
4. 集合与数组
    1. 数组既可以存储基本数据类型，也可以存储引用数据类型，集合只能存储应用数据类型，如果存储的是基本数据类型则会自动装箱
    2. 数组的长度是固定的，集合的长度是可变的，可随元素增加而自动增长
    3. 部分集合的底层采用的仍是数组，如ArrayList，ArrayList自动增长的原因在于，既定的初始化内存长度不够后会在内存中申请一块大于当前
        1.5(不一定是1.5)倍的内存块然后将数据拷贝到其中，在将原有对象标记为垃圾。
## 参考
1. http://www.runoob.com/java/java-collections.html
2. https://juejin.im/post/5ad82dbef265da503825b240#heading-28
3. https://www.jianshu.com/p/1031c23f080c
4. https://www.cnblogs.com/dassmeta/p/5338955.html