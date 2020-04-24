# Exist
> 给定一个二维网格和一个单词，找出该单词是否存在于网格中。单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。[链接](https://leetcode-cn.com/problems/word-search/)
## 1.解题思路一 回溯法
### 1.1 思路
>
### 1.2 具体实现
```
public class Exist {

    private static int[][] d = {{-1,0},{0,1},{1,0},{0,-1}};

    private static boolean[][] visited;

    private static int m;
    private static int n;

    private static boolean inArea(int x,int y){
        return x >= 0 && x < m && y >= 0 && y < n;
    }

    public boolean exist(char[][] board, String word) {
        if(board.length == 0) return false;
        m = board.length;
        n = board[0].length;
        visited = new boolean[m][n];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j <board[i].length ; j++) {
                if(search(board,i,j,word,0))return true;
            }
        }
        return false;
    }

    private static boolean search(char[][] board,int x, int y,String word, int index){

        if(index == word.length() -1) {
            return board[x][y] == word.charAt(index);
        }
        if(board[x][y] == word.charAt(index)){
            visited[x][y] = true;
            // 向四个方向查找
            for (int i = 0; i < 4 ; i++) {
                int newX = x+d[i][0];
                int newY = y+d[i][1];

                // 如果越界或已经访问过则跳过
                if(!inArea(newX,newY) || visited[newX][newY])continue;
                if(search(board, newX, newY, word, index+1)){
                    return true;
                }
            }
            // 重置
            visited[x][y] = false;
        }
        return false;
    }
    public static void main(String[] args) {
    }
}
```
