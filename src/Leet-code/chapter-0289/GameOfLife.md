# GameOfLife
> [链接](https://leetcode-cn.com/problems/game-of-life/)
```
根据 百度百科 ，生命游戏，简称为生命，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态：1 即为活细胞（live），或 0 即为死细胞（dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
根据当前状态，写一个函数来计算面板上所有细胞的下一个（一次更新后的）状态。下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。
 

示例：
输入： 
[
  [0,1,0],
  [0,0,1],
  [1,1,1],
  [0,0,0]
]
输出：
[
  [0,0,0],
  [1,0,1],
  [0,1,1],
  [0,1,0]
]
```
## 1.解题思路一 
### 1.1 思路
> 复制一份原数组
### 1.2 具体实现
```
public class Chapter {
    public static void gameOfLife(int[][] board) {
            int m = board.length;
            if(m == 0) return;
            int n = board[0].length;
            int[][] copyBoard = new int[m][n];
            for (int row = 0; row < m; row++) {
                for (int col = 0; col < n; col++) {
                    copyBoard[row][col] = board[row][col];
                }
            }


            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int live = 0;
                    // 上
                    if(i > 0){
                        if(copyBoard[i-1][j] == 1)live++;
                    }
                    // 左
                    if(j > 0){
                        if(copyBoard[i][j-1] == 1)live++;
                    }
                    // 左上
                    if(i > 0 && j > 0) {
                        if(copyBoard[i-1][j-1] == 1)live++;
                    }

                    // 下
                    if(i < m-1) {
                        if(copyBoard[i+1][j] == 1)live++;
                    }
                    // 左下
                    if(i < m-1 && j > 0) {
                        if(copyBoard[i+1][j-1] == 1)live++;
                    }
                    // 右
                    if( j < n-1) {
                        if(copyBoard[i][j+1] == 1)live++;
                    }
                    // 右上
                    if( j < n-1 && i > 0) {
                        if(copyBoard[i-1][j+1] == 1)live++;
                    }
                    // 右下
                    if( j < n-1 && i < m-1) {
                        if(copyBoard[i+1][j+1] == 1)live++;
                    }

                    // 规则 1 或规则 3
                    if ((copyBoard[i][j] == 1) && (live < 2 || live > 3)) {
                        board[i][j] = 0;
                    }
                    // 规则 4
                    if (copyBoard[i][j] == 0 && live == 3) {
                        board[i][j] = 1;
                    }

                }

            }
    }

    public static void main(String[] args) {
        String arr1 = "[\n" +
                "  [0,1,0],\n" +
                "  [0,0,1],\n" +
                "  [1,1,1],\n" +
                "  [0,0,0]\n" +
                "]";
        String arr2 = "[[1,1],[1,0]]";
        gameOfLife(ArrayUtil.stringToInt2dArray(arr2));
    }

}
```


## 2. 解题思路二 
### 2.1 思路
> 使用原地置换算法，原地置换的难点在于判断当前细胞是否存活时需要按照题意不可使用更改后的细胞状态，也就是说不可使用临近细胞的新状态只能使用具体方法输入的细胞状态，了解难点后可以换个方式实现原地置换，
> 我们增加2个状态，数字2代表细胞更新前是存活更新后细胞仍是存活，数字-1代表更新前细胞是存活更新后是死亡状态，在加上原有数字0代表死亡+死亡，原有数字1代表存活+存活，一共4个状态就可以进行原地置换
### 2.2 具体实现
```
public class Chapter {
    public static void gameOfLife(int[][] board) {
        int m = board.length;
        if(m == 0) return;
        int n = board[0].length;
        int[][] copyBoard = new int[m][n];
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                copyBoard[row][col] = board[row][col];
            }
        }


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int live = 0;
                // 上
                if(i > 0){
                    if(copyBoard[i-1][j] == 1)live++;
                }
                // 左
                if(j > 0){
                    if(copyBoard[i][j-1] == 1)live++;
                }
                // 左上
                if(i > 0 && j > 0) {
                    if(copyBoard[i-1][j-1] == 1)live++;
                }

                // 下
                if(i < m-1) {
                    if(copyBoard[i+1][j] == 1)live++;
                }
                // 左下
                if(i < m-1 && j > 0) {
                    if(copyBoard[i+1][j-1] == 1)live++;
                }
                // 右
                if( j < n-1) {
                    if(copyBoard[i][j+1] == 1)live++;
                }
                // 右上
                if( j < n-1 && i > 0) {
                    if(copyBoard[i-1][j+1] == 1)live++;
                }
                // 右下
                if( j < n-1 && i < m-1) {
                    if(copyBoard[i+1][j+1] == 1)live++;
                }

                // 规则 1 或规则 3
                if ((copyBoard[i][j] == 1) && (live < 2 || live > 3)) {
                    board[i][j] = -1;
                }
                // 规则 4
                if (copyBoard[i][j] == 0 && live == 3) {
                    board[i][j] = 2;
                }

            }

        }
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if(board[row][col] > 0) {
                    board[row][col] = 1;
                }else {
                    board[row][col] = 0;
                }
            }
        }



    }

    public static void main(String[] args) {
        String arr1 = "[\n" +
                "  [0,1,0],\n" +
                "  [0,0,1],\n" +
                "  [1,1,1],\n" +
                "  [0,0,0]\n" +
                "]";
        String arr2 = "[[1,1],[1,0]]";
        gameOfLife(ArrayUtil.stringToInt2dArray(arr2));
    }

}
```