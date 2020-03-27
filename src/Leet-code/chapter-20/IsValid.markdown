# IsValid
> 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。 [链接](https://leetcode-cn.com/problems/valid-parentheses/)
## 1.解题思路一 栈
### 1.1 思路
> 使用栈先进后出的特性判断括号是否相对应,循环字符串遇到左括号则存储到栈中，遇到右括号则从栈顶取值判断是否对应
### 1.2 具体实现
```
public class IsValid {
    private static HashMap<String,String> map = new HashMap();
    public static boolean isValid(String s) {
        if(s.length() == 0)return true;
        if(s.length()%2 !=0) return false;
        Stack<String> stack = new Stack();
        String[] strs = s.split("");
        for (int i = 0; i < strs.length; i++) {
            if(strs[i].equals(")") ||  strs[i].equals("]") || strs[i].equals("}")){
                if(stack.empty())return false;
                String str = stack.pop();
                if(!map.get(str).equals(strs[i])){
                    return false;
                }
            }else{
                stack.push(strs[i]);
            }
        }
        return stack.empty() ? true : false;
    }

    public static void main(String[] args) {
        map.put("(",")");
        map.put("[","]");
        map.put("{","}");
        String str1 = "()";
        String str2 = "()[]{}";
        String str3 = "(]";
        String str4 = "((";
        String str5 = "){";
        String str6 = ")}{({))[{{[}";
        System.out.println(isValid(str6));
    }
}
```
