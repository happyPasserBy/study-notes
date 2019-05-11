# Mysql数据库的设计
## 1.常见的设计规范
### 1.1 命名规范
* 所有数据库表列等名称尽量全部用小写，单词用 _ 连接
* 数据库的列名不要使用Mysql关键字 
* 数据库的列名做最好做到见名知意，并且最好不要超过32个字符
* 临时表必须以tmp为前缀并以日期为后缀，备份表以bak为前缀日期为后缀
* 所有存储相同数据的列名和列类型必须一致，如用户表的用户ID与订单表的用户ID
### 1.2 基本设计规范
* 建议使用Innodb存储引擎
* 数据库和表的字符集统一使用UTF8
* 所有的表和注释添加注释
* 尽量控制单表数据量的大小，建议控制在500万以内
* 谨慎使用Mysql分区表
* 尽量做到冷热数据分离，减小表的宽度
* 禁止在表中建立预留字段
* 禁止在数据库中存储图片文件等二进制数据
* 禁止在线上做数据库的压力测试
* 禁止从开发环境，测试环境直接连接生产环境
### 1.3 索引设计规范
* 限制每张表上的索引数量，建议单重表的索引不找过5个，索引数量也是和列的数量成正比
* 每个Innodb表必须有一个主键，如果没有Innodb会查找第一个非空唯一索引，如果也没有则会自动创建隐式主键
* 建议不要使用更新频繁的列作为主键，不用试多列主键
* 索引列的顺序
    * 区分度高的列放在联合索引的最左侧
    * 尽量把字段长度小的列放在联合索引的左侧
    * 使用频繁的列放到联合索引的左侧
* 避免建立冗余和重复索引
* 对于频繁的查询有限考虑使用覆盖索引
* 尽量避免使用外键约束，但建议在表与表之间的关联键上建立索引
### 1.4数据库字段设计规范
* 优先选择符合存储需要的最小数据类型
* 对于非负数采用无符号进行存储
* 避免使用TEXT、BLOB数据类型或者建议将TEXT、BLOB进拆表存储
* 避免使用ENUM数据类型
* 尽可能把所有列定义为NOT NULL
* 使用TIMESTAMP或DATETIME类型存储时间
* 与金额相关的必须使用decimal
### 1.5 SQL开发规范
* 建议使用预编译语句进行数据库操作
* 避免数据类型的隐式转换
    * 隐式转换会导致索引失效
* 充分利用表上已经纯在的索引
    * 避免使用双%号的查询条件，如 like '%111%'
    * 一个SQL只能利用到符合索引中的一列进行范围查询
    * 使用left join或者not exists来优惠not in,not in 会导致索引失效
* 连接不同的数据库应使用不同的账号，禁止跨库查询
* 禁止使用select *
* 禁止使用不含字段列表的INSERT语句，如insert into t values(?,?,?,?);
* 避免使用子查询，可以把子查询优化为join操作
* 避免使用JOIN关联过多表
    * 会产生临时表，影响查询效率
* 禁止使用order by rand()进行随机排序
    * 会把所有符合条件的数据加载到内存中
* WHERE从句禁止对列进行函数转换和计算
    * 对列进行转换和计算会导致无法使用索引
* 在明显不会有重复值时使用UNION ALL而不是UNION
    * UNION会把所有数据加载到临时表在进行去重操作
### 1.6 数据库操作行为规范
* 超过百万的批量写操作，要分批多次进行操作
    * 大批量操作可能会导致严重的主从延迟
    * binlog日志为row格式会产生大量的日志
* 对大表使用pt-online-schema-chane修改表结构
* 禁止为程序使用的账号赋予super权限
* 对于程序连接数据库账号遵循权限最小原则
    * 不允许一个账号跨库操作
    * 程序使用的账号不允许有drop权限
