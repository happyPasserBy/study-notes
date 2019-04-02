# Redis初识
## 什么是Redis?
> Redis是一个开源(BSD许可)的内存数据结构存储，用作数据库、缓存和消息代理。它支持诸如字符串、散列、列表、集、带范围查询的排序集、位图、hyperloglog、带半径查询和流的地理空间索引等数据结构。Redis具有内置的复制、Lua脚本、LRU清除、事务和不同级别的磁盘持久性，
并通过Redis Sentinel和Redis集群的自动分区提供高可用性。以上来自于官方介绍，用自己的话说Redis的关键点是不同于Mysql的磁盘存储，Redis是基于内存,读取数据的速度远远大于Mysql
## Redis的特点
* 基于内存存储
* 数据类型丰富
* 支持磁盘持久化
* 支持主从同步
* 支持分片????
* 采用单线程处理任务(此处描述不准确，待补充)
* 使用非阻塞IO模型
## Redis的数据类型
#### String
> 字符串，最基本的数据类型，底层采用SDS来存储
* 演示
```
127.0.0.1:6379> set test "2008"
OK
127.0.0.1:6379> get test
"2008"
```
#### Hash
> String作为key组成的hash表
* 演示
```
127.0.0.1:6379> hset test name "xiaoming"
(integer) 1
127.0.0.1:6379> hget test name
"xiaoming"
127.0.0.1:6379> hmset test name "xiaohong" age 25
OK
127.0.0.1:6379> hmget test name age
1) "xiaohong"
2) "25"
127.0.0.1:6379>
```
#### List
> 列表，按照String元素插入的顺序排序
* 演示
```
127.0.0.1:6379> lpush test a
(integer) 1
127.0.0.1:6379> lpush test b
(integer) 2
127.0.0.1:6379> lrange test 0 5
1) "b"
2) "a"
127.0.0.1:6379> llen test
(integer) 2
127.0.0.1:6379> lindex test 0
"b"
```
#### Set
> String元素组成的无序集合，不允许重复
* 演示
```
127.0.0.1:6379> sadd test a b c
(integer) 3
127.0.0.1:6379> sadd test 1
(integer) 1
127.0.0.1:6379> sadd test 2 3
(integer) 2
127.0.0.1:6379> sadd test 1
(integer) 0
127.0.0.1:6379> smembers test
1) "1"
2) "a"
3) "b"
4) "2"
5) "3"
6) "c"
```
#### Sorted Set
> 与Set相似，但可以为集合中的元素进行排序，集合中每个元素拥有一个分数，通过分数为每个元素排序，
集合中的每个元素不可以重复，但是分数可以重复
* 演示
```
127.0.0.1:6379> del test
(integer) 1
127.0.0.1:6379> zadd test 1 a
(integer) 1
127.0.0.1:6379> zadd test 2 b
(integer) 1
127.0.0.1:6379> zadd test 3 c
(integer) 1
127.0.0.1:6379> zadd test 0 aa
(integer) 1
127.0.0.1:6379> zrangebyscore test 0 5
1) "aa"
2) "a"
3) "b"
4) "c"
```
## Redis持久化
> Redis基于内存存储数据，如果服务器出现故障导致Redis进程退出就会出现数据丢失的情况，因此需要定期的将数据持久化到磁盘上，数据持久化有两种方式RDB,AOF
### RDB(快照)
> 制作快照生成RDB文件有两种方式SAVE与BGSAVE,Redis服务器启动时会自动载入安装目录的RDB文件，载入RDB文件期间Redis服务器处于阻塞状态
##### SAVE
> SAVE命令会阻塞Redis的服务器进程，知道RDB文件被创建完毕
##### BGSAVE
> BGSAVE命令首先检查有没有已经fork出的子进程或者正在执行的AOF任务，如果有则抛出错误,没有则fork出一个子进程来创建RDB文件且不会阻塞Redis主进程，
* Redis默认自动执行的的BGSAVE配置，
```
redis.conf

save 900 1      900秒内如果有1个键发生变化则保存快照
save 300 10     300秒内如果有10个键发生变化则保存快照
save 60 10000   60秒内有10000键发生变化则保存快照
```
##### RDB触发方式
* 手动触发: 主动输入SAVE命令或BGSAVE命令
* 自动触发: 
    1. 根据redis.conf配置里的 save m n 定时触发
    2. 主从复制时，主节点自动触发
    3. 执行Debug Reload
    4. 执行Shutdown且没有开启AOF持久化
##### RDB的优缺点
* 全量数据快照，文件小，恢复快
* 无法保存最近一次快照之后的数据
### AOF(Append-Only-File)持久化
> AOF持久化的方式采用保存除了查询以外的所有数据库变更指令,保存指令的方式以append形式追加到AOF文件中，
AOF默认是关闭状态，修改redis.conf文件下 appendonly 为 yes即可开启
##### AOF的优缺点
* AOF内保存的指令可读性高，适合保存增量数据，数据不易丢失
* 文件体积大，恢复时间长
### Redis数据恢复
> 在同时拥有AOF与RDB的情况下，Redis默认只会优先加载AOF然后启动，如没有AOF则加载RDB然后启动
## 参考
1. https://segmentfault.com/a/1190000016837791#articleHeader4
2. http://try.redis.io/
