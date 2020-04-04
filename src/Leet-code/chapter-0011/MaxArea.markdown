# MaxArea
> [链接](https://leetcode-cn.com/problems/container-with-most-water/)
```
给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
说明：你不能倾斜容器，且 n 的值至少为 2。

示例：
输入：[1,8,6,2,5,4,8,3,7]
输出：49
```
![图片](https://aliyun-lc-upload.oss-cn-hangzhou.aliyuncs.com/aliyun-lc-upload/uploads/2018/07/25/question_11.jpg)
## 1. 解题思路一
### 1.1 思路
> 创建头尾两个指针i、j，判断height[i] < height[j]，如果height[i]大则j--，如果height[j]大则i++,因为影响容量的是最小值，只有改变最小值才有可能扩大容量
### 1.2 具体实现
```
public class Chapter {
    public static int maxArea(int[] height) {
        int maxNum = 0;
        int i = 0;
        int j = height.length-1;
        while (i < j) {
            maxNum = Math.max(maxNum, Math.abs(j-i)*Math.min(height[i],height[j]));
            if(height[i] < height[j]) {
                i++;
            }else {
                j--;
            }
        }
        return maxNum;
    }

    public static void main(String[] args) {
        int[] arr1 = {1,8,6,2,5,4,8,3,7};
        int[] arr2 = {2,3,4,5,18,17,6};
        System.out.println(maxArea(arr2));
    }
}
```