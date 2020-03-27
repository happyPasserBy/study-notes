# NumberOfBoomerangs
> 给定平面上 n 对不同的点，“回旋镖” 是由点表示的元组 (i, j, k) ，其中 i 和 j 之间的距离和 i 和 k 之间的距离相等（需要考虑元组的顺序）。找到所有回旋镖的数量。你可以假设 n 最大为 500，所有点的坐标在闭区间 [-10000, 10000] 中。
[链接](https://leetcode-cn.com/problems/number-of-boomerangs/)
## 1.解题思路一 暴力解法 O(n^3)
### 1.1思路
> 嵌套三重循环
## 2.解题思路二 查找表 O(n^2)
### 2.1思路 
> 第一层循环遍历每个坐标 i，第二重循环 j 遍历剩余的坐标并计算j到i的距离已距离为key,坐标个数为value存储到map中，遍历map查找 value > 1的值
### 2.2 具体实现
```
public class NumberOfBoomerangs {
    public static int numberOfBoomerangs(int[][] points) {
        int result = 0;
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if(i == j)continue;
                int disJ = (points[i][0] - points[j][0])*(points[i][0] - points[j][0]) + (points[i][1] - points[j][1])*(points[i][1] - points[j][1]);
                if(map.containsKey(disJ)) {
                    map.put(disJ,map.get(disJ)+1);
                }else{
                    map.put(disJ,1);
                }
            }
            List<Integer> list = map.values().stream().collect(Collectors.toList());
            for (int k = 0; k < list.size(); k++) {
                if(list.get(k) > 1){
                    int sum = list.get(k);
                    result += sum*(sum-1);
                }
            }
            map.clear();
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] arr = {{0,0},{1,0},{2,0}}; // 2
        int[][] arr1 = {{1,1},{2,2},{3,3}}; // 2
        System.out.println(numberOfBoomerangs(arr1));
    }
}
```