## 2.商城数据库设计实例
### 2.1 第三范式
> 一个表中的列和其他列之间不包含部分函数依赖关系，也不包含传递函数依赖关系，
那么这个表的设计就符合第三范式
### 2.2 用户相关
#### 2.2.1 所需字段
![](mysql_design_user_1.png)
#### 2.2.2 设计方案（一）
> 全部字段存于一张表
##### 2.2.2.1 优点
* 方便存取
##### 2.2.2.2 缺点 ？？？？？
* 插入数据异常
* 数据更新异常
* 数据删除异常
* 数据冗余，冷热数据分离
##### 2.2.2.3 是否符合第三范式
* 存在函数依赖关系：用户表中的级别积分上限与级别积分下限依赖于用户级别(不同的级别需要达到固定的积分)
#### 2.1.2 设计方案（二）
> 遵循第三范式并进行冷热数据分离
* 级别表: 会员级别、级别积分下限、级别积分上限（存在依赖关系）
* 用户登录表: 登录名、密码、用户状态(热数据，需要经常查询)
```sql
create table `customer_login`(
	`customer_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
	`login_name` VARCHAR(20) NOT NULL COMMENT '用户登录名',
	`password` CHAR(32) NOT NULL COMMENT 'MD5加密密码',
	`user_stats` TINYINT NOT NULL DEFAULT 1 COMMENT "用户状态",
	`modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT "最后修改时间",
	PRIMARY KEY pk_customerid(customer_id)
)ENGINE=INNODB COMMENT="用户登录表"
```
```sql
CREATE TABLE customer_level_INF(
	customer_level TINYINT NOT NULL AUTO_INCREMENT COMMENT "会员级别ID",
	level_name VARCHAR(10) NOT NULL COMMENT "会员级别",
	min_point INT UNSIGNED NOT NULL DEFAULT 0 COMMENT "该级别最低积分",
	max_point INT UNSIGNED NOT NULL DEFAULT 0 COMMENT "该级别最高积分",
	modified_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT "最后修改时间",
	PRIMARY KEY pk_levelid(customer_level)
)ENGINE=INNODB COMMENT="用户级别表"
```
* 用户地址表: 省、市、区、邮编、地址(可能存在多个收货地址)
```sql
CREATE TABLE `order_customer_addr` (
  `customer_addr_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `customer_id` int(10) unsigned NOT NULL COMMENT 'customer_login表的自增ID',
  `zip` int(11) NOT NULL COMMENT '邮编',
  `province` int(11) NOT NULL COMMENT '地区表中省份的id',
  `city` int(11) NOT NULL COMMENT '地区表中城市的id',
  `district` int(11) NOT NULL COMMENT '地区表中的区id',
  `address` varchar(200) NOT NULL COMMENT '具体的地址门牌号',
  `is_default` tinyint(4) NOT NULL COMMENT '是否默认',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`customer_addr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户地址表';
```
* 用户信息表: 用户姓名......
```sql
CREATE TABLE `customer_inf` (
  `customer_inf_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `customer_id` int(10) unsigned NOT NULL COMMENT 'customer_login表的自增ID',
  `customer_name` varchar(20) NOT NULL COMMENT '用户真实姓名',
  `identity_card_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '证件类型：1 身份证,2军官证,3护照',
  `identity_card_no` varchar(20) DEFAULT NULL COMMENT '证件号码',
  `mobile_phone` int(10) unsigned DEFAULT NULL COMMENT '手机号',
  `customer_email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `gender` char(1) DEFAULT NULL COMMENT '性别',
  `user_point` int(11) NOT NULL DEFAULT '0' COMMENT '用户积分',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `birthday` datetime DEFAULT NULL COMMENT '会员生日',
  `customer_level` tinyint(4) NOT NULL DEFAULT '1' COMMENT '会员级别:1普通会员,2青铜会员,3白银会员,4黄金会员,5钻石会员',
  `user_money` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '用户余额',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`customer_inf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16384 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
```
* 积分日志表
> 用于业务人员对积分进行审计
```sql
CREATE TABLE `customer_point_log` (
  `point_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '积分日志ID',
  `customer_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `source` tinyint(3) unsigned NOT NULL COMMENT '积分来源:0订单,1登录,2活动',
  `refer_number` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '积分来源相关编号',
  `change_point` smallint(6) NOT NULL DEFAULT '0' COMMENT '变更积分数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '积分日志生成时间',
  PRIMARY KEY (`point_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户积分日志表';
