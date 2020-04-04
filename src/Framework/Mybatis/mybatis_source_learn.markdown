# Mybatis源码学习
## 1. 关键对象
* Configuration: MyBatis所有的配置信息都维持在Configuration对象之中，核心类
* SqlSessionFactory: 创建SqlSession
* SqlSession: 作为MyBatis工作的主要顶层API，表示和数据库交互的会话，完成必要数据库增删改查功能
* Executor: MyBatis执行器，是MyBatis 调度的核心，负责SQL语句的生成和查询缓存的维护；
* StatementHandler: 封装了JDBC Statement操作，负责对JDBC statement 的操作，如设置参数、将Statement结果集转换成List集合
* ParameterHandler: 负责对用户传递的参数转换成JDBC Statement 所需要的参数
* ResultSetHandler: 负责将JDBC返回的ResultSet结果集对象转换成List类型的集合
* MappedStatement: MappedStatement维护了一条<select|update|delete|insert>节点的封装
* MapperProxy和MapperProxyFactory: Mapper代理，使用原生的Proxy执行mapper里的方法
* MetaClass: 用来解析目标类的一些元信息，比如类的成员变量，getter/setter 方法等
* Reflector: 通过反射获取目标类的 getter 方法及其返回值类型，setter 方法及其参数值类型等元信息
* XMLMapperBuilder: Mapper的XML解析对象
## 2. 设计模式
## 2.1 代理模式

## 2.2 建造者模式
* SqlSessionFactory
* DefaultReflectorFactory
> 创建Reflector
* MappedStatement.Builder
> 创建MappedStatement
## 2.3 装饰器模式
* 
## 3. 解析Mybatis-config.xml

1. 创建SqlSessionFactory
    * 创建Configuration 
    * 解析标签如 properties、settings等
    * 解析 settings
        1. 解析子节点
        2. 创建 Configuration 类的“元信息”对象
        3. 将setting配置到Configuration中
    * 解析 typeAlias
        1. 解析子节点
        2. 注册创建别名如: JDBC(事物工厂别名)、LRU(缓存别名)、string(常用类别名)....
    * 解析 typeHandlers
        1. 解析子节点
        2. 创建两个Map,一个用来建立jdbcType与TypeHandler的映射，一个用来建立javaType到前一个Map的映射 
    * 解析其它节点相似后续在做补充
    * 解析 mappers
        1. 解析子节点加载映射文件
        2. 解析 mapper 节点
        3. 通过命名空间绑定 Mapper 接口
        4. 解析特殊节点如:cache、resultMap等
        5. 解析 cache
            * 读取属性配置cache
            * 根据属性配置不同的缓存装饰器如: ScheduledCache(定时删除)、LoggingCache(日志打印)、SynchronizedCache(并发控制)等
            * 设置cache到Configuration
        6. 解析 cache-ref
        7. 解析 resultMap
        8. 解析 sql 节点
            * 匹配databaseId 属性判断是否需要缓存
        9. 解析 select、update、insert、delete等节点
            * 解析节点属性与子节点
            * 创建 MappedStatement 对象
        10. mapper生成代理类，绑定命名空间后添加到Configuration中
2. Sql执行过程
    * 查询语句执行过程
        1. 内部调用selectList
        2. 获取 MappedStatement 
        3. executor.query 执行
        4. 创建BoundSql
        5. 判断是否命中二级缓存
        6. 判断是否命中一级缓存
        7. 创建PreparedStatement执行sql 
    * BoundSql 的创建
        1. 创建 DynamicContext( Sql 语句构建的上下文)
        2. 解析 Sql 片段，并将解析结果存储到 DynamicContext 中
        3. 解析 Sql 语句，并构建 StaticSqlSource
        4. 调用 StaticSqlSource 的 getBoundSql 获取 BoundSql
        5. DynamicContext 的 ContextMap 中的内容拷贝到 BoundSql 中
        6. 解析 #{} 
    * 结果集的映射
## 参考
1. https://www.cnblogs.com/nullllun/p/9352792.html
2. https://github.com/SeasonPanPan/minimybatis