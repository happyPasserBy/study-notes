# Redis进阶
> 主要记录redis的哨兵、集群、分布式锁、数据缓存等进阶知识
## 7.Redis Sentinel
> 哨兵: 一个貌不惊人的少年无意间喝下了实验室的秘密药水，获得相当于一百万个恒星爆炸的能量，成为了保护人类的超级英雄.....额，走错片场了
Redis主从同步存在一个致命的问题，如果主库挂掉则代表Redis不能提供写操作，因此Sentinel(哨兵)应运而生，哨兵监控Redis集群，如发现Master挂掉之后可以合理的进行主从切换
### 7.1 主要功能
* 监控主从服务器是否运行正常
* 如果监控的某个Redis程序出现异常则通过API向管理员发送异常通知
* 自动主从切换，如果监控的Master出现故障，则将一台Slave切换为Master
* Sentinel本质上是运行在特殊模式下的Redis服务器，Sentinel的初始化并不会加载AOF/RDB文件，并且也无法执行set等存储命令
* 客户端不在记录Redis主从的地址，只要记录Sentinel地址即可
### 7.2 故障转移
* 多个Sentinel发现确认master故障
* 选举一个Sentinel作为领导处理故障
* 选出一个slave作为master
* 通知其它slave新的master
* 通知客户端变化
* 老master修复完成则成为slave加入集群
### 7.3 内部定时任务
* 每10秒对master和slave执行info来发现主从关系与slave
* 每2秒每个sentinel通过master节点的channel交换信息
* 每秒sentienl对其它的sentinel和redis执行ping
### 7.4 领导者选举
> 需要一个Sentinel完成故障转移
* 每个做主观下线的Sentinel节点向其他Sentinel节点发送命令，要求将它设置为领导者. 收到命令的Sentinel节点如果没有同意通过其他Sentinel节点发送的命令，那么将同意该请求，否则拒绝
* 如果该Sentine节点发现自己的票数已经超过Sentine集合半数且超过quorum,那么它将成为领导者
* 如果此过程有多个Sentinel节点成为了领导者，那么将等待一段时间重新进行选举
### 7.5 master选择
> 当master宕机后选择一个slave为新的master
* 选择slave-priority(slave节点优先级)最高的slave节点，如果存在则返回，不存在则继续
* 选择复制偏移置最大的slave节点(复制的最完整）,如果存在则返回,不存在则继续
* 选择runld最小的slave节点
## 8.Redis Cluster集群存储
> 为了提高数据的查询效率，将数据按照指定的规则存储到不同的Redis上......未完待续
## 9.Redis实现分布式锁
* SETNX key value + EXPIRE key seconds
```
127.0.0.1:6379> setnx lock 1
(integer) 1
127.0.0.1:6379> setnx lock 2
(integer) 0
127.0.0.1:6379> expire lock 10
(integer) 1
127.0.0.1:6379> setnx lock 2
(integer) 1
```
1. key如果不存在则设置成功返回1，否则设置失败返回0,为了防止客户端宕机导致的死锁，
给key设置超时时间，虽然可以实现分布式锁单仍有缺点，如这样命令相结合的方式不是原子性
 
* SET key value [EX seconds] [PX milliseconds] [NX|XX]
1. EX seconds: 设置键的过去时间为seconds秒
2. PX milliseconds: 设置键的过去时间为milliseconds毫秒
3. NX: 只在键不存在时，才对键进行操作
4. XX: 只在键已经存在时，才对键进行操作
5  SET操作完成时，成功返回ok，否则返回nil
```
127.0.0.1:6379> set lock threadId ex 15 nx
OK
127.0.0.1:6379> set lock threadId ex 15 nx
(nil)
127.0.0.1:6379> set lock threadId ex 15 nx
OK
......
```
## 10.Redis实现异步队列
* 使用List数据结构
* pub/sub订阅者模式
## 11.名词解释
> 假设访问DB前都先去Redis查询是否有相应的cache
### 11.1 缓存穿透
> 访问不存在数据从而绕过缓存
#### 11.1.1 解决方案
* 设置默认缓存
* 布隆过滤器
### 11.2 缓存击穿
> 某一热点数据在cache某一刻过期，导致大量请求直接打在了DB上
### 11.2.1 解决方案
* 加锁处理，拿到锁的可以请求数据库，请求时再次检查缓存是否存在，查询完数据库则设置缓存
### 11.3 缓存雪崩
> 大量的key设置相同的过期时间从而同时失效
#### 11.3.1 解决方案
* 永不过期
* 过期时间错开
* 多缓存结合
## 12. redis的安装
```
# 1.解压
tar -zxvf redis-5.0.7.tar.gz
# 2.安装依赖
yum install gcc-c++
# 3.编译
make
# 4.安装(安装完成)
make install
# 5.
cd utils
cp redis_init_script /etc/init.d/
# 6.复制redis配置
mkdir /usr/local/redis -p
cp /home/software/redis-5.0.7/redis.conf /usr/local/redis/
# 7. 更改核心配置
vim redis.conf
-- 
# 后台运行
daemonize yes
# 指定工作空间 创建自定义目录
dir /usr/local/redis/working 
# 可访问的id(0.0.0.0任何ip都可访问)
bind 0.0.0.0
# 登录密码
requirepass
-- 
# 8.更改运行脚本(第5步的脚本)
vim /etc/init.d/redis_init_script
--
# 核心配置文件地址
CONF="/usr/local/redis/redis.conf"
--
# 9.赋予脚本权限
chmod 777 redis_init_script
# 10. 启动并初始化
./redis_init_script start
# 1.1 验证
ps -ef | grep redis
# 11. 加入开机启动项
vim redis_init_script
--
#chkconfig 22345 10 90
#description: Start and Stop redis
--
chkconfig redis_init_script on
```
## 13.配置主从
```
# 1.查看主从配置
info replication
# 2. 配置主ip
vim redis.conf
replicaof master_ip master_port
# 3. 配置主 登录密码
masterauth <master-password>
```
## 14. 配置哨兵
```
# 1. 在redis安装包下找到sentinel.conf
vim sentinel.conf
# 2. 关闭节点保护(方便操作，线上环境谨慎)
protected-mode no
# 3. 开启后台运行
daemonize yes
# 4. 开启日志
logfile /var/log/redis/sentinel/sentinel.log
# 5. 工作空间
dir /usr/local/redis/sentinel
# 6. 配置哨兵监控的master节点与哨兵检测的数量
sentinel monitor mymaster 127.0.0.1 6379 2
# 7. master密码
sentinel auth-pass mymaster 密码
# 8. 
sentinel down-after-milliseconds mymaster 30000

# 9.最终的配置
---
# 基础配置
protected—mode no
port 26379
daemonize yes
pidfile /var/run/redis—sentinel.pid
logfile /usr/local/redis/sentinel/redis—sentinel.log
dir /usr/local/redis/sentinel
# 核心配置
## 监控的master地址，2是代表几台sentinel同时发现master有问题需要处理
sentinel monitor mymaster ip 6379 2
sentinel auth-pass mymaster 密码
## 如果故障时间超过10000则确定故障
sentinel down-after-milliseconds mymaster 10000
## 故障转移后同步数据时代表几台redis可以同时同步数据
sentinel parallel-syncs mymaster 1
sentinel failover-timeout mymaster 180000

---
# 10.启动
redis-sentinel 配置文件地址
```