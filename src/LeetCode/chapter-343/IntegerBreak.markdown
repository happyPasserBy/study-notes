# IntegerBreak
> 给定一个正整数 n，将其拆分为至少两个正整数的和，并使这些整数的乘积最大化。 返回你可以获得的最大乘积。[链接](https://leetcode-cn.com/problems/integer-break/)

示例 1:
```
输入: 2
输出: 1
解释: 2 = 1 + 1, 1 × 1 = 1。
```
示例 2:
```
输入: 10
输出: 36
解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36。
```
## 1.解题思路一 自上而下的递归+记忆优化搜索
### 1.1 思路
![](./images/break_1.png)
> 
### 1.2 具体实现
```
public class IntegerBreak {

    private static int[] memory;

    private static int max3(int a, int b, int c){
        return Math.max(a, Math.max(b,c));
    }

    private static int breakInt(int n){
        if(n == 1) return 1;
        int res = -1;
        if(memory[n] != 0) return memory[n];
        for (int i = 1; i < n; i++) {
            res = max3(res,i*(n-i), i * breakInt(n-i));
        }
        memory[n] = res;
        return res;
    }

    public int integerBreak(int n) {
        memory = new int[n+1];
        return breakInt(n);
    }
}
```
## 1.解题思路一 自下而上的动态规划
### 1.1 思路
> 
### 1.2 具体实现
```
public class IntegerBreak {

    private static int max3(int a, int b, int c){
        return Math.max(a, Math.max(b,c));
    }

    public int integerBreak(int n) {
        int[] memory = new int[n+1];
        memory[1] = 1;
        for (int i = 2; i <=n ; i++) {
            for (int j = 1; j <= i-1 ; j++) {
                // j*(i-j): 将i分割成 j + (i-j)
                // j*memory[i-j]: 
                memory[i] = max3(memory[i],j*(i-j),j*memory[i-j]);
            }
        }
        return memory[n];
    }
}
```

## 参考
1. https://coding.imooc.com/class/82.html