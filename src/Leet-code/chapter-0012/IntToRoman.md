# IntToRoman
> [链接](https://leetcode-cn.com/problems/integer-to-roman/)
```
罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
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
## 1. 解题思路一 贪心
### 1.1 思路
> 在已知的罗马数字中找出小于num且是所有小于num中最大的那个值cur，使num-cur，不断重复直到num<0为止，这就是贪心算法，每次减去最大值。那如何快速找到cur呢？使用TreeMap的floorKey方法可以获取TreeMap中小于指定数字的最大值，没有则返回null。
### 1.2 具体实现
```
public class Chapter {zh

    private static final TreeMap<Integer,String> map = new TreeMap<>();

    static {
        map.put(1000,"M");
        map.put(900,"CM");
        map.put(500,"D");
        map.put(400,"CD");
        map.put(100,"C");
        map.put(90,"XC");
        map.put(50,"L");
        map.put(40,"XL");
        map.put(10,"X");
        map.put(9,"IX");
        map.put(5,"V");
        map.put(4,"IV");
        map.put(1,"I");
    }


    public static String intToRoman(int num) {
        StringBuilder stringBuilder = new StringBuilder();
        while (num > 0) {
            Integer floor = map.floorKey(num);
            if(floor == null) break;

            num -= floor;
            stringBuilder.append(map.get(floor));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        int num1 = 3;
        int num2 = 4;
        int num3 = 9;
        int num4 = 12;
        System.out.println(intToRoman(num4));


//        System.out.println("firstKey:"+map.firstKey());
//        System.out.println("lastKey:"+map.lastKey());
//        System.out.println("ceilingKey: 51"+map.ceilingKey(51));
//        System.out.println("ceilingKey 1001:"+map.ceilingKey(1001));
//        System.out.println("floorKey 51:"+map.floorKey(51));
//        System.out.println("floorKey 1001:"+map.floorKey(1001));
    }

}
```