# MajorityElement
> [链接](https://leetcode-cn.com/problems/find-majority-element-lcci/)
```
如果数组中多一半的数都是同一个，则称之为主要元素。给定一个整数数组，找到它的主要元素。若没有，返回-1。
示例 1：
输入：[1,2,5,9,5,9,5,5,5]
输出：5
 
示例 2：
输入：[3,2]
输出：-1
```
## 1. 解题思路一 投票器
### 1.1 具体实现
> 根据题意得知主要元素占数组的多一半，我们创建计数器count并循环数组，当遇到相同的元素则count++元素不同则count--，如果存在主要元素则count必定大于0。
### 1.2 具体实现
```
public class Interview {
    public int majorityElement(int[] nums) {
        if(nums.length < 1 )return 0;
        int count = 1;
        int target = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if(target == nums[i]) {
                count++;
            }else {
                count--;
                if(count == 0){
                    target = nums[i];
                    count = 1;
                }
            }
        }
        int t = nums.length / 2 + 1;
        count = 0;
        for (int num : nums) {
            if (num == target) count++;
            if (count >= t) return target;
        }
        return -1;
    }
}
```