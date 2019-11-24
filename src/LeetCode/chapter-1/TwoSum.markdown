# TwoSum
> 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
## 1.解题思路一 暴力解法O(n^2)
### 1.1 思路
> 双重循环
### 1.2 具体实现
```
待实现
```
## 2. 解题思路二 对撞指针
### 2.1 思路
> 先进行排序并记录每个元素的下标，然后使用对撞指针查找元素
### 2.2 具体实现
```
待实现
```
## 3.解题思路三 hash表 O(n)
### 3.1 思路
> 先将元素存入hash表，循环数组在hash表中查询目标值
### 3.2 具体实现
```
public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        Map map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i],i);
        }
        for (int i = 0; i < nums.length; i++) {
            int search = target-nums[i];
            if(map.containsKey(search) && (int)map.get(search) != i) {
                result[0] = i;
                result[1] = (int)map.get(search);
                return result;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {2, 7, 11, 15};
        int[] result = twoSum(arr,9);
        for (int i : result) {
            System.out.println(i);
        }
    }
}
```




