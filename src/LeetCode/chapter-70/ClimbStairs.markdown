# ClimbStairs
> 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？注意：给定 n 是一个正整数。[链接](https://leetcode-cn.com/problems/climbing-stairs/)

示例 1：
```
输入： 2
输出： 2
解释： 有两种方法可以爬到楼顶。
1.  1 阶 + 1 阶
2.  2 阶
```
示例 2：
```

输入： 3
输出： 3
解释： 有三种方法可以爬到楼顶。
1.  1 阶 + 1 阶 + 1 阶

2.  1 阶 + 2 阶
3.  2 阶 + 1 阶
```

## 1.解题思路一 自上而下的递归+记忆化搜索
### 1.1 思路
> 
### 1.2 解题思路一
```
public class ClimbStairs {
    private static int[] memory;
    private static int calcWays(int n) {
        if(n == 1) return 1;
        if(n == 2) return 2;

        if(memory[n] == 0) {
            memory[n] = calcWays(n-1) + calcWays(n-2);
        }
        return memory[n];
    }
    public static int climbStairs(int n) {
        if(n == 0)return n;
        memory = new int[n+1];
        return calcWays(n);
    }
    public static void main(String[] args) {
        System.out.println(climbStairs(10));
    }
}
```
## 1.解题思路一 自下而上的动态规划
### 1.1 思路
> 
### 1.2 解题思路一
```
public class ClimbStairs {
    public static int climbStairs(int n) {
        if(n < 2)return n;
        int[] memory = new int[n+1];
        memory[0] = 1;
        memory[1] = 1;

        for (int i = 2; i <= n; i++) {
            memory[i] = memory[i-1]+memory[i-2];
        }
        return memory[n];
    }

    public static void main(String[] args) {
        System.out.println(climbStairs(10));
    }
}
```
## 参考
1. https://coding.imooc.com/class/82.html

