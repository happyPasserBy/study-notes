# MissingNumber
> [链接](https://leetcode-cn.com/problems/missing-number-lcci/)
```
数组nums包含从0到n的所有整数，但其中缺了一个。请编写代码找出那个缺失的整数。你有办法在O(n)时间内完成吗？
注意：本题相对书上原题稍作改动

示例 1：
输入：[3,0,1]
输出：2
```
## 1. 解题思路一 高斯求和
## 2. 解题思路二 异或
### 2.1 思路(来源于leet code高赞)
> 巧用异或(相同为0不同为1)，根据异或的特性数字n对相同数字异或一次为0异或两次为本身，循环数组使i异或数组当前值直到结束，这样就能找出缺失的数字
### 2.2 具体实现
```
public class Interview {
    public static int missingNumber(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; ++i) {
            res ^= i;
            res ^= nums[i];
        }
        res ^= nums.length;

        return res;
    }
    public static void main(String[] args) {
        int[] arr1 = {3,0,1};
        System.out.println(missingNumber(arr1));
    }
}
```