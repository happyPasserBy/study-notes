# Search
> [链接](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/)
```
假设按照升序排序的数组在预先未知的某个点上进行了旋转。
( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
你可以假设数组中不存在重复的元素。
你的算法时间复杂度必须是 O(log n) 级别。

示例 1:
输入: nums = [4,5,6,7,0,1,2], target = 0
输出: 4
```
## 1. 解题思路一 二分查找
> 创建两个指针begin与end,begin执行起始下标，end指向结束下标，使用二分查找找到中间下标middle,根据begin、end、middle这三个位置上元素的大小关系来确定是否有序或旋转，如果有序则判断target是否在有序范围中，在就缩小范围至有序中查询，不在同样可以缩小范围，这样就可以不断缩减范围直到结束。[来源于leetcode官方](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/solution/sou-suo-xuan-zhuan-pai-xu-shu-zu-by-leetcode-solut/)
### 1.2 具体实现
```
public class Chapter {
    public static int search(int[] nums, int target) {
        if(nums.length == 0)return -1;
        int begin = 0;
        int end = nums.length-1;
        int middle = 0;
        while (begin <= end && end < nums.length && begin >= 0) {
            middle = (begin+end)/2;
            if(nums[begin] == target) return begin;
            if(nums[end] == target) return end;
            if(nums[middle] == target) return middle;
            if(nums[begin] < nums[middle]){
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
        return -1;
    }
}
```