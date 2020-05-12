# Multiply
> [链接](https://leetcode-cn.com/problems/multiply-strings/)
```
给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。

示例 1:
输入: num1 = "2", num2 = "3"
输出: "6"

示例 2:
输入: num1 = "123", num2 = "456"
输出: "56088"

说明：
num1 和 num2 的长度小于110。
num1 和 num2 只包含数字 0-9。
num1 和 num2 均不以零开头，除非是数字 0 本身。
不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。
```

## 1. 解题思路一 
### 1.2 具体实现
```java
public class Chapter {

    public static String multiply(String num1, String num2) {
        if("0".equals(num1) || "0".equals(num2))return "0";
        
        int[] arr = new int[num1.length()+num2.length()];

        // 从num1尾部开始循环，从个位开始乘
        for (int i = num1.length()-1; i >= 0 ; i--) {
            int n1 = num1.charAt(i) - '0';
            // 从num2尾部开始循环，从个位开始乘
            for (int j = num2.length()-1; j >= 0 ; j--) {
                int n2 = num2.charAt(j) - '0';
                /*
                    当前乘积的结果与arr数组有对应关系
                    arr[i+j+1] 对应个位
                    arr[i+j] 对应十位及以上
                */
                // 汇总结果
                int num = arr[i+j+1] + n1*n2;
                // num%10取个位数
                arr[i+j+1] = num%10;
                // num/10取十位及以上数字
                arr[i+j] += num/10;
            }
        }
        // 拼接结果
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if(i == 0 && arr[i] == 0) continue;
            stringBuilder.append(arr[i]);
        }


        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String num1 = "2";
        String num2 = "3";
        String num3 = "123";
        String num4 = "456";


        multiply(num3,num4);
    }
}


```