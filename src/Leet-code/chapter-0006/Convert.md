# Convert
> [链接](https://leetcode-cn.com/problems/zigzag-conversion/)
```
将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
L   C   I   R
E T O E S I I G
E   D   H   N
之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
请你实现这个将字符串进行指定行数变换的函数：string convert(string s, int numRows);

示例 1:
输入: s = "LEETCODEISHIRING", numRows = 3
输出: "LCIRETOESIIGEDHN"

示例 2:
输入: s = "LEETCODEISHIRING", numRows = 4
输出: "LDREOEIIECIHNTSG"
解释:
L     D     R
E   O E   I I
E C   I H   N
T     S     G

```
## 1. 解题思路一 Z字形读取
### 1.1 思路 
> 先将一行的字符串转换为numRows行的Z字形字符串，然后在拼接每行返回即可。[来源于leetcode官方题解](https://leetcode-cn.com/problems/zigzag-conversion/solution/z-zi-xing-bian-huan-by-leetcode/)
### 1.2 
```
public class Chapter {


    public static String convert(String s, int numRows) {
        if (numRows == 1) return s;

        List<StringBuilder> list = new ArrayList<>();
        /* 
            因为Z字形所以是多行，用list来保存多行，下标0位置代表第一行，下标1位置代表第二行以此类推，
            根据s.length与numRows来确定需要的行数。
        */
        for (int i = 0; i < Math.min(s.length(),numRows); i++) {
            list.add(new StringBuilder());
        }

        // 代表当前行
        int row = 0;
        // 判断方向
        boolean direction = false;

        for (Character str : s.toCharArray()) {
            // (1). 先添加字符创在移动方向
            list.get(row).append(str);
            /*

                开始按Z字形移动，direction控制上下移动，(1)的位置控制前后移动
                (2).
                row == 0 说明是第一行，需要向下移动
                row == list.size()-1 说明是最后一行，需要向上移动
            */
            if( row == 0 || row == list.size()-1) direction = row == 0 ?  true : false;
            // (3). 根据direction来移动行
            row += direction ? 1 : -1;
        }
        // 根据题意拼接返回值
        StringBuilder res = new StringBuilder();
        for (StringBuilder stringBuilder : list) {
            res.append(stringBuilder);
        }

        return res.toString();
    }

    public static void main(String[] args) {
        String str1 = "LEETCODEISHIRING";
        int rows1 = 4;
        String str2 = "LEE";
        int rows2 = 4;
        String str3 = "AB";
        int rows3 = 1;
        System.out.println(convert(str3,rows3));

    }

}
```
