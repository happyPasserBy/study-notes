# LrgeGroupPositions
> [链接](https://leetcode-cn.com/problems/positions-of-large-groups/submissions/)
```
在一个由小写字母构成的字符串 S 中，包含由一些连续的相同字符所构成的分组。
例如，在字符串 S = "abbxxxxzyy" 中，就含有 "a", "bb", "xxxx", "z" 和 "yy" 这样的一些分组。
我们称所有包含大于或等于三个连续字符的分组为较大分组。找到每一个较大分组的起始和终止位置。
最终结果按照字典顺序输出。

示例 1:
输入: "abbxxxxzzy"
输出: [[3,6]]
解释: "xxxx" 是一个起始于 3 且终止于 6 的较大分组。
```
## 1.解题思路一 双指针
### 1.1 思路
> 记录两个指针 first与i, 循环字符串保证first与i之间的字符相同，若不同则判断是否 >= 3，并更新first
### 1.2 具体实现
```
public class Chapter {
    public static List<List<Integer>> largeGroupPositions(String S) {
        List<List<Integer>> res = new ArrayList<>();
        int first = 0;
        String[] sArr =  S.split("");
        for (int j = 0; j < sArr.length; ++j) {
            if (j == sArr.length-1 || !sArr[j].equals(sArr[j+1])) {
                if (j-first+1 >= 3){
                    res.add(Arrays.asList(new Integer[]{first, j}));
                }
                first = j + 1;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String str1 = "abbxxxxzzy";
        String str2 = "abcdddeeeeaabbbcd";
        String str3 = "aaa";
        System.out.println(largeGroupPositions(str3));
    }
}
```