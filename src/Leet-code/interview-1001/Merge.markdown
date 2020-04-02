# merge
> [链接](https://leetcode-cn.com/problems/sorted-merge-lcci/solution/)
```
给定两个排序后的数组 A 和 B，其中 A 的末端有足够的缓冲空间容纳 B。 编写一个方法，将 B 合并入 A 并排序。
初始化 A 和 B 的元素数量分别为 m 和 n。

示例:
输入:
A = [1,2,3,0,0,0], m = 3
B = [2,5,6],       n = 3
输出: [1,2,2,3,5,6]
```
## 1. 解题思路一 合并后排序
### 1.1 思路
> 先将B合并到A，A在排序
## 2. 解题思路二 双指针+额外空间
### 2.1 思路
> 创建两个指针分别只向A、B起始值，在创建一个长度为A的空白数组,循环A,对比A、B将较小的值放入空白数组后移动相应的指针继续对比知道完毕即可
## 3. 解题思路三 双指针+倒叙对比
### 3.1 思路
> 本思路是优化解题思路二的方式，思路二需要额外的空间，因外是往其实位置插入。我门可以换个思路从尾部开始插入，创建双指针指向A、B的尾节点，循环对比放入A的尾节点即可
### 3.2 具体实现
```
class Solution {
    public void merge(int[] A, int m, int[] B, int n) {
        int pointA = m-1;
        int pointB = n-1;
        int tail = A.length-1;
        while (pointA >= 0 && pointB >= 0) {
            if(A[pointA] >= B[pointB]) {
                A[tail] = A[pointA];
                pointA--;
            }else {
                A[tail] = B[pointB];
                pointB--;
            }
            tail--;
        }
        if(pointA >= 0){
            System.arraycopy(A,0,A,0,pointA+1);
        }
        if(pointB >= 0){
            System.arraycopy(B,0,A,0,pointB+1);
        }
    }
}
```