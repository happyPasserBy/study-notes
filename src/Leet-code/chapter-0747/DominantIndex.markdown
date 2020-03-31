# DominantIndex
> [链接](https://leetcode-cn.com/problems/largest-number-at-least-twice-of-others/)
```
在一个给定的数组nums中，总是存在一个最大元素 。
查找数组中的最大元素是否至少是数组中每个其他数字的两倍。
如果是，则返回最大元素的索引，否则返回-1。

示例 1:
输入: nums = [3, 6, 1, 0]
输出: 1
解释: 6是最大的整数, 对于数组中的其他整数,
6大于数组中其他元素的两倍。6的索引是1, 所以我们返回1.
```
## 1. 解题思路一 循环
### 1.1 思路
> 没什么可说的，先找出最大值，然后比较
### 1.2 具体实现
```
class Solution {
    public int dominantIndex(int[] nums) {
        if(nums.length == 0)return -1;
        int index = 0;
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] > nums[index]) {
                max = nums[i];
                index = i;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if(i == index)continue;
            if( max < (nums[i]*2)){
                return -1;
            }
        }
        return index;
    }
}
```