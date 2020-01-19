# SubsetsWithDup
> 给定一个可能包含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。说明：解集不能包含重复的子集。[https://leetcode-cn.com/problems/subsets-ii/](链接)
## 1. 解题思路 回溯算法
### 1.1 思路
> 先画图。去重的关键在于理解排序，排序之后当遇到两个相同的元素可直接跳过，也不会出现大小值穿插现象如[1,2,3]、[3,2,1]，重复的原因就是第二个数据3后又出现的小于3的数，就有可能与之前的组合重复。

### 1.2 具体实现

```
public class SubsetsWithDup90 {

    private static List<List<Integer>> res = new ArrayList<>();
    private static boolean[] placeholder;

    private static void split(int[] nums, int start, ArrayDeque<Integer> list){
        res.add(new ArrayList<>(list));

        for (int i = start; i < nums.length; i++) {
            if(i > 0 && nums[i] == nums[i-1] && !placeholder[i-1])continue;
            placeholder[i] = true;
            list.addLast(nums[i]);
            split(nums,i+1,list);
            placeholder[i] = false;
            list.removeLast();
        }
    }
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        res.clear();
        placeholder = new boolean[nums.length];
        Arrays.sort(nums);
        split(nums,0,new ArrayDeque<>());
        return res;
    }
    public static void main(String[] args) {
        int[] arr1 = {1,2,2};
        System.out.println(subsetsWithDup(arr1));
    }
}

```


