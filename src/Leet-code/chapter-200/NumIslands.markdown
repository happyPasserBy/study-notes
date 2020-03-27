# NumIslands
> 给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。[链接](https://leetcode-cn.com/problems/number-of-islands/)
## 1. 解题思路一 回溯法
### 1.1 思路
> 
### 1.2 具体实现
```
public class NumIslands200 {

    private static int[][] d = {{-1,0},{0,1},{1,0},{0,-1}};

    private static boolean[][] visited;

    private static int m;
    private static int n;

    private static boolean inArea(int x,int y){
        return x >= 0 && x < m && y >= 0 && y < n;
    }

    private static void dfs(char[][] grid,int x, int y){
        visited[x][y] = true;
        // 向四个方向查找
        for (int i = 0; i < 4 ; i++) {
            int newX = x+d[i][0];
            int newY = y+d[i][1];
            // 如果越界或已经访问过则跳过
            if(!inArea(newX,newY) || visited[newX][newY] || grid[newX][newY] == "0".charAt(0))continue;
            dfs(grid, newX, newY);
        }
    }
    public int numIslands(char[][] grid) {
        int ret = 0;
        if(grid.length == 0) return ret;
        m = grid.length;
        n = grid[0].length;
        visited = new boolean[m][n];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j <grid[i].length ; j++) {
                if(grid[i][j] ==  "1".charAt(0) && !visited[i][j]){
                    dfs(grid,i,j);
                    ret++;
                }
            }
        }
        return ret;
    }
}
```