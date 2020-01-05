# LetterCombinations
> 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。[链接](https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/)

## 1.解题思路一 回溯
### 1.1 思路
> 
### 1.2 具体实现
```
public class LtterCombinations {
    private static List<String> ret = new ArrayList<String>();
    private static Map<String, String> keyword = new HashMap<String, String>() {{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
    }};
    private static void combinations(String digits, int index, String str){
        if(index == digits.length()){
            ret.add(str);
            return;
        }
        String cur = keyword.get(String.valueOf(digits.charAt(index)));
        for (int i = 0; i < cur.length(); i++) {
            combinations(digits,index+1,str+cur.charAt(i));
        }
    }
    public static List<String> letterCombinations(String digits) {
        ret.clear();
        if(digits.length() == 0) return ret;
        combinations(digits,0,"");
        return ret;
    }
    public static void main(String[] args) {
        System.out.println(letterCombinations("234").size());
    }
}
```