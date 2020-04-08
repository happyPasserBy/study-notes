# RemoveDuplicates
> [链接](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii/)
```
给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素最多出现两次，返回移除后数组的新长度。
不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。

示例 1:
给定 nums = [1,1,1,2,2,3],
函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。
你不需要考虑数组中超出新长度后面的元素
```
## 1. 解题思路一 双指针
### 1.1 思路
> 创建指针cur,cur之前的元素为符合题意的元素，创建计数器count，用于统计当前元素重复个数，循环数组，判断每个元素与前一个元素是否重复并更新count。
```
public class Chapter80 {
    public static int removeDuplicates(int[] nums) {
        if(nums.length <= 1)return 1;
        int cur = 1;
        int count = 1;
        for (int i = 1; i < nums.length ; i++) {
            if(nums[i] == nums[i-1]) {
                count++;
            }else{
                count=1;
            }

            if(count <= 2){
                nums[cur++] = nums[i];
            }
        }
        return cur;
    }

    public static void main(String[] args) {
        int[] arr1 = {1,1,1,2,2,3};
        int[] arr2 = {0,0,1,1,1,1,2,3,3};
        System.out.println(removeDuplicates(arr1));
    }
}
```