# FindMinDifference
> [链接](https://leetcode-cn.com/problems/minimum-time-difference/)
## 1. 解题思路一 
### 1.1 思路
> 为了方便计算，创建临时数组保存每个时间的分钟数并排序，循环临时数组对比相邻的两个值。
### 1.2 具体实现
```
public class Chapter {
    public static int findMinDifference(List<String> timePoints) {
        if(timePoints.size() > 1440){
            return 0;
        }
        int[] timeTemp = new int[timePoints.size()];
        for (int i = 0; i < timePoints.size(); i++) {
            int time = Integer.parseInt(timePoints.get(i).substring(0, 2)) * 60 + Integer.parseInt(timePoints.get(i).substring(3));
            if(time == 0) {
                time = 1440;
            }
            timeTemp[i] = time;
        }
        Arrays.sort(timeTemp);

        int min = Math.min(1440-timeTemp[timeTemp.length-1]+timeTemp[0],Math.abs(timeTemp[0]-timeTemp[timeTemp.length-1]));

        for (int i = 0; i < timeTemp.length-1; i++) {
            int curMin = Math.min(1440-timeTemp[i+1]+timeTemp[i], Math.abs(timeTemp[i]-timeTemp[i+1]));
            min = Math.min(min,curMin);
        }

        return min;
    }

    public static void main(String[] args) {
        List<String> list1 = Arrays.asList("05:31","22:08","00:35");
        List<String> list2 = Arrays.asList("23:59","00:00");
        System.out.println(findMinDifference(list2));
    }
}
```