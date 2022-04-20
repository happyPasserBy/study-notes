# 线程池
> 统一管理线程，收集空闲线程，节约创建线程所耗费的资源。
## 1.线程池核心参数
### 1.1 corePoolSize
> 核心线程数
### 1.2 maximumPoolSize
> 最大线程数
### 1.3 keepAliveTime
> 线程存活时间,若当前线程空闲且不属于核心线程则空闲时间到达指定值就会被释放
### 1.4 workQueue
> 任务队列
## 1.4.1 常见工作队列
* SynchronousQueue
> 直接交接型队列
*  LinkedBlockingQueue
> 无界队列
*  ArrayBlockingQueue
> 有界队列
### 1.5 threadFactory
> 线程构建器
### 1.6 Handler
> 任务拒绝策略
* CallerRunsPolicy
> 只要线程池没有关闭，就由提交任务的当前线程处理
* AbortPolicy
> 直接抛出拒绝执行的异常，打断当前执行流程
* DiscardPolicy
> 发生拒绝策略时，不触发任何动作
* 如果线程池未关闭，就弹出队列头部的元素，然后尝试执行
## 2. 创建线程规则
1.	如果线程数小于corePoolSize ,即使其他工作线程处于空闲状态，也会创建一个新线程来运行新任务。
2.	如果线程数等于（或大于）corePoolSize但少于maximumPoolSize ,则将任务放入P人列。
3.	如果队列已满，并且线程个数小于maxPoolSize ,则创建一个新线程来运行任务。
4.	如果队列已满，并且线程数大于或等于maxPoolSize,则拒绝该任务。
## 3. 常见线程池
### 3.1 FixedThreadPool
> 固定线程数量的线程池
### 3.2 SingleThreadExecutor
> 只有一个线程处理任务
### 3.3 CachedThreadPool
> 最大线程数量取决于Integer.MAX_VALUE,可以自动回收空闲线程
### 3.4 ScheduledThreadPool
> 延期执行任务或周期执行任务
## 4. 如何确定线程数量
* CPU密集型
> 线程数为CPU核心数的1-2倍
* IO型
> 大于CPU核心数多倍，线程数 = CPU核心数*(1+平均等待时间、平均工作时间)
## 5. 线程池状态
* RUNNING : 能接受新提交的任务，并且也能处理阻塞队列中的任务
* SHUTDOWN : 不在接收新提交的任务，但可以处理队列中的任务
* STOP : 不在接收新的提交任务，也不处理队列任务
* TIDYING : 所有的任务都已终止
* TERMINATED : terminated()方法执行完成后进入该状态

![](./线程池状态.png)
## 6. 线程池组成部分
### 6.1 线程池管理器
> 协调管理线程池状态、线程的执行、任务队列的存储。
### 6.2 工作线程
> 用于处理外部的任务
### 6.3 任务队列
> 任务过多时，放入任务队列进行存储，有线程空闲时从队列中获取任务执行
### 6.4 任务接口
> 规范任务的类型
## 7. Executor
* Executor
> 顶层接口，只包含了一个执行任务的方法
* ExecutorService
> 继承了Executor并定义了终止、提交任务等方法
* AbstractExecutorService
> ExecutorService的实现，实现了submit，invokeAll，invokeAny 等
* ThreadPoolExecutor
> 线程池的最终实现
## 8. 线程池工作流程
* 如果正在运行的线程数少于corePoolSize（用户定义的核心线程数），线程池就会立刻创建线程并执行该线程任务；
* 如果正在运行的线程数大于corePoolSize，该任务就会被放入阻塞队列中；
* 如果阻塞队列已满且正在运行的线程数少于maximumPoolSize时，线程池会创建非核心线程立刻执行该线程任务；
* 在阻塞队列已满并且正在运行的线程数大于maximunPoolSize时，线程池将会拒绝执行该任务并且抛出拒绝异常RejectExecutionException;
* 在线程执行完毕后，该任务会从线程队列中移除，然后从队列中取下一个任务继续执行；
* 在线程处于空闲的时间超过keepAliveTime，并且正在运行的线程数大于corePoolSize， 该线程将会被认为是空闲线程并停止；所以线程任务都执行完毕后，线程池会收缩到corePoolSize大小。

