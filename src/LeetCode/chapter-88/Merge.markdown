# Merge
> [链接](https://leetcode-cn.com/problems/merge-sorted-array/)
```
给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 num1 成为一个有序数组。
说明:
初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。

示例:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3
输出: [1,2,2,3,5,6]
```
## 1. 解题思路一 合并后排序

## 2. 解题思路二 双指针

## 3. 解题思路三 三指针
### 3.1 思路
> 利用两个数组的排序规则，创建三个指针，p1表示nums1的最大值，p2表示nums2的最大值，convert表示待替换的元素。循环对比p1与p2，p1>p2需要插入到p1之前，所以把convert替换成p1，移动p1与convert准备下一次对比，若p2>p1说明p2需要插入到p1之后，convert替换成p2，继续循环至结束，最后查看p2是否小于0，不小于则将nums2剩余元素拷贝到nums1。
### 3.2 具体实现
```
public class Merge {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m-1;
        int p2 = n-1;

        int convert = m+n-1;

        while (p1 >= 0 && p2 >= 0) {
            if(nums1[p1] <= nums2[p2]) {
                nums1[convert] = nums2[p2];
                p2--;
            }else if(nums1[p1] > nums2[p2]) {
                nums1[convert] = nums1[p1];
                p1--;
            }
            convert--;
        }
        System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
    }

    public static void main(String[] args) {
        int[] arr1 = {1,2,3,0,0,0};
        int[] arr2 = {2,5,6};
        int[] arr3 = {0};
        int[] arr4 = {1};
        merge(arr3,0,arr4,1);
        for (int i : arr3) {
            System.out.println(i);
        }
    }
}
```