# IsAnagram
> 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。 [链接](https://leetcode-cn.com/problems/valid-anagram/)
## 1.解题思路一 两数组对比
### 1.1 思路
> 将两个字符串转为数组，两数组对比
### 1.2 具体实现一
```
public class IsAnagram {
    public static boolean isAnagram(String s, String t) {
        if(s.length() != t.length())return false;
        String[] s1 = s.split("");
        String[] t2 = t.split("");
        Arrays.sort(s1);
        Arrays.sort(t2);
        for (int i = 0; i < s1.length; i++) {
            if(!s1[i].equals(t2[i]))return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";
        System.out.println(isAnagram(s, t));
        System.out.println(t);
    }
}
```
### 1.2 具体实现二 效率高
```
public class IsAnagram {
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);
        return Arrays.equals(str1, str2);
    }

    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";
        System.out.println(isAnagram(s, t));
        System.out.println(t);
    }
}
```
## 1.解题思路一 查找表
### 1.1 思路
> 将其中之一转为数组，另一个转为查找表，循环其中之一进行对比
### 1.2 具体实现
```
public class IsAnagram {

    public static boolean isAnagram(String s, String t) {
        if(s.length() != t.length())return false;
        String[] s1 = s.split("");
        Map<String,Long> map = Arrays.stream(t.split("")).collect(Collectors.groupingBy(i->i,Collectors.counting()));
        for (int i = 0; i < s1.length; i++) {
            if(!map.containsKey(s1[i]))return false;
            Long num = map.get(s1[i]) - 1;
            if(num == 0){
                map.remove(s1[i]);
            }else {
                map.put(s1[i],num);
            }
        }
        return true;
    }
    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";
        System.out.println(isAnagram(s, t));
        System.out.println(t);
    }
}
```




