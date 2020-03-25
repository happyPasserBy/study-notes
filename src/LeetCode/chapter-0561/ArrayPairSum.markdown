# ArrayPairSum
> [链接](https://leetcode-cn.com/problems/array-partition-i/)
```
给定长度为 2n 的数组, 你的任务是将这些数分成 n 对, 例如 (a1, b1), (a2, b2), ..., (an, bn) ，使得从1 到 n 的 min(ai, bi) 总和最大。

示例 1:
输入: [1,4,3,2]
输出: 4
解释: n 等于 2, 最大总和为 4 = min(1, 2) + min(3, 4).
```
## 1. 解题思路一 排序
### 1.1 思路
> 没啥可说的.....
### 1.2 具体实现
```
public class ArrayPairSum {
    public static int arrayPairSum(int[] nums) {
        if(nums.length == 0) return 0;
        Arrays.sort(nums);
        int total = 0;
        for (int i = 0; i < nums.length-1;) {
            total+=Math.min(nums[i],nums[i+1]);
            i+=2;
        }
        return total;
    }

    public static void main(String[] args) {
        int[] arr1 = {1,4,3,2};
        System.out.println(arrayPairSum(arr1));
    }
}
```
