# FindShortestSubArray
> [链接](https://leetcode-cn.com/problems/degree-of-an-array/)
```
给定一个非空且只包含非负数的整数数组 nums, 数组的度的定义是指数组里任一元素出现频数的最大值。
你的任务是找到与 nums 拥有相同大小的度的最短连续子数组，返回其长度。

示例 1:
输入: [1, 2, 2, 3, 1]
输出: 2
解释: 
输入数组的度是2，因为元素1和2的出现频数最大，均为2.
连续子数组里面拥有相同度的有如下所示:
[1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
最短连续子数组[2, 2]的长度为2，所以返回2.
```
## 1.解题思路一 记录边界
### 1.1 思路
> 创建3个HashMap(map,left,right),以nums的值为key,但value分别记录元素出现的次数与元素的左右边界，循环map找出长度最小的子数组
### 1.2 具体实现
```
class Solution {
    public int findShortestSubArray(int[] nums) {
        HashMap<Integer,Integer> map = new HashMap<>();
        HashMap<Integer,Integer> left = new HashMap<>();
        HashMap<Integer,Integer> right = new HashMap<>();
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if(map.containsKey(nums[i])) {
                map.put(nums[i],map.get(nums[i])+1);
                right.put(nums[i],i);
            }else {
                map.put(nums[i],1);
                left.put(nums[i],i);
                right.put(nums[i],i);
            }
            max = Math.max(max, map.get(nums[i]));
        }
        int length = nums.length+1;
        for (Integer integer : map.keySet()) {
            if(map.get(integer) != max) continue;
            length = Math.min(length,right.get(integer) - left.get(integer)+1);
        }
        return length;
    }
}

```