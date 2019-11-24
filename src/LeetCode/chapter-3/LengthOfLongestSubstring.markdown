# LengthOfLongestSubstring
> 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
## 1.解决思路一
### 1.1 动态滑窗
### 1.2 具体实现 有bug待解决
```
public class LengthOfLongestSubstring {
    public static int lengthOfLongestSubstring(String s) {
        if(s.length() == 0) {
            return 0;
        }
        if(s.length() == 1) {
            return 1;
        }
        int i = 0;
        int j = 0;
        int result = 0;
        Set<Character> set = new HashSet<>();
        while (i<s.length() && j < s.length()) {
            char str = s.charAt(j);
            if(!set.contains(str)){
                set.add(str);
                j++;
                result = Math.max(set.size(),j-i);
            }else {
                set.remove(str);
                i++;
            }
        }
        return result == s.length()+1 ? 0 : result;
    }

    public static void main(String[] args) {
        String str = "pwwk";
        System.out.println(lengthOfLongestSubstring(str));
    }

}
```