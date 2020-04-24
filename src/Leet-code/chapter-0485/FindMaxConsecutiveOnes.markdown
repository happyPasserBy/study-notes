# findMaxConsecutiveOnes
>[链接](https://leetcode-cn.com/problems/max-consecutive-ones/)
```
给定一个二进制数组， 计算其中最大连续1的个数。

示例 1:
输入: [1,1,0,1,1,1]
输出: 3
解释: 开头的两位和最后的三位都是连续1，所以最大连续1的个数是 3.

注意：
输入的数组只包含 0 和1。
输入数组的长度是正整数，且不超过 10,000。
```
## 1.解题思路一 循环计数
```
public class FindMaxConsecutiveOnes {
    public static int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;
        int count= 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == 1) {
                count++;
            }
            max = Math.max(max, count);
            if(nums[i] == 0) {
                count = 0;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] arr1 = {0,1,1,1,1,0,1,1,1,0,1};
        int[] arr2 = {1,1,0,1,1,1};
        System.out.println(findMaxConsecutiveOnes(arr2));
    }
}

```