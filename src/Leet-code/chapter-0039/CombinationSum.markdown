# CombinationSum
> 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。candidates 中的数字可以无限制重复被选取。说明：所有数字（包括 target）都是正整数。解集不能包含重复的组合。 [链接](https://leetcode-cn.com/problems/combination-sum/)

# 1. 解题思路一 回溯法
### 1.1 思路
> 本题的关键在于去重，
### 具体实现
```
public class CombinationSum {

    private static List<List<Integer>> res = new ArrayList<>();

    private static void combination(int[] candidates, int target, int start,int nowSum, Deque<Integer> list){
        if(nowSum > target) return;
        if(nowSum == target) {
            res.add(new ArrayList<>(list));
        }
        for (int i = start; i < candidates.length; i++) {
            list.addLast(candidates[i]);
            combination(candidates,target,i,nowSum+candidates[i],list);
            list.removeLast();
        }
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        res.clear();
        Arrays.sort(candidates);
        combination(candidates,target,0, 0,new ArrayDeque<>());
        return res;
    }

    public static void main(String[] args) {
        int[] nums1 = {2,3,6,7};
        int target1 = 7;
        int[] nums2 = {2,3,5};
        int target2 = 8;
        int[] nums3 = {1,2};
        int target3 = 1;
        System.out.println(combinationSum(nums1, target1));
    }
}
```


## 参考
1. https://leetcode-cn.com/problems/combination-sum/solution/hui-su-suan-fa-jian-zhi-python-dai-ma-java-dai-m-2/
