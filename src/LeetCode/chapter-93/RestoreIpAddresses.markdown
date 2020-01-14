# RestoreIpAddresses
> 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。[链接](https://leetcode-cn.com/problems/restore-ip-addresses/)
## 1. 解题思路一 回溯
## 1.2 具体实现
```
public class RestoreIpAddresses {

    private static List<String> res = new ArrayList<>();

    private static void split(String str, int index, StringBuilder section, int stage){
        if(stage == 4 && index == str.length() && section.length()-4 == str.length()){// section.length()-4 == str.length() 判断多个0合并的情况
            res.add(section.replace(section.length()-1,section.length(),"").toString());
        }
        if(stage == 4 && index < str.length())return;
        for (int i = index; i <= str.length() ; i++) {
            if(i == index)continue;
            Long num = Long.parseLong(str.substring(index,i)); // 多个0则合并为一个0
            if(num >= 256) break;
            split(str,i,new StringBuilder(section.toString()).append(num+"."),stage+1);
        }
    }
    public static List<String> restoreIpAddresses(String s) {
        res.clear();
        split(s,0,new StringBuilder(),0);
        return res;
    }
    public static void main(String[] args) {
        String str1 = "25525511135";
        String str2 = "010010"; // ["0.10.0.10","0.100.1.0"]
        System.out.println(restoreIpAddresses(str2));
    }
}
```