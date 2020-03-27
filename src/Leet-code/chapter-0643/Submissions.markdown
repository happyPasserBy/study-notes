# Submissions
> [连接](https://leetcode-cn.com/problems/maximum-average-subarray-i/)
```
给定 n 个整数，找出平均数最大且长度为 k 的连续子数组，并输出该最大平均数。

示例 1:
输入: [1,12,-5,-6,50,3], k = 4
输出: 12.75
解释: 最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
```
## 1. 解体思路一 动态滑窗
### 1.1 思路
> 根据题目得知，需要查找连续子数组，我们可以维护两个指针，使得指针的长度等于k,这两个指针就像一个窗口，接下来循环数组来移动窗口求出最大平均数。
### 1.2 具体实现
```
public class Chapter643 {
    public static double findMaxAverage(int[] nums, int k) {
        double blance = Integer.MIN_VALUE;
        int total = 0;
        int first = 0;
        for (int i = 0; i < nums.length; i++) {
            total += nums[i];
            if( i - first + 1 == k ) {
                blance = Math.max(blance, (double) total/k);
                total -= nums[first];
                first++;
            }
        }
        return blance;
    }

    public static void main(String[] args) {
        int[] arr1 = {1,12,-5,-6,50,3};
        int k1 = 4;
        int[] arr2 = {5};
        int k2 = 1;
        int[] arr3 = {-1};
        int k3 = 1;
        System.out.println(findMaxAverage(arr3,k3));
    }
}
```