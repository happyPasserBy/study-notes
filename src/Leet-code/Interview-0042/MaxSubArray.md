# MaxSubArray
> [链接](https://leetcode-cn.com/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof/)
```
输入一个整型数组，数组里有正数也有负数。数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
要求时间复杂度为O(n)。

示例1:
输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
输出: 6
解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
```
## 1. 解题思路一 动态规划
### 1.1 具体实现
```java
class Solution {
    public int maxSubArray(int[] nums) {
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            //状态转移方程: 当前的最大值 = max(当前值,当前值+当前下标-1)
            nums[i] = Math.max(nums[i],nums[i]+nums[i-1]);
            // 最大值
            res = Math.max(nums[i],res);
        }
        return res;
    }
}
```