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
