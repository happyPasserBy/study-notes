# Rotate
> [链接](https://leetcode-cn.com/problems/rotate-image/)
```
给定一个 n × n 的二维矩阵表示一个图像。
将图像顺时针旋转 90 度。
说明：
你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。
示例 1:

给定 matrix = 
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],
原地旋转输入矩阵，使其变为:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]
```
## 1. 解题思路一 横纵坐标规律
### 1.1 思路
> [链接](https://leetcode-cn.com/problems/rotate-image/solution/yi-ci-xing-jiao-huan-by-powcai/)
### 1.2 具体实现
```
public class Chapter {
    public void rotate(int[][] matrix) {
        int len = matrix.length;
        for (int i = 0; i < len/2; i++) {
            for (int j = i; j < len-i-1; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[len-j-1][i];
                matrix[len-j-1][i] = matrix[len-i-1][len-j-1];
                matrix[len-i-1][len-j-1] = matrix[j][len-i-1];
                matrix[j][len-i-1] = temp;
            }
        }
    }
    public static void main(String[] args) {

    }
}
```





