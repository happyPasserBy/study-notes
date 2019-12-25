# left join 与 inner join
## 1. left join
> 左连接，会以做表为驱动表关联右表，当右表关联数据为null时则以null填充
```
select * from a left join b 
```
## 2. inner join
> 内连接，取两表的交集
```
select * from a inner join b
```
## 3. 效率对比
* 普遍情况下inner 比 left 效率要高，但具体情况具体分析
* 当a表数据量大于b表时建议在满足需求的情况下以b表为驱动表
* inner 在进行连接时会自动选择数据量小的表为驱动表
### 3.1 join算法
* Nested-Loop Join 
* Block Nested-Loop Join 

## 参考
1. https://www.cnblogs.com/danhuangpai/p/8137393.html