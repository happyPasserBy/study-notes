# ContainsNearbyDuplicate
> 给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，使得 nums [i] = nums [j]，并且 i 和 j 的差不超过 k。
[链接](https://leetcode-cn.com/problems/contains-duplicate-ii/)

## 1.解题思路一 暴力解法O(n^2)
### 1.1思路
> 双重循环，找到相同值判断差
## 2.解题思路二 动态滑窗+查找表
### 2.1 思路
> 创建一个长度为k的滑窗,且滑窗内容全部存储到查找表中保持唯一性，循环判断下一个元素是否在查找表中
### 2.2 具体实现
```
public class ContainsNearbyDuplicate {
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; ++i) {
            if (set.contains(nums[i])) return true;
            set.add(nums[i]);
            if (set.size() > k) {
                set.remove(nums[i - k]);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr1 = {1,2,3,1};//3 true
        int[] arr2 = {1,0,1,1};//1 true
        int[] arr3 = {1,2,3,1,2,3};//2 false
        int[] arr4 = {-1,-1};//1 false
        System.out.println(containsNearbyDuplicate(arr4, 1));
    }
}
```