# LetterCombinations
> 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。[链接](https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/)

## 1.解题思路一 回溯
### 1.1 思路
![图示](./images/tree.png)
> 在解决回溯相关的问题时先把题意转为一个树，这样便于理解。
* 假设数字组合为 23，2代表abc，3代表def。问题是23所代表的字符共有多少种组合？
* 首先以2为节点，因2所代表的每个字符都可以进行组合所以创建出a、b、c三条线
* 在2节点的abc三条线后创建3节点并创建3节点所代表的def线，这样从图上可以看出abc与def的每种组合了。如果还有4节点则依次在def线后创建4节点并画出其所代表的字母线即可
* 现在我们根据刚才所画出的图来求出本题的解
* 1. 首先从 2节点 开始沿 a线 找到 3节点
* 2. 到达3节点后在沿 d线 找到 3节点 的下一个节点
* 3. 此时发现本题中只输入了 23 且 3节点 后并没有链接其它节点则刚才所经过的路线 ad 就是本题的其中一个解，保存 ad
* 4. 我们沿 d线 回到 3节点 准备下一次的查找
* 5. d线 我们刚才已经找过了，这次我们沿 e线 查找 3节点 的下一个节点
* 6. 此时发现本题中只输入了 23 且 3节点 后并没有链接其它节点则我们此时的路线 ae 就是本题的另一个解，保存 ae
* 7. 我们沿 e线 回到 3节点 准备下一次的查找
* 8. d、e线 我们刚才已经找过了，这次我们沿 f线 查找 3节点 的下一个节点
* 9. 本题中只输入了 23 且 3节点 后并没有链接其它节点则我们此时的路线 af 就是本题的另一个解，保存 af
* 10. 当前 a线 链接的3节点已经全部找过，从a线返回2节点准备下一次查找
* 11. 2节点 的 a路线 已经找完了我们沿 b线 找到 3节点
* 12. 我们到达 3节点 后依次沿不同的路线查找就像从上一个3节点 开始查找一样直至3个 3节点 全部查找完毕
* 通过以上的步骤就求出了本题的解，而这种求解的方式被称为回溯法,上边的第 4 步就可以称为回溯


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

## 参考
1. 图片来源于[链接](https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/solution/leetcode-17-letter-combinations-of-a-phone-number-/)