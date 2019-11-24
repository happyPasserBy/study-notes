# MinSubArrayLen
> 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组。如果不存在符合条件的连续子数组，返回 0。
[链接](https://leetcode-cn.com/problems/minimum-size-subarray-sum/)
## 1.解题思路一 暴力循环 O(n^2)
### 1.1 思路
> 使用双重循环依次对比每个元素
### 1.2 具体实现
```
待实现
```
### 2. 解题思路二 动态滑窗
### 1.2 思路
> 设置i、j，两个指针，i为头指针，j为尾指针，统计两指针之和是否符合题目要求，当和大于要求值则i++缩小范围，当小于要求值时则j++增大范围，直到i指针大于数组长度则遍历完毕，
i、j两指针在数组中像一个窗口来回滑动因为称为动态滑窗。
### 1.3 具体实现
```
public class MinSubArrayLen {
    // 滑动窗口
    public static int minSubArrayLen(int s, int[] nums){
        if(nums.length == 0){
            return 0;
        }
        int i = 0;
        int j = -1;
        int add = 0;
        int result = nums.length+1; // 设置固定值方便后续比较
        while (i<nums.length) {
            if(j+1 < nums.length && add < s){ 
                add += nums[++j];
            } else {//踩坑记录，每次循环i/j其中必定有一个进行++
                add -= nums[i];
                i++;
            }
            if(add>=s) {
                result = result > j-i+1 ? j-i+1 : result;
            }
        }
        return result == nums.length+1 ? 0 : result;
    }

    public static void main(String[] args) {
        int[] arr = {2,3,1,2,4,3};
        System.out.println(minSubArrayLen(7,arr));
    }
}
```
滑动窗口相似题目 438、76