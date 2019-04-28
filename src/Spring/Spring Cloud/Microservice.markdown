# 了解微服务
## 1. 多种多样的架构风格
### 1.1 传统的单体架构
> 一个项目包含多个业务模块，打包后部署到同一机器上

![spring_cloud_1.png](spring_cloud_1.png)
#### 1.1.1 优点
* 易于测试
* 统一部署
#### 1.1.2 缺点
* 开发效率低
* 部署不够灵活
* 扩展性不强
* 稳定性不高

### 1.2 小巧简单的微服务
####  1.2.1 什么是微服务
* 一系列微小的服务
* 每个微服务跑在自己的进程里
* 每个服务为独立的业务
* 独立部署
* 分布式管理

![](spring_cloud_2.png)
#### 1.2.2 常用的微服务组件
* 服务注册与发现
* 服务网关
* 后端服务
* 前端服务
#### 1.2.3 如何治理多个微服务？
* 阿里系: Dubbo、Zookeeper、SpringMVC......
* SpringCloud: Spring Cloud NetFlix Eureka、SpringBoot...... 
## 2. Spring Cloud
> SpringCloud 是一个开发工具集，包含多个子项目，利用SpringBoot的开发便利性，对NetFlix开源组件进行分装，简化了分布式开发，
### 2.1 Eureka
> 基于NetFlix Eureka做了二次封装，由Eureka Servcer(注册中心)与Eureka Client(服务发现)组成,具有心跳检测、健康检查、负载均衡等功能
#### 2.1.1 创建一个简单的Eureka注册中心
* 初始化Eureka(注意版本)
![](spring_cloud_3.png)
![](spring_cloud_4.png)
![](spring_cloud_5.png)
![](spring_cloud_6.png)
* pom
```


```
* 配置启动类
```java
@SpringBootApplication
@EnableEurekaServer //标识是Eureka Server
public class StudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

}
```
* 配置properties后启动访问http://localhost:8761进行测试
```
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    # 在注册中心不显示当前应用
    register-with-eureka: false
 # 设置application 名字
spring:
  application:
    name: eureka
# 设置端口
server:
  port: 8761
```
#### 2.1.2 创建一个简单的Eureka Client端
* 与注册中心创建方式大致相同，只列出不同点，注意Spring Boot版本、依赖的版本与注册中心的保持一致
* 创建Client
![](spring_cloud_7.png)
* pom
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.xuesong</groupId>
    <artifactId>study</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>study</name>
    <description>Demo project for Spring Boot</description>
    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>

```
* 配置启动类
```java
@SpringBootApplication
@EnableEurekaClient
public class StudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }
}
```
* 配置properties
```
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
spring:
  application:
    name: client
```
* 访问注册中心查看是否已经注册(注册中心要以后台进程的方式运行)

![](spring_cloud_8.png)
#### 2.1.3 创建高可用的Eureka注册中心
* 根据之前的方式创建两个Eureka，Eureka的端口个各不相同，application.yml中的defaultZone填写对方的地址已达到相互注册的功能
* 在Eureka Client上修改application.yml，defaultZone上填写两个Eureka注册中心的地址以 , 分割

## 3. Spring Cloud 服务间的通信
> Spring Cloud与Dubbo不同，Dubbo采用RPC方式，而SpringCloud采用HTTP
### 3.1 Spring Cloud中两种restful通信方式
#### 3.1.1 RestTemplate
* 直接使用RestTemplate调用，简单粗暴
```
RestTemplate restTemplate = new RestTemplate();
String response = restTemplate.getForObject("http://xxxx:8080/xxx",String.class);
```
* 利用loadBalancerClient根据注册名获取地址，灵活多变
```
RestTemplate restTemplate = new restTemplate();
ServiceInstance serviceInstance = loadBalancerClient.choose("注册中心的服务名称");
String url = String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort())+"/xxx";
String response = restTemplate.getForObject('http://xxxx:8080/xxx',String.class);
```
* 利用@LoadBalanced注解
```
// 创建工具类
@Component
public class RestTemplateConfig{
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    } 
}
```
```
// 调用
......
@Autowired
private RestTemplate restTemplate;
public String test(){
    String response = restTemplate.getForObject("http://注册中心的服务名称/xxx",String.class);
    return response;
}
......
```
#### 3.1.2 Feign
* 在服务调用方添加Feign依赖
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-feign</artifactId>
    <scope>test</scope>
</dependency>
```
* 在启动类上添加注解 @EnableFeignClients
```
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ProductApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}
}
```
* 定义需要访问的接口
```java
@FeignClient(name="访问的服务名称")
public interface ProductClient{
    @GetMapping("/访问的接口名称")
    String getMag();
}
```
* 调用测试
```
......
@Autowired
private ProductClient productClient;
public String test(){
    String response = productClient.getMag();
    return response;
}
......
```
#### 负载据衡器Ribbon
> 使用RestTemplate、Feign、Zuul向某个Eureka发起请求时Spring Cloud会转换成Ribbon并执行相应的负载均衡策略
##### 核心功能
* 服务发现
> 根据服务名称获取该服务的所有实例
* 服务选择
> 根据策略选取有效的服务
* 服务监听
> 删除异常的实例
### 2.1 统一配置中心config-server
> 
![](spring_cloud_config_1.png)

