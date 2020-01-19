# CombinationSum3
> 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。说明：所有数字都是正整数。解集不能包含重复的组合[链接](https://leetcode-cn.com/problems/combination-sum-iii/)

# 1. 解题思路一 回溯法
## 1.1 思路
> 做题顺序 39、40、216
## 1.2 具体实现
```
public class CombinationSum3 {

    private static List<List<Integer>> res = new ArrayList<>();

    private static void combination(int k, int n,int start, int sum, ArrayDeque<Integer> list){
        if(sum > n) return;
        if(list.size() > k) return;
        if(sum == n && list.size() == k) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int i = start; i < 10; i++) {
            list.addLast(i);
            combination(k,n,i+1,sum+i,list);
            list.removeLast();
        }
    }

    public static List<List<Integer>> combinationSum3(int k, int n) {
        res.clear();
        combination(k,n,1, 0,new ArrayDeque<>());
        return res;
    }

    public static void main(String[] args) {
        int k1 = 3;
        int n1 = 7;
        int k2 = 3;
        int n2 = 9;
        System.out.println(combinationSum3(k2, n2));

    }
}
```


