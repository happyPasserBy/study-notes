# DetectCapitalUse
> [链接](https://leetcode-cn.com/problems/detect-capital/)
```
给定一个单词，你需要判断单词的大写使用是否正确。
我们定义，在以下情况时，单词的大写用法是正确的：

全部字母都是大写，比如"USA"。
单词中所有字母都不是大写，比如"leetcode"。
如果单词不只含有一个字母，只有首字母大写， 比如 "Google"。
否则，我们定义这个单词没有正确使用大写字母。

示例 1:
输入: "USA"
输出: True
```
## 1. 解题思路一 统计大小写
### 1.1 思路
> 遍历字符串，统计是否全部大写、全部小写、最后一个大写字符串位置，最后根据题目条件判断即可。
### 1.2 具体实现
```
public class Chapter {


    public static boolean detectCapitalUse(String word) {

        boolean isAllUpperCase = true;
        boolean isAllLowerCase = true;
        int lastUpperCaseIndex = -1;
        for (int i = 0; i < word.length(); i++) {
            if(!Character.isUpperCase(word.charAt(i))){
                isAllUpperCase = false;
            }
            if(!Character.isLowerCase(word.charAt(i))){
                isAllLowerCase = false;
            }
            if(Character.isUpperCase(word.charAt(i))){
                lastUpperCaseIndex = i;
            }
        }
        if(isAllUpperCase) return true;
        if(isAllLowerCase) return true;
        if(lastUpperCaseIndex == 0) return true;
        return false;
        
    }

    public static void main(String[] args) {
        String str1 = "FlaG";

        System.out.println(detectCapitalUse(str1));
    }
}
```