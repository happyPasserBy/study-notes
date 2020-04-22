# ReverseString
> [链接](https://leetcode-cn.com/problems/reverse-string/)
```
编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。
 

示例 1：
输入：["h","e","l","l","o"]
输出：["o","l","l","e","h"]
```
## 1. 解题思路一 对撞指针
### 1.1 思路
> 创建两个指针left与right,left指向数组起始位置，right指向数组末尾，循环数据同时移动left与right并交换。
### 1.2 具体实现
```
public class Chapter {
    public static void reverseString(char[] s) {
        if(s.length == 0 )return;
        int left = 0;
        int right = s.length-1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }

    }

    public static void main(String[] args) {
        String str1 = "hello";
        reverseString(str1.toCharArray());
    }
}
```