#### 2.1.1 创建配置中心
* 初始化项目，除了选择依赖不同其它均与Eureka创建方式相同
![](spring_cloud_config_2.png)
* pom
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.study</groupId>
    <artifactId>config</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>config</name>
    <description>Demo project for Spring Boot</description>
    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>

```
* 添加注解以便添加到Eureka注册中心
```java
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }
}
```
* 在个人的github仓库创建空白项目后添加一个简单的配置文件
![](spring_cloud_config_3.png)
* yml中添加配置
```
spring:
  application:
    name: config
  cloud:
    config:
      server:
        git:
          # 存放配置的仓库地址
          uri: git@github.com:happyPasserBy/spring_config_test.git
          # 如果为私有仓库需添加用户名与密码
          username: xxxxx
          password: xxxxx
          # 拉取远程文件后的本地存储地址
          basedir: xxxxx
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
server:
  port: 8081
```
#### 2.1.2 从配置中心获取资源
* pom中添加依赖
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-client</artifactId>
</dependency>
```
* 项目启动类
```
@SpringBootApplication
@EnableDiscoveryClient(注意)
@EnableFeignClients
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}
}
```
* 将application.yml改名为bootstrap.yml,并修改其内容
```yml
spring:
  application:
    name: product
  cloud:
    config:
      discovery:
        enabled: true
        service-id: CONFIG
      profile: dev
# 如没有设置defaultZone，则会默认向本机8761建立连接(待验证)
# eureka:
#  client:
#    service-url:
#      defaultZone: http://localhost:8761/eureka/
#    register-with-eureka: false


```
* 编写测试代码获取env
```
@RestController
public class ServerController {
    @Value("env")
    private String env;
    @GetMapping("/msg")
    public String msg() {
        return env;
    }
}
```

#### 2.1.3 配置文件
##### 2.1.3.1 访问规则
```
/{name}-{profiles}.yml
/{label}/{name}-{profiles}.yml
```
* name: 服务名
* profiles: 当前环境，此字段根据配置文件中的env来确定如何访问
* label: 当前分支，默认是master
* 浏览器输入 http://localhost:端口/远程配置文件名称-a.yml(如：http://localhost:8081/product-a.yml)进行测试，返回配置文件则成功
##### 2.1.3.2 合并规则
* 获取配置文件时有配置文件的合并规则........待补充
#### 2.1.4 自动拉取最新的配置文件
* 图示

![](spring_cloud_config_4.png)
* 启动RabbitMQ
* 启动在配置中心与业务项目(porduct举例)添加依赖
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```
* 在配置中心的bootstrap.yml添加一下配置
```yml
spring:
  rabbitmq:
    host: 192.168.1.10
    port: 5672
    username: guest
    password: guest
management:
  endpoints:
    web:
      expose: "*"
```
* 查看MQ是否已经连接
![](spring_cloud_config_5.png)
* 更改git仓库中的配置代用bus-refresh接口通知配置中心拉取资源
> http://ip:端口/actuator/bus-refresh
......以失败告终，调用bus-refresh后配置中心资源已经更新，但MQ里没有消息从而product配置文件也没有更新
### 2.3 CenOS下安装docker及其它中间件
#### docker参考链接
* docker version: 1.13.1
* CentOS version: 3.10.0-514.10.2.el7.x86_64
* http://www.runoob.com/docker/docker-image-usage.html
* https://www.cnblogs.com/yufeng218/p/8370670.html
#### docker下安装RabbitMQ
* docker run -d --hostname my-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3.7.3-management
* 浏览器访问 http://机器IP:15672 默认密码guest
* 启动RabbitMQ
```
service docker start
docker run -d --hostname my-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3.7.3-management

docker ps // 此命令出现my-rabbit进程说明成功
// 启动redis docker run -d -p 6379:6379 redis:4.0.8 
```
### 2.4 微服务中的门神-Zuul
#### 2.4.1 Zuul的初始化
* 与其它IDEA创建spring项目相似，但选择依不同
![](spring_cloud_zuul_2.png)
* 修改properties.application为bootstrap.yml，内容如下
```yml
// 配置沿用之前Spring Cloud笔记内容 
spring:
  application:
    name: api-gateway
  cloud:
    config:
      discovery:
        enabled: true
        service-id: CONFIG
      profile: dev
  rabbitmq:
    host: 192.168.1.10
    port: 5672
    username: guest
    password: guest
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
server:
  port: 8082
```
* 启动类添加Zuul注解
```
@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
```
#### 2.4.1 网关的请求流程
![](spring_cloud_zuul_1.png)

#### 2.4.1 Zuul的应用场景
* 限流
* 鉴权
* 参数校验
* 请求转发
* 统计
* 日志

### 2.5 Hystrix-服务降级与断路器

### 2.6 Sleuth与Zipkin-链路监控

### 分布式
> 利用物理架构，由多个自治元素，不共享内存，但通过网路发送消息合作


## 参考
1. https://coding.imooc.com/class/chapter/187.html#Anchor





