# ReverseVowels
> [链接](https://leetcode-cn.com/problems/reverse-vowels-of-a-string/)
```
编写一个函数，以字符串作为输入，反转该字符串中的元音字母。

示例 1:
输入: "hello"
输出: "holle"

示例 2:
输入: "leetcode"
输出: "leotcede"
```
## 1. 解题思路一 对撞指针
### 1.1 思路
> 创建头(left)尾(right)两个指针，循环字符串只有满足两个指针都是元音字母时进行交换，否则不断缩减两个指针。
### 1.2 具体实现
```
class Solution {
    public String reverseVowels(String s) {
        String[] sArr = s.split("");
        List<String> vowels = new ArrayList<>();
        vowels.addAll(Arrays.asList("a","e","i","o","u"));
        int left = 0;
        int right = sArr.length-1;

        while (left < right) {
            boolean leftIsVowel = vowels.contains(sArr[left].toLowerCase());
            boolean rightIsVowel = vowels.contains(sArr[right].toLowerCase());
            if(leftIsVowel && rightIsVowel){
                String temp = sArr[left];
                sArr[left] = sArr[right];
                sArr[right] = temp;
                left++;
                right--;
            }

            if(!leftIsVowel){
                left++;
            }
            if(!rightIsVowel){
                right--;
            }

        }
        StringBuffer str5 = new StringBuffer();
        for (String str : sArr) {
            str5.append(str);
        }
        return str5.toString();
    }
}
```