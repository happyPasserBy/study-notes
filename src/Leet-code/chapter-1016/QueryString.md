# QueryString
> [链接](https://leetcode-cn.com/problems/binary-string-with-substrings-representing-1-to-n/)
```
给定一个二进制字符串 S（一个仅由若干 '0' 和 '1' 构成的字符串）和一个正整数 N，如果对于从 1 到 N 的每个整数 X，其二进制表示都是 S 的子串，就返回 true，否则返回 false。
示例 1：
输入：S = "0110", N = 3
输出：true

示例 2：
输入：S = "0110", N = 4
输出：false
```
## 1. 解题思路一 Integer.toBinaryString(i) 
### 1.1 思路
> 使用Integer.toBinaryString将数字转换为二进制表示的字符串，循环N，使用字符串的contains判断是否是子串。
### 1.2 具体实现
```
public class Chapter {
    public static boolean queryString(String S, int N) {
        String str;
        for (int i = 1; i <= N; i++) {
             str = Integer.toBinaryString(i);
            if(!S.contains(str)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String str1 = "0110";
        int n1 = 3;
        String str2 = "0110";
        int n2 = 4;
        String str3 = "1";
        int n3 = 1;
        System.out.println(queryString(str3, n3));
    }
}
```