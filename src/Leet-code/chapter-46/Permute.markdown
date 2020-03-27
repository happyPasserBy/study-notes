# Permute
> 给定一个没有重复数字的序列，返回其所有可能的全排列。 [链接](https://leetcode-cn.com/problems/permutations/)
## 1. 解题思路一 回溯法
### 1.1 思路
![图示](./images/tree.png)
> 本题的解题思路与[此题](https://github.com/happyPasserBy/study-notes/blob/master/src/LeetCode/chapter-17/LetterCombinations.markdown)思路大体一致,就不详细解释了，我大致说一下思路

* 首先根据题中的 1、2、3的画出树状图
* 根据图中的线路找到循环体与循环结束条件
* 额，写不出还有啥了，这道题与LeetCode-17题相似度估计在80%以上，强烈建议先解决[17](https://github.com/happyPasserBy/study-notes/blob/master/src/LeetCode/chapter-17/LetterCombinations.markdown)题

### 1.2 具体实现
```
public class Permute {

    private static List<List<Integer>> ret = new ArrayList<>();

    private static boolean[] used;

    private static void combine( int[] nums, int index,  List<Integer> p){
        if(p.size() == nums.length) {
            ret.add(p);
        }
        for (int i = 0; i < nums.length; i++) {
            if(used[i]) continue;
            p.add(nums[i]);
            used[i] = true;
            combine(nums,index+1,new ArrayList<>(p));
            p.remove(p.size()-1);
            used[i] = false;
        }
    }

    public static List<List<Integer>> permute(int[] nums) {
        ret.clear();
        used = new boolean[nums.length];
        if(nums.length == 0) return ret;
        combine(nums,0,new ArrayList<>());
        return ret;
    }
    public static void main(String[] args) {
        int[] arr = {1,2,3};
        System.out.println(permute(arr));
    }
}
```

## 参考
1. 图片来源于[链接](https://leetcode-cn.com/problems/permutations/solution/hui-su-suan-fa-python-dai-ma-java-dai-ma-by-liweiw/)