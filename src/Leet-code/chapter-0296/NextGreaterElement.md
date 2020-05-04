# NextGreaterElement
> [链接](https://leetcode-cn.com/problems/next-greater-element-i/)
```
给定两个 没有重复元素 的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。找到 nums1 中每个元素在 nums2 中的下一个比其大的值。
nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出 -1 。
 
示例 1:
输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
输出: [-1,3,-1]
解释:
    对于num1中的数字4，你无法在第二个数组中找到下一个更大的数字，因此输出 -1。
    对于num1中的数字1，第二个数组中数字1右边的下一个较大数字是 3。
    对于num1中的数字2，第二个数组中没有下一个更大的数字，因此输出 -1。

示例 2:
输入: nums1 = [2,4], nums2 = [1,2,3,4].
输出: [3,-1]
解释:
    对于 num1 中的数字 2 ，第二个数组中的下一个较大数字是 3 。
    对于 num1 中的数字 4 ，第二个数组中没有下一个更大的数字，因此输出 -1 。
```
## 1. 解题思路一 栈
### 1.1 思路
> 因为nums1 是 nums2 的子集，我们求出nums2每个元素的下一个最大值后将结果保存到Map中。创建栈用于查找下一个最大值，循环nums2，如果当前元素大于栈顶的元素，说明找到了栈顶的下一个最大值，将栈顶元素为key，最大值为value存入map中，将当前元素放入栈中寻找下一个最大值，且当前指针并不移动，如果小于栈顶元素则将当前元素入，移动指针继续循环。
### 1.2 具体实现
```java
public class Chapter {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // 保存nums2每个元素的下一个最大值
        HashMap<Integer,Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        // i的递增在循环体内部
        for (int i = 0; i < nums2.length;) {
            // 第一次为空
            if(stack.isEmpty()) {
                stack.push(nums2[i]);
                i++;
                continue;
            }
            // 栈顶元素
            Integer top = stack.peek();
            // 大于则找到了最大值
            if(nums2[i] > top) {
                // 存入map中，此时i并不一定，因为当前值可能是栈中元素的下一个最大值
                map.put(stack.pop(),nums2[i]);
            }else {
                // 小于则继续循环且i++；     
                stack.push(nums2[i]);
                i++;
            }
        }

        // 生成结果集
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            if(map.containsKey(nums1[i])) {
                res[i] = map.get(nums1[i]);
            }else {
                res[i] = -1;
            }
        }

        return res;
    }
}
```