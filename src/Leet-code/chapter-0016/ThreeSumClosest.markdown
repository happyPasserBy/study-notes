# ThreeSumClosest
> [链接](https://leetcode-cn.com/problems/3sum-closest/)
```
给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
例如，给定数组 nums = [-1，2，1，-4], 和 target = 1.
与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
```
## 1.解题思路一 三指针
### 1.1 思路
### 1.2 具体实现
```
public class Chapter {
    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int res = nums[0]+nums[1]+nums[nums.length-1];
        for (int i = 0; i < nums.length; i++) {
            int j = i+1;
            int k = nums.length-1;
            while (j < k){
                int curRes =  nums[i]+nums[j]+nums[k];
                if(Math.abs(res - target) > Math.abs(curRes - target)) {
                    res = curRes;
                }
                if(curRes == target) {
                    return curRes;
                }else if(curRes > target) {
                    k--;
                }else {
                    j++;
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] arr1 = {-1,2,1,-4};
        int target1 = 1;
        int[] arr2= {1,1,-1,-1,3};
        int target2 = -1;
        int[] arr3= {0,5,-1,-2,4,-1,0,-3,4,-5};
        int target3 = 1;
        System.out.println(threeSumClosest(arr1, target1));
    }
}

```

