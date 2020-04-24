# Intersection
> 给定两个数组，编写一个函数来计算它们的交集。
## 1.思路一 数组去重
> 用集合类实现去重
### 1.1具体实现
```
public class Intersection {
    public static int[] intersection(int[] nums1, int[] nums2) {
        int[] result = new int[Math.min(nums1.length,nums2.length)];
        List<Integer> list = new ArrayList();
        if(nums1.length == 0 || nums2.length == 0) {
            return result;
        }

        Set nums1Set = new HashSet();
        for (int i = 0; i < nums1.length; i++) {
            nums1Set.add(nums1[i]);
        }

        for (int i = 0; i < nums2.length; i++) {
            if(nums1Set.contains(nums2[i])&&!list.contains(nums2[i])){
                 list.add(nums2[i]);
            }
        }
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    public static void main(String[] args) {
        int[] arr1 = {4,9,5};
        int[] arr2 = {9,4,9,8,4};
        int[] result = intersection(arr1,arr2);
        for (int i : result) {
            System.out.println(i);
        }

    }
}
```
## 2. 思路二 流优化代码，效率并不一定高
```
public class Intersection {
        Set<Integer> set1 = Arrays.stream(nums1).boxed().collect(Collectors.toSet());
        Set<Integer> set2 = Arrays.stream(nums2).boxed().collect(Collectors.toSet());
        set1.retainAll(set2);
        return set1.stream().mapToInt(Integer::intValue).toArray();
    }
    public static void main(String[] args) {
        int[] arr1 = {4,9,5};
        int[] arr2 = {9,4,9,8,4};
        int[] result = intersection(arr1,arr2);
        for (int i : result) {
            System.out.println(i);
        }
    }
}
```


