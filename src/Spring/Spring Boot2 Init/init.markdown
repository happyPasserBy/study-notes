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
### 4.整合Mybatis 
#### 4.1整合Mybatis并使用generator生成mapper
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
#### 4.2手动整合Mybatis
* pom文件
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
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
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
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.1</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.1</version>
        </dependency>
        <!-- 数据库 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!-- 连接池 -->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
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
* 在resources目录下创建mybatis-config.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 配置全局属性 -->
    <settings>
        <!-- 使用jdbc的getGeneratedKeys获取数据库自增主键值 -->
        <setting name="useGeneratedKeys" value="true" />

        <!-- 使用列标签替换列别名 默认:true -->
        <setting name="useColumnLabel" value="true" />

        <!-- 开启驼峰命名转换:Table{create_time} -> Entity{createTime} -->
        <setting name="mapUnderscoreToCamelCase" value="true" />
    </settings>
</configuration>
```
* application.properties中添加配置
```
#DataSource
#数据库驱动
jdbc.driver=com.mysql.cj.jdbc.Driver
#数据库链接
jdbc.url=jdbc:mysql://localhost:3306/niu_ke?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
#数据库用户名
jdbc.username=root
#数据库密码
jdbc.password=root

#Mybatis
mybatis_config_file=mybatis-config.xml
mapper_path=/mapper/**.xml
type_alias_package=com.xuesong.study.demo.dao.entity
```
* 在项目中的config目录添加DataSource配置
```
// 文件位置com.xuesong.study.demo.config.dao.DataSourceConfiguration

@Configuration
// 配置mybatis mapper的扫描路径
@MapperScan("com.xuesong.study.demo.dao")
public class DataSourceConfiguration {
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    /**
     * 生成与spring-dao.xml对应的bean dataSource
     *
     * @return
     * @throws PropertyVetoException
     */
    @Bean(name = "dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        // 生成datasource实例
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        // 跟配置文件一样设置以下信息
        // 驱动
        dataSource.setDriverClass(jdbcDriver);
        // 数据库连接URL
        dataSource.setJdbcUrl(jdbcUrl);
        // 设置用户名
        dataSource.setUser(jdbcUsername);
        // 设置用户密码
        dataSource.setPassword(jdbcPassword);
        // 配置c3p0连接池的私有属性
        // 连接池最大线程数
        dataSource.setMaxPoolSize(30);
        // 连接池最小线程数
        dataSource.setMinPoolSize(10);
        // 关闭连接后不自动commit
        dataSource.setAutoCommitOnClose(false);
        // 连接超时时间
        dataSource.setCheckoutTimeout(10000);
        // 连接失败重试次数
        dataSource.setAcquireRetryAttempts(2);
        return dataSource;
    }
}

```
* 在项目中的config目录添加SessionFactoryConfiguration配置
```
@Configuration
public class SessionFactoryConfiguration {
    // mybatis-config.xml配置文件的路径
    private static String mybatisConfigFile;

    @Value("${mybatis_config_file}")
    public void setMybatisConfigFile(String mybatisConfigFile) {
        SessionFactoryConfiguration.mybatisConfigFile = mybatisConfigFile;
    }

    // mybatis mapper文件所在路径
    private static String mapperPath;

    @Value("${mapper_path}")
    public void setMapperPath(String mapperPath) {
        SessionFactoryConfiguration.mapperPath = mapperPath;
    }

    // 实体类所在的package
    @Value("${type_alias_package}")
    private String typeAliasPackage;

    @Autowired
    @Qualifier(value="dataSource")
    private DataSource dataSource;

    /**
     * 创建sqlSessionFactoryBean 实例 并且设置configtion 设置mapper 映射路径 设置datasource数据源
     *
     * @return
     * @throws IOException
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置mybatis configuration 扫描路径
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFile));
        // 添加mapper 扫描路径
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
        // 设置dataSource
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 设置typeAlias 包扫描路径
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);
        return sqlSessionFactoryBean;
    }
}

```
* 在resources下创建mapper
```
// FilmDao.xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.xuesong.study.demo.dao.FilmDao">
    <select id="queryFilmById" resultType="com.xuesong.study.demo.entity.Film">
		SELECT film_id, title,
		description
		FROM film
		where film_id = #{id}
	</select>
</mapper>
```
* 在controller与service中补充相应的测试逻辑进行测试，第二种Spring Boot与Mybatis的整合方式完毕
#### 4.3 整合Mybatis，以注解方式开发
* pom中添加依赖
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
    <artifactId>demo</artifactId>
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
        <!-- 数据库 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--mybatis-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.1</version>
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
* 配置application.properties
```
mybatis.type-aliases-package=com.xuesong.entity
## 下划线自动转驼峰
mybatis.configuration.map-underscore-to-camel-case=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/niu_ke?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
spring.datasource.username=root
spring.datasource.password=root
```
* 创建dao并添加测试代码
```
@Mapper
@Repository
public interface FilmDao {
    @Select("select film_id,title,description from film where film_id = #{id}")
    Film queryFilmById(Integer id);
}
```
* 完善controller、service代码并进行测试，注解方式完毕
## 参考链接
1. https://www.imooc.com/video/16539
2. https://www.imooc.com/video/16726
3. https://www.jianshu.com/p/c43483839e14