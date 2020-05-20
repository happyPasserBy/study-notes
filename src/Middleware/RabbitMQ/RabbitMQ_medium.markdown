# RabbitMQ进阶
> 讲述RabbitMQ如何保证消息可靠性
## 可靠性
### 1.1 可靠性保障——Confirm
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
### 1.2 可靠性保障——Return
> 生产端添加Return Listener用于监听发送的消息匹配不到Exchange或RoutingKey的情况

### 1.3 可靠性保障——Transaction 
> 以下就是开启事物与RabbitMQ的通信流程
```
客户端发送给服务器Tx.Select(开启事务模式)
服务器端返回Tx.Select-Ok(开启事务模式ok)
推送消息
客户端发送给事务提交Tx.Commit
服务器端返回Tx.Commit-Ok
```
#### 注意
* 生产者投递消息时需指定Exchange和RoutingKey，如没有指定Exchange则会走RabbitMQ的默认Exchange,然后用RoutingKey与匹配是否有
同名的Queue,如果有则进行投递没有则投递失败
## 2.如何保证消息100%投递成功
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
* 使用redis实现互斥锁来防止重复消费，set ""  nx 
##### 2.4.1.3 数据库乐观锁
* 创建version实现乐观锁


## 参考
1. https://www.cnblogs.com/javalyy/p/8882144.html
2. https://segmentfault.com/a/1190000020035137#articleHeader6