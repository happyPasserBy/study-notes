# 防火墙
## 1.逻辑防火墙
> 从逻辑上讲。防火墙可以大体分为主机防火墙和网络防火墙。网络防火墙和主机防火墙并不冲突,可以理解为,网络防火墙主外（集体）, 主机防火墙主内（个人）
* 主机防火墙：针对于单个主机进行防护。
* 往往处于网络入口或边缘,针对于网络入口进行防护,服务于防火墙背后的本地局域网。
## 2.物理防火墙
> 从物理上讲,防火墙可以分为硬件防火墙和软件防火墙。
* 硬件防火墙：在硬件级别实现部分防火墙功能,另一部分功能基于软件实现,性能高,成本高。
* 软件防火墙：应用软件处理逻辑运行于通用硬件平台之上的防火墙,性能低,成本低。
## 3.Netfilter
> Netfilter是防火墙真正的安全框架,它提供网络地址转换、数据包内容修改、以及数据包过滤的防火墙功能。
### 3.1Netfilter提供的挂载位置
* 内核空间,从一个网络接口进来到另一个网络接口去
* 数据从内核到用户空间
* 数据从用户空间流出
* 进入/离开本机的外网接口
* 进入/离开本机的内网接口
### 3.2策略
* PREOUTING(路由前)
* INPUT(数据流入口)
* FORWARD(转发)
* OUTPUT(数据流出)
* POSTROUTING(路由后)

![](./images/iptables_rule.png)
## 4.基于Netfilter的iptables
> iptables是Linux提供给用户的命令接口,可以通过iptables给Netfilter添加策略,策略其实就是网络管理员预定义的条件,策略一般的定义为"如果数据包头符合这样的条件,就这样处理这个数据包"。策略存储在内核空间的信息包过滤表中,这些策略分别指定了源地址、目的地址、传输协议（如TCP、UDP、ICMP）和服务类型（如HTTP、FTP和SMTP）等。当数据包与策略匹配时,iptables就根据策略所定义的方法来处理这些数据包,如放行（accept）、拒绝（reject）和丢弃（drop）等。配置防火墙的主要工作就是添加、修改和删除这些策略。
### 4.1 链
> 在每个策略上我们都可以定义多个规则,而这些规则串在一起时旧形成了链,当报文处于某个策略中,报文会把当前策略上的规则全部匹配一边,当符合规则时则执行相应的动作

![](./images/iptables_chain.png)

### 4.2 表
> 将链中功能相同的规则进行分类,iptables提供了四种表
* filter表：负责过滤功能,防火墙；内核模块：iptables_filter
* nat表：network address translation,网络地址转换功能；内核模块：iptable_nat
* mangle表：拆解报文,做出修改,并重新封装 的功能；iptable_mangle
* raw表：关闭nat表上启用的连接追踪机制；iptable_raw
### 4.3 链与表
> 因每种策略不同,所以种策略上的表也不同
* PREROUTING: raw表、mangle表、nat表
* INPUT: angle表、filter表、(centos7中还有nat表,centos6中没有)
* FORWARD: mangle表,filter表
* OUTPUT: raw表mangle表、nat表、filter表
* POSTROUTING: mangle表、nat表

### 4.4 报文经过防火墙的过程
![](./images/iptables_flow.png)

### 4.5 匹配规则通过后的动作
* ACCEPT: 通过
* DROP: 直接丢弃数据包,不给任何回应信息,这时候客户端会感觉自己的请求泥牛入海了,过了超时时间才会有反应。
* REJECT: 拒绝数据包通过,必要时会给数据发送端一个响应的信息,客户端刚请求就会收到拒绝的信息
* SNAT: 源地址转换,解决内网用户用同一个公网地址上网的问题
* MASQUERADE: 是SNAT的一种特殊形式,适用于动态的、临时会变的ip上
* DNAT: 目标地址转换
* REDIRECT: 在本机做端口映射
* LOG: 在/var/log/messages文件中记录日志信息,然后将数据包传递给下一条规则,也就是说除了记录以外不对数据包做任何其他操作,仍然让下一条规则去匹配

## 5.常用命令
* 检查防火墙状态：service iptables status
* 开启防火墙(临时): service iptables start
* 关闭防火墙(临时): service iptables stop
* 开启防火墙(永久): chkconfig iptables on
* 关闭防火墙(永久): chkconfig iptables off
* iptables命令格式: iptables [-t table] COMMAND chain [options] [-j] ACTION
```
-t table: 用于指定最那个表操作, 主要是filter, nat, managle, 默认为filter
COMMAND: 常用-P(设置默认动作), -A(添加), -D(删除), -I(插入), -R(替换), -L(列举策略), -S(打印策略), -F(删除所策略)
chain: 定义策略应用于五个规则链中的哪条上
options: 定义数据包中的特征, 比如源ip，目的ip，源端口，目的端口，协议类型等
-j: 定义匹配到策略数据包的执行动作, 常用ACCEPT, DROP, REJEC
示例-对INPUT策略filter表中插入10001端口放行的规则: iptables -A INPUT -p tcp --dport 10001 -j ACCEPT
```
* https://wangchujiang.com/linux-command/c/iptables.html

## 参考
1. https://www.jianshu.com/p/207a1c79cfa1
2. https://www.jianshu.com/p/7f91b05fa8c6
3. https://www.zsythink.net/archives/1199