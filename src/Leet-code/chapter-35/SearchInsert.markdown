# SearchInsert
> [链接](https://leetcode-cn.com/problems/search-insert-position/)
```
给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
你可以假设数组中无重复元素。

示例 1:
输入: [1,3,5,6], 5
输出: 2

示例 2:
输入: [1,3,5,6], 2
输出: 1
```
## 1.解题思路一 暴力解法
### 1.1 思路
> 一趟循环
### 1.1 具体实现
```
public class SearchInsert35 {
    public static int searchInsert(int[] nums, int target) {
        int index = -1;

        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == target || nums[i] > target){
                index = i;
                break;
            }
        }

        return index == -1 ? nums.length : index;
    }

    public static void main(String[] args) {
        int[] arr1 = {1,3,5,6};
        int val1 = 5;
        int[] arr2 = {1,3,5,6};
        int val2 = 7;
        int[] arr3 = {1,3,5,6};
        int val3 = 2;
        System.out.println(searchInsert(arr3, val3));
    }
}
```
## 2.解题思路二 二分查找
### 2.1 思路
......