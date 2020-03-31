# IsOneBitCharacter
> [链接](https://leetcode-cn.com/problems/1-bit-and-2-bit-characters/)
```
有两种特殊字符。第一种字符可以用一比特0来表示。第二种字符可以用两比特(10 或 11)来表示。
现给一个由若干比特组成的字符串。问最后一个字符是否必定为一个一比特字符。给定的字符串总是由0结束。

示例 1:
输入: 
bits = [1, 0, 0]
输出: True
解释: 
唯一的编码方式是一个两比特字符和一个一比特字符。所以最后一个字符是一比特字符。
```
## 1. 解题思路一 循环计数
### 1.1 思路
> 根据题意得知bits中只会出现1或0，循环数组当i为1时向后移动两部，当i为0时向后移动一步，最后判断i是否为最后一位即可
### 1.2 具体实现
```
public class Chapter {
    public static boolean isOneBitCharacter(int[] bits) {
        int i = 0;
        while (i < bits.length-1) {
            if(bits[i] == 1) {
                i+=2;
            }else {
                i++;
            }
        }
        return i == bits.length-1;
    }
    public static void main(String[] args) {
        int[] arr1 = {1, 0, 0};
        int[] arr2 = {1, 1, 1, 0};
        int[] arr3 = {1, 0};
        System.out.println(isOneBitCharacter(arr2));
    }
}
```


