# SetZeroes
> [链接](https://leetcode-cn.com/problems/set-matrix-zeroes/)
```
给定一个 m x n 的矩阵，如果一个元素为 0，则将其所在行和列的所有元素都设为 0。请使用原地算法。
示例 1:

输入: 
[
  [1,1,1],
  [1,0,1],
  [1,1,1]
]
输出: 
[
  [1,0,1],
  [0,0,0],
  [1,0,1]
]

```
## 1. 解题思路一 标记行与列
### 1.1 思路
> 创建与矩阵相同行数的数组row与相同列的数组col,循环矩阵如果该行或该列有0则在相应的col与row中做标记，再次循环矩阵将row与col中标记的行与列改为0。
```
class Solution {
    public void setZeroes(int[][] matrix) {
        boolean[] row = new boolean[matrix.length];
        boolean[] col = new boolean[matrix[0].length];
        for (int i = 0; i < row.length; i++) {
            for (int j = 0; j < col.length; j++) {
                if(matrix[i][j] == 0) {
                    row[i] = true;
                    col[j] = true;
                }
            }
        }
        for (int i = 0; i < row.length; i++) {
            for (int j = 0; j < col.length; j++) {
                if(row[i] || col[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
}
```

