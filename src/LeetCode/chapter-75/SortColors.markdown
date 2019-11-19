# sort colors
## 1.思路-计数排序
```
public class SortColors {
    public static void sortColors(int[] nums) {
        if(nums.length <= 1) {
            return;
        }
        int red = 0;
        int white = 0;
        int blue = 0;

        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) {
                red ++;
            }else if(nums[i] == 1) {
                white++;
            }else {
                blue++;
            }
        }
        for (int i = 0; i < nums.length; i++) {
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