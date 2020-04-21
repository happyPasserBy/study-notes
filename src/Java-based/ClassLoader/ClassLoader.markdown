# 类加载的过程
## 一 类的加载方式
> 隐式加载 new

> 显式加载 loadClass、forName
* loadClass使用场景: springIOC的延迟加载，可以加快项目的初始化
>loadClass、forName加载的区别
* loadClass不会对类执行链接、初始化的操作，而forName会

## 类的装载过程
> 加载
* 通过classLoader加载字节码生成class对象

> 链接
* 校验: 检查加载的class的正确性和安全性
* 准备: 为类变量分配存储空间并生成初始值，类变量存放在方法区中
* 解析: JVM将常量池内的符号引用转为直接引用

> 初始化 
* 执行类变量赋值和静态代码块
## 一 类的加载过程
1. 首先编写在编写源码后由编译器编译成.class字节码文件
2. 程序运行时由不同的classLoader加载进JVM
## 二 4种classLoader
> BootStrapClassLoader:加载核心java库(java.*)由c++编写

> ExtClassLoader:加载扩展库(javax.*)由java编写

> AppClassLoader:加载程序所在目录(classpath)，由java编写

> 自定义classLoader:加载自定义文件，由java编写
* 继承ClassLoader
* 重写findClass方法，获取class的二进制数据并传递给父类的defineClass方法

 ```
 public class MyClassLoader extends ClassLoader {

    private String path;
    private String classLoaderName;

    public MyClassLoader(String path, String classLoaderName) {
        this.path = path;
        this.classLoaderName = classLoaderName;
    }

    //用于寻找类文件
    @Override
    public Class findClass(String name) {
        byte[] b = loadClassData(name);
        return defineClass(name, b, 0, b.length);
    }

    //用于加载类文件
    private byte[] loadClassData(String name) {
        name = path + name + ".class";
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(new File(name));
            out = new ByteArrayOutputStream();
            int i = 0;
            while ((i = in.read()) != -1) {
                out.write(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
    }
 }
```
## 三 classLoader的双亲委派机制
 1. classLoader的执行顺序
> 类的加载首先由 自定义classLoader 》 AppClassLoader 》 ExtClassLoader 》 BootStrapClassLoader
的加载顺序进行查看，如在某一环节发现已经加载指定类则返回，当检查到BootStrapClassLoader后仍未发现指定类则由BootStrapClassLoader开始向自定义classLoader
自顶而下的顺序开始进行加载，如果某一加载器成功加载到指定类则返回

 ```
    package java.lang.ClassLoader;
    ......
    //synchronized防止多个线程加载同一个类
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException synchronized (getClassLoadingLock(name)) {
            // 查看一个类是否加载过
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    // 查看是否有上层classLoader,有则调用上层loadClass来检查指定类
                    if (parent != null) {
                        c = parent.loadClass(name, false);
                    } else {
                        // parent为null说明是顶层classLoader(BootStrapClassLoader),BootStrapClassLoader由c++编写无parent
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {
                    //由上自下的依次调用findClass查找(BootStrapClassLoader 》......》自定义classLoader)
                    long t1 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
    ......
```
2. 为什么采用双亲委派机制加载类
> 防止一个类被多次加载，节省内存

## 参考链接
1. https://coding.imooc.com/class/303.html
