# MinStack
> [链接](https://leetcode-cn.com/problems/min-stack/)
```
设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
push(x) -- 将元素 x 推入栈中。
pop() -- 删除栈顶的元素。
top() -- 获取栈顶元素。
getMin() -- 检索栈中的最小元素。

示例:
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> 返回 -3.
minStack.pop();
minStack.top();      --> 返回 0.
minStack.getMin();   --> 返回 -2.
```
## 1. 解题思路一 记录最小值
### 1.1 思路
> 没啥可说的，存储的时候记录一下最小值
### 1.2 具体实现
```
public class MinStack {

    private List<Integer> arr = new ArrayList<>();
    private int size = 0;
    private int min = 0;
    /** initialize your data structure here. */
    public MinStack() {

    }

    public void push(int x) {
        if(size == 0) {
            min = x;
        }else {
            min = min > x ? x : min;
        }
        arr.add(x);
        size++;
    }

    public void pop() {
        if(size == 0) return;
        int cur = top();
        arr.remove(size-1);
        size--;
        if(cur == min && size > 0) {
            Integer reMin = null;
            for (Integer integer : arr) {
                if(reMin == null) {
                    reMin = integer;
                }else{
                    reMin = reMin > integer ? integer : reMin;
                }
            }
            min = reMin;
        }
    }

    public int top() {
        return arr.get(size-1);
    }

    public int getMin() {
        return min;
    }
}
```


