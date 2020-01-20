# Fib
> 斐波那契数，通常用 F(n) 表示，形成的序列称为斐波那契数列。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：F(0) = 0,   F(1) = 1。F(N) = F(N - 1) + F(N - 2), 其中 N > 1. [链接](https://leetcode-cn.com/problems/fibonacci-number/)

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

## 1. 解题思路一 自下而上的动态规划
### 1.1 思路 
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