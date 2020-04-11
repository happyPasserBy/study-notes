# FindMin
> [链接](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/)
```
假设按照升序排序的数组在预先未知的某个点上进行了旋转。
( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
请找出其中最小的元素。
你可以假设数组中不存在重复元素。

示例 1:
输入: [3,4,5,1,2]
输出: 1
```
## 1. 解题思路一 二分查找
### 1.1 思路
> 根据二分查找的特性，快速定位到某一点，判断当前点与右边界的大小关系，如果当前点大于右节点说明当前点的右侧有旋转且最小值必定在右侧，如果小于则反之。
### 1.1 具体实现
```
class Solution {
    public int findMin(int[] nums) {
        if(nums.length < 1)return 0;

        int left = 0;
        int right = nums.length-1;

        while (left < right) {
            int middle = left + (right-left)/2;
            if(nums[middle] > nums[right]){
                left = middle+1;
            }else {
                right = middle;
            }
        }
        return nums[left];
    }

}
```