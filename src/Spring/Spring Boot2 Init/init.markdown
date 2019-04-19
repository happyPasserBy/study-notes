# 基于Spring Boot搭建简单项目
## 一项目初始化
### 1.SprngBoot初始化
> 在idea中或者在Spring官网(https://start.spring.io/)都可以使用Spring Boot初始化项目,本次在idea中演示
* 选择 File -> new -> Project 
* 选择 Spring Initializr,选择jdk版本，默认从spring.io拉取数据即可,点击next
![spring_boot_1.png](spring_boot_1.png)
* 根据个人情况输入maven坐标等，点击next
![spring_boot_2.png](spring_boot_2.png)
* 初始化web项目，选择Web点击next
![spring_boot_3.png](spring_boot_3.png)
* 输入项目名称，点击Finish开始下载依赖
![spring_boot_4.png](spring_boot_4.png)
### 2.配置热部署
* 具体步骤:  https://www.cnblogs.com/magicalSam/p/7196355.html
* 以debug模式启动项目
### 3.项目补充
* 创建controller层
* 创建service层，其中包括service与service implements
* 创建dao层
### 4.整合Mybatis并使用generator生成mapper
* pom中添加mysql、mybais、druid相关依赖
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
    <groupId>com.xuesong.study</groupId>
    <artifactId>study</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>study</name>
    <description>Demo project for Spring Boot</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <!--连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.0</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.16</version>
        </dependency>
        <!--mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.41</version>
        </dependency>
        <!--mybatis-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.1</version>
        </dependency>
        <!--mapper-->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>1.2.4</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.2</version>
            <scope>compile</scope>
            <optional>true</optional>
        </dependency>
        <!--pagehelper-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.3</version>
        </dependency>
    </dependencies>
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
* application.properties中添加mysql、mybais、druid配置
```
# 数据源
spring.datasource.url=jdbc:mysql://localhost:3306/niu_ke?characterEncoding=utf8&useSSL=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.druid.initial-size=1
spring.datasource.druid.min-idle=1
spring.datasource.druid.max-active=20
spring.datasource.druid.test-on-borrow=true
spring.datasource.druid.stat-view-servlet.allow=true
# generator
mybatis.type-aliases-package=com.xuesong.study.pojo
mybatis.mapper-locations=classpath:mapper/*.xml
# 
mapper.mappers=com.xuesong.study.utils.MyMapper
mapper.not-empty=false
mapper.identity=MYSQL
# 分页插件
pagehelper.helperDialect=mysql
pagehelper.reasonable=true
pagehelper.supportMethodsArguments=true
pagehelper.params=count=countSql

restart.include.mapper=/mapper-[\\w-\\.]+jar
restart.include.pagehelper=/pagehelper-[\\w-\\.]+jar
```
* 在项目根目录(pom.xml同级目录)创建generatorConfig.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <context id="MysqlContext" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>
        <plugin type="tk.mybatis.mapper.generator.MapperPlugin">
            <property name="mappers" value="com.xuesong.study.utils.MyMapper"/>
        </plugin>
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/niu_ke"
                        userId="root"
                        password="root">
        </jdbcConnection>
        <!-- 对于生成的pojo所在包 -->
        <javaModelGenerator targetPackage="com.xuesong.study.pojo" targetProject="src/main/java"/>
        <!-- 对于生成的mapper所在目录 -->
        <sqlMapGenerator targetPackage="mapper" targetProject="src/main/resources"/>
        <!-- 配置mapper对应的java映射 -->
        <javaClientGenerator targetPackage="com.xuesong.study.mapper" targetProject="src/main/java"
                             type="XMLMAPPER"/>
        <!--表名-->
        <table tableName="film_category"></table>
    </context>
</generatorConfiguration>
```
* 根据项目情况创建工具类并运行main方法，运行完毕后查看mapper与pojo目录，第一种Spring Boot与Mybatis的整合方式完毕
```java
public class GeneratorDisplay {
    public void generator() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定 逆向工程配置文件
        File configFile = new File("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
    }
    public static void main(String[] args) throws Exception {
        try {
            GeneratorDisplay generatorSqlmap = new GeneratorDisplay();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```
