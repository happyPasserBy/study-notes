# MinimumTotal
> [链接](https://leetcode-cn.com/problems/triangle/)
```
给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。

例如，给定三角形：
[
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]
自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。

说明：
如果你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题，那么你的算法会很加分。
```
## 1. 解题思路一 递归
### 1.1 思路
> 单纯的递归每个节点累加其值即可。
### 1.2 具体实现
```
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        if(triangle.size() == 0)return 0;
        return sum(triangle,1,0,triangle.get(0).get(0));
    }
    private static int sum(List<List<Integer>> triangle, int line, int preIndex, int total){
        if(triangle.size() <= line)return total;

        int sum1 = sum(triangle, line+1, preIndex, total+triangle.get(line).get(preIndex));
        int sum2 = sum(triangle, line+1, preIndex+1, total+triangle.get(line).get(preIndex+1));

        return Math.min(sum1, sum2);
    }
}
```
## 2. 解题思路二 记忆化搜索
### 2.1 思路
> 递归的方式中出现了重复计算，可以采用缓存的方式将计算结构存储起来，方便下次使用
### 2.2 具体实现
```
public class MinimumTotal {
    public static int minimumTotal(List<List<Integer>> triangle) {
        if(triangle.size() == 0)return 0;
        map.clear();
        return memeorySeracr(triangle,0,0,triangle.get(0).get(0));
    }

    private static Map<String,Integer> map = new HashMap<>();
    private static int memeorySeracr(List<List<Integer>> triangle, int line, int preIndex, int total){
        if(triangle.size() <= line+1)return total;
        if(map.containsKey(line+"-"+preIndex)) return map.get(line+"-"+preIndex);

        int sum1 = memeorySeracr(triangle, line+1, preIndex, triangle.get(line+1).get(preIndex))+total;
        int sum2 = memeorySeracr(triangle, line+1, preIndex+1, triangle.get(line+1).get(preIndex+1))+total;

        int min = Math.min(sum1, sum2);
        map.put(line+"-"+preIndex,min);

        return min;
    }

    public static void main(String[] args) {
        String arr1 = "[[2],[3,4],[6,5,7],[4,1,8,3]]"; // 11
        String arr2 = "[[-7],[-2,1],[-5,-5,9],[-4,-5,4,4],[-6,-6,2,-1,-5],[3,7,8,-3,7,-9],[-9,-1,-9,6,9,0,7],[-7,0,-6,-8,7,1,-4,9],[-3,2,-6,-9,-7,-6,-9,4,0],[-8,-6,-3,-9,-2,-6,7,-5,0,7],[-9,-1,-2,4,-2,4,4,-1,2,-5,5],[1,1,-6,1,-2,-4,4,-2,6,-6,0,6],[-3,-3,-6,-2,-6,-2,7,-9,-5,-7,-5,5,1]]"; //  -63
        System.out.println(minimumTotal(stringToInt2dList(arr2)));
    }

    private static List<Integer> stringToIntegerList(String input) {
        JSONArray jsonArray = new JSONArray(input);
        List<Integer> arr = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            arr.add(jsonArray.getInt(i));
        }
        return arr;
    }

    public static List<List<Integer>> stringToInt2dList(String input) {
        JSONArray jsonArray = new JSONArray(input);
        if (jsonArray.length() == 0) {
            return new ArrayList<List<Integer>>();
        }

        List<List<Integer>> list = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray cols = jsonArray.getJSONArray(i);
            list.add(stringToIntegerList(cols.toString()));
        }
        return list;
    }
}
```
## 3. 解题思路二 自底而上的动态规划
### 3.1 思路
> 采用了与记忆化搜索相反的"自底而上"的计算思路。
### 3.2 具体实现
```
public class MinimumTotal {
    private static int db(List<List<Integer>> triangle){
        if(triangle == null || triangle.size() == 0)return 0;
        int size = triangle.size();

        for (int i = size-2; i >= 0 ; i--) {
            for (int j = 0; j < i+1; j++) {
                triangle.get(i).set(j,Math.min(triangle.get(i+1).get(j),triangle.get(i+1).get(j+1)) + triangle.get(i).get(j));
            }
        }
        return triangle.get(0).get(0);
    }

    public static void main(String[] args) {
        String arr1 = "[[2],[3,4],[6,5,7],[4,1,8,3]]"; // 11
        String arr2 = "[[-7],[-2,1],[-5,-5,9],[-4,-5,4,4],[-6,-6,2,-1,-5],[3,7,8,-3,7,-9],[-9,-1,-9,6,9,0,7],[-7,0,-6,-8,7,1,-4,9],[-3,2,-6,-9,-7,-6,-9,4,0],[-8,-6,-3,-9,-2,-6,7,-5,0,7],[-9,-1,-2,4,-2,4,4,-1,2,-5,5],[1,1,-6,1,-2,-4,4,-2,6,-6,0,6],[-3,-3,-6,-2,-6,-2,7,-9,-5,-7,-5,5,1]]"; //  -63
        System.out.println(db(stringToInt2dList(arr2)));
    }

    private static List<Integer> stringToIntegerList(String input) {
        JSONArray jsonArray = new JSONArray(input);
        List<Integer> arr = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            arr.add(jsonArray.getInt(i));
        }
        return arr;
    }

    public static List<List<Integer>> stringToInt2dList(String input) {
        JSONArray jsonArray = new JSONArray(input);
        if (jsonArray.length() == 0) {
            return new ArrayList<List<Integer>>();
        }

        List<List<Integer>> list = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray cols = jsonArray.getJSONArray(i);
            list.add(stringToIntegerList(cols.toString()));
        }
        return list;
    }
}
```

