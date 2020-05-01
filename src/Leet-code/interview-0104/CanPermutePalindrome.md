# CanPermutePalindrome
> [链接](https://leetcode-cn.com/problems/palindrome-permutation-lcci/)
```
给定一个字符串，编写一个函数判定其是否为某个回文串的排列之一。
回文串是指正反两个方向都一样的单词或短语。排列是指字母的重新排列。
回文串不一定是字典当中的单词。

示例1：
输入："tactcoa"
输出：true（排列有"tacocat"、"atcocta"，等等）

```
## 1. 解题思路 HashSet表
### 1.1 思路
> 本题最终想求字符串是否回文，我们使用HashSet来存储字符串，如果HashSet中没有就存储否则就删除，这样成对出现的字符串就会消失，最后判断HashSet的长度是否小于等于1，回文中允许存在一个单独字符创。
### 1.2 具体实现
```java
public class Interview {
    public boolean canPermutePalindrome(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if(!set.add(s.charAt(i))){
                set.remove(s.charAt(i));
            }
        }
        return set.size() <= 1;
    }
}
```