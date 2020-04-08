# Mybatis基础支持层
> 用于支撑核心处理层，本层包括数据源模块、事物管理模块、缓存模块、日志模块、解析器模块等等。
## 1. 解析器模块
> Mybatis的各个XML文件都使用DOM并结合XPath进行解析的。
## 2. 反射模块
### 2.1 Reflector 与 ReflectorFactory
> 反射的核心，存储了目标类的所有属性值、getter、setter方法与其相关的参数与返回值，构造函数等信息。
#### 2.1.1 Reflector.addGetMethods 与 Reflector.addUniqueMethods
> addGetMethods主要是存储类的全部getter方法，addUniqueMethods是给每个方法计算唯一签名，防止父类与子类方法冲突。
> addUniqueMethods的签名格式为: 返回值类型#方法名称:参数列表。
#### 2.1.2 ReflectorFactory
> 用于创建指定类的Reflector
### 2.2 TypeParameterResolver
> 用于解析字段类型，方法参数与方法返回值。在Reflector解析类相关的信息时就调用的此工具类。
### 2.3 ObjectFactory
> 根据参数类型为指定类创建对象。
### 2.4 ObjectWrapper
> 对对象的包装，抽象了对象的属性信息，它定义了一系查询对象属性一级更新属性的方法。
### 2.5 MetaObject
> 负责解析指定对象的属性表达式。
## 3. 类型转换
### 3.1 TypeHandler
> 用于将JavaType于JdbcType相互转换。TypeHandler有众多实现类用于转换类型如: IntegerTypeHandler、SqlDateTypeHandler等。
### 3.2 TypeHandlerRegister
> 注册并管理TypeHandler、JavaType、JdbcType这三者的关系。
#### 3.2.1 成员变量
* private final Map<JdbcType, TypeHandler<?>> jdbcTypeHandlerMap = new EnumMap(JdbcType.class);
> 保存了JdbcType与TypeHandler的关系
* private final Map<Type, Map<JdbcType, TypeHandler<?>>> typeHandlerMap = new ConcurrentHashMap();
> 保存了Java转换为JdbcType所需要的TypeHandler，Java对象可转为多个JdbcType
* private final TypeHandler<Object> unknownTypeHandler = new UnknownTypeHandler(this);
> 保存了全部的TypeHandler与之对应的对象
* private final Map<Class<?>, TypeHandler<?>> allTypeHandlersMap = new HashMap();
> 
* private static final Map<JdbcType, TypeHandler<?>> NULL_TYPE_HANDLER_MAP = Collections.emptyMap();
> 
* private Class<? extends TypeHandler> defaultEnumTypeHandler = EnumTypeHandler.class;
> 
### 3.3.2 常用方法一  register(Type javaType, JdbcType jdbcType, TypeHandler<?> handler)
```
private void register(Type javaType, JdbcType jdbcType, TypeHandler<?> handler) {
    if (javaType != null) {
        // 建立javaType、jdbcType、handler三者的关系
        Map<JdbcType, TypeHandler<?>> map = (Map)this.typeHandlerMap.get(javaType);
        if (map == null || map == NULL_TYPE_HANDLER_MAP) {
            map = new HashMap();
            this.typeHandlerMap.put(javaType, map);
        }

        ((Map)map).put(jdbcType, handler);
    }
    // 添加到全部handler集合中
    this.allTypeHandlersMap.put(handler.getClass(), handler);
}
```
### 3.3.2 常用方法二  register(Type javaType, TypeHandler<? extends T> typeHandler)
> 获取typeHandler的MappedJdbcTypes注解后建立关联关系
```
private <T> void register(Type javaType, TypeHandler<? extends T> typeHandler) {
    MappedJdbcTypes mappedJdbcTypes = (MappedJdbcTypes)typeHandler.getClass().getAnnotation(MappedJdbcTypes.class);
    if (mappedJdbcTypes != null) {
        JdbcType[] var4 = mappedJdbcTypes.value();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            JdbcType handledJdbcType = var4[var6];
            // 调用常用方法一
            this.register(javaType, handledJdbcType, typeHandler);
        }

        if (mappedJdbcTypes.includeNullJdbcType()) {
            // 调用常用方法一
            this.register((Type)javaType, (JdbcType)null, (TypeHandler)typeHandler);
        }
    } else {
        // 调用常用方法一
        this.register((Type)javaType, (JdbcType)null, (TypeHandler)typeHandler);
    }

}
```
### 3.3.3 常用方法三  register(Class<?> typeHandlerClass)
> 获取MappedTypes注解后再建立关联关系
```
public void register(Class<?> typeHandlerClass) {
    boolean mappedTypeFound = false;
    MappedTypes mappedTypes = (MappedTypes)typeHandlerClass.getAnnotation(MappedTypes.class);
    if (mappedTypes != null) {
        Class[] var4 = mappedTypes.value();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Class<?> javaTypeClass = var4[var6];
            // 常用方法二
            this.register(javaTypeClass, typeHandlerClass);
            mappedTypeFound = true;
        }
    }

    if (!mappedTypeFound) {
        this.register(this.getInstance((Class)null, typeHandlerClass));
    }

}
```
### 3.3.4 常用方法四  register(JdbcType jdbcType, TypeHandler<?> handler)
> 向jdbcTypeHandlerMap注册
```
public void register(JdbcType jdbcType, TypeHandler<?> handler) {
    this.jdbcTypeHandlerMap.put(jdbcType, handler);
}
```
### 3.3.5 常用方法五  register(String packageName)
> 解析指定包下的文件
```
public void register(String packageName) {
    ResolverUtil<Class<?>> resolverUtil = new ResolverUtil();
    resolverUtil.find(new IsA(TypeHandler.class), packageName);
    Set<Class<? extends Class<?>>> handlerSet = resolverUtil.getClasses();
    Iterator var4 = handlerSet.iterator();

    while(var4.hasNext()) {
        Class<?> type = (Class)var4.next();
        if (!type.isAnonymousClass() && !type.isInterface() && !Modifier.isAbstract(type.getModifiers())) {
            this.register(type);
        }
    }

}
```
### 3.3.6 常用方法六 getTypeHandler(Type type, JdbcType jdbcType)
> 根据JavaType与JdbcType获取handler
```
private <T> TypeHandler<T> getTypeHandler(Type type, JdbcType jdbcType) {
    if (ParamMap.class.equals(type)) {
        return null;
    } else {
        Map<JdbcType, TypeHandler<?>> jdbcHandlerMap = this.getJdbcHandlerMap(type);
        TypeHandler<?> handler = null;
        if (jdbcHandlerMap != null) {
            handler = (TypeHandler)jdbcHandlerMap.get(jdbcType);
            if (handler == null) {
                handler = (TypeHandler)jdbcHandlerMap.get((Object)null);
            }

            if (handler == null) {
                handler = this.pickSoleHandler(jdbcHandlerMap);
            }
        }

        return handler;
    }
}
```
### 3.3 TypeAliasRegister
> 注册并管理Java类的别名，在TypeAliasRegister的构造方法中会初始化Java常用类的别名,TypeAliasRegister维护了一个HashMap，key为别名，value为对应的class。

