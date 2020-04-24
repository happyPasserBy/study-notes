# FourSum
> 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。注意：答案中不可以包含重复的四元组。[链接](https://leetcode-cn.com/problems/4sum/)

## 1. 解题思路一 排序+对撞指针
### 1.1 思路
> 本体是leetcode-15题的变种，从求三数之和到四数之和，解题思路的核心完全一致，先排序，创建2个循环指针(i、j),创建两个对撞指针(left、right)依次循环，具体的就不细讲了，建议理解15题，在看本解题思路
### 1.2 具体实现
```
public class FourSum18 {
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ret = new ArrayList<>();
        if(nums.length == 0)return ret;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            // 去重，nums[i]==nums[i-1]的原因是i之后的相同值可以重复使用，但i之前的在下方的对撞指针扫描时必定使用过
            if(i > 0 && nums[i] == nums[i-1]) continue;
            for (int j = i+1; j < nums.length; j++) {
                // 去重
                if(j != i+1 && nums[j] == nums[j-1]) continue;
                int left = j+1;
                int right = nums.length-1;
                if( left >= nums.length || left >= right) continue;
                while (left < right){
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if(sum == target) {
                        ret.add(Arrays.asList(nums[i],nums[j],nums[left],nums[right]));
                        // 此处去重原因为i不变，如果left不去重，right值也必定不变导致结果重复
                        while (left<right && nums[right] == nums[right-1])right--;
                        while (left<right && nums[left] == nums[left+1])left++;
                        left++;
                        right--;
                    }
                    // 因排序，最大值在最右侧，>0 则 right-- 缩小数字和
                    if(sum > target) {
                        right--;
                    }
                    // 因排序，最小值在最左侧，<0 则 left++ 增大数字和
                    if(sum < target) {
                        left++;
                    }
                }
            }
        }
        return ret;
    }
    public static void main(String[] args) {
       int[] nums1 = {1, 0, -1, 0, -2, 2};
       int target1 = 0;
       int[] nums2 = {1,-2,-5,-4,-3,3,3,5};
       int target2 = -11;
        System.out.println(fourSum(nums1, target1));
    }
}
```



