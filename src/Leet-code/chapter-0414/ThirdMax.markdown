# ThirdMax
> [链接](https://leetcode-cn.com/problems/third-maximum-number/)
## 1.解题思路 三指针
### 1.1 思路
> 创建三个指针分别记录前三个大数，循环比较每个数
### 1.2 具体实现
```
public class ThirdMax {

    public static int thirdMax(int[] nums) {
        int max = nums[0];
        long two = Long.MIN_VALUE;
        long three = Long.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] == max || nums[i] == two || nums[i] == three)continue;
            if(nums[i] > max) {
                three = two;
                two = max;
                max = nums[i];
            }else if(nums[i] > two){
                three = two;
                two = nums[i];
            }else if(nums[i] > three){
                three = nums[i];
            }
        }
        if(three ==  Long.MIN_VALUE) return max;
        return (int)three;
    }

    public static void main(String[] args) {
        int[] arr1 = {3, 2, 1};
        int[] arr2 = {1, 2};
        int[] arr3 = {2, 2, 3, 1};
        int[] arr4 = {5,2,4,1,3,6,0};
        System.out.println(thirdMax(arr4));

    }
}
```
