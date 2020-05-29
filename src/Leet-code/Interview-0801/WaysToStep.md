# WaysToStep
> [链接](https://leetcode-cn.com/problems/three-steps-problem-lcci/)
```
三步问题。有个小孩正在上楼梯，楼梯有n阶台阶，小孩一次可以上1阶、2阶或3阶。实现一种方法，计算小孩有多少种上楼梯的方式。结果可能很大，你需要对结果模1000000007。

示例1:
 输入：n = 3 
 输出：4
 说明: 有四种走法
示例2:
 输入：n = 5
 输出：13

提示:
n范围在[1, 1000000]之间
```
## 1. 解题思路一 动态规划
### 1.2 思路
> 
### 1.1 具体实现
```java
class Solution {

    public int waysToStep(int n) {
        if(n < 2)return n;
        int[] memory = new int[n+1];
        // 将大问拆分成小问题
        // 方便后续操作
        memory[0] = 1;
        // 当只有一级台阶时有一种走法
        memory[1] = 1;
        // 当只有两级台阶时有两种走法
        memory[2] = 2;
        /*
        n = 3
        第三级台阶可以从0的位置走或从1的位置走或从2的位置走
        所以三级台阶的走法是  memory[3] = 0级台阶的走法 + 1级台阶的走法 + 2级台阶的走法;
        状态转移方程: memory[i] = memory[i-1]+memory[i-2]+memory[i-3]
        */
        for (int i = 3; i <= n; i++) {
//            memory[i] = memory[i-1]+memory[i-2]+memory[i-3];
            // 取模防止溢出
            memory[i] = ((memory[i-1]+memory[i-2])% 1000000007+memory[i-3])% 1000000007;
        }
        return memory[n];
    }
}
```