# RepeatedSubstringPattern
> [链接](https://leetcode-cn.com/problems/repeated-substring-pattern/)
## 1. 解题思路一
### 1.1 思路
> [来源于leetcode高赞](https://leetcode-cn.com/problems/repeated-substring-pattern/solution/bu-yong-zheng-ze-jie-yong-zi-fu-chuan-fang-fa-lian/)
### 1.2 具体实现
```
public boolean repeatedSubstringPattern(String s) {
    String str = s + s;
    return str.substring(1, str.length() - 1).contains(s);
}
```