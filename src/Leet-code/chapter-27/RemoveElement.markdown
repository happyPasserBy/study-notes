# RemoveElement
>[链接](https://leetcode-cn.com/problems/remove-element/)
```
给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。

示例 1:
给定 nums = [3,2,2,3], val = 3,
函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
你不需要考虑数组中超出新长度后面的元素。

示例 2:
给定 nums = [0,1,2,2,3,0,4,2], val = 2,
函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。
注意这五个元素可为任意顺序。
你不需要考虑数组中超出新长度后面的元素。
```
## 1.解题思路一 双指针
### 1.1 思路
> 创建两个指针one、two,one指向待替换元素，two指向不为val的元素
### 1.2 具体实现
```
public class RemoveElement27 {
    public static int removeElement(int[] nums, int val) {
        if(nums.length == 0)return 0;
        if(nums.length == 1 && nums[0] == val)return 0;
        int one = 0;
        int two = 0;
        while (two < nums.length){
            if(nums[one] == val && nums[two] == val) {
                two++;
            }else if(nums[one] == val && nums[two] != val){
                nums[one] = nums[two];
                nums[two] = val;
                one++;
                two++;
            }else {
                one++;
                two++;
            }
        }
        return one;
    }

    public static void main(String[] args) {
        int[] arr1 = {3,2,2,3};
        int val1 = 3;
        int[] arr2 = {0,1,2,2,3,0,4,2};
        int val2 = 2;
        System.out.println(removeElement(arr2, val2));
    }
}
```
