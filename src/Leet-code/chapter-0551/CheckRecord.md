# checkRecord
> [链接](https://leetcode-cn.com/problems/student-attendance-record-i/)
```
给定一个字符串来代表一个学生的出勤记录，这个记录仅包含以下三个字符：

'A' : Absent，缺勤
'L' : Late，迟到
'P' : Present，到场
如果一个学生的出勤记录中不超过一个'A'(缺勤)并且不超过两个连续的'L'(迟到),那么这个学生会被奖赏。
你需要根据这个学生的出勤记录判断他是否会被奖赏。

示例 1:
输入: "PPALLP"
输出: True

示例 2:
输入: "PPALLL"
输出: False
```
## 1. 解题思路一 函数库
### 1.1 思路
>循环统计A的个数与使用indexOf判断LLL是否出现即可。
### 1.2 具体实现
```
class Solution {
    public boolean checkRecord(String s) {
        int count=0;
        for(int i=0;i<s.length();i++)
            if(s.charAt(i)=='A')
                count++;
        return count<2 && s.indexOf("LLL")<0;
    }
}
``` 
## 2. 解题思路二
### 2.1 思路
> 思路一使用了两次循环，分别统计A与indexOf,思路二是一次循环一起统计A与L，最终结果发现与思路一耗时相同且空间还上升了 - -。
```
public class Chapter {
    public boolean checkRecord(String s) {

        int countA = 0;
        int firstL = -1;
        int lastL = -1;
        boolean isRepet = false;
        for (int i = 0; i < s.length(); i++) {
            if(String.valueOf(s.charAt(i)).equals("A")){
                countA++;
            }
            if(String.valueOf(s.charAt(i)).equals("L") && !isRepet) {
                if(firstL == -1) {
                    firstL = i;
                    lastL = i;
                }else {
                    lastL = i;
                }
                if(lastL-firstL >= 2) {
                    isRepet = true;
                }
            }else {
                firstL = -1;
                lastL = -1;
            }
        }
        if(lastL-firstL >= 2) {
            isRepet = true;
        }
        if(isRepet || countA > 1){
            return false;
        }else {
            return true;
        }

    }

    public static void main(String[] args) {
    }
}
``