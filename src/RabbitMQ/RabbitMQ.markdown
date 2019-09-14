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
### 1.4 安装RabbitMQ
#### 1.4.1 服务器安装
```
docker pull rabbitmq
docker run -d --hostname my-rabbit --name rabbit -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 15672:15672 -p 5672:5672 rabbitmq:management
docker ps
lsof -i:15672

```
#### 1.4.2 客户端安装
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
```

spring:
  rabbitmq:
    password: admin
    username: admin
    addresses: 104.168.234.140
    port: 5672
```
### 1.4 核心——交换机
#### 1.4.1 交换机属性常用
* Name: 交换机属性
* Type: 交换机类型 direct、topic、fanout、headers
* Durability: 是否需要持久化，true为持久化 
* Auto Delete: 当前Exchange上没有任何队列时是否自动删除该Exchange
* Internal: 当前Exchange是否用于RabbitMQ内部使用，默认为false
* Arguments: 扩展参数，用于扩展AMQP协议时使用
#### 1.4.2 Direct Exchange
> 发送到DirectExchange的消息，根据消息的RoutingKey与BindingKey进行匹配然后传递到Queue中

![](rabbitmq_4.png)

```
// Producer
//1 创建ConnectionFactory
ConnectionFactory connectionFactory = new ConnectionFactory();
connectionFactory.setHost("192.168.11.76");
connectionFactory.setPort(5672);
connectionFactory.setVirtualHost("/");
//2 创建Connection
Connection connection = connectionFactory.newConnection();
//3 创建Channel
Channel channel = connection.createChannel();  
//4 声明
String exchangeName = "test_direct_exchange";
String routingKey = "test.direct";
//5 发送
String msg = "Hello World RabbitMQ 4  Direct Exchange Message 111 ... ";
channel.basicPublish(exchangeName, routingKey , null , msg.getBytes()); 	
```
```
// Consumer
ConnectionFactory connectionFactory = new ConnectionFactory() ;  
connectionFactory.setHost("192.168.11.76");
connectionFactory.setPort(5672);
connectionFactory.setVirtualHost("/");

connectionFactory.setAutomaticRecoveryEnabled(true);
connectionFactory.setNetworkRecoveryInterval(3000);
Connection connection = connectionFactory.newConnection();

Channel channel = connection.createChannel();  
//4 声明
String exchangeName = "test_direct_exchange";
String exchangeType = "direct";
String queueName = "test_direct_queue";
String routingKey = "test.direct";
//表示声明了一个交换机
channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
//表示声明了一个队列
channel.queueDeclare(queueName, false, false, false, null);
//建立一个绑定关系:
channel.queueBind(queueName, exchangeName, routingKey);

//durable 是否持久化消息
QueueingConsumer consumer = new QueueingConsumer(channel);
//参数：队列名称、是否自动ACK、Consumer
channel.basicConsume(queueName, true, consumer);  
//循环获取消息  
while(true){  
    //获取消息，如果没有消息，这一步将会一直阻塞  
    Delivery delivery = consumer.nextDelivery();  
    String msg = new String(delivery.getBody());    
    System.out.println("收到消息：" + msg);  
} 
```
#### 1.4.3 Topic Exchange
> 与Direct Exchange相似，但Binding Key可以使用匹配符号

