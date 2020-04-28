# SingleNumbers
> [链接](https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-lcof/)
```
一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。

示例 1：
输入：nums = [4,1,4,6]
输出：[1,6] 或 [6,1]

示例 2：
输入：nums = [1,2,10,4,1,4,3,3]
输出：[2,10] 或 [10,2]
```
## 1. 解题思路一 位运算
### 1.1 思路
> 待补充[源于leetcode高赞(https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-lcof/solution/jie-di-qi-jiang-jie-fen-zu-wei-yun-suan-by-eddievi/)
### 1.2 具体实现
```java
public class Interview {
    public int[] singleNumbers(int[] nums) {
        if(nums.length == 0) return null;
        int total = 0;
        for (int num : nums) {
            total^=num;
        }
        int mark = 1;
        while ((total & mark) == 0) {
            mark <<= 1;
        }
        int a = 0;
        int b = 0;

        for(int num: nums) {
            if((num & mark) == 0) {
                a ^= num;
            } else {
                b ^= num;
            }
        }

        return new int[]{a, b};
    }
}

```