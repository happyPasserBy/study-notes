## Docker安装RocketMQ
1. 下载namesrv
```
# docker pull rocketmqinc/rocketmq-namesrv:4.5.0-alpine
```
2. 启动 namesrv
```
# docker run -d -p 9876:9876 -v /logs/rocketmq/namesrv:/root/logs -v /data/rocketmq/namesrv/store:/root/store --name dev-rocket-namesrv -e "MAX_POSSIBLE_HEAP=100000000" rocketmqinc/rocketmq-namesrv:4.5.0-alpine
```
1. 下载broker
```
# docker pull rocketmqinc/rocketmq-broker:4.5.0-alpine
```
5. 在/etc/rocketmq/创建配置文件
```
# mkdir -p /etc/rocketmq/
# cd /etc/rocketmq/
# touch broker.conf
# vim broker.conf

brokerClusterName = DefaultCluster
brokerName = broker-a
brokerId = 0
deleteWhen = 04
fileReservedTime = 48
brokerRole = ASYNC_MASTER
flushDiskType = ASYNC_FLUSH
brokerIP1 = 公网ip
aclEnable = true
brokerId = 0
brokerRole = MASTER
```
6. 增加acl
```
# cd /etc/rocketmq/
# touch plain_acl.yml
# vim plain_acl.yml
# 具体信息参考rocketmq acl官方配置
balWhiteRemoteAddresses:
accounts:
- accessKey: 用户名
  secretKey: 密码
  whiteRemoteAddress:
  admin: false
  defaultTopicPerm: DENY
  defaultGroupPerm: SUB
  topicPerms:
  - topicA=PUB|SUB
  - topicB=PUB|SUB
  - test=PUB|SUB
  groupPerms:
  # the group should convert to retry topic
  #   - groupA=DENY
  #     - groupB=PUB|SUB
  #       - groupC=SUB
  #
  #       - accessKey: rocketmq2
  #         secretKey: 12345678
  #           whiteRemoteAddress: 192.168.1.*
  #             # if it is admin, it could access all resources
  #               admin: true
```
7. 启动broker
```
# docker run -d -p 10911:10911 -p 10909:10909 -v /etc/rocketmq/plain_acl.yml:/root/rocketmq-4.5.0/conf/plain_acl.yml -v /etc/rocketmq/broker.conf:/root/rocketmq-4.5.0/conf/broker.conf --name dev-rocket-broker --link dev-rocket-namesrv:namesrv -e "NAMESRV_ADDR=namesrv:9876" -e "MAX_POSSIBLE_HEAP=200000000" rocketmqinc/rocketmq-broker:4.5.0-alpine sh mqbroker -c /root/rocketmq-4.5.0/conf/broker.conf
```
8. 安装控制台
```
# docker pull styletang/rocketmq-console-ng:latest
```
9. 启动控制台
```
# docker run -d -e "JAVA_OPTS=-Drocketmq.namesrv.addr=xxx:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false" -p 10900:8080 --name dev-rmq-conso styletang/rocketmq-console-ng
```
