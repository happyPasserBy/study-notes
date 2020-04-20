# FindDuplicates
> [链接](https://leetcode-cn.com/problems/find-all-duplicates-in-an-array/)
```
给定一个整数数组 a，其中1 ≤ a[i] ≤ n （n为数组长度）, 其中有些元素出现两次而其他元素出现一次。
找到所有出现两次的元素。
你可以不用到任何额外空间并在O(n)时间复杂度内解决这个问题吗？

示例：
输入:
[4,3,2,7,8,2,3,1]
输出:
[2,3]
```

## 1. 解题思路一 占位符
### 1.1 思路
> 根据题意得知1 ≤ a[i] ≤ n，我们可以创建额外数组temp,循环nums将nums[i]作为下标存储到temp中，如果当前下标锁存储的数字不为0说明该数字出现次数大于2。
### 1.2 具体实现 
```
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if(nums.length == 0)return res;

        int[] temp = new int[nums.length+1];

        for (int i = 0; i < nums.length; i++) {
            if(temp[nums[i]] != 0) {
                res.add(nums[i]);
            }else {
                temp[nums[i]] = nums[i];
            }
        }

        return res;
    }
}
```