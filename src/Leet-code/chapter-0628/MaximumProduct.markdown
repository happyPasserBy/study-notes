# MaximumProduct
>[链接](https://leetcode-cn.com/problems/maximum-product-of-three-numbers/)
```
给定一个整型数组，在数组中找出由三个数组成的最大乘积，并输出这个乘积。

示例 1:
输入: [1,2,3]
输出: 6
```
## 1.解题思路一 排序
### 1.1 思路
> 没什么可说的，排序找出最大值想乘即可，注意负数。
### 1.2 具体实现
```
class Solution {
    public int maximumProduct(int[] nums) {
        if(nums.length < 3) return 0;
        Arrays.sort(nums);
        int max = nums[nums.length-1] * nums[nums.length-2] * nums[nums.length-3];
        if(nums[1] < 0) {
            max = Math.max(max,nums[0] * nums[1] * nums[nums.length-1]);
        }
        return max;
    }
}
```