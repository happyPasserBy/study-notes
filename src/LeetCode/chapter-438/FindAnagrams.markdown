# FindAnagrams
> 给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。[链接](https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/)

## 1.解题思路一 动态滑窗+hash表
### 1.1 思路 
> 思路来源于[LeetCode](https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/solution/hua-dong-chuang-kou-tong-yong-si-xiang-jie-jue-zi-/)，
> 解题核心是动态滑窗，创建两指针left、right,left:头指针，right: 尾指针，left-right所形成的区间可以理解为一个窗口window，window从 字符串s 0的位置不断扩大或缩小来进行查找符合 字符串p的字符，
当不符合 p 时window不断增大(right++)直到符合 P串 条件/找到s串末尾，当window符合 p串 的条件时不断缩小window(left++)再次判断是否符合 P串条件，window不断移动直到结束，这就是动态滑窗，也就是这道题的核心。hash表用于存储window中的字符与 p串，方便进行判断window是否符合 P串。
### 1.2 具体实现
```
public class FindAnagrams {
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> ret = new ArrayList<>();
        if(s.length() == 0) return ret;
        HashMap<Character,Integer> window = new HashMap<>(); // 窗口容器
        HashMap<Character,Integer> needs = new HashMap<>(); // 所需匹配字符的容器

        int left = 0; // 窗口左指针
        int right = 0; // 窗口右指针
        int match = 0; // 符合要求的字符数

        for (char c : p.toCharArray()) {
            if(needs.containsKey(Character.valueOf(c))){
                needs.put(Character.valueOf(c),needs.get(Character.valueOf(c))+1);
            }else{
                needs.put(Character.valueOf(c),1);
            }
        }
        while (right<s.length()){
            Character c = s.charAt(right);
            if(needs.containsKey(c)){
                if(window.containsKey(c)){
                    window.put(c,window.get(c)+1);
                }else{
                    window.put(c,1);
                }
                if(window.get(c).equals(needs.get(c))){
                    match++;
                }
            }
            right++;
            while(match == needs.size()){ // needs.size()
                if(right-left == p.length()){
                    ret.add(left);
                }
                Character removeC = s.charAt(left);
                if(needs.containsKey(removeC)){
                    window.put(removeC,window.get(removeC)-1);
                    if(window.get(removeC) < needs.get(removeC)) {
                        match--;
                    }
                }
                left++;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        String s = "cbaebabacd";
        String p =  "abc";
        String s2 = "baa";
        String p2 =  "aa";
        String s3 = "abab";
        String p3 =  "ab";
        String s4 = "abacbabc";
        String p4 =  "abc";
        System.out.println(findAnagrams(s3,p3));
    }
}
```







## 2. 解题思路二 动态滑窗+增删字符串
### 1.2 思路
> 第一次实现本题想到的方法就是动态滑窗，核心没错，但实现完了被提示“超出时间限制”，哎，预料之中，顾换了本思路,具体更换的就在于如何判断滑窗 window 符合 p串，维护一个 p串的副本 copy_p，当window中有符合条件的字符则在 copy_p中进行删除，当left++时再将left的字符拼接回copy_p中，不断维护copy_p这个判断条件，当copy_p为空时说明当前window符合条件。但最终第32个用例测试失败，本题就先暂时这样吧，日后再来回顾。
```
public class FindAnagrams {
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> ret = new ArrayList<>();
        if(s.length() == 0 || p.length() == 0 || s.length() < p.length()) return ret;
        char[] sArr = s.toCharArray();
        int f = 0;
        int l = 1;
        String searchStr = p;
        while (l <= sArr.length){
            String str = String.valueOf(sArr[l-1]);
            if(!p.contains(str)){
                searchStr = p;
                f++;
                l++;
            }else{
                searchStr = searchStr.replaceFirst(str,"");
                if(searchStr.length() == 0){
                    ret.add(f);
                    searchStr = p.replaceFirst(str,"");
                }
                if(l-f < p.length()) {
                    l++;
                } else {
                    f++;
                    l++;
                }
            }
        }
        return ret;
    }
    public static void main(String[] args) {
        String s = "cbaebabacd";
        String p =  "abc";
        String s2 = "baa";
        String p2 =  "aa";
        String s3 = "abab";
        String p3 =  "ab";
        String s4 = "abacbabc";
        String p4 =  "abc";
        System.out.println(findAnagrams(s3,p3));
    }
}

```
