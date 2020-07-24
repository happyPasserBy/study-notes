# Maven

## 1. 下载
地址:http://mirror.bit.edu.cn/apache/maven/maven-3/3.6.3/binaries/
## 2. 传输到云服务器(如果直接下载到了云则忽略)

scp .....
## 3. 解压
tar -zvxf apache-maven-3.6.3-bin.tar.gz
## 4. 配置阿里镜像来加速
```
vim 解压地址//apache-maven-3.6.3/conf/settings.xml
```
```
将以下内容复制到mirrors节点下
<mirror>

      <id>alimaven</id>

      <name>aliyun maven</name>

      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>

      <mirrorOf>central</mirrorOf>       

</mirror>
```
## 5. 配置环境变量
```
vim /etc/profile
```

```
追加以下内容到文件中
export MAVEN_HOME=/opt/maven/apache-maven-3.6.1
export PATH=$MAVEN_HOME/bin:$PATH
```
## 5. 验证
```
source /etc/profile
mvn -v
```