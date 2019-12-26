# ThreeSum
> 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。注意：答案中不可以包含重复的三元组。 [连接](https://leetcode-cn.com/problems/3sum/)

## 1. 如何判定重复
* 一个三元组内同一下标的元素只可出现一次
* 多个三元组判断重复为不区分顺序、下标，只要数字相同则为重复
## 2. 解题思路一 排序+对撞指针
### 2.1 思路 (解题思路来源于最下方参考)
> 解题的关键在于如何高效的去重与查找。先排序将相同元素分组(相同的元素挨在一起)，这样就可跳过连续的元素达到去重。因为数组有序可使用对撞指针查找元素，i为当前元素、left为i+1(left为i+1也是去重，因i从下标0开始依次遍历，对撞指针依次对比可保证i之前符合条件的元素已经被筛选出来)、right为当前数组最大值，此时根据三数之和判断是增大或缩小进而不断查找相加为0的元素
### 2.2 具体实现
```
public class ThreeSum {
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        if(nums.length == 0) return ret;

        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            // 排序后当前值大于0 则后续两数值必定大于0
            if(nums[i] > 0) break;
            // 去重，nums[i]==nums[i-1]的原因是i之后的相同元素可以重复使用，但i之前的元素在下方的对撞指针扫描时必定使用过所以可跳过
            if(i > 0 && nums[i] == nums[i-1]) continue;
            int left = i+1;
            int right = nums.length-1;
            if( left >= nums.length || left >= right) continue;
            while (left < right){
                int sum = nums[i] + nums[left] + nums[right];
                if(sum == 0) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    ret.add(list);
                    // 此处去重原因为i不变，如果left不去重，right值也必定不变导致结果重复
                    while (left<right && nums[right] == nums[right-1])right--;
                    while (left<right && nums[left] == nums[left+1])left++;
                    left++;
                    right--;
                }
                // 因排序，最大值在最右侧，>0 则 right-- 缩小数字和
                if(sum > 0) {
                    right--;
                }
                // 因排序，最小值在最左侧，<0 则 left++ 增大数字和
                if(sum < 0) {
                    left++;
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] arr = {-1, 0, 1, 2, -1, -4}; // [[-1,-1,2],[-1,0,1]]
        int[] arr2 = {-2,0,1,1,2}; // [[-2,0,2],[-2,1,1]]
        int[] arr3 = {-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0};
        int[] arr4 = {0,0,0};
        int[] arr5 = {7,5,-8,-6,-13,7,10,1,1,-4,-14,0,-1,-10,1,-13,-4,6,-11,8,-6,0,0,-5,0,11,-9,8,2,-6,4,-14,6,4,-5,0,-12,12,-13,5,-6,10,-10,0,7,-2,-5,-12,12,-9,12,-9,6,-11,1,14,8,-1,7,-13,8,-11,-11,0,0,-1,-15,3,-11,9,-7,-10,4,-2,5,-4,12,7,-8,9,14,-11,7,5,-15,-15,-4,0,0,-11,3,-15,-15,7,0,0,13,-7,-12,9,9,-3,14,-1,2,5,2,-9,-3,1,7,-12,-3,-1,1,-2,0,12,5,7,8,-7,7,8,7,-15};
        System.out.println(threeSum(arr2));
       Arrays.stream(arr).forEach(i-> System.out.println(i));
    }
}
```

## 3. 解题思路二 hash表
### 3.1 思路
> 这是我第一次的思路，解题"超出实现限制"，在此记录一下，方便以后总结失败原因
### 3.2 具体实现
```
    public class ThreeSum15 {
    public static List<List<Integer>> threeSum2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        HashMap<Integer,List<Integer>> hashMap = new HashMap<>();
        HashSet<String> hashSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            int key = nums[i];
            if(hashMap.containsKey(key)) {
                hashMap.get(key).add(i);
            }else{
                List<Integer> list = new ArrayList<>();
                list.add(i);
                hashMap.put(key,list);
            }
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if(i == j) continue;
                int search = 0-nums[i]-nums[j];
                if(hashMap.containsKey(search)){
                    List<Integer> list = hashMap.get(search);
                    for (int k = 0; k < list.size(); k++) {
                        if(list.get(k) == i || list.get(k) == j)continue;
                        List<Integer> indexList = new ArrayList<>();
                        indexList.add(nums[i]);
                        indexList.add(nums[j]);
                        indexList.add(search);
                        indexList = indexList.stream().sorted().collect(Collectors.toList());
                        String curListString = indexList.toString();
                        // 去重
                        if(hashSet.contains(curListString)) continue;
                        result.add(indexList);
                        hashSet.add(curListString);
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {-1, 0, 1, 2, -1, -4}; // [[-1,-1,2],[-1,0,1]]
        int[] arr2 = {-2,0,1,1,2}; // [[-2,0,2],[-2,1,1]]
        int[] arr3 = {-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0};
        int[] arr4 = {0,0,0};
        int[] arr5 = {7,5,-8,-6,-13,7,10,1,1,-4,-14,0,-1,-10,1,-13,-4,6,-11,8,-6,0,0,-5,0,11,-9,8,2,-6,4,-14,6,4,-5,0,-12,12,-13,5,-6,10,-10,0,7,-2,-5,-12,12,-9,12,-9,6,-11,1,14,8,-1,7,-13,8,-11,-11,0,0,-1,-15,3,-11,9,-7,-10,4,-2,5,-4,12,7,-8,9,14,-11,7,5,-15,-15,-4,0,0,-11,3,-15,-15,7,0,0,13,-7,-12,9,9,-3,14,-1,2,5,2,-9,-3,1,7,-12,-3,-1,1,-2,0,12,5,7,8,-7,7,8,7,-15};
        System.out.println(threeSum(arr2));
        Arrays.sort(arr3);
       Arrays.stream(arr).forEach(i-> System.out.println(i));
    }
}
```
## 参考
1. https://leetcode-cn.com/problems/3sum/solution/three-sum-ti-jie-by-wonderful611/
2. https://leetcode-cn.com/problems/3sum/solution/3sumpai-xu-shuang-zhi-zhen-yi-dong-by-jyd/


