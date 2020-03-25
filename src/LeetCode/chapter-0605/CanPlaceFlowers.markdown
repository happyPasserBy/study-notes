# CanPlaceFlowers
> [链接](https://leetcode-cn.com/problems/can-place-flowers/)
```
假设你有一个很长的花坛，一部分地块种植了花，另一部分却没有。可是，花卉不能种植在相邻的地块上，它们会争夺水源，两者都会死去。
给定一个花坛（表示为一个数组包含0和1，其中0表示没种植花，1表示种植了花），和一个数 n 。能否在不打破种植规则的情况下种入 n 朵花？能则返回True，不能则返回False。

示例 1:
输入: flowerbed = [1,0,0,0,1], n = 1
输出: True

示例 2:
输入: flowerbed = [1,0,0,0,1], n = 2
输出: False
```
## 1.解题思路一 贪心
### 1.1 思路
> 贪心算法，注意 1 可以出现在任何出现的位置
### 1.2 具体实现
```
public class Chapter605 {
    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0) && (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                flowerbed[i] = 1;
                n--;
            }
        }
        return n <= 0;
    }
    public static void main(String[] args) {
        int[] arr1 = {1,0,0,0,1};
        int n1 = 1;
        int[] arr2 = {1,0,0,0,1};
        int n2 = 2;
        int[] arr3 = {1,0,0,0,0,1};
        int n3 = 2;
        System.out.println(canPlaceFlowers(arr2,n1));
    }
}
```