# GenerateMatrix
> [链接](https://leetcode-cn.com/problems/spiral-matrix-ii/)
## 1. 解题思路一 设置边界
### 1.2 思路
> 设置上(top)、下(bottom)、左(left)、右(right)四个边界，循环螺旋创建值并且不断缩短边界。
### 1.2 具体实现
```
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int top = 0;
        int bottom = n-1;
        int left = 0;
        int right = n-1;
        int count = 1;
        while (top <= bottom){
            for (int i = left; i <= right; i++) res[top][i] = count++;
            top++;
            for (int i = top; i <= bottom; i++) res[i][right] = count++;
            right--;
            for (int i = right; i >= left; i--) res[bottom][i] = count++;
            bottom--;
            for (int i = bottom; i >= top ; i--) res[i][left] = count++;
            left++;
        }
        return res;
    }
}
```