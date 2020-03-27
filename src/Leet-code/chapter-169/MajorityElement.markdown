## MajorityElement
> 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。你可以假设数组是非空的，并且给定的数组总是存在多数元素。[链接](https://leetcode-cn.com/problems/majority-element/)
```
示例 1:
输入: [3,2,3]
输出: 3

示例 2:
输入: [2,2,1,1,1,2,2]
输出: 2
```
## 1.解题思路一 hash表
### 1.1 思路
> 维护一个数字与出现次数的对应关系，循环数组统计出现此处筛选出最大值即可
### 1.2 具体实现
```
public class MajorityElement169 {
    public static int majorityElement(int[] nums) {
        int max = 0;
        int num = 0;
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer cur = map.get(nums[i]);
            if(cur != null) {
                cur += 1;
                map.put(nums[i],cur +1);
            }else{
                cur = 1;
                map.put(nums[i],cur);

            }
            if(cur > max) {
                max = cur;
                num = nums[i];
            }
        }
        return num;
    }
}
```




## 2. 解题思路二 投票
### 2.1 思路
> 根据题目知道数组中必定存在一个大于⌊ n/2 ⌋的元素。维护一个计数器与变量candidate存储元素，循环数组，遇到相同元素计数器+1，不同的元素计数器-1，计数器为0时更换统计元素candidate，遍历结束candidate值就是答案(思路来源于官方)
### 2.2 具体实现
```
class Solution {
    public int majorityElement(int[] nums) {
        int count = 0;
        Integer candidate = null;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }
}

```