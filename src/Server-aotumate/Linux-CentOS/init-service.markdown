# CentOS7.6环境初始化
## 1.安装 Jdk
1. 删除openjdk
```
# 检测openjdk
rpm -qa|grep openjdk -i

# 删除openjdk
rpm -e --nodes 待删除的软件名称

# 创建java目录
mkdir /usr/java

# 将java安装包拷贝到 /usr/java 
# 根据后缀名解压 
tar -zxvf jdk...tar.gz

# 配置环境变量
vim /etc/profile

# 粘贴再最后
export JAVA_HOME=/usr/java/jdk1.8.0_191
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$JAVA_HOME/bin:$PATH

# 刷新配置文件
source /etc/profile

# 验证
java -version
```
## 2.安装jdk
```
# 将安装包上传到/home/software

# 解压安装包gz格式
tar -zxvf apache-tomcat-9.0.24.tar.gz

# 移动
mv apache-tomcat-9.0.24 /usr/local/

# 启动 进入tomcat/bin/
./startup.sh
# Tomcat started. 看到这句代表成功

# 浏览器验证，如果无法访问则设置防火墙与阿里云安全组，具体请看参考1

```

## MariaDB安装
```
# 安装

yum install rsync nmap lsof perl-DBI nc

rpm -ivh jemalloc-3.6.0-1.el7.x86_64.rpm

rpm -ivh jemalloc-devel-3.6.0-1.el7.x86_64.rpm 

# 注意顺序
rpm -ivh MariaDB-common-10.4.8-1.el7.centos.x86_64.rpm MariaDB-compat-10.4.8-1.el7.centos.x86_64.rpm MariaDB-client-10.4.8-1.el7.centos.x86_64.rpm galera-4-26.4.2-1.rhel7.el7.centos.x86_64.rpm MariaDB-server-10.4.8-1.el7.centos.x86_64.rpm 
# 检测lib冲突
rpm -qa|grep mariadb-libs
# rpm -ev --nodeps mariadb-libs-5.5.60-1.el7_5.x86_64

# 安装galera环境
yum install boost-devel.x86_64
# 导入key
rpm --import http://yum.mariadb.org/RPM-GPG-KEY-MariaDB

# 安装libaio
# wget http://mirror.centos.org/centos/6/os/x86_64/Packages/libaio-0.3.107-10.el6.x86_64.rpm
# rpm -ivh libaio-0.3.107-10.el6.x86_64.rpm
```
```
# 启动与配置
# 启动
service mysql start
# 安全配置
mysql_secure_installation

# 登录控制台 
mysql -u root -p
# 开启远程链接
grant all privileges on *.* to 'root'@'%' identified by '密码'
# 刷新配置
flush privileges;
```

![](./images/MariaDB-10.4.png)

## 参考
1. https://blog.csdn.net/Blue_Sky_rain/article/details/91348791
2. https://downloads.mariadb.org/mariadb/repositories/#distro=CentOS&distro_release=centos7-amd64--centos7&mirror=marwan&version=10.4