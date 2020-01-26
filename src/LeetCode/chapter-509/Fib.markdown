# Fib
> 斐波那契数，通常用 F(n) 表示，形成的序列称为斐波那契数列。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：F(0) = 0,   F(1) = 1。F(N) = F(N - 1) + F(N - 2), 其中 N > 1. [链接](https://leetcode-cn.com/problems/fibonacci-number/)

示例 1：
```
输入：2
输出：1
解释：F(2) = F(1) + F(0) = 1 + 0 = 1.
```
示例 2：
```
输入：3
输出：2
解释：F(3) = F(2) + F(1) = 1 + 1 = 2.
```
示例 3：
```
输入：4
输出：3
解释：F(4) = F(3) + F(2) = 2 + 1 = 3.
```
## 1. 解题思路一 自上而下的递归
### 1.1 思路 
> 套公式
### 1.2
```
public class Fib {
    public static int fib(int N) {
        if(N == 0) return 0;
        if(N == 1) return 1;
        return fib(N-1)+fib(N-2);
    }

    public static void main(String[] args) {
        System.out.println(fib(4));
    }
}
```
## 1. 解题思路二 自下而上的动态规划
### 1.1 思路 
> 同样是套公式，但我们采用递推的方式，换句话说也就是从小到大，自下而上的方式
### 1.2
```
public class Fib {

    public static int fib(int N) {
        if(N < 2)return N;
        int[] memory = new int[N+1];
        memory[0] = 0;
        memory[1] = 1;

        for (int i = 2; i <= N; i++) {
            memory[i] = memory[i-1]+memory[i-2];
        }
        return memory[N];
    }
    public static void main(String[] args) {
        System.out.println(fib(20));
    }
}
```