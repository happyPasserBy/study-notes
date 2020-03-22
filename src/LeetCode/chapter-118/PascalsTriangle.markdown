# PascalsTriangle
>[链接](https://leetcode-cn.com/problems/pascals-triangle/)
## 1.解题思路一
### 1.1 具体实现
```
public class PascalsTriangle {
    public static List<List<Integer>> generate(int numRows) {
        if(numRows == 0)return new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> row1 = new ArrayList<>();
        row1.add(1);
        List<Integer> row2 = new ArrayList<>();
        row2.add(1);
        row2.add(1);
        res.add(row1);
        if(numRows == 1)return res;
        res.add(row2);
        if(numRows == 2)return res;
        for (int i = 2; i < numRows; i++) {
            generateRow(res,i);
        }
        return res;
    }

    public static void generateRow(List<List<Integer>> lists,Integer createRow){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        List<Integer> preList = lists.get(createRow-1);
        for (int i = 1; i <= createRow; i++) {
            if(i == createRow) {
                list.add(1);
            }else {
                list.add(preList.get(i-1)+preList.get(i));
            }
        }
        lists.add(list);
    }

    public static void main(String[] args) {
        System.out.println(generate(5));
    }
}
```