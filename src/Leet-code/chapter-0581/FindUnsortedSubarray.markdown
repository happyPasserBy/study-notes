# FindUnsortedSubarray
> 给定一个整数数组，你需要寻找一个连续的子数组，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
你找到的子数组应是最短的，请输出它的长。[链接](https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/)
```
示例 1:

输入: [2, 6, 4, 8, 10, 9, 15]
输出: 5
解释: 你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序。

说明 :
输入的数组长度范围在 [1, 10,000]。
输入的数组可能包含重复元素 ，所以升序的意思是<=。
```
## 1解题思路一 排序+对比
### 1.1思路
> 先排序，然后对比两个数组的差异
### 1.2 具体实现
```
class Solution {
    public int findUnsortedSubarray(int[] nums) {
        if(nums.length == 0)return 0;
        int[] arr = new int[nums.length];

        System.arraycopy(nums,0,arr,0,nums.length);
        Arrays.sort(arr);
        int start = -1,end = -1;

        for (int i = 0; i < nums.length; i++) {
            if(start == -1 && nums[i] != arr[i]){
                start = i;
            }
            if(nums[i] != arr[i]){
                end = i;
            }
        }
        return start == -1 ? 0 : end-start+1;
    }
}
```