# subarraySum
> [链接](https://leetcode-cn.com/problems/subarray-sum-equals-k/)
```
给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。

示例 1 :
输入:nums = [1,1,1], k = 2
输出: 2 , [1,1] 与 [1,1] 为两种不同的情况。

说明 :
数组的长度为 [1, 20,000]。
数组中元素的范围是 [-1000, 1000] ，且整数 k 的范围是 [-1e7, 1e7]。
```
## 1. 解题思路一 暴力解法
### 1.1 思路
> 创建额外的数组sum，sum[i]存储从0到nums[i]的和，循环nums创建两个指针i与j，通过sum[j]-sum[i]求出是否等于k。
### 1.2 具体实现
```
public class Chapter {

    public static int subarraySum(int[] nums, int k) {
        int[] sum = new int[nums.length+1];
        sum[0] = 0;
        int count = 0;
        for (int i = 1; i <= nums.length; i++) {
            sum[i] = nums[i-1]+sum[i-1];
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < sum.length; j++) {
                if(sum[j] - sum[i] == k) {
                    count++;
                }
            }
        }

        return count++;

    }

    public static void main(String[] args) {
        int[] arr1 = {1,1,1};
        int k1 = 2;
        int[] arr2 = {1,2,3};
        int k2 = 3;
        System.out.println(subarraySum(arr1,k1));
    }
}
```

## 2. 解题思路二 暴力解法(累计和)
### 2.1 思路
> 优化了第一种暴力解法，从O(n)的空间复杂度减低至O(1),不在创建sum,而是每次创建累计变量。
### 2.2 具体实现
```
public class Chapter {

    public static int subarraySum(int[] nums, int k) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum+=nums[j];
                if(sum == k){
                    count++;
                }
            }
        }

        return count;

    }


    public static void main(String[] args) {
        int[] arr1 = {1,1,1};
        int k1 = 2;
        int[] arr2 = {1,2,3};
        int k2 = 3;
        System.out.println(subarraySum(arr1,k1));
    }
}

```
## 3. 解题思路二 HashMap
### 3.1 思路
> 使用map存储和与求出该和的子数组个数，循环nums并求出每个节点的子数组，利用当前子数组与k的差值查找map中是否包含该差值，如何包含则说明只要有map对应的value个结果。
### 3.2 具体实现
```
public class Chapter {

    public static int subarraySum(int[] nums, int k) {
        int count = 0;
        Map<Integer,Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if(map.containsKey(sum-k)){
                count+= map.get(sum-k);
            }
            map.put(sum,map.getOrDefault(sum,0)+1);
        }

        return count;

    }

    public static void main(String[] args) {
        int[] arr1 = {1,1,1};
        int k1 = 2;
        int[] arr2 = {1,2,3};
        int k2 = 3;
        System.out.println(subarraySum(arr1,k1));
    }
}
```