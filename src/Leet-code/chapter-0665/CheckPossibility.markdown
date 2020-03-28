# CheckPossibility
> [链接](https://leetcode-cn.com/problems/non-decreasing-array/)
```
给你一个长度为 n 的整数数组，请你判断在 最多 改变 1 个元素的情况下，该数组能否变成一个非递减数列。
我们是这样定义一个非递减数列的： 对于数组中所有的 i (1 <= i < n)，总满足 array[i] <= array[i + 1]。

示例 1:
输入: nums = [4,2,3]
输出: true
解释: 你可以通过把第一个4变成1来使得它成为一个非递减数列。

示例 2:
输入: nums = [4,2,1]
输出: false
解释: 你不能在只改变一个元素的情况下将其变为非递减数列。
```
## 1.解题思路一 三指针
### 1.1 思路
> 循环数组判断i是否满足array[i] <= array[i+1]，当不满足时根据[i-1]、[i]、[i+1]对比那个需要改变。
### 1.2 具体实现
```
public class Chapter665 {
    public static boolean checkPossibility(int[] nums) {
        if(nums.length <= 1) return true;
        int count = 0;
        for (int i = 0; i < nums.length-1; i++) {
            if(nums[i] > nums[i+1]){
                if(i >= 1 && nums[i-1] >= nums[i+1]){
                    nums[i+1] = nums[i];
                }else{
                    nums[i] = nums[i+1];
                }
                count++;
            }
        }
        return count <= 1 ? true : false;
    }

    public static void main(String[] args) {
        int[] arr1 = {4,2,3};
        int[] arr2 = {4,2,1};
        int[] arr3 = {2,3,3,2,4};
        System.out.println(checkPossibility(arr2));

    }
}
```