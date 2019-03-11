# 反射与动态代理
## （一）反射
#### 定义
> 1. 允许程序在运行中动态的加载类文件获取类对象并用其初始化对象
> 2. 想要用动态创建某个类对象必须获取其字节码文件
> 3. 运用动态创建的对象可以获取其自身所有成员变量与方法，但不可获取其父类的方法
> 4. 这栋动态获取类以及调用对象的方法称为反射
#### 获取class的三种方式
> 1. Object类的getClass方法
> 2. 类名.class(Student.calss)
> 3. Class类中的forName
```java
public class Car implements Cloneable ,Engin{
    private String CarName;
    private Car(){}
    public Car(String carName) {
        CarName = carName;
    }

    public String getCarName() {
        return CarName;
    }
    public void setCarName(String carName) {
        CarName = carName;
    }
    public void dirve(String str){
        System.out.println("嘀嘀！出发！" + str);
    }
    public void run (){
        System.out.println("启动");
    }
}
```
```java
public class Demo1 {
    public static void main(String[] args) throws Exception{
        Class clazz = Class.forName("com.company.testDay09.Car");
        Constructor con = clazz.getConstructor(String.class);
        Car car = (Car)con.newInstance("奔驰-G500");
        System.out.println(car.getCarName());
        Field f = clazz.getDeclaredField("CarName");
        f.setAccessible(true); // 如果是私有成员则需要再次设置setAccessible = true
        f.set(car,"宝马-X6");
        System.out.println(car.getCarName()); // 宝马-X6
        Method m = clazz.getMethod("dirve",String.class);
        m.invoke(car,"xxx"); // 嘀嘀！出发！xxx
    }
}
```
##（二）动态代理
#### InvocationHandler + Proxy.newProxyInstance
1. 实现过程
    * 创建代理对象并实现InvocationHandler接口，重写其invoke方法
    * 创建接口并创建其实现类(nvocationHandler + Proxy.newProxyInstance的方式被代理对象必须实现某个接口)
    * 调用Proxy.newProxyInstance传入相关参数
2. 缺点
    * 被代理的类必须实现了接口
    * 无法
```java
public class MyProxy implements InvocationHandler {
    private Object target;
    MyProxy(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("权限校验");
        method.invoke(target,args);
        return null;
    }
}
```
```java
public class Demo2 {
    public static void main(String[] args) {
        Car car = new Car("兰德酷路泽-陆地巡洋舰");
        MyProxy proxy = new MyProxy(car);
        Engin e = (Engin) Proxy.newProxyInstance(Demo2.class.getClassLoader(),car.getClass().getInterfaces(),proxy);
        e.run();
    }
}
```
## （三）Cglib
????????


