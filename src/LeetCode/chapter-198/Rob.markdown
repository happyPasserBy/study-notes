# Rob
> 
## 1. 解题思路一 自上而下的递归+记忆化搜索
### 1.1 思路

### 1.2 具体实现
```
public class Rob {
    private static int[] memory;

    private int tryRob(int[] nums,int index){
        if(index >= nums.length) return 0;
        int price = 0;
        if(memory[index] != -1) return memory[index];
        for (int i = index; i < nums.length; i ++) {
            price = Math.max(price,nums[i] + tryRob(nums,i+2));
        }
        memory[index] = price;
        return price;
    }

    public int rob(int[] nums) {
        memory = new int[nums.length];
        Arrays.fill(memory,-1);
        return tryRob(nums,0);
    }
}
```

## 2. 解题思路一 自下而上的动态规划
### 2.1 思路
> 
### 2.2 具体实现
```
public class Rob198 {

    public int rob(int[] nums) {
        int n = nums.length;
        if(n == 0)
            return 0;
        int[] memo = new int[nums.length];
        memo[n - 1] = nums[n - 1];
        for(int i = n - 2 ; i >= 0 ; i --)
            memo[i] = Math.max(memo[i + 1], nums[i] + (i + 2 < n ? memo[i + 2] : 0));

        return memo[0];
    }
}
```

## 参考
1. https://coding.imooc.com/class/82.html