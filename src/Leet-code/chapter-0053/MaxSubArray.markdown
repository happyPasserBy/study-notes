# MaxSubArray
> [链接](https://leetcode-cn.com/problems/maximum-subarray/)
```
给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

示例:
输入: [-2,1,-3,4,-1,2,1,-5,4],
输出: 6
解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。

进阶:
如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
```
## 1. 解题思路一 暴力解法
### 1.1 思路
> 双重循环计算每个子序列找出最大值
### 1.2 具体实现
```
class Solution {
    public int maxSubArray(int[] nums) {
        if(nums.length == 0)return 0;
        int max = nums[0];
        for (int i = 0; i < nums.length; i++) {
            int sum = nums[i];
            max = Math.max(sum,max);
            for (int j = i+1; j < nums.length ; j++) {
                sum+=nums[j];
                max = Math.max(sum,max);
            }
        }
        return max;
    }
}
```
## 1. 解题思路一 自底而上的动态规划
### 1.1 思路
> 首先拆分子问题，假设数组有两个元素[1,2]如何解决呢？首先从最后开始遍历下标为1(从前也可以)。找到最优子结构，因为只有一个2,所以最大值就是2，此时继续遍历下标为0，此时状况有两种，1本身加前一个遍历的元素大于1本身或者小于1本身，情况一如果大于1本身说明这是一个有效的子数组且得出的值是从下标0开始的子数组的最大值，把当前值记录到当前下标上，情况二如果小于1本身说明下标为0的子数组的最大值就是1本身无需其它操作，最后将本次循环的结果与max对比找出最大值即可。数组的长度为3或更大与长度为2的处理方式一样。
### 1.2 具体实现
```
public static int maxSubArray(int[] nums) {
    if(nums.length == 0)return 0;
    int max = nums[nums.length-1];
    for (int i = nums.length-2; i >= 0; i--) {
        int cur = Math.max(nums[i], nums[i]+nums[i+1]);
        if(cur >= nums[i]){
            nums[i] = cur;
            max = Math.max(nums[i],max);
        }
        max = Math.max(nums[i],max);

    }
    return max;
}

public static void main(String[] args) {
    String str1 = "[-2,1,-3,4,-1,2,1,-5,4]";
    String str2 = "[-2,1]";
    String str3 = "[-1,0,-2]";
    System.out.println(maxSubArray(stringToIntegerArray(str1)));
}
```

