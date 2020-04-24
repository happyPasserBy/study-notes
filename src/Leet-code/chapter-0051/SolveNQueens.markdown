# SolveNQueens
> n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击

![8queen](./images/8-queens.png)

>上图为 8 皇后问题的一种解法。
给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。[链接](https://leetcode-cn.com/problems/n-queens/)
```
示例:

输入: 4
输出: [
 [".Q..",  // 解法 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // 解法 2
  "Q...",
  "...Q",
  ".Q.."]
]
解释: 4 皇后问题存在两个不同的解法
```
## 1.解题思路 回溯法
### 1.1 思路
> 先画图，但这次与之前的图有点差别，请看

![](./images/4-queens_1.png)
> 先画出n*n个格子并且以左上角第一个各自为起始，坐标为0,0。根据题意我们知道只要每列不出现重复的“Q”,且每个格子的对角线也不出现重复的Q就可以求出解，因此我们画出格子的对角线尝试解题。
![](./images/4-queens_2.png)
![](./images/4-queens_3.png)
> 对比两种颜色的对角线我们可以发现一些规律
1. 每种颜色的对角线条数为2*n-1
2. 红色对角线多经过格子的和为两坐标相加，如(0,0)=0,(0,1)=(1,0)=1=(x+y)
3. 紫色对角线每个格子内的数字相减存在n到-n之间,如(x-y)=(0,3)<-n , n>(3,0)=(x-y)
> 使用回溯算法尝试在每行且每个格子创建Q并符合每列于每条对角线的要求即可
1. 创建长度为n的boolean数组用于判断每列上是否存在Q，当创建Q时将当前列标记为true用于后续判断
2. 创建长度为2n-1的红色对角线boolean数组，根据红色对角线的规律可以计算出当前格子的对角线是否存在Q
2. 创建长度为2n-1的紫色对角线boolean数组，因为数组下标从0开始所以将紫色对角线的结果转换为从0开始即 (x-y)+n-1,根据公式可以计算出当前格子的对角线是否存在Q
### 1.2 具体实现
```
public class SolveNQueens2 {

    private static List<List<String>> res = new ArrayList<>();

    // 每列占位数组
    private static boolean[] col;

    // 红色对角线数组 
    private static boolean[] angle1;
    // 紫色对角线数组
    private static boolean[] angle2;

    // 结果辅助函数，生成字符串
    private static List<String> generateBoard(int n, ArrayDeque<Integer> row) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Integer QIndex = row.pollFirst();
            StringBuilder stringBuilder = new StringBuilder();
            for(int j = 0; j < n; j++) {
                if(j != QIndex) {
                    stringBuilder.append(".");
                }else {
                    stringBuilder.append("Q");
                }
            }
            list.add(stringBuilder.toString());
        }
        return list;
    }

    // 递归体
    private static void putQueen(int n, int index, ArrayDeque<Integer> row){
        if(index == n) {
            res.add(generateBoard(n,new ArrayDeque<>(row)));
            return;
        }

        // 回溯
        for (int i = 0; i < n; i++) {
            // 根据之前的解释此处判断每列、每条对角线是否存在Q 
            if(col[i] || angle1[index+i] || angle2[index-i+n-1])continue;
            col[i] = true;
            angle1[index+i] = true;
            angle2[index-i+n-1] = true;
            row.addLast(i);

            putQueen(n,index+1,row);
            // 恢复
            col[i] = false;
            angle1[index+i] = false;
            angle2[index-i+n-1] = false;
            row.removeLast();
        }
    }

    public static List<List<String>> solveNQueens(int n) {
        res.clear();
        col = new boolean[n]; // 列的个数
        angle1 = new boolean[2*n-1]; // 对角线条数
        angle2 = new boolean[2*n-1];
        putQueen(n,0, new ArrayDeque<>());
        return res;
    }
    public static void main(String[] args) {
        System.out.println(solveNQueens(4));
    }
}
```