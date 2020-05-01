# LongestCommonPrefix
> [链接](https://leetcode-cn.com/problems/longest-common-prefix/)
```
编写一个函数来查找字符串数组中的最长公共前缀。
如果不存在公共前缀，返回空字符串 ""。

示例 1:
输入: ["flower","flow","flight"]
输出: "fl"

示例 2:
输入: ["dog","racecar","car"]
输出: ""
解释: 输入不存在公共前缀。
```
## 1. 解题思路一 
### 1.1 思路
> 取出strs中第一个字符串firstStr，循环从firstStr取出字符串sub,在与strs中剩余字符串进行对比，查看是否是公共前缀，直到firstStr循环完毕或公共前缀匹配失败。
### 1.2 具体实现，
```
public class Chapter14 {
    public static String longestCommonPrefix(String[] strs) {
        if(strs.length == 0) return "";

        String firstStr = strs[0];
        String res = "";
        for (int i = 1; i <= firstStr.length() ; i++) {

            String sub = firstStr.substring(0,i);
            for (int j = 1; j < strs.length; j++) {
                if(i > strs[j].length() || !sub.equals(strs[j].substring(0,i))) {
                    sub = "";
                    break;
                }
            }
            if(i > 1 && "".equals(sub)) {
                break;
            }
            res = sub;
        }

        return res;
    }

}
```