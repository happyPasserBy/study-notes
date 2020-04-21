# CreateTargetArray
> [链接](https://leetcode-cn.com/problems/create-target-array-in-the-given-order/)
```
给你两个整数数组 nums 和 index。你需要按照以下规则创建目标数组：
目标数组 target 最初为空。
按从左到右的顺序依次读取 nums[i] 和 index[i]，在 target 数组中的下标 index[i] 处插入值 nums[i] 。
重复上一步，直到在 nums 和 index 中都没有要读取的元素。
请你返回目标数组。
题目保证数字插入位置总是存在。
 

示例 1：
输入：nums = [0,1,2,3,4], index = [0,1,2,2,1]
输出：[0,4,1,3,2]
解释：
nums       index     target
0            0        [0]
1            1        [0,1]
2            2        [0,1,2]
3            2        [0,1,3,2]
4            1        [0,4,1,3,2]
```
## 1. 解题思路一 ArrayList
### 1.1 思路
> 本题的困难在于"插入"，插入时需要位移后续元素，Java的ArrayList提供了此功能。
### 1.2 具体实现
```
class Solution {
    public int[] createTargetArray(int[] nums, int[] index) {
        ArrayList<Integer> res = new ArrayList(nums.length);

        for (int i = 0; i < index.length; i++) {
            res.add(index[i],nums[i]);
        }
        int[] resArr = new int[res.size()];

        for (int i = 0; i < res.size(); i++) {
            resArr[i] = res.get(i);
        };
        return resArr;
    }
}
```