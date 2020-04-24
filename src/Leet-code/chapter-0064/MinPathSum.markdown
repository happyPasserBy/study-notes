# MinPathSum
> [链接](https://leetcode-cn.com/problems/minimum-path-sum/)
```
给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
说明：每次只能向下或者向右移动一步。

示例:
输入:
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
输出: 7
解释: 因为路径 1→3→1→1→1 的总和最小。
```
## 1. 暴力解法
### 1.1 思路
> 根据题意可知每次只能向下或向右移动，当到达网格右边界或下边界时只能向下或向右移动。简单的方式就是递归网格找出所有走法后选择最小值即可。
### 1.2 具体实现
```
class Solution {
    public int minPathSum(int[][] grid) {
        if(grid.length == 0) return 0;
        return step(grid,0,0,grid[0][0]);
    }
    private static int step(int[][] grid,int x, int y, int total){
        if(x == grid.length-1 && y == grid[0].length-1) return total;
        if(x == grid.length-1) {
            return step(grid,x,y+1,total+grid[x][y+1]);
        }else if(y == grid[0].length-1){
            return step(grid,x+1,y,total+grid[x+1][y]);
        }else {
            return Math.min(step(grid, x,y+1, grid[x][y+1]),step(grid,x+1, y, grid[x+1][y])) + total;
        }
    }
}
```
## 2. 自上而下的记忆化搜索
### 2.1 思路
> 暴力解法简单，但在递归中存在许多重复计算，本方式加入缓存来进行优化。
### 2.2 具体实现
```
class Solution {

    public int minPathSum(int[][] grid) {
        if(grid.length == 0) return 0;
        memory = new int[grid.length][grid[0].length];
        return step(grid,0,0,grid[0][0]);
    }
    
    private static int[][] memory;
    
    private static int step(int[][] grid,int x, int y, int total){
        if(x == grid.length-1 && y == grid[0].length-1) return total;
        if(memory[x][y] != 0) return memory[x][y];
        int num = 0;
        if(x == grid.length-1) {
            num = step(grid,x,y+1, grid[x][y+1]) + total;
        }else if(y == grid[0].length-1){
            num = step(grid,x+1,y, grid[x+1][y]) + total;
        }else {
            num = Math.min(step(grid, x,y+1, grid[x][y+1]),step(grid,x+1, y, grid[x+1][y])) + total;
        }
        memory[x][y] = num;
        return num;
    }
}
```
## 3. 自下而上的动态规划
### 3.1 思路
> 暴力解法简单，但在递归中存在许多重复计算，本方式加入缓存来进行优化。
### 3.2 具体实现
```
class Solution {
    public int minPathSum(int[][] grid) {
        // 处理下边界问题
        int[] temp = new int[grid[0].length];
        temp[temp.length-1] = grid[grid.length-1][grid[0].length-1];
        for (int i = grid[0].length-2; i >= 0 ; i--) {
            temp[i] = temp[i+1]+grid[grid.length-1][i];
        }

        if(grid.length == 1)return temp[0];

        for (int i = grid.length-2; i >= 0; i--) {
            for (int j = grid[i].length-1; j >= 0; j--) {
                if( j == grid[i].length-1) { // 右边界问题
                    grid[i][j] = grid[i+1][j]+grid[i][j];
                }else if(i == grid.length-2){ // 下边界问题
                    grid[i][j] =  Math.min(grid[i][j+1],temp[j])+grid[i][j];
                }else {
                    grid[i][j] = Math.min(grid[i][j+1],grid[i+1][j])+grid[i][j];
                }
            }
        }
        return grid[0][0];
    }
}
```




