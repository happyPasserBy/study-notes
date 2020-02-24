# DynamicProgramming
> 维基百科: 将原问题拆解成若干个子问题，同时保存子问题的答案，使得每个子问题只求解一次，最终获得原问题的答案

![](./images/dp_1.png)

1. 重叠子问题
> 再将一个问题拆分成若干个子问题时会发现有重复的子问题，在解决时需要将计算过的问题进行存储防止反复计算
2. 最优子结构
> 通过求子问题的最优解，可以获得原问题的最优解
3. 状态
> 定义里说将问题拆分成的子问题，这些问题就是状态
4. 状态转移
> 指的是如何将问题拆分成子问题或者说问题与问题之间的关系

## 1. 0-1背包问题
> 有一个背包，他的容量为C(Capacity)。现在有n中不同的物品，编号为0…n-1，其中每一件物品的重量为w(i)，价值为v(i)。问可以向这个背包中盛放哪些物品，使得在不超过背包容量的基础上，物品的总价值最大。
* 状态 F(n,C)
> 将n个物品放进容量为C的背包，使得价值最大
* 状态转移方程 F(i,c) = max( F(i-1,c), v(i) + F(i-1,c - w(i)))
> 针对某一物品有两种可能，
1. 不放入背包中 F(i-1,c) = 如果不放入当前物品的最大价值，
2. 放入背包中 v(i) + F(i-1, c - w(i)) = 如果放入当前物品的价值 + 剩余空间的最大价值
### 1.1 记忆优化搜索
```
public class Knapsack01 {
    private static int[][] memory;
    public static int bestValue(int[] w, int[] v, int index, int c) {
        if(index < 0 || c <= 0) return 0;

        if(memory[index][c] != 0) return memory[index][c];
        int res = bestValue(w, v,index-1, c);
        if(c >= w[index]) {
            res = Math.max(res, v[index] + bestValue(w,v,index-1, c-w[index]));
        }
        memory[index][c] = res;
        return res;
    }
    public static int knapsack01(int[] w, int[] v, int C) {
        memory = new int[w.length][C+1];
        return bestValue(w,v,w.length-1,C);
    }

    public static void main(String[] args) {
        int[][] arr1 = {{1,2,3},{6,10,12}};
        System.out.println(knapsack01(arr1[0], arr1[1], 7));
    }
}

```
### 1.2 动态规划

![](./images/knapsack01_1.png)

```
public class Knapsack01 {

    private static int[][] memory;

    public static int bestValue(int[] w, int[] v, int index, int c) {
        if(index < 0 || c <= 0) return 0;

        if(memory[index][c] != 0) return memory[index][c];
        int res = bestValue(w, v,index-1, c);
        if(c >= w[index]) {
            res = Math.max(res, v[index] + bestValue(w,v,index-1, c-w[index]));
        }
        memory[index][c] = res;
        return res;
    }

    public static int knapsack01(int[] w, int[] v, int C) {
        if(w.length != v.length || w.length == 0) return 0;
        int n = w.length;
        int[][] memory = new int[w.length][C+1];
        for (int i = 0; i <= C; i++) {
            memory[0][i] = i >= w[0] ? v[0] : 0;
        }
        for (int i = 1; i < n; i++ ) {
            for (int j = 0; j <= C; j++) {
                memory[i][j] = memory[i-1][j];
                if(j >= w[i]) {
                    memory[i][j] = Math.max(memory[i][j], v[i] + memory[i-1][j-w[i]]);
                }
            }
        }
        return memory[n-1][C];
    }

    public static void main(String[] args) {
        int[][] arr1 = {{1,2,3},{6,10,12}};
        System.out.println(knapsack01(arr1[0], arr1[1], 7));
    }
}
```
### 相似的背包问题
1. 完全背包问题、多重背包问题、多维费用问题、物品互斥问题

## 参考
1. https://coding.imooc.com/class/82.html
2. https://www.zhihu.com/question/23995189