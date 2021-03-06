# linux
## 1. 系统分区
* /boot: 引导程序，内核等存放的目录
* /sbin: 系统管理命令，这里存放的是系统管理员使用的管理命令
* /bin: 普通用户命令存储墓库
* /dev: 设备文件(这里的设备可以是硬盘，键盘，鼠标，网卡，终端，等设备)
* /etc: 配置文件
* /home: 用户主目录
* /lib: 系统工具函数库
* /temp: 临时文件存储
* /root: 系统管理员目录
* /mnt: 用户可以临时挂载其他文件系统
* /var: 系统可变文档目录
* /usr: 存储应用程序和文件
* /sys: 内存数据目录
## 1.常用命令
### 1.1 命令格式
```
命令 [选项] [参数]
```
### 1.2 基本命令
#### 1.2.1
>  [root@localhost ~]#
* root: 当前登录用户
* localhost: 主机名
* ~: 所在目录
* #: 超级用户提示符
* %: 普通用户提示符

> ls [选项] [文件或目录]
* -a: 显示所有文件包括隐藏文件
* -l: 显示详细信息
* -d: 查看目录属性
* -h: 人性化显示
```
权限       引用计数   所属用户 所属组 大小  更新时间     文件
lrwxrwxrwx      1    root    root    7   8月 25 10:39 bin -> usr/bin
dr-xr-xr-x.     5    root    root 4096   9月 12 08:59 boot
drwxr-xr-x     20    root    root 2900   9月 11 19:17 dev
drwxr-xr-x.    93    root    root 8192   9月  5 09:11 etc
```
### 1.3 文件处理命令

> 创建目录:mkdir -p [目录名]
* -p: 递归创建目录
> 切换目录: cd
* cd -:返回上一次目录
* cd ..: 进入上级目录
* cd ~: 进入当前用户家目录

> 删除目录: mkdir [目录名]
> 删除目录或文件: rm -rf [文件或目录]
* -r: 删除目录
* -f: 强制

> 复制: cp [选项] [原文件或目录] [目标目录]
* -r: 复制目录
* -p: 连带文件属性复制
* -d: 若文件时链接文件则复制连接属性
* -a: 相当于 -pdr

> 剪切或改名: mv [源文件或目录] [目标目录]
### 1.4 搜索命令

> locate 文件名
> 命令搜索的命令 whereis
* -d: 只查找可执行文件
* -m: 只查找帮助文档

> find [搜索范围] [搜索条件]
```
find / install.log
find / -name install.log
find / -name "install.log*"
```
* 搜索条件可加通配符 * ? []
* -atime: 文件访问时间 -10(10天内) 10(10天当天修改) +10(10天前修改)
```
find . -atime 10
```
* -ctime: 文件属性修改时间
* -mtime: 文件内容修改时间
* -size: 文件大小搜索
```
find . -size 20k
```
* -a/-o 逻辑与/逻辑或
find . -size +10k -a -size -100k

> grep [选项] 字符串 文件名
* -i: 忽略大小写
* -v: 排除指定字符
```
grep -i "class" "TwoSum.markdown"
```

> awk [options] "cmd" file
> 一次读取一行文本，按输入的分隔符进行切片,支持对单个切片的判断
## 1.5 帮助命令
> man 命令
```
man ls
```
> 命令 --help
## 1.6 压缩命令
> 常见压缩格式: zip、gz、bz2、tar.gz、tar.bz2

> zip 压缩文件名 源文件
* -r 压缩目录

> unzip 压缩文件
> tar -cvf 打包文件名 源文件
* -c: 打包
* -v: 显示过程
* -f: 指定打包后的文件名
* -x: 解压缩
* -z: 压缩为.tar.gz格式
## 1.7 服务命令

> systemctl [选项] [服务]
* start: 启动
* stop: 停止
* status: 当前状态
* restart: 重启


## 参考
1. https://www.imooc.com/learn/175
1. https://www.cnblogs.com/yoke/p/7217019.html