## 4. 日志模块
## 5. 资源加载
### 5.1 ClassLoaderWrapper
> 用于选取正确的ClassLoader。
## 6. DataSource
### 6.1 DataSourceFactory
> DataSource工厂接口。
### 6.3 UnPooledDataSource
> 不含数据库连接池功能的DataSource。
### 6.4 PooledDataSource
> 不含数据库连接池功能的DataSource。
### 6.5 PooledConnection
> 数据库连接池对象。
### 6.6 PoolState
> 用于管理PooledConnection状态的对象，如空闲状态与活跃状态。
## 7. Transaction
### 7.1 JdbcTransaction
> 使用JDBC的事务管理机制,就是利用java.sql.Connection对象完成对事务的提交。
### 7.2 ManagedTransaction
> 这种机制Mybatis自身不会去实现事务管理，而是让程序的WEB容器（JBOSS,WebLogic,Tomcat）来实现对事务的管理。
## 8. Bingding模块
### 8.1 MapperRegister & MapperProxyFactory
> MapperRegister 是 Mapper的注册中心，MapperProxyFactory用于创建Mapper的代理对象。
### 8.2 MapperProxy
> Mapper的代理对象，存储了关联的SqlSession、接口的Class、与每个接口方法对应的MapperMethod。
### 8.3 MapperMethod
> 存储了Sql与Method相关的信息。
## 9. 缓存
### 9.1  PerpetualCache
> Cache接口的唯一实现，由于Cahce使用了装饰器模式，PerpetualCache提供基本的功能，使用HashMap作为容器。
### 9.2 Cache的装饰器
#### 9.2.1 BlockingCache
> 保证只有一个线程到缓存中查找指定key的缓存
#### 9.2.2 FifoCache & LruCache
> FifoCache按照先进先出的规则清理缓存，LruCache则按照最少使用的规则清理。
#### 9.2.3 SoftCache & WeakCache
#### 9.2.4 ScheduledCache & LoggingCache & SynchronizedCache & CacheSerializedCache
* ScheduledCache
> 定时删除缓存
* LoggingCache
> 提供缓存日志的功能，可输出命中的次数与访问次数
* Synchronized
> 并发控制
* CacheSerializedCache
> 存储缓存是对数据进行序列化与反序列化
### 9.3 CacheKey
> 缓存的key, CacheKey包括了MapperedStatment的id、范围查询偏移量、hashcode等信息

## 参考
1. Mybatis技术内幕