```
* 用户余额变动表
```sql
CREATE TABLE `customer_balance_log` (
  `balance_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '余额日志id',
  `customer_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `source` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '记录来源:1订单,2退货单',
  `source_sn` int(10) unsigned NOT NULL COMMENT '相关单据ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录生成时间',
  `amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '变动金额',
  PRIMARY KEY (`balance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户余额变动表';
```
* 用户登录日志表
```sql
CREATE TABLE `customer_login_log` (
  `login_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '登录日志ID',
  `customer_id` int(10) unsigned NOT NULL COMMENT '登录用户ID',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户登录时间',
  `login_ip` int(10) unsigned NOT NULL COMMENT '登录IP',
  `login_type` tinyint(4) NOT NULL COMMENT '登录类型:0未成功 1成功',
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录日志表';
```
### 2.3 商品相关
#### 2.3.1 所需字段
![](mysql_design_product_1.png)
#### 商品相关设计
* 品牌信息表
```sql
CREATE TABLE `product_brand_info` (
  `brand_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
  `brand_name` varchar(50) NOT NULL COMMENT '品牌名称',
  `telephone` varchar(50) NOT NULL COMMENT '联系电话',
  `brand_web` varchar(100) DEFAULT NULL COMMENT '品牌网站',
  `brand_logo` varchar(100) DEFAULT NULL COMMENT '品牌logo URL',
  `brand_desc` varchar(150) DEFAULT NULL COMMENT '品牌描述',
  `brand_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '品牌状态,0禁用,1启用',
  `brand_order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '排序',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='品牌信息表';
```
* 商品分类表
```sql
CREATE TABLE `product_category` (
  `category_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(10) NOT NULL COMMENT '分类名称',
  `category_code` varchar(10) NOT NULL COMMENT '分类编码',
  `parent_id` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '父分类ID',
  `category_level` tinyint(4) NOT NULL DEFAULT '1' COMMENT '分类层级',
  `category_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '分类状态',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COMMENT='商品分类表';
```
* 供应商信息表
```sql
CREATE TABLE `product_supplier_info` (
  `supplier_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `supplier_code` char(8) NOT NULL COMMENT '供应商编码',
  `supplier_name` char(50) NOT NULL COMMENT '供应商名称',
  `supplier_type` tinyint(4) NOT NULL COMMENT '供应商类型:1.自营,2.平台',
  `link_man` varchar(10) NOT NULL COMMENT '供应商联系人',
  `phone_number` varchar(50) NOT NULL COMMENT '联系电话',
  `bank_name` varchar(50) NOT NULL COMMENT '供应商开户银行名称',
  `bank_account` varchar(50) NOT NULL COMMENT '银行账号',
  `address` varchar(200) NOT NULL COMMENT '供应商地址',
  `supplier_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0禁用,1启用',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='供应商信息表';
```
* 商品信息表
```sql
CREATE TABLE `product_info` (
  `product_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_code` char(16) NOT NULL COMMENT '商品编码',
  `product_name` varchar(50) NOT NULL COMMENT '商品名称',
  `bar_code` varchar(50) NOT NULL COMMENT '国条码',
  `brand_id` int(10) unsigned NOT NULL COMMENT '品牌表的ID',
  `one_category_id` smallint(5) unsigned NOT NULL COMMENT '一级分类ID',
  `two_category_id` smallint(5) unsigned NOT NULL COMMENT '二级分类ID',
  `three_category_id` smallint(5) unsigned NOT NULL COMMENT '三级分类ID',
  `supplier_id` int(10) unsigned NOT NULL COMMENT '商品的供应商id',
  `price` decimal(8,2) NOT NULL COMMENT '商品销售价格',
  `average_cost` decimal(18,2) NOT NULL COMMENT '商品加权平均成本',
  `publish_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '上下架状态:0下架1上架',
  `audit_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态:0未审核,1已审核',
  `weight` float DEFAULT NULL COMMENT '商品重量',
  `length` float DEFAULT NULL COMMENT '商品长度',
  `heigh` float DEFAULT NULL COMMENT '商品高度',
  `width` float DEFAULT NULL COMMENT '商品宽度',
  `color_type` enum('红','黄','蓝','黒') DEFAULT NULL,
  `production_date` datetime NOT NULL COMMENT '生产日期',
  `shelf_life` int(11) NOT NULL COMMENT '商品有效期',
  `descript` text NOT NULL COMMENT '商品描述',
  `indate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品录入时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=262141 DEFAULT CHARSET=utf8 COMMENT='商品信息表';
```
* 商品图片信息表
```sql
CREATE TABLE `product_pic_info` (
  `product_pic_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品图片ID',
  `product_id` int(10) unsigned NOT NULL COMMENT '商品ID',
  `pic_desc` varchar(50) DEFAULT NULL COMMENT '图片描述',
  `pic_url` varchar(200) NOT NULL COMMENT '图片URL',
  `is_master` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否主图:0.非主图1.主图',
  `pic_order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '图片排序',
  `pic_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '图片是否有效:0无效 1有效',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`product_pic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品图片信息表';
```
* 商品评论表
```sql
CREATE TABLE `product_comment` (
  `comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `product_id` int(10) unsigned NOT NULL COMMENT '商品ID',
  `order_id` bigint(20) unsigned NOT NULL COMMENT '订单ID',
  `customer_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `title` varchar(50) NOT NULL COMMENT '评论标题',
  `content` varchar(300) NOT NULL COMMENT '评论内容',
  `audit_status` tinyint(4) NOT NULL COMMENT '审核状态:0未审核1已审核',
  `audit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '评论时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品评论表';
```
### 2.4 订单与仓库相关
#### 2.4.1 所需字段
.....
#### 2.4.2 设计方案
* 订单主表
```sql
CREATE TABLE `order_master` (
  `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_sn` bigint(20) unsigned NOT NULL COMMENT '订单编号 yyyymmddnnnnnnnn',
  `customer_id` int(10) unsigned NOT NULL COMMENT '下单人ID',
  `shipping_user` varchar(10) NOT NULL COMMENT '收货人姓名',
  `province` smallint(6) NOT NULL COMMENT '收货人所在省',
  `city` smallint(6) NOT NULL COMMENT '收货人所在市',
  `district` smallint(6) NOT NULL COMMENT '收货人所在区',
  `address` varchar(100) NOT NULL COMMENT '收货人详细地址',
  `payment_method` tinyint(4) NOT NULL COMMENT '支付方式:1现金,2余额,3网银,4支付宝,5微信',
  `order_money` decimal(8,2) NOT NULL COMMENT '订单金额',
  `district_money` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
  `shipping_money` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '运费金额',
  `payment_money` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `shipping_comp_name` varchar(10) DEFAULT NULL COMMENT '快递公司名称',
  `shipping_sn` varchar(50) DEFAULT NULL COMMENT '快递单号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `shipping_time` datetime DEFAULT NULL COMMENT '发货时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `order_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态',
  `order_point` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单积分',
  `invoice_title` varchar(100) DEFAULT NULL COMMENT '发票抬头',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `ux_ordersn` (`order_sn`)
) ENGINE=InnoDB AUTO_INCREMENT=10011 DEFAULT CHARSET=utf8 COMMENT='订单主表';
```
* 订单详情表
```sql
CREATE TABLE `order_detail` (
  `order_detail_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键ID,订单详情表ID',
  `order_id` int(10) unsigned NOT NULL COMMENT '订单表ID',
  `product_id` int(10) unsigned NOT NULL COMMENT '订单商品ID',
  `product_name` varchar(50) NOT NULL COMMENT '商品名称',
  `product_cnt` int(11) NOT NULL DEFAULT '1' COMMENT '购买商品数量',
  `product_price` decimal(8,2) NOT NULL COMMENT '购买商品单价',
  `average_cost` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '平均成本价格',
  `weight` float DEFAULT NULL COMMENT '商品重量',
  `fee_money` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '优惠分摊金额',
  `w_id` int(10) unsigned NOT NULL COMMENT '仓库ID',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`order_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29698 DEFAULT CHARSET=utf8 COMMENT='订单详情表';
```
* 购物车表
```sql
CREATE TABLE `order_cart` (
  `cart_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `customer_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `product_id` int(10) unsigned NOT NULL COMMENT '商品ID',
  `product_amount` int(11) NOT NULL COMMENT '加入购物车商品数量',
  `price` decimal(8,2) NOT NULL COMMENT '商品价格',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入购物车时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车表';
```
 * 仓库信息表
 ```sql
CREATE TABLE `warehouse_info` (
  `w_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
  `warehouse_sn` char(5) NOT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(10) NOT NULL COMMENT '仓库名称',
  `warehouse_phone` varchar(20) NOT NULL COMMENT '仓库电话',
  `contact` varchar(10) NOT NULL COMMENT '仓库联系人',
  `province` smallint(6) NOT NULL COMMENT '省',
  `city` smallint(6) NOT NULL COMMENT '市',
  `district` smallint(6) NOT NULL COMMENT '区',
  `address` varchar(100) NOT NULL COMMENT '仓库地址',
  `warehouse_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '仓库状态:0禁用,1启用',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`w_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='仓库信息表';
```
* 商品库存表
```sql
CREATE TABLE `warehouse_proudct` (
  `wp_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  `product_id` int(10) unsigned NOT NULL COMMENT '商品id',
  `w_id` smallint(5) unsigned NOT NULL COMMENT '仓库ID',
  `currnet_cnt` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '当前商品数量',
  `lock_cnt` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '当前占用数据',
  `in_transit_cnt` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '在途数据',
  `average_cost` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '移动加权成本',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`wp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品库存表';
```
* 物流公司信息表
```sql
CREATE TABLE `shipping_info` (
  `ship_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ship_name` varchar(20) NOT NULL COMMENT '物流公司名称',
  `ship_contact` varchar(20) NOT NULL COMMENT '物流公司联系人',
  `telphone` varchar(20) NOT NULL COMMENT '物流公司联系电话',
  `price` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '配送价格',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`ship_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物流公司信息表';
```