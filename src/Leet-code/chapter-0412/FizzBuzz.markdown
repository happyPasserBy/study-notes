# Fizz Buzz
> 写一个程序，输出从 1 到 n 数字的字符串表示。如果 n 是3的倍数，输出“Fizz”；如果 n 是5的倍数，输出“Buzz”；如果 n 同时是3和5的倍数，输出 “FizzBuzz”。[链接](https://leetcode-cn.com/problems/fizz-buzz/)

## 1.解题思路一 简单循环
### 1.1 思路
> 创建循环 逐一进行 % 运算进行判断
### 1.2 具体实现
```
public class FizzBuzz {
    public static List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= n; i++){
            if(i%3 == 0 && i%5==0){
                list.add("FizzBuzz");
            }else if(i%3 == 0){
                list.add("Fizz");
            }else if(i%5 == 0){
                list.add("Buzz");
            }else {
                list.add(String.valueOf(i));
            }

        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(fizzBuzz(15));
    }
}
```

## 1.解题思路二 创建hash映射
### 1.1 思路
> 优化思路一，创建计算映射当判断条件增多时可减少代码便于理解
### 1.2 具体实现
```
public class FizzBuzz {
    public static List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>();
        HashMap<Integer,String> map = new HashMap<>();
        map.put(3,"Fizz");
        map.put(5,"Buzz");
        for (int i = 1; i <= n; i++){
            String str = "";
            for (Integer integer : map.keySet()) {
                if(i % integer == 0){
                    str+= map.get(integer);
                }
            }
            if(str.equals("")){
                str = String.valueOf(i);
            }
            list.add(str);
        }
        return list;
    }
    public static void main(String[] args) {
        System.out.println(fizzBuzz(15));
    }
}
```




