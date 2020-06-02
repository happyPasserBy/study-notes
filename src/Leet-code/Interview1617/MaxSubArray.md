# MaxSubArray
> [链接](https://leetcode-cn.com/problems/contiguous-sequence-lcci/)
```
给定一个整数数组，找出总和最大的连续数列，并返回总和。

示例：
输入： [-2,1,-3,4,-1,2,1,-5,4]
输出： 6
解释： 连续子数组 [4,-1,2,1] 的和最大，为 6。
进阶：
如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
```
## 1. 解题思路一 动态规划
### 1.1 具体实现
```java
class Solution {
    public int maxSubArray(int[] nums) {
        if(nums.length == 0){
            return 0;
        }else if(nums.length == 1) {
            return nums[0];
        }

        int res = nums[0];
        /*
            重叠子问题: 想要求出nums[i]就需要知道nums[i-1]的最大值
            最优子结构: Math.max(nums[i],nums[i-1]+nums[i]),判断最大值
            状态: nums[i]用于保存[0,i]的最大值
            状态转移方程:  nums[i] = Math.max(nums[i],nums[i-1]+nums[i]);
        */

        for (int i = 1; i < nums.length; i++) {
            nums[i] = Math.max(nums[i],nums[i-1]+nums[i]);
            res = Math.max(res,nums[i]);
        }

        return res;
    }
}
```