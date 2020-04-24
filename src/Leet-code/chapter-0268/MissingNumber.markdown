# MissingNumber
> [链接](https://leetcode-cn.com/problems/missing-number/)
## 1.解题思路一 高斯求和
### 1.1 思路
> 使用公式求除和0-n的和，再求出nums的和，求差
### 1.2 具体实现
```
public class MissingNumber {
    public static int missingNumber(int[] nums) {
        int total = (0+nums.length)*(nums.length+1)/2;
        int temp = 0;
        for (int i = 0; i < nums.length; i++) {
            temp+=nums[i];
        }
        return total-temp;
    }

    public static void main(String[] args) {
        int[] arr1 = {3,0,1};
        int[] arr2 = {9,6,4,2,3,5,7,0,1};
        System.out.println(missingNumber(arr2));
    }
}
```