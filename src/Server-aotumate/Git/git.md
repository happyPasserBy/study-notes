# Git的安装并使用
## 1. Git安装
```
1. 安装
yum -y install git
2. 检查
git --version
> git version 1.8.3.1
```
## 2. Git配置ssh
```
1. 生成密钥
ssh-keygen -t rsa -C "邮箱"
2. 复制公钥
cat  ~/.ssh/id_rsa.pub
3. 配置到github或其他平台的账户里
```