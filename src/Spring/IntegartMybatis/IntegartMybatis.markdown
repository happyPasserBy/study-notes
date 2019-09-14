# SpringBoot集成Mybatis并完
## 1.安装依赖
```
<!-- mybatis -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
</dependency>

<!-- mysql -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>

<!-- junit -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

## 2.创建数据库与实体
```
create table `pic`(
	`id` int(11) not null primary key AUTO_INCREMENT comment "主键",
	`pic_name` varchar(50) not null comment "图片名称",
	`person_num` int(11) not null comment "图片人数",
	`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片数据表';
```
```
/**
 * pic实体
 */
@Data
public class Pic {
    private Integer id;
    private String picName;
    private Integer personNum;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
```

## 3. application.yml
```
spring:
  rabbitmq:
    password: 
    username: 
    addresses:
    port: 
  datasource:
    url: jdbc:mysql://localhost:3306/pic_discern?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
mybatis:
  mapper-locations: classpath:mapper/*.xml # XML路径
  type-aliases-package: com.pic.demo.po  # 注意：对应实体类的路径
  configuration:
    map-underscore-to-camel-case: true # 驼峰转换

```
## 4.创建Dao
```
package com.pic.demo.dao;

import com.pic.demo.po.Pic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * pic DAO
 */
@Mapper
@Component
public interface PicMapper {
    List<Pic> selectAllPic();
}

```
## 5.创建Service
```
package com.pic.demo.Service;

import com.pic.demo.po.Pic;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * pic Service
 */
@Service
public interface PicService {
    List<Pic> selectAllPic();
}

```
```
package com.pic.demo.Service.Impl;

import com.pic.demo.Service.PicService;
import com.pic.demo.dao.PicDao;
import com.pic.demo.po.Pic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class PicServiceImpl implements PicService {

    @Autowired
    PicDao picMapper;

    @Override
    public List<Pic> selectAllPic() {
        return picMapper.selectAllPic();
    }
}

```
## 6.创建Mapper(resources/mapper/PicMapper)
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.pic.demo.dao.PicDao" >
    <sql id="BASE_TABLE">
        tb_pic
    </sql>
    <sql id="BASE_COLUMN">
        id,pic_name,person_num,update_time,create_time
    </sql>
    <select id="selectAllPic" resultType="com.pic.demo.po.Pic">
        SELECT
            <include refid="BASE_COLUMN"/>
        FROM
            <include refid="BASE_TABLE"/>
    </select>
</mapper>

```
## 7.测试
```
@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscernApplicationTests {

    @Autowired
    PicServiceImpl picService;

    @Test
    public void contextLoads() {
        System.out.println(picService.selectAllPic());
    }

}
--- 
[Pic(id=1, picName=1, personNum=1, updateTime=2019-09-14T15:22:52, createTime=2019-09-14T15:22:52)]
---
```




