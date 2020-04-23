# FirstUniqChar
> [链接](https://leetcode-cn.com/problems/first-unique-character-in-a-string/)
```
给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。

案例:
s = "leetcode"
返回 0.
s = "loveleetcode",
返回 2.
```
## 1. 解题思路一 HashMap
### 1.1 思路
> 使用HashMap存储每个字符串出现的次数，在遍历字符串一次判断是否出现一次即可。
### 1.2 具体实现
```
public class Chapter {

    public int firstUniqChar(String s) {
        Map<Character,Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character key = s.charAt(i);
            map.put(key, map.getOrDefault(key,0)+1);
        }

        for (int i = 0; i < s.length(); i++) {
            Character key = s.charAt(i);
            if(map.get(key) == 1) {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {

    }
}
```