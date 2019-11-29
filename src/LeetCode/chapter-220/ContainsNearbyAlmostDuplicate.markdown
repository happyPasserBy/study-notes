# ContainsNearbyAlmostDuplicate 
> 给定一个整数数组，判断数组中是否有两个不同的索引 i 和 j，使得 nums [i] 和 nums [j] 的差的绝对值最大为 t，并且 i 和 j 之间的差的绝对值最大为 ķ。
[链接](https://leetcode-cn.com/problems/contains-duplicate-iii/)
## 1.解题思路一 暴力解法
### 1.1 思路
> 双重循环
## 2.解题思路二 滑动窗口+二叉树
### 2.1 思路
> 使用动态滑窗保持查找属于的范围<=ķ,使用二叉树保存动态滑窗数据，循环数组在二叉树中查找符合nums[i]-t <= nums[i]+t
### 2.2 具体实现
```
public class ContainsNearbyAlmostDuplicate {
    public static boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if(nums.length < 2 || k == 0) return false;
        TreeSet<Long> treeSet = new TreeSet<>(); // long防止int溢出
        for (int i = 0; i < nums.length; i++) {
            if(treeSet.ceiling((long)nums[i]-(long)t) != null &&
                    treeSet.ceiling((long)nums[i]-(long)t) <= (long)nums[i]+(long)t)return true;
            treeSet.add((long)nums[i]);
            if(treeSet.size() > k){
                treeSet.remove((long)nums[i - k]);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] nums = {0,2147483647};
        int k = 1;
        int t = 2147483647;
        System.out.println(containsNearbyAlmostDuplicate(nums,k,t));
    }
```