![](rabbitmq_3.png)
##### 1.4.3.1 匹配符号
* "#": 匹配一个或多个词，如"log.#"匹配 “log.info” "log.info.a"
* "*": 匹配一个词，如"log.a"
```
//Producer
//1 创建ConnectionFactory
ConnectionFactory connectionFactory = new ConnectionFactory();
connectionFactory.setHost("http://192.168.1.9");
connectionFactory.setPort(5672);
connectionFactory.setVirtualHost("/");
//2 创建Connection
Connection connection = connectionFactory.newConnection();
//3 创建Channel
Channel channel = connection.createChannel();
//4 声明
String exchangeName = "test_topic_exchange";
String routingKey1 = "user.save";
String routingKey2 = "user.update";
String routingKey3 = "user.delete.abc";
//5 发送
String msg = "Hello World RabbitMQ 4 Topic Exchange Message ...";
channel.basicPublish(exchangeName, routingKey1 , null , msg.getBytes());
channel.basicPublish(exchangeName, routingKey2 , null , msg.getBytes());
channel.basicPublish(exchangeName, routingKey3 , null , msg.getBytes());
channel.close();
connection.close();
```
```
//Consumer
ConnectionFactory connectionFactory = new ConnectionFactory() ;  
connectionFactory.setHost("http://192.168.1.9");
connectionFactory.setPort(5672);
connectionFactory.setVirtualHost("/");
connectionFactory.setAutomaticRecoveryEnabled(true);
connectionFactory.setNetworkRecoveryInterval(3000);
Connection connection = connectionFactory.newConnection();
Channel channel = connection.createChannel();  
//4 声明
String exchangeName = "test_topic_exchange";
String exchangeType = "topic";
String queueName = "test_topic_queue";
//String routingKey = "user.*";
String routingKey = "user.*";
// 1 声明交换机 
channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
// 2 声明队列
channel.queueDeclare(queueName, false, false, false, null);
// 3 建立交换机和队列的绑定关系:
channel.queueBind(queueName, exchangeName, routingKey);
//durable 是否持久化消息
QueueingConsumer consumer = new QueueingConsumer(channel);
//参数：队列名称、是否自动ACK、Consumer
channel.basicConsume(queueName, true, consumer);  
//循环获取消息  
while(true){  
    //获取消息，如果没有消息，这一步将会一直阻塞  
    Delivery delivery = consumer.nextDelivery();  
    String msg = new String(delivery.getBody());    
    System.out.println("收到消息：" + msg);  
} 
```
### 1.4.4 Fanout Exchange
> 此模式的交换机不需要路由键，队列与交换机存在绑定关系，交换机接收到消息后会被转发到与该交换机绑定的所有队列上
![](rabbitmq_5.png)
```
// Producer
//1 创建ConnectionFactory
ConnectionFactory connectionFactory = new ConnectionFactory();
connectionFactory.setHost("192.168.11.76");
connectionFactory.setPort(5672);
connectionFactory.setVirtualHost("/");
//2 创建Connection
Connection connection = connectionFactory.newConnection();
//3 创建Channel
Channel channel = connection.createChannel();  
//4 声明
String exchangeName = "test_fanout_exchange";
//5 发送
for(int i = 0; i < 10; i ++) {
    String msg = "Hello World RabbitMQ 4 FANOUT Exchange Message ...";
    channel.basicPublish(exchangeName, "", null , msg.getBytes()); 			
}
channel.close();  
connection.close();  
```
```
// Consumer
ConnectionFactory connectionFactory = new ConnectionFactory() ;  
connectionFactory.setHost("192.168.11.76");
connectionFactory.setPort(5672);
connectionFactory.setVirtualHost("/");
connectionFactory.setAutomaticRecoveryEnabled(true);
connectionFactory.setNetworkRecoveryInterval(3000);
Connection connection = connectionFactory.newConnection();
Channel channel = connection.createChannel();  
String exchangeName = "test_fanout_exchange";
String exchangeType = "fanout";
String queueName = "test_fanout_queue";
String routingKey = "";	//不设置路由键
channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
channel.queueDeclare(queueName, false, false, false, null);
channel.queueBind(queueName, exchangeName, routingKey);
//durable 是否持久化消息
QueueingConsumer consumer = new QueueingConsumer(channel);
//参数：队列名称、是否自动ACK、Consumer
channel.basicConsume(queueName, true, consumer); 
//循环获取消息  
while(true){  
    //获取消息，如果没有消息，这一步将会一直阻塞  
    Delivery delivery = consumer.nextDelivery();  
    String msg = new String(delivery.getBody());    
    System.out.println("收到消息：" + msg);  
} 
```
#### 1.4.5 Headers Exchange
......
### 1.5 可靠性保障——Confirm
> 生产者发送消息到MQ后，MQ会对这条消息进行应答
```
// Producer
//1 创建ConnectionFactory
ConnectionFactory connectionFactory = new ConnectionFactory();
connectionFactory.setHost("192.168.11.76");
connectionFactory.setPort(5672);
connectionFactory.setVirtualHost("/");
//2 获取C	onnection
Connection connection = connectionFactory.newConnection();
//3 通过Connection创建一个新的Channel
Channel channel = connection.createChannel();
//4 指定我们的消息投递模式: 消息的确认模式 
channel.confirmSelect();
String exchangeName = "test_confirm_exchange";
String routingKey = "confirm.save";
//5 发送一条消息
String msg = "Hello RabbitMQ Send confirm message!";
channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
//6 添加一个确认监听
channel.addConfirmListener(new ConfirmListener() {
    @Override
    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        System.err.println("-------no ack!-----------");
    }
    @Override
    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
        System.err.println("-------ack!-----------");
    }
});
```
```
// Customer
//1 创建ConnectionFactory
ConnectionFactory connectionFactory = new ConnectionFactory();
connectionFactory.setHost("192.168.11.76");
connectionFactory.setPort(5672);
connectionFactory.setVirtualHost("/");

//2 获取C	onnection
Connection connection = connectionFactory.newConnection();

//3 通过Connection创建一个新的Channel
Channel channel = connection.createChannel();

String exchangeName = "test_confirm_exchange";
String routingKey = "confirm.#";
String queueName = "test_confirm_queue";

//4 声明交换机和队列 然后进行绑定设置, 最后制定路由Key
channel.exchangeDeclare(exchangeName, "topic", true);
channel.queueDeclare(queueName, true, false, false, null);
channel.queueBind(queueName, exchangeName, routingKey);

//5 创建消费者 
QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
channel.basicConsume(queueName, true, queueingConsumer);

while(true){
    Delivery delivery = queueingConsumer.nextDelivery();
    String msg = new String(delivery.getBody());
    
    System.err.println("消费端: " + msg);
}
```
### 1.6 可靠性保障——Return
> 生产端添加Return Listener用于监听发送的消息匹配不到Exchange或RoutingKey的情况
#### 注意
* 生产者投递消息时需指定Exchange和RoutingKey，如没有指定Exchange则会走RabbitMQ的默认Exchange,然后用RoutingKey与匹配是否有
同名的Queue,如果有则进行投递没有则投递失败
## 2。如何保证消息100%投递成功
### 2.1 什么是消息的可靠性？
* 消息的成功发出
* MQ节点成功接收
* 发送端接收到MQ节点的确认应答
* 完善的消息补偿机制
### 2.2 可靠性投递——方案（一）
> 将消息存储到数据库中，并为该消息添加状态字段(已发送、已确认、已消费.....)，启动定时任务轮训数据库将发送失败的消息再次发送
![](rabbitmq_6.png)
1. 业务数据与消息数据入库
2. 发送消息到MQ
3. 接收MQ对消息的响应
4. 将数据库中消息对应的状态改为已确认...
5. 启动定时任务轮训数据库，收集没有确认的消息或投递间隔大于某个时间点的消息
6. 将收集好的消息进行重发并记录重发次数
7. 对于超过固定重发次数的消息进行业务处理
### 2.3 可靠性投递——方案（二）
> 消息的延迟投递，做二次确认，回调检查

