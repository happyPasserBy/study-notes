# MatrixReshape
> [链接](https://leetcode-cn.com/problems/reshape-the-matrix/)
```
在MATLAB中，有一个非常有用的函数 reshape，它可以将一个矩阵重塑为另一个大小不同的新矩阵，但保留其原始数据。
给出一个由二维数组表示的矩阵，以及两个正整数r和c，分别表示想要的重构的矩阵的行数和列数。
重构后的矩阵需要将原始矩阵的所有元素以相同的行遍历顺序填充。
如果具有给定参数的reshape操作是可行且合理的，则输出新的重塑矩阵；否则，输出原始矩阵。

示例 1:
输入: 
nums = 
[[1,2],
 [3,4]]
r = 1, c = 4
输出: 
[[1,2,3,4]]
解释:
行遍历nums的结果是 [1,2,3,4]。新的矩阵是 1 * 4 矩阵, 用之前的元素值一行一行填充新矩阵。
```
## 1.解题思路一 
### 1.1 思路
> 只要注意下标的转换即可，没什么可说的
### 1.2 具体实现
```
public class Chapter566 {
    public static int[][] matrixReshape(int[][] nums, int r, int c) {
        if(nums.length == 0)return nums;
        if(r * c > nums.length * nums[0].length)return nums;
        int ni = 0;
        int nj = 0;
        int[][] arr = new int[r][c];
        for (int ai = 0; ai < arr.length; ai++) {
            for (int aj = 0; aj < arr[ai].length; aj++) {
                arr[ai][aj] = nums[ni][nj];
                nj++;
                if(nj >= nums[0].length) {
                    nj = 0;
                    ni++;
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        String str1 = "[[1,2],[3,4]]";
        int r1 = 1;
        int c1 = 4;
        System.out.println(matrixReshape(ArrayUtil.stringToInt2dArray(str1), r1, c1));
        int[][] res = matrixReshape(ArrayUtil.stringToInt2dArray(str1), r1, c1);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j <res[i].length ; j++) {
                System.out.println(res[i][j]);
            }
        }
    }
}
```