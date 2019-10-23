# 对象的序列化
> 将内存中的对象数据转为可传输可存储的二进制数据
## 1.简单序列化示例
### 1.1 创建待序列化对象
> 待序列化对象必须实现Serializable接口
```
@Data
public class Animal implements Serializable {
    private String name;
    private Integer leg;
    public Animal(){
    }

    public Animal(String name, Integer leg) {
        this.name = name;
        this.leg = leg;
    }
}
```
### 1.2 使用流进行序列化
```
Animal animal = new Animal("猴子",4);
File file = new File(path+"Animal.txt");
if(!file.exists())file.createNewFile();
FileOutputStream fileOutputStream = new FileOutputStream(new File(path+"/Animal.txt"));
ObjectOutputStream stream = new ObjectOutputStream(fileOutputStream);
stream.writeObject(animal);
stream.close();

```
```
// Animal.txt
�� sr com.fxm.Test.Animal��U^΅ L legt Ljava/lang/Integer;L namet Ljava/lang/String;xpsr java.lang.Integer⠤���8 I valuexr java.lang.Number������  xp   t 猴子
```

### 1.3 使用流进行反序列化
```
ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path+"/Animal.txt")));
Animal animal = (Animal) ois.readObject();
System.out.println(animal);
// Animal(name=猴子, leg=4)
```

## 2. serialVersionUID
> 用于标识序列化对象的版本
### 2.1 生成serialVersionUID方式
* 默认方式:private static final long serialVersionUID = 1L;
* 根据包名、类名、属性等值生成64为hash值,如: private static final long  serialVersionUID = xxxL;
### 2.2 serialVersionUID作用
> 假设AB两端传输序列化后的对象
#### 2.2.1 serialVersionUID一致，A端新增字段
> B端接收到反序列化后A端新增字段丢失
#### 2.2.1 serialVersionUID不一致，B端缺少字段
> B端接收到反序列化后B端只接收B端包含字段，缺少字段进行忽略
#### 2.2.1 serialVersionUID不一致，B端新增字段
> B端接收到反序列化后B端新增字段被赋予默认值

## 3. Transient 关键字
> Transient 关键字的作用是控制变量的序列化，在变量声明前加上该关键字，可以阻止该变量被序列化到文件中，在被反序列化后，transient 变量的值被设为默认值

## 参考
1. https://www.cnblogs.com/xdp-gacl/p/3777987.html

