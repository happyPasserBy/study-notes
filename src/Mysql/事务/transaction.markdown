# 事务
1. 定义: 只一组操作，里面包含多个单一逻辑，只要有一个逻辑没有成功，就算失败，所有数据回归到最初状态
2. 使用事务(以mysql为例，mysql默认使用事务并且开启了autocommit模式)
    * set autocommit = off; 关闭mysql autocommit模式
    * start transaction; 开启事务
    * 执行语句操作
    * commit; 或 rollback;
        
3. 事务的特性
    * 原子性: 一组操作不可拆分，要么全部成功，要么全部失败
    * 一致性: 事务执行前后，数据的完整性
    * 持久性: 事务执行成功，数据将持久的保存的磁盘上
    * 隔离性: 事务在执行期间不应受到其他事务的影响
4. 事务的隔离级别(SELECT @@tx_isolation)
    * read-uncommitted(脏读)
        1. 现象: A事务读到了B事务未提交的数据
        2. 解决: set session tx_isolation='read-uncommitted';
    * read-commited(不可重复读)
        1. 现象: A开启事务读取数据，如果中途B开启事务并在A事务提交之前提交，A在此读取的数据与第一次读取的数据不同
        2. 解决: set session tx_isolation='repeatable-read';
    * repeatable-read(幻读)
        1. 现象: A开启事务，B开启事务，B插入一条主键为2的数据并提交事务，A也插入一条主键为2的数据报错
        2. 解决: serializable
    * serializable
        1. 原理: 对表的任何操作都会对该表进行加锁，只有提交事务后，其他事务才可以操作，对表的操作是串行状态
## 参考
1. https://segmentfault.com/a/1190000014837747





