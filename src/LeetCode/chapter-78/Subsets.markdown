# Subsets
> 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。解集不能包含重复的子集。[链接](https://leetcode-cn.com/problems/subsets/)

## 1. 解题思路
### 1.1 思路
> 先画图。去重的关键就是排序

### 1.2 具体实现
```
public class Subsets {

    private static List<List<Integer>> res = new ArrayList<>(new ArrayList<>());

    private static void split(int[] nums, int start, ArrayDeque<Integer> list){
        res.add(new ArrayList<>(list));
        for (int i = start; i < nums.length; i++) {
            list.addLast(nums[i]);
            split(nums,i+1,list);
            list.removeLast();
        }
    }

    public static List<List<Integer>> subsets(int[] nums) {
        res.clear();
        if(nums.length == 0) return res;
        Arrays.sort(nums);
        split(nums,0,new ArrayDeque<>());
        return res;
    }

    public static void main(String[] args) {
        int[] arr1 = {1,2,3};
        System.out.println(subsets(arr1));

    }
}
```

