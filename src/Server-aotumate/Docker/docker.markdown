# Docker
## 1.什么是Docker
> Docker是linux中的一个容器，隔离linux进程，Docker 将应用程序与该程序的依赖，打包在一个文件里面。运行这个文件，就会生成一个虚拟容器。程序在这个虚拟容器里运行，就好像在真实的物理机上运行一样。有了 Docker，就不用担心环境问题
## 2.安装Docker(CentOS)
1. 删除老版本Docker
```
yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
```

2. 安装系统工具
```
sudo yum install -y yum-utils \ device-mapper-persistent-data \ lvm2
```
3. 添加Docker信息
```
yum-config-manager \ --add-repo \ https://download.docker.com/linux/centos/docker-ce.repo
```
4. 安装Docker
```
yum install docker-ce docker-ce-cli containerd.io
```

5. 检测

```
docker version
```
## 3. Docker Engin
> Docker Engin用于将应用App与物理设备隔离开来
* DockerD(后台进程)
* REST API SERVER
* CLI接口
## 3.Image文件
> Docker把应用程序及其依赖打包在Image文件里。只有通过这个文件才能生成Docker容器。Image文件可以看作是容器模板，Docker根据Image文件生成实例。同一个Image文件可以生成多个实例，Image是分层了,并且每一层都可以添加删除改变文件,Image本身是只读的

![](./images/docker_image.png)

## 3.1获取Image(一) Dockerfile

## 3.2获取Image(二) Pull from Registry
> 从Docker Hub拉去Image

### 3.1常用命令
* 列出所有镜象
```
docker image ls
```
* 删除镜像
```
docker image rm [image name]
```

## 4. Docker简单应用-redis
* 拉取redis镜像
```
docker pull redis
```
* 启动redis实例一
```
docker run --name some-redis -d redis
```
* 启动redis实例二，映射端口设置redis密码
```
docker run --name redis -p 6379:6379 -d redis --requirepass "778899"
```
* 4.3 查看docker启动实例
```
docker ps
```
* 查看docker全部实例
```
docker ps -a
```

* -d: 后台运行
* -v: 数据存放目录
* --name: 容器名称
* --requirepass: 密码
* -p: 端口


## 参考
1. https://docs.docker.com/install/linux/docker-ce/centos/
2. http://www.ruanyifeng.com/blog/2018/02/docker-tutorial.html