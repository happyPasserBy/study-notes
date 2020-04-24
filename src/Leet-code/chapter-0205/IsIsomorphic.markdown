# IsIsomorphic
> 给定两个字符串 s 和 t，判断它们是否是同构的。如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。[链接](https://leetcode-cn.com/problems/isomorphic-strings/)

## 1.解题思路一 查找表
### 1.1 思路
> 根据题意可知s与t的长度相同，且s每个字符的位置与出现的次数与t的每个字符相同,建立s、t两个查找表，以字符为key,字符下标为value，循环对比每个字符串
### 1.2 具体实现
```
public class IsIsomorphic {
    public static boolean isIsomorphic(String s, String t) {
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        HashMap<String,List<Integer>> tHashMap = new HashMap<>();
        for (int i = 0; i < tArr.length; i++) {
            String str = String.valueOf(tArr[i]);
            if(tHashMap.containsKey(str)){
                tHashMap.get(str).add(i);
            }else{
                List<Integer> list = new ArrayList<>();
                list.add(i);
                tHashMap.put(str,list);
            }
        }
        HashMap<String,List<Integer>> sHashMap = new HashMap<>();
        for (int i = 0; i < sArr.length; i++) {
            String str = String.valueOf(sArr[i]);
            if(sHashMap.containsKey(str)){
                sHashMap.get(str).add(i);
            }else{
                List<Integer> list = new ArrayList<>();
                list.add(i);
                sHashMap.put(str,list);
            }
        }
        if(tHashMap.size() != sHashMap.size())return false;
        for (int i = 0; i < sArr.length; i++) {
            List<Integer> list1 = sHashMap.get(String.valueOf(sArr[i]));
            List<Integer> list2 = tHashMap.get(String.valueOf(tArr[i]));
            if(list1.size()!=list2.size() ||  !list1.containsAll(list2))return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String s1 = "egg";
        String t1 = "add";
        String s2 = "foo";
        String t2 = "bar";
        String s3 = "paper";
        String t3 = "title";
        System.out.println(isIsomorphic(s2,t2));
    }

}
```
## 2.解题思路一 对比下标
### 2.1 思路
> 与第一种解题思路相同，但无需查找表，依次对比两个字符串的下标即可(首先想到的就是这种方式，但考虑不周到放弃了，事实证明没问题)
### 2.2 具体实现
```
public class IsIsomorphic {
    public static boolean isIsomorphic(String s, String t) {
        char[] ch1 = s.toCharArray();
        char[] ch2 = t.toCharArray();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if(s.indexOf(ch1[i]) != t.indexOf(ch2[i])){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s1 = "egg";
        String t1 = "add";
        String s2 = "foo";
        String t2 = "bar";
        String s3 = "paper";
        String t3 = "title";
        System.out.println(isIsomorphic(s2,t2));
    }

}
```
