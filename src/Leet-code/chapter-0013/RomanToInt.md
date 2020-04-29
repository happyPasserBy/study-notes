# RomanToInt
> [链接](https://leetcode-cn.com/problems/roman-to-integer/)
```
罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。

字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000

例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：

I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
```
## 1. 解题思路一
### 1.1 思路
> 创建HashMap存储7种罗马数字，循环字符串，如果当前i大于i-1说明存在特殊情况，从sum中减去上一个值并加上当前i-(i-1)的值，知道循环完毕即可。
### 1.2 具体实现
```
public class Chapter {

    static final HashMap<String,Integer> map = new HashMap<>();

    static {
        map.put("I",1);
        map.put("V",5);
        map.put("X",10);
        map.put("L",50);
        map.put("C",100);
        map.put("D",500);
        map.put("M",1000);
    }

    public static int romanToInt(String s) {

        int sum = 0;

        for (int i = 0; i < s.length(); i++) {
            int cur = map.get(String.valueOf(s.charAt(i)));
            int pre = 0;

            if( i > 0 && cur > map.get(String.valueOf(s.charAt(i-1))) ){
                pre = map.get(String.valueOf(s.charAt(i-1)));
            }

            sum += cur - pre - pre;
        }
        return sum;
    }

    public static void main(String[] args) {
        String str1 = "III";
        String str2 = "IV";
        String str3 = "IX";
        System.out.println(romanToInt(str3));
    }
}
```