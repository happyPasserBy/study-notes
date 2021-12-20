

## Transaction基础介绍

### 什么是事务
> 指一组操作，里面包含多个单一逻辑，只要有一个逻辑没有成功，就算失败，所有数据回归到最初状态。
### 事务的基本特性
* 原子性: 一组操作不可拆分，要么全部成功，要么全部失败
* 持久性: 事务执行成功，数据将持久的保存的磁盘上
* 一致性: 事务执行前后，数据的完整性？
* 隔离性: 事务在执行期间不应受到其他事务的影响

## Spring中@Transaction
> Spring的事务支持声明式与编程式，在当前大部分业务中使用声明式较多。在类或方法上加上@Transaction注解即可。@Transaction本质上是使用数据库对事务的支持，Spring使用AOP的方式帮助开发者省去了部分调用事务的代码，Spring提供了平台化的事务接口以便兼容与扩展。
```

```
### Transaction核心接口之一 PlatformTransactionManager
> 便于开发人员对事务管理的扩展，常见实现类有DataSourceTransactionManager、JtaTransactionManager等
```
package org.springframework.transaction;
public interface PlatformTransactionManager {
    TransactionStatus getTransaction(@Nullable TransactionDefinition var1) throws TransactionException;

    void commit(TransactionStatus var1) throws TransactionException;

    void rollback(TransactionStatus var1) throws TransactionException;
}
```
### Transaction核心接口之一 TransactionDefinition
> 事务的属性定义
```
public interface TransactionDefinition {

    // 返回事务的传播属性，默认是PROPAGATION_REQUIRED
    int getPropagationBehavior();
    // 返回事务隔离级别
    int getIsolationLevel();
    // 返回事务超时时间，以秒为单位
    int getTimeout();
    ......
}
```

### Transaction核心接口之一 TransactionStatus
> 当前事务状态
```
public interface TransactionStatus extends SavepointManager, Flushable {
    // 当前方法是否创建了新事务
    boolean isNewTransaction();

    ......
    
    boolean isRollbackOnly();
    // 刷新底层会话中的修改到数据库，一般用于刷新如Hibernate/JPA的会话，是否生效由具体事务资源实现决定；
    void flush();
    // 判断当前事务是否已完成
    boolean isCompleted();
}
```

### Transaction核心类之一 TransactionDefinition
> 具体的事务执行类。Spring管理事务和执行的操作可以简化为三步
* 收集带有@Transaction注解的类生成代理对象
* 将代理对象交给IOC容器
* 调用
```
package org.springframework.transaction.interceptor.TransactionInterceptor;
@Override
public Object invoke(final MethodInvocation invocation) throws Throwable {

	Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);

	return invokeWithinTransaction(invocation.getMethod(), targetClass, new InvocationCallback() {
		@Override
		public Object proceedWithInvocation() throws Throwable {
			return invocation.proceed();
		}
	});
}
```
```
package org.springframework.transaction.interceptor.TransactionAspectSupport;
protected Object invokeWithinTransaction(Method method, Class<?> targetClass, final InvocationCallback invocation)
      throws Throwable {

   final TransactionAttribute txAttr = getTransactionAttributeSource().getTransactionAttribute(method, targetClass);
   final PlatformTransactionManager tm = determineTransactionManager(txAttr);
   final String joinpointIdentification = methodIdentification(method, targetClass);
    
    TransactionInfo txInfo = createTransactionIfNecessary(tm, txAttr, joinpointIdentification);
    Object retVal = null;
    try {
    
        retVal = invocation.proceedWithInvocation();
    }
    catch (Throwable ex) {
        
        completeTransactionAfterThrowing(txInfo, ex);
        throw ex;
    }
    finally {
        cleanupTransactionInfo(txInfo);
    }

    commitTransactionAfterReturning(txInfo);
    return retVal;
}
```

## Transaction失效的场景

* 自身调用
> 事务的操作是有AOP加上去的，自身调用没有经过代理对象
```
Service
public class TransactionDemoService {

    public void m1(){
        this.m2();;
    }

    @Transactional
    public void m2(){
        //db操作
        System.out.println(1/0);
    }
}
```
*  非plublic方法
```
protected TransactionAttribute computeTransactionAttribute(Method method,
    Class<?> targetClass) {
        // Don't allow no-public methods as required.
        if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
        return null;
}
```
* 使用线程池技术中或多线程技术中，需要事务的逻辑必须在统一线程内
```
@Transactional
public void m1() {
    new Thread() {
        //db操作不会回滚
    }.start();
    System.out.println(1/0);
}
```

* 错误的异常
> 想要回滚必须是RuntimeException或者Error，想要自定义异常可以使用rollbackFor属性
* 数据库引擎不支持事务
* 未开启或未配置事务管理器
* 异常被try catch住 