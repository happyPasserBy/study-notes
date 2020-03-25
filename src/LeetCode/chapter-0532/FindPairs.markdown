# FindPairs
> [链接](https://leetcode-cn.com/problems/k-diff-pairs-in-an-array/)
```
给定一个整数数组和一个整数 k, 你需要在数组里找到不同的 k-diff 数对。这里将 k-diff 数对定义为一个整数对 (i, j), 其中 i 和 j 都是数组中的数字，且两数之差的绝对值是 k.

示例 1:
输入: [3, 1, 4, 1, 5], k = 2
输出: 2
解释: 数组中有两个 2-diff 数对, (1, 3) 和 (3, 5)。
尽管数组中有两个1，但我们只应返回不同的数对的数量。

示例 2:
输入:[1, 2, 3, 4, 5], k = 1
输出: 4
解释: 数组中有四个 1-diff 数对, (1, 2), (2, 3), (3, 4) 和 (4, 5)。
```
## 1. 解题思路一 排序过滤重复
### 1.1 思路
> 先排序，将相同数字聚集到一起，再循环时跳过重复数字
### 1.2 具体实现
```
public class FindPairs {
    public static int findPairs(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if(i > 0 && nums[i] == nums[i-1])continue;
            for (int j = i+1; j < nums.length; j++) {
                if( j > i+1 && nums[j] == nums[j-1])continue;
                if(Math.abs(nums[i] - nums[j]) > k) break;
                if(Math.abs(nums[i] - nums[j]) == k) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] arr1 = {3, 1, 4, 1, 5};
        int target1 = 2;
        int[] arr2 = {1, 2, 3, 4, 5};
        int target2 = 1;
        int[] arr3 = {1, 3, 1, 5, 4};
        int target3 = 0;
        int[] arr4 = {1, 1, 1, 1, 1};
        int target4 = 0;
        System.out.println(findPairs(arr2,target2));
    }
}
```