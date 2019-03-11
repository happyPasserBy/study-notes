# Java异常体系
## 体系划分
1. 异常的顶层父类是Throwable,两个子类分别是Error与Exception

![](./construction.png)

2. Error: 程序无法处理的错误，编译器不做检查，如
    * OutOfMemoryError
    * StackOverflowError
    ```java
       public class Demo1 {
           public static void main(String[] args) {
               test1();
           }
           public static void test1(){
               test1(); // java.lang.StackOverflowError
           }
       }

    ```
3. Exception: 程序可以捕获的错误，处理错误后程序可能恢复正常
    * RuntimeException: 运行时异常，程序不可知
        * 除以0
        ```java
           public class Demo1 {
               public static void main(String[] args) {
                   int num = 10;
                   System.out.println(num/0); // java.lang.ArithmeticException: / by zero
               }
           }
        ```
        * 常见的RunTimeException
            1. NullPointerException 空指针异常
            2. ClassCastException 强制转换异常
            3. IllegalArgumentException 传递非法参数异常
            4. IndexOutOfBoundsException 下标越界异常
            5. NumberFormatException 数字格式异常
    * Check Exception: 编译阶段发现的异常
        * io异常
        ```java
        public class Demo1 {
            public static void main(String[] args) throws FileNotFoundException {
                File file = new File("C:\\user\\demo.txt");
                FileInputStream fis = new FileInputStream(file);
            }
        }
        ```
        * 常见Check Exception
            1. ClassNotFoundException
            2. IOException
## 异常处理
1. try catch
    * try中存放可能会出现异常的代码块
    * catch用于捕获处理try中的异常，当有多个try时，会根据异常的类型进行匹配
    * finally代表肯定会执行的代码块,
```java
public class Demo2 {
    public static void doWork() {
        try {
            int i = 10 / 0;    //会抛出异常
            System.out.println("i=" + i);
        } catch (ArithmeticException e) {
            //捕获 Exception
            System.out.println("ArithmeticException: " + e);
            return 10; // 记录10，用作方法返回，但此刻并不会立即退出方法
        } catch(Exception e){
            System.out.println("Exception: " + e);
            return 20;
        }finally {
            System.out.println("Finally");
            return 30; // 修改catch中记录的10，将其改为30，返回结束方法
        }
    }
    public static void main(String[] args) {
        System.out.println(doWork()); // 30
        System.out.println("Mission Complete");
    }
}
```
## 异常处理原则
1. 具体明确: 抛出的异常应能根据异常名称与信息推断出大致原因
2. 提早抛出: 尽可能早的发现异常并抛出，便于精确定位原因
3. 延迟捕获: 异常的捕获和处理应尽可能延迟，让异常层或者掌握更多信息的作用于来处理
## try catch的性能问题
1. try-catch影响JVM的优化
2. 异常对象实例需要保存栈的快照等信息，开销较大
## 参考
1. https://coding.imooc.com/class/303.html
2. https://www.cnblogs.com/lanxuezaipiao/p/3440471.html
3. https://www.zhihu.com/question/62447192