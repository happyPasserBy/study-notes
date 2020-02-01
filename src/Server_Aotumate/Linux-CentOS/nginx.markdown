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
