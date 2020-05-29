# NumArray
![链接](https://leetcode-cn.com/problems/range-sum-query-immutable/)
```
给定一个整数数组  nums，求出数组从索引 i 到 j  (i ≤ j) 范围内元素的总和，包含 i,  j 两点。

示例：
给定 nums = [-2, 0, 3, -5, 2, -1]，求和函数为 sumRange()
sumRange(0, 2) -> 1
sumRange(2, 5) -> -1
sumRange(0, 5) -> -3
说明:
你可以假设数组不可变。
会多次调用 sumRange 方法。
```
## 解题思路一

```java
class NumArray {

    private int[] nums;

    private int[] cache;

    public NumArray(int[] nums) {
        this.nums = nums;
        // 创建缓存
        cache = new int[nums.length+1];
        // 记录每个节点从0到i的和
        for (int i = 0; i < nums.length; i++) {
            // [i+1] 是方便统计sumRange(0, 2)这样的计算
            cache[i+1] = cache[i]+nums[i];
        }
    }
    public int sumRange(int i, int j){
        // 计算差值
        return cache[j+1] - cache[i];
    }
}
```