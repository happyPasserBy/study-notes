# AddStrings
> [链接](https://leetcode-cn.com/problems/add-strings/)
```
给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。
注意：

num1 和num2 的长度都小于 5100.
num1 和num2 都只包含数字 0-9.
num1 和num2 都不包含任何前导零。
你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式。
```
## 1.解题思路一 双指针
### 1.1 思路
> 创建两个指针分别指向两个字符串的末尾，创建一个保存进制的变量scale，同时循环两个字符串并移动指针，当指针指向的两个数字的和大于10则scale=1否则scale=0,下次循环时加上scale即可。
### 1.2 具体实现
```
public class Chapter {
    public static String addStrings(String num1, String num2) {

        int scale = 0;
        int indexA = num1.length()-1;
        int indexB = num2.length()-1;
        StringBuilder stringBuilder = new StringBuilder();

        while (indexA >= 0 || indexB >= 0) {
            int a = 0;
            int b = 0;
            if(indexA >= 0) {
                a = num1.charAt(indexA)- '0';
                indexA--;
            }
            if(indexB >= 0) {
                b = num2.charAt(indexB)- '0';
                indexB--;
            }
            int total = a+b+scale;
            scale = total/10;
            stringBuilder.append(total%10);
        }
        if(scale > 0){
            stringBuilder.append(1);
        }

        return stringBuilder.reverse().toString();

    }

    public static void main(String[] args) {
        String str1 = "0";
        String str2 = "0";

        String str3 = "10";
        String str4 = "11";

        String str5 = "9";
        String str6 = "99";

        String str7 = "123456789";
        String str8 = "987654321";
        System.out.println(addStrings(str7,str8));
    }
}

```

