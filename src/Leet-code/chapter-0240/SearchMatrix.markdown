# SearchMatrix
> [链接](https://leetcode-cn.com/problems/search-a-2d-matrix-ii/)
```
编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
每行的元素从左到右升序排列。
每列的元素从上到下升序排列。

示例:
现有矩阵 matrix 如下：
[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
给定 target = 5，返回 true。
给定 target = 20，返回 false。
```
## 1. 解题思路一 暴力解法
## 2. 解题思路二 二分查找
## 3. 解题思路三 范围查询
### 3.1 思路
> 根据二维数组的排序特性，创建两个指针x,y用来缩小搜索范围，x指向横坐标搜索范围，y指向纵坐标搜索范围
```
public class Chapter240 {
    public static boolean searchMatrix(int[][] matrix, int target) {
        if(matrix.length == 0 || matrix[0].length == 0) return false;
        int x = matrix[0].length-1,y = matrix.length-1;
        for (int i = 0; i < matrix[0].length; i++) {
            if(matrix[0][i] == target)return true;
            if(matrix[0][i] > target){
                x = i;
                break;
            };
        }
        for (int i = 0; i < matrix.length; i++) {
            if(matrix[i][0] == target)return true;
            if(matrix[i][0] > target){
                y = i;
                break;
            };
        }

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if(matrix[i][j] == target)return true;
            }
        }
        return false;
    }
}
```