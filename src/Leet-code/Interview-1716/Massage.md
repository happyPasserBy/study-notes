# Massage
> [链接](https://leetcode-cn.com/problems/the-masseuse-lcci/)
```
一个有名的按摩师会收到源源不断的预约请求，每个预约都可以选择接或不接。在每次预约服务之间要有休息时间，因此她不能接受相邻的预约。给定一个预约请求序列，替按摩师找到最优的预约集合（总预约时间最长），返回总的分钟数。
注意：本题相对原题稍作改动

示例 1：
输入： [1,2,3,1]
输出： 4
解释： 选择 1 号预约和 3 号预约，总时长 = 1 + 3 = 4。

示例 2：
输入： [2,7,9,3,1]
输出： 12
解释： 选择 1 号预约、 3 号预约和 5 号预约，总时长 = 2 + 9 + 1 = 12。

示例 3：
输入： [2,1,4,5,3,1,1,3]
输出： 12
解释： 选择 1 号预约、 3 号预约、 5 号预约和 8 号预约，总时长 = 2 + 4 + 3 + 3 = 12。

```
## 1. 解题思路一 动态规划
### 1.1 具体实现
```java
class Solution {
    public int massage(int[] nums) {
        int n = nums.length;
        // 如果长度小于2直接返回
        if(n == 0) {
            return 0;
        }else if(n == 1){
            return nums[0];
        }else if(n == 2){
            return Math.max(nums[0],nums[1]);
        }

        int[] memo = new int[nums.length];
        memo[0] = nums[0];
        /*
        dp(i) 就是求出nums[i]的最大值，
        而dp(i) = max(dp(i-1),db(i-2)+nums[i]),也就是想要求出n的最大值需要求出i-1的最大值与i-2的最大值
        
        重叠子问题: 每当求i时都需要求出i-1与i-2的值，这些值都可以缓存
        最优子结构: max(dp(i-1),db(i-2)+nums[i])
        状态：db(i)就是[0,i]的最大值
        状态转移方程: dp(i) = max(dp(i-1),db(i-2)+nums[i])
        */
        
        for(int i = 1 ; i < n ; i++){
            memo[i] = Math.max(memo[i -1], nums[i] + (i - 2 >= 0 ? memo[i - 2] : 0));
        }
        return memo[n-1];
    }
}
```