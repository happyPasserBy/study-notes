# MoveZeros
## 1.实现思路
> 创建两个指针k、i, k指向的当前元素为0或待替换元素，k指针之前的元素必定不为0,i指针依次指向每个元素，将不为0的元素与k替换，依次类推，最后i大于索引后将k指针及其之后的元素全部替换为0
``` 
int k = 0;
int i = 0;
int[] num = [0,1,0,2];
1. 初始状态
 k
[0,1,0,2]
 i
2. 第一次循环，当前元素为0,则k不变，i++ 
 k
[0,1,0,2]
   i
3. 第二次循环，i指向的元素不为0则 num[k] = num[i],并且k++;i++;
 k
[1,1,0,2]
   i
   k
[1,1,0,2]
     i
4. 第三次循环，i指向的元素为0,i++,k不变
   k
[1,1,0,2]
     i
   k
[1,1,0,2]
       i

5. 第四次循环，i指向的元素不为0,则 num[k] = num[i],并且k++,i++。i超出索引范围改为-1,方便后续判断
   k
[1,1,0,2]
       i   
     k
[1,2,0,2]
          i=-1   
6. 第五次循环，i为-1说明以不存在不为0的元素，则k指向的当前元素改为0,k++
     k
[1,2,0,2]
          i=-1  
       k
[1,2,0,2]
          i=-1  
7. 第六次循环，i为-1说明以不存在不为0的元素，则k指向的当前元素改为0,k++,循环结束
       k
[1,2,0,0]
          i=-1  

```
## 2. 具体实现
```
public class MoveZeros {
    public static void moveZeroes(int[] nums){
        if(nums.length<=1) {
            return;
        }
        int k = 0;
        int i = 0;
        while (k < nums.length) {
            if( i != -1 && nums[i] != 0 ) {
                nums[k] = nums[i];
                i++;
                k++;
            } else if(i != -1 && nums[i] == 0){
                i++;
            }
            if( (i == nums.length || i == -1) && k!=i ) {
                i = -1;
                nums[k] = 0;
                k++;
            }
        }
    }

    public static void main(String[] args) {
        int arr[] = {2,1};
        moveZeroes(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
```