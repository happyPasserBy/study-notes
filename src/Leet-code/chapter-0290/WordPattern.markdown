# WordPattern
> 给定一种规律 pattern 和一个字符串 str ，判断 str 是否遵循相同的规律。这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 str 中的每个非空单词之间存在着双向连接的对应规律。 [链接](https://leetcode-cn.com/problems/word-pattern/)

## 1.解题思路一 给pattern分组
### 1.1 思路
> 题意中pattern与str相匹配，因此可以推断出pattern的长度与str的长度相等，且pattern的每个字符都代表着str的中的某一个字符，循环pattern将每个子pattern通过下标将str中关联的字符保存到hashMap中,再与hashMap对比字符串。说白了就是将pattern根据字符拆分成多个子pattern，循环对比子pattern所对应的str中的字符是否都相等。
### 1.2 具体实现
```
public class WordPattern290 {
    public static boolean wordPattern(String pattern, String str) {
        char[] pattern1= pattern.toCharArray();
        String[] str1 = str.split(" ");
        if(pattern1.length != str1.length) return false;
        HashMap<Character,String> hashMap = new HashMap<>();
        for (int i = 0; i < pattern1.length; i++) {
            Character cur = Character.valueOf(pattern1[i]);
            if(hashMap.containsKey(cur)){
                if(!hashMap.get(cur).equals(str1[i])) return false;
            }else{
                if(hashMap.containsValue(str1[i]))return false; // 每个子patter所代表的字符不可能相等
                hashMap.put(cur,str1[i]);
            }
        }
        return true;
    }
    public static void main(String[] args) {
        String pattern1 = "abba";
        String str1 = "dog cat cat dog";
        String pattern2 = "abba";
        String str2 = "dog cat cat fish";
        String pattern3 = "aaaa";
        String str3 = "dog cat cat dog";
        String pattern4 = "abba";
        String str4 = "dog dog dog dog";
        System.out.println(wordPattern(pattern3,str3));

    }
}
```