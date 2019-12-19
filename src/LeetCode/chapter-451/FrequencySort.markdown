# FrequencySort
> 给定一个字符串，请将字符串里的字符按照出现的频率降序排列。 [链接](https://leetcode-cn.com/problems/sort-characters-by-frequency/)
## 1.解题思路一 
### 1.1 思路
> 循环字符串统计每个字符出现的次数存到TreeMap中，key:每个字符，value:出现次数，将key、value转为两个数组根据下标作为关联关系，最后排序拼接字符串，此种方式实现麻烦，时间、空间复杂度极高所以放弃
### 1.2 具体实现
```
public class FrequencySort {

    public static String frequencySort(String s) {
        TreeMap<String,Integer> treeMap = new TreeMap<>();
        for (int i = 0; i < s.length(); i++) {
            String str = String.valueOf(s.charAt(i));
            if(treeMap.containsKey(str)){
                treeMap.put(str,treeMap.get(str)+1);
            }else {
                treeMap.put(str,1);
            }
        }
        Integer[] sort1 = treeMap.values().toArray(new Integer[]{});
        String[] key = treeMap.keySet().toArray(new String[]{});
        Integer temp = null;
        String temp2 = null;
        for(int i=0;i<sort1.length-1;i++){
            for(int j=i+1;j>0;j--){
                if(sort1[j] < sort1[j-1]){
                    temp = sort1[j-1];
                    sort1[j-1] = sort1[j];
                    sort1[j] = temp;
                    temp2 = key[j-1];
                    key[j-1] = key[j];
                    key[j] = temp2;
                }else{         //不需要交换
                    break;
                }
            }
        }
        // 放弃
        return "";
    }
    public static void main(String[] args) {
        String str1 = "tree";
        System.out.println(frequencySort(str1));
    }
}
```
## 1.解题思路二 
### 1.1 思路(来自LeetCode解答)
### 1.2 具体实现
```
public class FrequencySort {
    public String frequencySort(String s) {
        HashMap<Integer, String> map = new HashMap<>();

        int[] freq = new int[256];
        for (char c : s.toCharArray()) {
            freq[c]++;
        }

        for (int i = 0; i < freq.length; i++) {
            if (freq[i] != 0) {
                char ch = (char) i;

                String str = map.get(freq[i]);
                // 字符出现次数相同, 进行拼接
                if (str != null) {
                    String strNew = str.concat(build(ch, freq[i]));
                    map.put(freq[i], strNew);
                }else {
                    map.put(freq[i], build(ch, freq[i]));
                }
            }
        }

        Integer[] keys = map.keySet().toArray(new Integer[]{});
        Arrays.sort(keys);
        StringBuilder sbl = new StringBuilder();
        for (int i = keys.length - 1; i >= 0; i--) {
            sbl.append(map.get(keys[i]));
        }

        return sbl.toString();
    }

    private String build(char ch, int times) {
        String string = Character.toString(ch);
        StringBuilder res = new StringBuilder(string);
        int t = 1;
        while (t < times) {
            res.append(string);
            t++;
        }

        return res.toString();
    }
    public static void main(String[] args) {
        String str1 = "tree";
        System.out.println(frequencySort(str1));
    }
}
```