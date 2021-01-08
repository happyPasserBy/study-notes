# Sentienl
> Sentinel 以流量为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。

## 1.Sentinel 与 Hystrix
[对比链接](https://zhuanlan.zhihu.com/p/43721287)

![](https://pic4.zhimg.com/v2-f3b8b0331876f8eb7fdfef96bc990d93_r.jpg)

## 2.搭建
### 2.1 sentinel客户端集成
* 引入依赖，如果项目主要使用netflix的微服务治理组件仍可以执行引入sentinel但注意版本
```
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    <version>2.1.2.RELEASE</version>
</dependency>
```
* 配置yml
```
spring:
  application:
    name: sentinel-clent
  cloud:
    sentinel:
      transport:
        port: 8719
        # 指定sentinel控制台的地址
        dashboard: localhost:8080
        # 指定客户端ip
        clientIp: ${spring.cloud.client.ip-address}
```
* 创建sentinel配置文件
```
@Configuration
public class SentinelConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @PostConstruct
    private void initRules() throws Exception {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("limit");// 规则名称
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(1);   // 每秒调用最大次数为 1 次

        List<FlowRule> rules = new ArrayList<>();
        rules.add(flowRule);

        // 将控制规则载入到 Sentinel
        FlowRuleManager.loadRules(rules);
    }
}
```
* 对方法进行限流
```
@RestController
@RequestMapping("/sentinel/test")
public class SentinelTestController {

    @SentinelResource(value = "limit" ) // 和配置文件中的规则名称保持一致
    @GetMapping("/list")
    public Result list()throws InterruptedException{

        System.out.println("执行中");
        Thread.sleep(10000);
        return Result.createSuccessByPage(null);
    }
}

```
* 启动没有报错就可以对接口进行验证了
### 2.2 sentinel控制台搭建
* 下载
![下载链接](https://github.com/alibaba/Sentinel/releases)
* 启动
```
java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
或 
java  -jar sentinel-dashboard.jar
```
* 访问验证，账号密码默认sentinel
## 参考
1. [官方链接](https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D)