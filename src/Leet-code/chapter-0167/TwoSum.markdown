# TwoSum
> 给定一个已按照升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。

## 1. 解题方式一 暴力枚举 O(n^2)
### 1.1 思路
> 双重循环依次对比
### 1.2 具体实现
```
public class TwoSum {

    public static int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        if (numbers.length < 2) {
            return result;
        }
        for (int i = 0; i < numbers.length; i++) {
            for (int i1 = i + 1; i1 < numbers.length; i1++) {
                if (numbers[i] + numbers[i1] == target) {
                    result[0] = i + 1;
                    result[1] = i1 + 1;
                    return result;
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        int[] arr = {0,0,1,2};
        int[] result = new int[2];
        result = twoSum(arr, 0);
        for (int i : result) {
            System.out.println(i);
        }
    }

}
```

## 2. 解题方式二 枚举+二分查找 O(nlogn)
### 2.1 思路
> 优化暴力枚举，基于排序特性使得第二重循环改为二分查找
### 2.2 具体实现
```
public class TwoSum {
    // todo 优化
    public static int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        if (numbers.length < 2) {
            return result;
        }
        for (int i = 0; i < numbers.length; i++) {
            int searchNum = target - numbers[i];
            int[] searchArr = new int[numbers.length - i - 1];
            System.arraycopy(numbers, i + 1, searchArr, 0, numbers.length - i - 1);
            int searchResult = binarySearch(searchArr, searchNum);

            if (searchResult != -1) {
                result[0] = i + 1;
                result[1] = searchResult + i + 2;
                return result;
            }
        }
        return result;
    }

    public static int binarySearch(int[] arr, int num) {
        int L = 0;
        int R = arr.length - 1;
        while (L <= R) {
            int middle = (L + R) / 2; // int middle = l+(r-l)/2 防止整型溢出
            if (arr[middle] > num) {
                R = middle - 1;
            } else if (arr[middle] < num) {
                L = middle + 1;
            } else if (arr[middle] == num) {
                return middle;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        int[] arr = {0,0,1,2};
        int[] result = new int[2];
        result = twoSum(arr, 0);
        for (int i : result) {
            System.out.println(i);
        }
    }

}
```
## 3.解题方式三 对撞指针 0(n)
### 3.1 思路
> 创建指针i、j, i指向数组头元素,j指向数组尾元素,循环判断当前头尾两元素相加是否等于查询元素，若大则尾指针--,若小则头指针++。
### 3.2 具体实现
```
// 相似问题 125、344、345、11 
public class TwoSum {
    public static int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        if(numbers.length < 1 || (numbers.length == 2 && numbers[0]+numbers[1] != target)) {
            return result;
        }
        int i = 0;
        int j = numbers.length-1;
        while(j-i > 0){
            int min = numbers[i];
            int max = numbers[j];
            if(min+max == target) {
                result[0] = i + 1;
                result[1] = j + 1;
                return result;
            }else if(min+max > target){
                j--;
            }else if(min+max < target) {
                i++;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        int[] arr = {0,0,1,2};
        int[] result = new int[2];
        result = twoSum(arr, 0);
        for (int i : result) {
            System.out.println(i);
        }
    }

}
```

