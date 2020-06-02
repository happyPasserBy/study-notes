# PrintNumbers
> https://leetcode-cn.com/problems/da-yin-cong-1dao-zui-da-de-nwei-shu-lcof/
```
输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。

示例 1:
输入: n = 1
输出: [1,2,3,4,5,6,7,8,9]

说明：
用返回一个整数列表来代替打印
n 为正整数
```
## 1. 解题思路一 求幂
### 1.1 具体实现
```
class Solution {
    public int[] printNumbers(int n) {
        int num = (int)Math.pow(10,n);
        int[] res = new int[num-1];
        for (int i = 1; i < num; i++) {
            res[i-1] = i;
        }
        return  res;
    }
}
```
## 2. 解题思路二 字符串大数
### 2.1 具体实现
```
public static void printNumbers(int n){
    StringBuilder stringBuilder = new StringBuilder();
    // 用字符创创建最大值，用0占位
    for (int i = 0; i < n; i++) {
        stringBuilder.append(0);
    }

    while (!add(stringBuilder)){
        int index = 0;
        while (index < stringBuilder.length() && stringBuilder.charAt(index) == '0'){
            index++;
        }
        System.out.println(stringBuilder.toString().substring(index));
    }

}
// 对字符串进行++
public static boolean add(StringBuilder str){
    // 判断是否溢出
    boolean scale = false;
    // 从个位开始增加
    for (int i = str.length() - 1; i >= 0; i--) {
        char s = (char)(str.charAt(i) + 1);
        
        // 判断是否需要进位
        if (s > '9') {
            str.replace(i, i + 1, "0");
            // 如果i==0已经到了最大值
            if (i == 0) {
                scale = true;
            }
        }else { // 不需要进位直接++
            str.replace(i, i + 1, String.valueOf(s));
            break;
        }
    }
    return scale;
}
```