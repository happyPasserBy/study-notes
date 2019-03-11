# 字符串三剑客:String、StringBuilder、StringBuffer
## 常量池
   * class常量池  
        1. 定义
            * 一个Java类被编译成class文件，class文件内部包含了类的版本、字段、方法、接口等描述信息外，还有一项信息就是常量池constant pool table)，用于存放编译器生成的各种字面量和符号引用
            * 每个class文件都有一个用于描述自身有关常量池的信息
        2. 字面量和符号引用
            * 字面量: 文本字符串、八种基本类型、被final修饰的变量等
            * 符号引用: 类和方法的全限定名、字段的名称和描述符、方法的名称和描述符
   * 字符串常量池
        1. 定义
            * 在HotSpot VM里实现的string pool功能的是一个StringTable类，它是一个Hash表，默认值大小长度是1009；
            这个StringTable在每个HotSpot VM的实例只有一份，被所有的类共享。字符串常量由一个一个字符组成，放在了StringTable上(在JDK7.0中，StringTable的长度可以通过参数 -XX:StringTableSize=66666)
            * String Pool里放的都是字符串常量,由于String#intern()发生了改变，因此String Pool中也可以存放放于堆内的字符串对象的引用
   * 运行时常量池
        1. 定义 
            * 运行时常量池存在于内存中，也就是class常量池被加载到内存之后的版本，不同之处是：它的字面量可以动态的添加(String#intern()),符号引用可以被解析为直接引用。？？？
## String  
   * 定义
        1. Java程序中的所有字符串文本都是此类的实例,String类由final修饰不可继承。String对象一旦创建就无法更改,字符串被创建后保存在堆的字符串常量池中，本地变量保存的是常量池中的引用
   * 适用场景
        2. 适用于对字符串操作较少的场景
        3. 字符串与字符串变量相加底层是先 new StringBuilder，然后调用StringBuilder的append方法进行拼接，最后在调用StringBuilder的toString方法将StringBuilder的值转换为String对象
```java
public class StringTest {
    public static void main(String[] args) {
        String str = "";
        for (int i = 0; i < 1000; i++) {
            str += "hello";
        }
    }
}
```
```
 将StringTest编译好的class文件进行javap -verbose反编译后的部分代码(连猜带蒙 - -!)
 0: ldc           #2                  // String
 2: astore_1
 3: iconst_0
 4: istore_2
 5: iload_2
 6: sipush        1000
 9: if_icmpge     38
12: new           #3                  // class java/lang/StringBuilder
15: dup
16: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
19: aload_1
20: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
23: ldc           #6                  // String hello
25: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
28: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
```
    1.  从第二段代码第12行可以看出 在循环开始后进行字符串相+操作是new 了一个 StringBuilder
    2.  第二段代码第20、25行在调用append方法
    3.  第二段代码第28行调用toString方法
   * 字符串字面量与字符串字面量相加，在编译阶段就会进行优化
```java
public class StringTest2 {
    public static void main(String[] args) {
        String str = "Hello" + "world";
        String str2 = "How";
        String str3 = "are you ?";
        String str4 = str2 + str3;
    }
}
```
```
将StringTest编译好的class文件进行javap -verbose反编译后代码(连猜带蒙 - -!)
Classfile /C:/Users/zhouxuesong/IdeaProjects/20190209_test/src/com/company/testDay05/StringTest2.class
Last modified 2019-2-13; size 519 bytes
        MD5 checksum c08cd6a8d225ed6c8cf23b4631b7138a
        Compiled from "StringTest2.java"
public class com.company.testDay05.StringTest2
        minor version: 0
        major version: 52
        flags: ACC_PUBLIC, ACC_SUPER
        Constant pool:
        #1 = Methodref          #10.#19        // java/lang/Object."<init>":()V
        #2 = String             #20            // Helloworld
        #3 = String             #21            // How
        #4 = String             #22            // are you ?
        #5 = Class              #23            // java/lang/StringBuilder
        #6 = Methodref          #5.#19         // java/lang/StringBuilder."<init>":()V
        #7 = Methodref          #5.#24         // java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        #8 = Methodref          #5.#25         // java/lang/StringBuilder.toString:()Ljava/lang/String;
        #9 = Class              #26            // com/company/testDay05/StringTest2
        #10 = Class              #27            // java/lang/Object
        #11 = Utf8               <init>
  #12 = Utf8               ()V
          #13 = Utf8               Code
          #14 = Utf8               LineNumberTable
          #15 = Utf8               main
          #16 = Utf8               ([Ljava/lang/String;)V
          #17 = Utf8               SourceFile
          #18 = Utf8               StringTest2.java
          #19 = NameAndType        #11:#12        // "<init>":()V
          #20 = Utf8               Helloworld
          #21 = Utf8               How
          #22 = Utf8               are you ?
          #23 = Utf8               java/lang/StringBuilder
          #24 = NameAndType        #28:#29        // append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
          #25 = NameAndType        #30:#31        // toString:()Ljava/lang/String;
          #26 = Utf8               com/company/testDay05/StringTest2
          #27 = Utf8               java/lang/Object
          #28 = Utf8               append
          #29 = Utf8               (Ljava/lang/String;)Ljava/lang/StringBuilder;
          #30 = Utf8               toString
          #31 = Utf8               ()Ljava/lang/String;
          {
public com.company.testDay05.StringTest2();
        descriptor: ()V
        flags: ACC_PUBLIC
        Code:
        stack=1, locals=1, args_size=1
        0: aload_0
        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
        4: return
        LineNumberTable:
        line 3: 0

public static void main(java.lang.String[]);
        descriptor: ([Ljava/lang/String;)V
        flags: ACC_PUBLIC, ACC_STATIC
        Code:
        stack=2, locals=5, args_size=1
        0: ldc           #2                  // String Helloworld
        2: astore_1
        3: ldc           #3                  // String How
        5: astore_2
        6: ldc           #4                  // String are you ?
        8: astore_3
        9: new           #5                  // class java/lang/StringBuilder
        12: dup
        13: invokespecial #6                  // Method java/lang/StringBuilder."<init>":()V
        16: aload_2
        17: invokevirtual #7                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        20: aload_3
        21: invokevirtual #7                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        24: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        27: astore        4
        29: return
        LineNumberTable:
        line 5: 0
        line 6: 3
        line 7: 6
        line 8: 9
        line 9: 29
        }
        SourceFile: "StringTest2.java"
```
    1. 第二段代码从头开始看，看到 Constant pool: 截止，此处就是之前我们说到的字符串常量池，接着往下看从main方法可以看到 // String Helloworld (只看注释 - -!) 说明 "Hello" + "world" 被编译期间就放进了常量池，本地变量保存的只是一个引用
    2. 第二段main方法查看 "How"  "are you ?" 发现它并没有像 "Hello world" 一样在编译期就进行拼接，而是再次调用了 StringBuilder
    
## StringBuffer
   * 定义
        1. 线程安全可变的字符序列。 StringBuffer类由final修饰不可继承。字符串缓冲区就像一个String ，但可以修改。 在任何时间点，它包含一些特定的字符序列，但可以通过某些方法调用来更改序列的长度和内容
        2. 适用于多个线程操作同一个字符串
## StringBuild
   * 定义
        1. StringBuffer类由final修饰不可继承。用法与StringBuffer相同，但StringBuilder是线程不安全的

## 参考
1. https://blog.csdn.net/zm13007310400/article/details/77534349
2. https://www.cnblogs.com/wxgblogs/p/5635099.html