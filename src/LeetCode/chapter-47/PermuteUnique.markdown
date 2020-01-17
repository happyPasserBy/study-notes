# PermuteUnique
> 给定一个可包含重复数字的序列，返回所有不重复的全排列。[链接](https://leetcode-cn.com/problems/permutations-ii/)

## 1.解题思路一 回溯法
### 1.1 思路
> 待完善
### 1.2 具体实现
```
class Solution {
    // 结果集
    private static List<List<Integer>> res = new ArrayList<>();
    // 占位数组 用于判断该位置上的数字是否使用过
    private static boolean[] perch;

   private static void permute(int[] nums,List<Integer> list){
        if(list.size() == nums.length) {
            res.add(new ArrayList<>(list));
        }
        for (int i = 0; i < nums.length; i++) {
            if(perch[i])continue;

            // nums[i] == nums[i-1] 判断当前与上一个是否相同，如果相同则结果集必定会出现重读。
            // !perch[i-1] 判断如果相同且上一个未被使用则会重复。
            if(i > 0 && nums[i] == nums[i-1] && !perch[i-1])continue;
            List<Integer> temp = new ArrayList<>(list);
            temp.add(nums[i]);
            perch[i] = true;
            permute3(nums,temp);
            perch[i] = false;
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        res.clear();
        perch =  new boolean[nums.length];
        // 去重的关键: 使用排序跳过相同的数字
        Arrays.sort(nums);
        permute(nums,new ArrayList<>());
        return res;
    }
}
```






