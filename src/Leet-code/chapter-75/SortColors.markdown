# sort colors
>给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色
[链接](https://leetcode-cn.com/problems/sort-colors)
## 1.解题方式一 计数排序
### 1.1 思路
> 根据颜色种类分组计数，然后重新填充到原数组
### 1.2 具体实现
```
public class SortColors {
    public static void sortColors(int[] nums) {
        if(nums.length <= 1) {
            return;
        }
        // 此处可替换为数组
        int red = 0;
        int white = 0;
        int blue = 0;

        for (int i = 0; i < nums.length; i++) {
            // 根据数组下标特性累计值
            if(nums[i] == 0) {
                red ++;
            }else if(nums[i] == 1) {
                white++;
            }else {
                blue++;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            // 根据数组下标特性替换值
            if(i<red) {
                nums[i] = 0;
            }else if(i >= red && i < red+white){
                nums[i] = 1;
            }else{
                nums[i] = 2;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,0};
        sortColors(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }
}
```