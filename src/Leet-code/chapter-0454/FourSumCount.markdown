# FourSumCount
> 给定四个包含整数的数组列表 A , B , C , D ,计算有多少个元组 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0。
[链接](https://leetcode-cn.com/problems/4sum-ii/)
## 1.解题思路一 暴力解法 O(n^4)
### 1.1 思路
> 四重循环
### 1.2 具体实现
```
```
## 2. 解题思路二 循环结合查找表O(n^2)
### 2.1 思路
> 优化暴力解法，将C、D两数组的所有相加结果方法查找表，循环A、B，在查找表中查找0-A[i]-B[j]的结果。
### 2.2 具体实现
```
public class FourSumCount {
    public static int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int result = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < D.length; j++) {
                int sum = C[i]+D[j];
                if(map.containsKey(sum)){
                    map.put(sum,map.get(sum).intValue()+1);
                }else{
                    map.put(sum, 1);
                }
            }
        }
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                int search = 0-(A[i]+B[j]);
                if(map.containsKey(search) && map.get(search) != 0){
                    result+=map.get(search).intValue();
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        int[] a = {-1,-1};
        int[] b = {-1,1};
        int[] c = {-1,1};
        int[] d = {1,-1};
        System.out.println(fourSumCount(a,b,c,d));
    }
}

```
