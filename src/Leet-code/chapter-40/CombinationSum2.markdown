# CombinationSum2
> 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。candidates 中的每个数字在每个组合中只能使用一次。说明：所有数字（包括目标数）都是正整数。解集不能包含重复的组合。 [链接](https://leetcode-cn.com/problems/combination-sum-ii/)

# 1.解题思路

## 1.1 思路

## 1.2 具体实现
```
public class CombinationSum2 {
    private static List<List<Integer>> res = new ArrayList<>();

    private static boolean[] placeholder;

    private static void combination(int[] candidates, int target, int start,int nowSum, Deque<Integer> list){
        if(nowSum > target) return;
        if(nowSum == target) {
            res.add(new ArrayList<>(list));
        }
        for (int i = start; i < candidates.length; i++) {
            if(placeholder[i])continue;
            if(i > 0 && candidates[i] == candidates[i-1] && !placeholder[i-1])continue; // 关键
            list.addLast(candidates[i]);
            placeholder[i] = true;
            combination(candidates,target,i,nowSum+candidates[i],list);
            placeholder[i] = false;
            list.removeLast();
        }
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        res.clear();
        Arrays.sort(candidates);
        placeholder = new boolean[candidates.length];
        combination(candidates,target,0, 0,new ArrayDeque<>());
        return res;
    }

    public static void main(String[] args) {
        int[] nums1 = {10,1,2,7,6,1,5};
        int target1 = 8;
        int[] nums2 = {2,5,2,1,2};
        int target2 = 5;
        System.out.println(combinationSum2(nums2, target2));

    }
}
```

