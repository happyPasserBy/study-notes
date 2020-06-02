# MissingNumber
> [链接](https://leetcode-cn.com/problems/que-shi-de-shu-zi-lcof/)
```
一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。

示例 1:
输入: [0,1,3]
输出: 2
```
## 1. 解题思路一 二分查找
### 1.1 思路(来源于leet code高赞)
> 根据题意得知每个数字都在范围0～n-1之内且是升序，可以录用这两个特性推算出正确的数组应该是 下标0对应0，下标1对应1，下标与数字一一对应，利用二分查找找出不对应的元素然后不断缩小范围即可。
### 1.2 具体实现
```
class Solution {
    public int missingNumber(int[] nums) {
        if(nums.length == 0) return -1;
        int low = 0;
        int hight = nums.length;
        while (low < hight) {
            int middle = low + (hight-low)/2;
            if(nums[middle] == middle) {
                low = middle+1;
            }else {
                hight = middle;
            }
        }
        return low;
    }
}
```