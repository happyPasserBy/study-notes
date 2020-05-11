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
### 11.2 缓存击透
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
# Base
protected—mode no
port 26379
daemonize yes
pidfile /var/run/redis—sentinel.pid
logfile /usr/local/redis/sentinel/redis—sentinel.log
dir /usr/local/redis/sentinel
# Core
sentinel monitor mymaster ip 6379 2
sentinel auth-pass mymaster 密码
sentinel down-after-milliseconds mymaster 10000
sentinel parallel-syncs mymaster 1
sentinel failover-timeout mymaster 180000

---
# 10.启动
redis-sentinel 配置文件地址
```