# FindLengthOfLCIS
>[链接](https://leetcode-cn.com/problems/longest-continuous-increasing-subsequence/)
## 1. 解题思路一 计数
### 1.1 思路
> 没什么可说的，循环计数即可
### 1.2 具体实现
```
class Solution {
    public int findLengthOfLCIS(int[] nums) {
        if(nums.length == 0) return 0;
        if(nums.length == 1) return 1;
        int count = 1;
        int max = 0;
        for (int i = 0; i < nums.length-1; i++) {
            if(nums[i] < nums[i+1]) {
                count++;
            }else {
                max = Math.max(max,count);
                count = 1;
            }
        }
        
        return Math.max(max,count);
    }
}
```

