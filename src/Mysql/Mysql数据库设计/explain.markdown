# explain 分析
> explain 关键字可以显示当前sql语句的执行信息
```sql
explain select customer_id,title,content from `product_comment` where audit_status = 1 and product_id = 199726 limit 0,5
```
![](mysql_design_explain_1.png)
### 3.1.1 ID列
* ID列中的数据为一组数字，表示执行select语句的顺序
* ID值相同，执行顺序由上至下
* ID值越大优先级越高，越先被执行
### 3.1.2 SELECT_TYPE列
* SIMPLE: 不包含子查询或是UNION操作的查询
* PRIMARY: 查询中如果包含任何子查询，那么最外层的查询则别标记为PRIMARY
* SUBQUERY: SELECT列表中的子查询
* DEPENDENT SUBQUERY: 外部依赖的子查询
* UNION: UNION操作的第二个或是之后的查询值为UNION
* DEPENDENT UNION: 当UNION做为子查询时，第二或第二个之后的查询值为DEPENDENT UNION
* UNION RESULT: UNION产生的结果集
* DERIVER: 出现在FROM子句中的子查询
### 3.1.3 TABLE列
* 输出数据行所在的表名或别名
* <unionM,N>由ID为M,N查询union产生的结果集
* <derivedN>/<subqueryN> 由ID为N的查询产生的结果
### 3.1.4 PARTITIONS列
* 对于分区表，现实查询的分区ID
* 对于非分区表，显示为NULL
### 3.1.5 TYPE列(性能由高到低)
* system: conset连接类型的特例，当查询的表只有一行时
* const: 表中有且只有一个匹配的行时使用，如对主键或是唯一索引的查寻，这是效率最该的连接方式
* eq_ref: 唯一索引或主键索引查寻，对于每个索引键，表中只有一条记录与之匹配
* ref: 非唯一索引查找，返回匹配某个值的所有行
* ref_or_null: 类似于ref类型的查询，但是附加了对NULL值列的查询
* index_merge: 表示使用了索引合并优化
* range: 索引范围扫描，常见于between、>、<这样的查询条件中
* index: FULL Index Scan全索引扫描，同ALL的区别是，遍历的是索引树
* ALL: FULL TABLE Scan全表扫描
### 3.1.6 Extra列
* Distince: 优化distinct操作，在找到第一个匹配元素后停止找同样值的动作
* Not exists: 使用not exists来优化查询
* Using filesort: 使用额外的操作进行排序，通常会出现在order by 或 group by 查询中
* Using Index: 使用了覆盖索引进行查询
* Using temporary: Mysql需要使用临时表来处理查询，常见于排序，子查询和分组查询
* Using where: 需要在Mysql服务器层使用where条件来过滤数据
* select tables optimized away: 直接通过索引来获得数据，不用访问表
### 3.1.7 POSSIBLE_KEY列
* 指出Mysql能使用那些索引来优化查询
* 查询列所涉及到的列上的索引都会被显示出，但不一定会使用
### 3.1.8 KEY列
* 查询优化器实际使用的索引
* 如果没有可用的索引，则显示为NULL
* 如果查询使用了覆盖索引，则该索引进出现在Key列中
### 3.1.9 KEY_LEN列
* 表示索引字段最大的可能长度
### 3.1.10 REF列
* 显示使用索引进行查询时的值来源
### 3.1.11 ROWS列
* 表示Mysql通过索引统计信息，估算的所需读取的行数
### 3.1.11 Filtered列
* 表示返回的结果行数占需要读取行数的百分比

