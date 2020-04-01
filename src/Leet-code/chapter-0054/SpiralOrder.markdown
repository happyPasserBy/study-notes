# SpiralOrder
> [链接](https://leetcode-cn.com/problems/spiral-matrix/)
```
给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。

示例 1:
输入:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
输出: [1,2,3,6,9,8,7,4,5]
```
## 1. 解题思路一 4边界
### 1.1 思路
> 根据题意得知我们要顺时针螺旋遍历一个二维数组，我们只要设置4个边界上边界(top)、下边界(bottom)、左边界(first)、右边界(last)，我们循环数组时不断缩小边界即可。
### 1.2 具体实现
```
public class Chapter {
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if(matrix.length == 0)return res;
        // 4个边界
        int first = 0;
        int top = 0;
        int last = matrix[0].length-1;
        int bottom = matrix.length-1;
        // 判断循环终止条件
        while (last-first >= 0 && bottom-top >= 0){
            // 循环第一行，完毕后缩小上边界
            for (int i = first; i <= last; i++) {
                res.add(matrix[top][i]);
            }
            top++;
            // 循环末尾列，完毕后缩小边界
            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][last]);
            }
            last--;
            // 注意，二维矩阵并非正方形，可能是长方形
            if(top <= bottom && first <= last){
                for (int i = last; i >= first; i--) {
                    res.add(matrix[bottom][i]);
                }
                bottom--;
                for (int i = bottom; i >= top; i--) {
                    res.add(matrix[i][first]);
                }
                first++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        String str1 = "[[ 1, 2, 3 ],[ 4, 5, 6 ],[ 7, 8, 9 ]]"; //  [1,2,3,6,9,8,7,4,5]
        String str2 = "[[1,2,3,4],[5,6,7,8],[9,10,11,12]]";// [1,2,3,4,8,12,11,10,9,5,6,7]
        String str3 = "[[6,9,7]]";// [6,9,7]
        String str4 = "[[2,5],[8,4],[0,-1]]";// [2,5,4,-1,0,8]
        System.out.println(spiralOrder(ArrayUtil.stringToInt2dArray(str2)));

    }
}
```