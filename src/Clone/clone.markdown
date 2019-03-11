# 对象的克隆
## Java值与址
1. Java对于基本数据类型如int保存的是值，对于引用类型保存的是内存的地址
```java
public class Test{
    public static void main(String[] args){
      int num = 10; // 在内存中，num保存的就是10
      Object obj = new Object(); // obj中保存的是new Object()这个对象的内存地址
    }
}
```
## 基本数据类型的拷贝
1.基本数据类型的拷贝就是讲值复制然后传递给另一个变量
## 引用数据类型的拷贝
1. 无法像基本数据类型一样进行赋值操作，引用数据类型传递的是内存地址
```java
public class Student{
    private String name;
    private Integer age;
    public Student(){}
    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
}
```
```java
public class Test1 {
    public static void main(String[] args) {
       Student stu1 = new Student();
       stu1.setName("张三");
       stu1.setAge(24);
       Student stu2 = stu1;
       System.out.println(stu2 == stu1); // true
       stu1.setName("张三2");
       System.out.println(stu2.getName); // 张三2
       
    }
}
```    
1. 实现浅克隆
* 克隆类继承Cloneable
* 克隆类重写clone方法并在内部调用Object.clone
```java
public class Student implements Cloneable{
    private String name;
    private Integer age;
    public Car car;
    private Student(){}
    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    @Override
    public Object clone(){
        Student stu = null;
        try {
            stu = (Student)super.clone();
        }catch(Exception e){
            e.printStackTrace();
        }
        return stu;
    }
}
```
```java
public class Demo3 {
    public static void main(String[] args) {
        Student2 stu1 = new Student2("张三",24);
        Car car = new Car("奔驰-G500");
        stu1.setCar(car);
        Student2 stu2 = (Student2)stu1.clone();
        System.out.println(stu1 == stu2); // false
        System.out.println(stu1.getCar() == stu2.getCar()); // true
    }
}
```
* 问题: 无法实现深克隆，也就是克隆类的引用类型的成员变量并没有被克隆，stu1与stu2的Car保存的仍是同一个内存地址，解决这个问题使用io流来序列化
2. 深克隆
* 克隆类实现Serializable接口
* 克隆类创建clone方法，内部使用io流来序列化自身
```java
public class Person implements Serializable {
    private String id;
    private Phone phone;
    private Person(){}
    public Person(String id){
        this.id = id;
    }
    public Phone getPhone() {
        return phone;
    }
    public void setPhone(Phone phone) {
        this.phone = phone;
    }
    public String getId(){
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Object clone(){
        Person p = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            p = (Person)ois.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
        return p;
    }
}
```
```java
public class Demo2 {
    public static void main(String[] args) {
        Person p = new Person("123");
        p.setPhone(new Phone("Apple-8Plus"));
        Person p2 = (Person)p.clone();
        System.out.println(p == p2); // false
        System.out.println(p.getPhone() == p2.getPhone()); // false
    }
}
```

## 参考
1. https://www.cnblogs.com/Qian123/p/5710533.html
1. https://www.zhihu.com/question/35240637