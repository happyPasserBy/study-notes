# 注解
> 与注释差不多，但比注释功能强大，不仅用于描述

## 1. 基本使用
### 1.1 创建注解
```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnn {
    /**
     * 自定义值
     */
    String value();
    /**
     * 自定义值
     */
    String name();
}
```
* 上面创建了一个名为MyAnn注解，创建注解与创建类相似，唯一不同的是将 class 关键字替换为 @interface
* @Target 属于元注解标识该注解MyAnn应用的范围，详细的稍后介绍
* @Retention 属于元注解标识该注解MyAnn的生命周期，详细的稍后介绍

### 1.2使用注解
```
@MyAnn(value="值",name="MyAnn")
public class MyTest {

    public static void main(String[] args) {
        MyTest myTest = new MyTest();
        Annotation[] types = myTest.getClass().getAnnotations();
        for (int i = 0; i < types.length; i++) {
            System.out.println(types[i].toString()); 
            //  MyAnn(value=值, name=MyAnn)
        }
    }
}

```
* 在main函数中创建了MyTest的实体，调用getClass().getAnnotations()获取到注解信息(获取注解的方式之一)


## 2. 元注解
### 2.1 @Target
> 用于描述该注解可应用的地方
#### 2.1.1 @Target的属性值 ElementType
```
public enum ElementType {
    /**标明该注解可以用于类、接口（包括注解类型）或enum声明*/
    TYPE,

    /** 标明该注解可以用于字段(域)声明，包括enum实例 */
    FIELD,

    /** 标明该注解可以用于方法声明 */
    METHOD,

    /** 标明该注解可以用于参数声明 */
    PARAMETER,

    /** 标明注解可以用于构造函数声明 */
    CONSTRUCTOR,

    /** 标明注解可以用于局部变量声明 */
    LOCAL_VARIABLE,

    /** 标明注解可以用于注解声明(应用于另一个注解上)*/
    ANNOTATION_TYPE,

    /** 标明注解可以用于包声明 */
    PACKAGE,

    /**
     * 标明注解可以用于类型参数声明（1.8新加入）
     * @since 1.8
     */
    TYPE_PARAMETER,

    /**
     * 类型使用声明（1.8新加入)
     * @since 1.8
     */
    TYPE_USE
}

```

### 2.2 @Retention
> 用于描述该注解的生命周期
#### 2.2.1 @Retention的属性值

```
public enum RetentionPolicy {
    /**
     * 源码级别,编译后不会存在class文件中
     */
    SOURCE,

    /**
     * class级别,在class字节码可见但运行不可见
     */
    CLASS,

    /**
     *
     * 运行级别,运行时可见
     *
     */
    RUNTIME
}
```

### 2.3 @Documented
> 被该注解标识将会生成到javadoc
### 2.4 @Inherited 
> 用于标识注解是否被继承，此处的继承是指被注解描述的类的子类可以通过 getClass().getAnnotations() 获取到父类的注解信息
```
@MyAnn(value="father",name="father")
public class Father {
}

```
```
public class MyTest extends Father{
    public static void main(String[] args) {
        MyTest myTest = new MyTest();
        Annotation[] types = myTest.getClass().getAnnotations();
        for (int i = 0; i < types.length; i++) {
            System.out.println(types[i].toString()); // 此处获取到了父类的注解值 MyAnn(value=father, name=father)
        }
    }
}
```

## 3.常见的内置注解
* @Override
> 标识覆盖了父级的方法

* @Deprecated
> 标识已过期的方法

* @SuppressWarnnings
> 标识有选择的关闭编译器对类、方法、成员变量、变量初始化的警告

* @FunctionalInterface
> 标识一个函数式接口

## 4. 注解常用的使用方式
> 通过反射对类方法等进行判断，如果包含某注解则执行相应的业务逻辑

## 参考
1. https://blog.csdn.net/briblue/article/details/73824058
2. https://blog.csdn.net/javazejian/article/details/71860633