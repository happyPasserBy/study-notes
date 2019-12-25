# 内存模型-JDK8
## 一 线程的角度
> 线程私有
* 程序计数器(字节码指令)
    1. 每个线程都独享自己的计数器
    2. 用于记录程序执行的逻辑地址
    3. 不会内存泄漏
    4. 如果为native方法则计数器值为undefined
* 虚拟机栈
    1. 每个Java方法执行都会创建栈帧
    2. 每个栈帧存储了局部变量表、返回地址等信息
* 本地方法栈
    1. 与虚拟机栈相似，是虚拟机调用了带有native关键字的方法，native方法为其他语言编写的扩展方法
> 线程共有
* 元空间
    1. 元空间与永久代均是方法区的实现，用于存储类的信息，JDK8开始，元空间替换了永久代
    2. 元空间MetaSpace(类加载信息,OOM)与永久代PermGen的区别
        1. 元空间使用本地内存，永久代使用JVM内存
    3. 元空间MetaSpace与永久代PermGen相比的优势
        1. 字符串常量池存在于永久代中，容易出现性能问题和内存溢出
        2. 类和方法的信息大小难以确定，给永久代的大小指定带来困难
        3. 永久代为GC带来不必要的复杂性
        4. 方便HotSpot与其他JVM集成
* Java堆
    1. 字符串常量池
    2. 引用类型
    
## 二 内存模型中堆和栈的区别
* 管理方式: 栈自动释放，堆需要GC
* 空间大小: 栈比堆小
* 碎片相关: 栈产生的碎片远小于堆
* 分配方式: 栈支持静态分配(编译器分配)和动态分配，堆是动态分配
* 效率: 栈的效率比堆高(堆的底层实现可能是双向链表)

## 三 思考
```
public class Test2 {
    public static void main(String[] args) {
        Integer c = 0;
        add(c);
        System.out.println(c);  // 为何是0
    }
    public static void add(Integer num){
        num++;
        System.out.println("add:"+num);
    }
}
```

## 参考链接
1. https://coding.imooc.com/class/303.html

