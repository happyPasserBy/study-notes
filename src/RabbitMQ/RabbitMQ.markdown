# 简单了解消息中间件——RabbitMQ
## 1. 什么是RabbitMQ
> RabbitMQ是跨平台跨语言实现了AMQP协议的消息中间件，它主要是让两个业务系统进行解耦，以中间件的形式接收消息或通知订阅者
### 1.1 AMQP
> 提供统一消息服务的应用层标准高级消息队列协议,是应用层协议的一个开放标准,为面向消息的中间件设计
* Server: 又称Broker，接受客户端的连接，实现AMQP实体服务
* Connection: 连接，应用程序与Broker的网络连接
* Channel: 网络信道，Channel是进行读写的通道，客户端可以建立多个Channel，每个Channel代表一个会话任务
* Message: 消息，服务器与应用程序传递的数据，由Properties和Body组成，Properties用来描述消息，Body是具体的消息内容
* Virtual host: .....
* Exchange: 交换机，接收消息根据路由键转发消息到队列
* Binding: Exchange和Queue之间的虚拟连接，Binding中可以包含Routing key,换句话说，Exchange接受到一个消息，根据消息的RoutingKey去匹配自身的BindingKey从而选择Queue
* Routing key: 一个路由规则，虚拟机可以用它来确定如何路由一个消息
* Queue: 消息队列，保存消息并将它们转发给消费者
### 1.2 RabbitMQ的特点
* 面向消息，保证数据不丢失的情况下做到高可靠与高可用
* 与SpringAMQP可以完美的整合
* 集群模式丰富
* 消息投递模式丰富
### 1.3 RabbitMQ的整体架构

![](rabbitmq_1.png)

![](rabbitmq_2.png)

### 1.4 核心——交换机
#### 1.4.1 交换机属性常用
* Name: 交换机属性
* Type: 交换机类型 direct、topic、fanout、headers
* Durability: 是否需要持久化，true为持久化 
* Auto Delete: 当前Exchange上没有任何队列时是否自动删除该Exchange
* Internal: 当前Exchange是否用于RabbitMQ内部使用，默认为false
* Arguments: 扩展参数，用于扩展AMQP协议时使用
#### Direct Exchange
> 发送到DirectExchange的消息，根据消息的RoutingKey发送到与RoutingKey名称相同的Queue中

![](rabbitmq_4.png)
#### Topic Exchange

![](rabbitmq_3.png)
# 注意
* 生产者投递消息时需指定Exchange和RoutingKey，如没有指定Exchange则会走RabbitMQ的默认Exchange,然后用RoutingKey与匹配是否有
同名的Queue,如果有则进行投递没有则投递失败

## 参考
1. https://coding.imooc.com/class/chapter/262.html#Anchor