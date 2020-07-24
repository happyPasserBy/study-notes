# 安装Jdk
## 1. 下载
http://openjdk.java.net/

https://www.oracle.com/java/technologies/javase-downloads.html

https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html
## 2. 解压
```
tar -zxvf jdk-8u144-linux-x64.tar.gz
```
## 3. 配置环境变量
```
vim /etc/profile
```
```
复制以下内容到文件
export JAVA_HOME=/usr/java/jdk1.8.0_191 你的安装目录
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$JAVA_HOME/bin:$PATH
```
## 4. 验证
```
source /etc/profile
java -version
```