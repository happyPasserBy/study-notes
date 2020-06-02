# GenerateParenthesis
> [链接](https://leetcode-cn.com/problems/bracket-lcci/)
```
括号。设计一种算法，打印n对括号的所有合法的（例如，开闭一一对应）组合。

说明：解集不能包含重复的子集。

例如，给出 n = 3，生成结果为：

[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]
```
## 1. 解题思路一 回溯法
### 1.1 具体实现
```java
public class Interview {

    private static final List<String> res = new ArrayList<>();

    /*
        生成括号是不外乎两种情况,添加左括号或右括号，知道括号使用完毕
    */
    private static void generate(StringBuilder parenthesis, int left, int right){
        // 如果左右括号都使用完则添加到结果集
        if(left == 0 && right == 0) {
            res.add(parenthesis.toString());
            return;
        }
        
        // 左括号数大于0时才可以生成，
        if(left > 0) {
            parenthesis.append("(");
            // 将left-1并继续递归添加，
            generate(parenthesis,left-1,right);
            // 回溯
            parenthesis.replace(parenthesis.length()-1,parenthesis.length(),"");
        }
        // right大于0，right > left是因为需要保证生成括号的合法性，也就是说生成右括号之前必须生成左括号
        if(right > 0 && right > left){
            parenthesis.append(")");
            // 递归添加
            generate(parenthesis,left,right-1);
            // 回溯
            parenthesis.replace(parenthesis.length()-1,parenthesis.length(),"");
        }

    }

    public static List<String> generateParenthesis(int n) {
        // 静态变量，每次清空
        res.clear();
        // 开始递归生成，生成的括号数一共是n*2个,左括号是n个，右括号同样也是n个。
        generate(new StringBuilder(), n, n);
        return res;
    }

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
    }
}

```