![](rabbitmq_7.png)
1. upstream service发送业务消息
2. upstream service发送用于检测业务消息可靠性的消息(有点绕)
3. downstream service接收业务消息并处理
4. downstream service处理完业务消息后发送业务确认消息
5. callback service接收业务确认消息并入库
6. callback service接收业务检测消息(由upstream service发起的)检查数据库中的确认状态，如已被消费则返回成功，否则发送RPC请求通知upstream service业务消息投递失败
### 2.4 幂等性保障
> 相同条件下，一次或多次对数据源进行操作，最后的结果与第一次的结果是一致的
#### 2.4.1 消息重读消费的问题
##### 2.4.1.1 唯一ID
> 以银行转账为例，防止转账时快速重复点击转账功能导致发送多个消息被重复消费
* 用户进入转账页面时服务端创建唯一id存储到缓存中并分配给客户端
* 客户端转账时携带id
* 服务端接收到重复的id进行去重
##### 2.4.1.2 Redis
* 使用redis实现互斥锁来防止重复消费
## 3. RabbitMQ的一些高级特性
### 3.1消费端限流
> RabbitMQ提供了一种qos功能，即在非自动确认消息的前提下，如果一定数据的消息未被确认前，不消费新的消息
#### 3.1.1 void BasicQos(unit perfetchSize,ushort prefetchCount,bool global)
* unit perfetchSize: 限制消息的大小(MB)
* ushort prefetchCount: 一次能处理多少条消息
* global: true限制于channel,false限制于consumer
### 3.2 TTL
> 消息的存活时间
### 3.3 死信队列(DLX)
> 当一个消息在一个队列中变成死信后，它能被重新publish到另一个Exchange并路由到对应的Queue中
#### 3.3.1 消息变成死信的情况
* 消息被拒绝(basic.reject/basic.nack)并且requeue=false
* 消息TTL过期
* 队列达到最大长度

## 参考
1. https://www.rabbitmq.com/getstarted.html
2. https://coding.imooc.com/class/chapter/262.html#Anchor
3. https://www.cnblogs.com/javalyy/p/8882144.html
4. https://segmentfault.com/a/1190000020035137#articleHeader6