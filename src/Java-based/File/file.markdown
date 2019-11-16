# File处理

## 获取项目中的文件路径
### 1.1 通过类的classLoader获取资源的绝对路径
```
public class Test {
    public static void main(String[] args) throws IOException {
        String path = Test.class.getClassLoader().getResource("test.txt").getPath();
    }
}
```
### 1.2 获取项目的绝对路径
```
public class Test {
    public static void main(String[] args) throws IOException {
        File file = new File("");
        System.out.println(file.getAbsolutePath());
    }
}
```
### 1.3 获取项目的绝对路径
```
public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
    }
}
```
### 1.4 获取当前文件的classes绝对路径
```
public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println(Test.class.getClassLoader().getResource(".").getPath());
    }
}
```


## 1.字节流与字符流
### 1.1 字节流
> 基于字节传输
#### 1.1.1 字节输入流
* InputStream
> 字节输输入流的基类，封装了读取文件的基本操作
* FileInputStream
> InputStream的子类

#### 1.1.2 字节输出流

* OutputStream

* FileOutputStream

### 1.2 字符流
> 基于字符传输

### 1.2.1 字符输出流

### 1.2.2 字符输入流

### 1.3 字节流与字符流的区别

