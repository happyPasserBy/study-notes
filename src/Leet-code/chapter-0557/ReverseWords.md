# ReverseWords
> [链接](https://leetcode-cn.com/problems/reverse-words-in-a-string-iii/)
```
给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
示例 1:

输入: "Let's take LeetCode contest"
输出: "s'teL ekat edoCteeL tsetnoc" 
注意：在字符串中，每个单词由单个空格分隔，并且字符串中不会有任何额外的空格。

```
## 1. 解题思路一 split+reverse
### 1.1 具体实现
```java
class Solution {
    public String reverseWords(String s) {
        String[] sArr = s.split(" ");
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < sArr.length; i++) {
            StringBuilder stringBuilder = new StringBuilder(sArr[i]);
            res.append(stringBuilder.reverse());
            if(i != sArr.length-1){
                res.append(" ");
            }
        }
        return res.toString();
    }
}
```