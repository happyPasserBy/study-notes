# PascalsTriangleIi
```
public class PascalsTriangleIi  {
    public static List<Integer> getRow(int rowIndex) {
        List<Integer> res = new ArrayList<>(rowIndex);
        res.add(1);
        if(rowIndex == 0)return res;
        res.add(1);
        if(rowIndex == 1)return res;
        for (int i = 2; i <= rowIndex; i++) {
            create(res,i);
        }
        return res;
    }
    private static void create(List<Integer> list,Integer curIndex){
        Integer cur = null;
        Integer pre = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            cur = list.get(i);
            list.set(i,cur+pre);
            pre = cur;
        }
        list.add(1);
    }

    public static void main(String[] args) {
        System.out.println(getRow(3));
    }
}
```