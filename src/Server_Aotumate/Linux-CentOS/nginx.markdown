# nginx 入门
## 1.安装 -v 1.1.6
```
# gcc环境
yum install gcc-c++
# 正则解析库
yum install -y pcre pcre-devel

# ssl套接字
yum install -y openssl openssl-devel

# 解压nginx.gz
tar -zxvf nginx-1.16.1.tar.gz 

# ngxin临时目录，不创建启动nginx报错
mkdir /var/temp/nginx -p

# 创建makefile文件
./configure \
--prefix=/usr/local/nginx \
--pid-path=/var/run/nginx/nginx.pid \
--lock-path=/var/lock/nginx.lock \
--error-log-path=/var/log/nginx/error.log \
--http-log-path=/var/log/nginx/access.log \
--with-http_gzip_static_module \
--http-client-body-temp-path=/var/temp/nginx/client \
--http-proxy-temp-path=/var/temp/nginx/proxy \
--http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
--http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
--http-scgi-temp-path=/var/temp/nginx/scgi

# 编译
make

# 安装
make install
```
## 2. 拆分访问日志
```
# 1.切割日志shell
#!/bin/bash
LOG_PATH="/var/log/nginx/"
RECORD_TIME=$(date -d "yesterday" +%Y-%m-%d+%H:%M)
PID=/var/run/nginx/nginx.pid
mv ${LOG_PATH}/access.log ${LOG_PATH}/access.${RECORD_TIME}.log
mv ${LOG_PATH}/error.log ${LOG_PATHI}/error.${RECORD_TIME}.log
#向 Nginx主进程发送信号,用于重新打开日志文件
kill -USR1 `cat $PID`

# 2.增加权限
chmod +x cut_mylog.sh 

# 3.安装定时任务依赖
yum install crontabs

# 4.增加定时任务
crontab -e;
*/1 * * * * /usr/local/nginx/sbin/cut_mylog.sh

# 5.常用定时任务命令
service crond start

service crond stop

service crond restart

service crond reload

crontab -l

crontab -e

```
## 3. 简单的静态资源服务器
```
# 1.创建虚拟机并引入到nginx.conf中
server {
	listen 80;
	server_name localhost;

       #允许跨域请求的域,*代表所有
       add_header 'Access-Control-Allow-Origin' *;
       #允许带上 cookie请求
       add_header 'Access-Control-Allow-Credentials' 'true';
       #允许请求的方法,比如GET/PosT/PUT/DELETE
       add_header 'Access -Control-Allow-Methods' *;
       #允许请求的 header
       add_header 'Access-Control-ALLow-Headers' *;
	
	location / {
		root 自定义地址;
		index index.html;
	}

	location /static {
		alias 自定义地址;
	}
}


# 2.开启gzip压缩 http块下
gzip  on;
gzip_min_length 1;
gzip_comp_level 3; # 值越大压缩比例越大，cpu消耗越大
gzip_types text/plain application/javascript application/x-javascript text/css image/jpeg image/png application/json;


# 3.静态资源防盗链
# 站点域名
valid_referers *.baidu.com;
# 不是指定源则返回404
if($valid_referers) {
    return 404;
}

```
## 负载均衡
### 四层负载均衡

### 七层负载均衡

### DNS负载均衡

### nginx负载均衡

#### 方式一 轮询
* 普通轮询
> 将请求按照服务器的次序发给某一台服务器，保证每台服务器接收的相同个数的请求.nginx默认使用此方式
```
upstream apis {
    server localhost:8088 weight=5;
    server 172.17.177.48:8088 weight=2;
}
```
* 加权轮询
> 将请求按照服务器的权重发给某一台服务器，服务器的权重越大请求的个数越多。weigth值越大权重越大，服务器接收请求的比例与weight值的比例成正比
```
upstream apis {
    server localhost:8088 weight=5;
    server 172.17.177.48:8088 weight=2;
}
```
#### 方式二 ip_hash
> 服务器从0开始计算下标，将请求的ip进行hash计算后后与服务器节点数在进行 % 运算求出具体的服务器下标，通过下标访问服务器。
```
upstream apis {
    ip_hash;

    server localhost:8088;
    server 172.17.177.48:8088;
}
```
#### 方式三 url_hash
> 服务器从0开始计算下标，将请求的url进行hash计算后后与服务器节点数在进行 % 运算求出具体的服务器下标，通过下标访问服务器。
```
upstream apis {
    hash $request_uri;

    server localhost:8088;
    server 172.17.177.48:8088;
}
```
#### 方式四 least_conn
> 将链接分配给负载较小也就是链接数较少的服务器
```
upstream apis {
    least_conn;

    server localhost:8088;
    server 172.17.177.48:8088;
}
```
#### upstream模块 指令参数
* max_conns
> 设置服务器最大连接数
```
upstream apis {
    server localhost:8088  weight=5 max_conns=10;
    server 192.17.22.48:8088 weight=2 max_conns=10;
}
```
* slow_start
> 设置服务器在加入到集群时会在slow_start设置时间内将权重从 0 增加到 设置的 weight 值
```
upstream apis {
    server localhost:8088  weight=10 max_conns=10 slow_start=10s;
    server 192.168.177.48:8088 weight=2 max_conns=10;
}
```
* down
> 设置该服务器不可用
```
upstream apis {
    server localhost:8088  weight=10 max_conns=10 slow_start=10s;
    server 192.168.177.48:8088 down;
}
```
* backup
> 设置该服务器为备用服务器，当所有服务器宕机时该服务器启用
```
upstream apis {
    server localhost:8088  weight=10 max_conns=10;
    server 192.168.177.48:8088 down;
}
```
* max_fails、fail_timeout
> max_fails表示请求失败达到指定次数将服务器标记为不可用，当不可用时间到达fail_timeout指定时间则再次启用
```
upstream apis {
    server localhost:8088  weight=10 max_fails=3 fail_timeout=60s;
    server 192.168.177.48:8088 down;
}
```
#### 提高效率的keeplive
> 使用http的长连接
```
upstream apis {
    server localhost:8088  weight=5 max_conns=10;
    server 192.17.22.48:8088 weight=2 max_conns=10;

    keeplive 30;
}
server {
	listen 80;
	server_name localhost;

       #允许跨域请求的域,*代表所有
       add_header 'Access-Control-Allow-Origin' *;
       #允许带上 cookie请求
       add_header 'Access-Control-Allow-Credentials' 'true';
       #允许请求的方法,比如GET/PosT/PUT/DELETE
       add_header 'Access -Control-Allow-Methods' *;
       #允许请求的 header
       add_header 'Access-Control-ALLow-Headers' *;
	
	location /api {
        proxy_pass http://apis/;
        proxy_http_version 1.1;
        proxy_set_header Connection "";
    }

}
```