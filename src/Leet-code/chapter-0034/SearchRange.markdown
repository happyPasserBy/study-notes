# SearchRange
> [链接](https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/)
```
给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
你的算法时间复杂度必须是 O(log n) 级别。
如果数组中不存在目标值，返回 [-1, -1]。

示例 1:
输入: nums = [5,7,7,8,8,10], target = 8
输出: [3,4]
```
## 1. 解题思路一 二分查找+遍历
### 1.1 思路
> 使用二分找到target,找到后无法确定target的边界，以target为中心向两边搜索
## 1. 解题思路二 变种二分查找
### 1.1 思路(来源于leet code高赞题解)
> 优化二分查找，增加isLeft参数使其可以查找边界，具体看实现
### 1.2 具体实现
```
public class Chapter {
    private static int binarySearch(int[] nums, int target, boolean isLeft){
        int low = 0;
        int hight = nums.length;
        while (low < hight){
            int mid = low+(hight-low)/2;
            // 注意后边的判断，当isLeft为true时可以不断缩短尾边界也就是可以查找到left
            if(nums[mid] > target || (isLeft && nums[mid] == target)) {
                hight = mid;
            }else {
                low = mid+1;
            }
        }
        return low;
    }

    public static int[] searchRange(int[] nums, int target) {
        if(nums.length == 0)return new int[]{-1,-1};
        // 县查找left边界
        int left = binarySearch(nums, target, true);
        if(left >= nums.length || nums[left] != target)return new int[]{-1,-1};
        int right = binarySearch(nums, target, false);
        // 返回时注意将right-1，因为在二分中需要+1
        return new int[]{left,right-1};
    }

    public static void main(String[] args) {
        int[] arr1 = {5,7,7,8,8,10};
        int target1 = 8;
        int[] arr2 = {2,2};
        int target2 = 3;
        int[] res = searchRange(arr2, target2);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
    }
}
```