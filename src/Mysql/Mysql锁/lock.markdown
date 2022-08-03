# Mysql与锁
## 1. 共享锁与排它锁
*  锁结构
> 这个所谓的锁其实是一个内存中的结构，在事务执行前本来是没有锁的，也就是说一开始是没有锁结构和记录进行关联的，当一个事务想对这条记录做改动时，首先会看看内存中有没有与这条记录关联的锁结构，当没有的时候就会在内存中生成一个锁结构与之关联。比方说事务T1要对这条记录做改动，就需要生成一个锁结构与之关联

* 锁所在的事务信息：代表这个锁结构是哪个事务生成的。
* 索引信息：对于行锁来说，需要记录一下加锁的记录是属于哪个索引的。
* 表锁／行锁信息： 表所/行锁的结构
* type_mode：记录了是表锁还是行锁，具体锁的类型如：S锁、X锁、IS锁、IX锁、next-key锁、gap锁等

![](./images/construction_lock.png)

*  锁
    * 共享锁(读锁/Shared Locks/s锁): 多个可以同时读取同一数据，但不可边读边改
    * 排它锁(写锁/Exclusive Locks/x锁): 只能单个线程操作数据阻塞其它线程
    * 意向共享锁(Intention Shared Lock/is锁): 当准备给某条数据加s锁时，需要先在表级添加一个is锁,
    * 意向独占所(Intention Exclusive Lock/ix锁): 当准备给某条记录加x锁时，先在表级添加一个ix锁
    * IS、IX锁是表级锁，它们的提出仅仅为了在之后加表级别的S锁和X锁时可以快速判断表中的记录是否被上锁，以避免用遍历的方式来查看表中有没有上锁的记录，也就是说其实IS锁和IX锁是兼容的，IX锁和IX锁是兼容的
    * 行锁-Gap Locks: gap锁的提出仅仅是为了防止插入幻影记录而提出的。加上gap锁后不允许前一条记录至当前记录中间插入数据。
    * 行锁-Record Locks: 用于对某行数据加锁，分为独占与共享。
    * 行锁-Next-Key Locks：next-key锁的本质就是一个Record Locks和一个gap锁的合体，它既能保护该条记录，又能阻止别的事务将新记录插入被保护记录前边的间隙。
## 2 表锁与行锁
### 2.1 表锁
> 对整个表进行加锁
#### 2.1.1 表读锁与表写锁
> mysql中写锁是优先级大于读锁
* 读读: 当前用户在读数据，其他的用户也在读数据，不会加锁
* 读写: 当前用户在读数据，其他的用户不能修改当前用户读的数据，会加锁并阻塞
* 写写: 当前用户在修改数据，其他的用户不能修改当前用户正在修改的数据，会加锁并阻塞
### 2.2 行锁
> 比表锁的控制粒度更细，对表中的行数据加锁


## 3. MyISAM
> MyISAM默认使用表级锁，不支持行级锁
## 4. InnoDB
> 默认用的时行级锁，也支持表级锁 
* 在查询时未用到索引则会为整张表加锁

## 3 READ COMMITED 隔离级别下的加锁情况

## 4 REPEATABLE READ 隔离级别下的加锁情况
### 4.1 查询聚簇索引
> 当 number 为8的数据存在时,对该数据加S锁
```
SELECT * FROM hero WHERE number = 8 LOCK IN SHARE MODE;
```
> 当 number 为7的数据不存在时，为了禁止幻读，会在 number 为8的数据上加gap锁
``` 
SELECT * FROM hero WHERE number = 7 LOCK IN SHARE MODE;
```
> 对 number 为 8 的增加S锁，并为后续查询的数据增加next key锁。
```
SELECT * FROM hero WHERE number >= 8 LOCK IN SHARE MODE;
```
>  对 number 为 1 的增加S锁，并未后续数据增加next key锁，虽然条件是<=8 但防止幻读仍会对 8后边的数据 number = 9的数据增加next key
```
SELECT * FROM hero WHERE number <= 8 LOCK IN SHARE MODE;
```

## 参考
1. [掘金小册](https://juejin.im/book/5bffcbc9f265da614b11b731)