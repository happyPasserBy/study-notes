# intersect
> 给定两个数组，编写一个函数来计算它们的交集。
## 1. 解题思路- Map
### 1.1 思路
> 使用map的key记录每个元素，value记录键出现的次数，最后map与第二个参数数组对比取值
### 1.2 具体实现
```
public class Intersection {
        public static int[] intersection(int[] nums1, int[] nums2) {
            int[] result = new int[Math.min(nums1.length,nums2.length)];
            if(nums1.length == 0 || nums2.length == 0) {
                return result;
            }
            Map<Integer,Integer> nums1Map = new HashMap<>();
            for (int i = 0; i < nums1.length; i++) {
                if(nums1Map.containsKey(nums1[i])) {
                    nums1Map.put(nums1[i],nums1Map.get(nums1[i])+1);
                }else{
                    nums1Map.put(nums1[i],1);
                }
            }
            List<Integer> list = new ArrayList<>();
            for (int i : nums2) {
                if(nums1Map.containsKey(i) && nums1Map.get(i).intValue()!=0){
                    nums1Map.put(i,nums1Map.get(i).intValue()-1);
                    list.add(i);
                }
            }
            return list.stream().filter(Objects::nonNull).mapToInt(Integer::valueOf).toArray();
        }
    public static void main(String[] args) {
        int[] arr1 = {1,2,2,1};
        int[] arr2 = {2,2};
        int[] result = intersection(arr1,arr2);
        for (int i : result) {
            System.out.println(i);
        }
    }
}
```