# PivotIndex
> [链接](https://leetcode-cn.com/problems/find-pivot-index/)
```
给定一个整数类型的数组 nums，请编写一个能够返回数组“中心索引”的方法。
我们是这样定义数组中心索引的：数组中心索引的左侧所有元素相加的和等于右侧所有元素相加的和。
如果数组不存在中心索引，那么我们应该返回 -1。如果数组有多个中心索引，那么我们应该返回最靠近左边的那一个。

示例 1:
输入: 
nums = [1, 7, 3, 6, 5, 6]
输出: 3
解释: 
索引3 (nums[3] = 6) 的左侧数之和(1 + 7 + 3 = 11)，与右侧数之和(5 + 6 = 11)相等。
同时, 3 也是第一个符合要求的中心索引。
```
## 1. 解题思路一 循环计数
### 1.1 思路
> 先统计总和，然后循环数组找出"中心索引"
### 1.2 具体实现
```
public class Chapter {
    public static int pivotIndex(int[] nums) {
        int total = 0;
        for (int i = 0; i < nums.length; i++) {
            total += nums[i];
        }
        int count = 0;
        int index = -1;
        for (int i = 0; i < nums.length; i++) {
            if(total-nums[i]-count == count) {
                index = i;
                break;
            }
            count+=nums[i];
        }
        return index;
    }
    public static void main(String[] args) {
        int[] arr1 = {1, 7, 3, 6, 5, 6};
        int[] arr2 = {1, 2, 3};
        System.out.println(pivotIndex(arr2));
    }
}
```