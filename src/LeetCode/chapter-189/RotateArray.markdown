# RotateArray
```
public class RotateArray {
    public static void rotate(int[] nums, int k) {
        for (int i = 0; i < k; i++) {
            rotate(nums,i,nums[nums.length-1]);
        }
    }
    private static void rotate(int[] nums, int index, int curNum){
        int pre = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = nums[i];
            nums[i] = pre;
            pre = temp;
        }
        nums[0] = curNum;
    }
    public static void main(String[] args) {
        int[] arr1 = {1,2,3,4,5,6,7};
        rotate(arr1, 3);
        for (int i : arr1) {
            System.out.println(i);
        }
    }
}
```