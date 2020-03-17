# GenerateParenthesis
[链接](https://leetcode-cn.com/problems/generate-parentheses/)
```
给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
例如，给出 n = 3，生成结果为：
[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]
```

```
class Solution {
    private final static String left = "(";
    
    private final static String right = ")";

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        concat(res,n-1, n,left);
        return res;
    }

    public static void concat(List<String> res,int ln, int rn, String str){
        if(ln == 0 && rn == 0) {
            res.add(str);
            return;
        }
        if(ln > 0) {
            concat(res,ln-1, rn,str+"(");
        }
        if(ln < rn) {
            concat(res, ln, rn-1,str+")");
        }
    }
}
```