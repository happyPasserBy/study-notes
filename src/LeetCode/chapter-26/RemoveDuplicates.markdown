# remove duplicates
> 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
[链接](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array)
## 1. 解题思路一 双指针
### 1.1 思路
> 创建k、i两个指针，k指向的元素及其之前的元素永远不重复，i根据循环遍历每个元素与k比较,总结为 k与i比较，k+1替换。
### 1.2 具体实现
```
public class RemoveDuplicates {

    public static int removeDuplicates(int[] nums) {

        // 简单判断
        if(nums.length <= 1) {
            return nums.length;
        }
        int k = 0;// 起始元素
        int i = 1;// 起始待比较元素

        // 简单判断
        if(nums.length == 2 && nums[k] == nums[i]) {
            return 1;
        }

        // i 为待比较元素，i大于length说明全部比较完成
        while(i < nums.length) {

            if(nums[k] != nums[i] && i-k == 1){ // i-k == 1 说明k与i之间没有重复元素，可直接 ++
                k++;
                i++;
            }else if(nums[k] == nums[i]){ // 出现重复元素，因k指向的是唯一元素则k不变，i++移动元素进行下一次比较
                i++;
            }else if(nums[k] != nums[i] && i-k != 1){ // 元素不相等并且i-k != 1，说明中间包含重复元素需要替换
                nums[k+1] = nums[i];
                i++;
                k++;
            }
        }
        // 因为k起始为0需+1
        return k+1;
    }

    public static void main(String[] args) {
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        System.out.println("length:"+removeDuplicates(nums));
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }
}
```
