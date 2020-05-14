# Spring
## SpringIOC
1. IOC定义: 控制反转(inversion of Control),在使用原始方法编写项目时，流程是 controller层new Service,Service层new Dao，
    层层手动创建，层层依赖。而采用IOC就可以将每层的new操作交给IOC容器去做，只要编写相应的配置并告诉它我需要什么就可以达到之前的功能，
    并且层层解耦，在直白点就是将对象的创建控制权交由之前的自己改为(反转)IOC容器，这就是控制反转
2. DI定义: 依赖注入(Dependency Injection)，将当前所需要的对象自动加载进来
4. SpringIOC核心接口
    * BeanFactory
        * 提供IOC的配置机制
        * 包含Bean的各种定义，便于实例化Bean
        * 建立Bean之间的依赖关系
        * Bean生命周期的控制
    * ApplicationContext(实现多个接口，具备多个功能)
        * BeanFactory能够管理装配Bean
        * ResourcePatternResolver加载资源文件
        * MessageSource实现国际化等功能
        * ApplicationEventPublisher 能够注册监听器，实现监听机制
        
3. Spring启动对Bean的操作
    * Spring启动时加载Bean的配置文件并在Bean的注册表中注册
    * 根据Bean的注册表实例化Bean
    * 将实例化好的Bean放入Bean缓存池
    * 使用Bean
4. Bean的作用域
    * singleton: singleton定义的Bean是单例模式，默认作用域
    * prototype: prototype定义的Bean每次getBean返回的都是Bean的新实例
    * request: request定义的Bean将为每次HTTP请求生成新的Bean实例
    * session: request定义的Bean每次调用都为每个Http Session返回一个Bean实例
    * global session: ???????
5. 依赖注入的方式
    * 构造注入
    * Setter注入
    * Interface
    * Annotation
## SpringAOP
1. 定义: 面向切面编程
    * 项目中出现重复的非业务代码，如日志记录，权限校验，为了避免耦合与重复通常采用两种方式解决，纵向继承，横向切割
        * 纵向继承: 采用继承的方式，将重复代码与非业务代码抽取到父类
        * 横向切割: 采用动态代理的方式拦截并增强，SpringAOP采用Cglib、JDK动态代理两种方式，如果目标类是接口，则默认使用JDK Proxy,否则使用Cglib
2.AOP术语
    * 连接点: Spring仅支持方法作为连接点，比如方法调用前，调用后，抛出异常后等这些点就称为连接点，这些点就是我们要将增强的代码插入的地方
    * 切点: 一个类可能拥有多个方法，而这些方法都是连接点，但我们不想对所有方法进行增强，指向针对特定的方法，这些方法就是切点
    * 增强/通知: 想要增强的代码，比如日志，权限校验等
    * 目标: 织入的目标对象，想要被增强功能的对象
    * 织入:  织入是将增强添加对目标类具体连接点上的过程。AOP像一台织布机，将目标类、增强或引介通过AOP这台织布机天衣无缝地编织到一起
    * 引介/引入: 动态的为某个类添加一些属性和方法
    * 切面: 通知和切入点的结合，有点只可意会不可言传
3. 通知的五种类型
    * 前置通知: 目标方法调用之前
    * 后置通知: 目标方法完成之后
    * 异常通知: 目标方法抛出异常之后调用通知
    * 环绕通知: 目标方法调用之前与调用之后
    * 最终通知: 目标方法执行完毕的通知，不论正常执行完毕还是抛出异常
4. AOP的三种织入方式
    * 编译时织入: 需要特殊的Java编译器，如AspectJ
    * 类加载时织入: 需要特殊的类加载器，如AspectJ、AspectWerkz
    * 运行时织入: 通过动态代理的方式(Spring采用此方式)
## Bean中自动装配的方式
1. no: 不自动进行装配，手动设置Bean的依赖关系
2. byName: 根据Bean的名字进行自动装配
3. byType: 根据Bean的类型自动装配
4. constructor: 应用于构造器，如果构造器参数类型与Bean相同则自动装配
5. autodetect: 如果有默认构造器则通过contructor装配否则使用byType
## Spring事务
#### Spring事务的管理
1. 编程式事务
> 需要开发人员自行配置Spring的事务并插入到业务代码中
2. 声明式事务(AOP)
    * XML方式
    * 注解方式
#### Spring事务的常用API
    * PlatformTransactionManager: 事务平台管理器，Spring用于管理事务的真正对象
        1. DataSourceTransactionManager: 底层用JDBC管理事务
        2. HibernateTransactionManager: 底层使用Hibernate管理事务
    * TransactionDefinition: 事务相关的配置，如合理级别，超时信息，传播行为，知否只读等
    * TransactionStatus: 事务的状态对象
        > PlatformTransactionManager根据TransactionDefinition进行事务的配置，执行事务时产生的状态会记录到TransactionStatus
#### Spring传播行为
> 场景假设: service中有两个方法A、B,B方法调用A
1. 保证多个操作在同一事务中
    * PROPAGATION_REQUIRED(Spring 事务默认值): 如果A中有事务，则使用A中的事务将A、B两个方法的操作包裹到一起执行，
        如果A没有则创建新的事务将A、B的操作包裹起来
    * PROPAGATION_SUPPORT: 如果A中有事务，则使用A中的事务将A、B两个方法的操作包裹到一起执行，如果A中没有事务就不使用事务
    * PROPAGATION_MANDATORY: 如果A中有事务，则使用A中的事务将A、B两个方法的操作包裹到一起执行，如果A中没有事务就抛出异常
2. 保证多个操作不在同一事务中
    * PROPAGATION_REQUIRES_NEW: 如果A中有事务，将A的事务挂起(暂停)，创建新的事务，只包含自身操作(不包含A),如果A中没有事务，创建新事务，包含自身操作
    * PROPAGATION_NOT_SUPPORTED: 如果A中有事务，将A的事务挂起，不使用事务管理
    * PROPAGATION_NEVER: 如果A中有事务则抛异常
3. 嵌套事务
    * PROPAGATION_NESTED: 嵌回套事务，如果A中有事务，执行A的操作，执行完毕后设置一个保存点，创建新的事务只包含B本身的操作，如果执行
        完毕没有异常则结束，如果有异常则可以选择回滚到初始位置或者回滚到保存点
## SpringMVC
![](./springmvc.png)
## 参考
1. https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483942&idx=1&sn=f71e1adeeaea3430dd989ef47cf9a0b3&chksm=ebd74327dca0ca3141c8636e95d41629843d2623d82be799cf72701fb02a665763140b480aec#rd
2. https://segmentfault.com/a/1190000015018888
3. http://yiidian.com/spring/aop-terminology.html
4. https://juejin.im/post/5a3b1dc4f265da43333e9049#comment
5. https://juejin.im/post/5c88ac835188257e3e4800bb