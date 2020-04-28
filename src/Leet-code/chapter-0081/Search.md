# Search
> [链接](https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/)
```
假设按照升序排序的数组在预先未知的某个点上进行了旋转。
( 例如，数组 [0,0,1,2,2,5,6] 可能变为 [2,5,6,0,0,1,2] )。
编写一个函数来判断给定的目标值是否存在于数组中。若存在返回 true，否则返回 false。

示例 1:
输入: nums = [2,5,6,0,0,1,2], target = 0
输出: true

示例 2:
输入: nums = [2,5,6,0,0,1,2], target = 3
输出: false
```
## 1. 解题思路一 二分查找
### 1.1 思路
> [参考33题](../chapter-0033/Search.md)
### 1.2 具体实现
```java
public class Chapter {
    public static boolean search(int[] nums, int target) {
        if(nums.length == 0)return false;
        int begin = 0;
        int end = nums.length-1;
        int middle = 0;
        while (begin <= end && end < nums.length && begin >= 0) {
            middle = (begin+end)/2;
            if(nums[begin] == target) return true;
            if(nums[end] == target) return true;
            if(nums[middle] == target) return true;
            if (nums[begin] == nums[middle]) {
                begin++;
                continue;
            }

            if(nums[begin] <= nums[middle]){
                if(nums[begin] < target && nums[middle] > target) {
                    end = middle-1;
                }else {
                    begin = middle+1;
                }
            }else {
                if(nums[middle] < target && nums[end] > target) {
                    begin = middle+1;
                }else {
                    end = middle-1;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr1 = {1,1,3,1};
        int target1 = 3;
        int[] arr2 = {2,5,6,0,0,1,2};
        int target2 = 0;
        int[] arr3 = {2,5,6,0,0,1,2};
        int target3 = 3;
        int[] arr4 = {1,3,1,1,1};
        int target4 = 3;
        System.out.println(search(arr4, target4));
    }
}

```