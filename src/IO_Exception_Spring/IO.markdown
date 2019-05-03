# IO机制
## （一）同步、异步、阻塞与非阻塞
1. 同步: 当有多个任务需要执行时，只有前一个任务执行完毕才能执行下一个，一一执行直到全部结束
2. 异步: 当有多个任务需要执行时，如果其中某个任务A对外部其他任务有依赖性，此时就可对任务A进行挂起，执行任务B，
    当任务A中依赖的外部任务执行完毕后，就可以再次执行任务A
3. 阻塞: 执行任务A时，无法执行其他任务，直到任务A执行完毕
3. 非阻塞: 执行任务A时，如果任务A内部包含阻塞任务，则跳过任务A执行任务B
4. 同步与阻塞，异步与非阻塞的区别: 乍一看同步与阻塞貌似一样，但是同步描述的是对多个任务的执行方式，阻塞描述的是执行当前任务导致的状态，
    异步与非阻塞同理
## （一）BIO(Block-IO)
2. 特点
    * 代码简单，便于理解
    * 在进行输入/输出时会阻塞线程并且是同步执行
## （二）NIO(NonBlock-IO)
1. NIO的组成部分
    * Channels、Buffer、Selector
2. Channels
    * Channel是一个双工管道，从Buffer读取数据或向Buffer写入数据
3. Buffer
    * NIO中所有的数据都要经过Buffer
4. Selector
    * 可以使单个线程处理多个Channel的任务
    * 只要Channel在Selector上注册过，Selector就会对Channel进行监听直到某个Channel有任务需要执行
2. 特点
    * 非阻塞同步
## （三）(Asynchronous)
1. AIO基于事件和回调机制(异步IO，记得nodejs也是这个模型)
2. 特点
    * 异步非阻塞
## 参考
1. https://coding.imooc.com/class/303.html
2. https://blog.csdn.net/ty497122758/article/details/78979302
3. https://blog.csdn.net/anxpp/article/details/51512200
4. https://segmentfault.com/a/1190000014850886
5. http://ifeve.com/overview/





