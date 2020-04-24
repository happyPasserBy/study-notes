# HasCycle
> 定一个链表，判断链表中是否有环。为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。[链接](https://leetcode-cn.com/problems/linked-list-cycle/)
```
输入：head = [3,2,0,-4], pos = 1
输出：true
解释：链表中有一个环，其尾部连接到第二个节点。
```
## 1. 解题思路一 使用额外的空间
### 1.1 思路
> 循环链表放到set或list中，在存放时每次判断是否存在
## 2. 解题思路二 快慢指针
### 2.1 思路
> 设置快慢两个指针，快指针每次向后移动2步，慢指针每次向后移动1步，如果这个链表没有环则快指针必定先走到尾部，如果有环，快指针会一致在这个环中移动并在某一时刻与慢指针相遇。
### 2.2 具体实现
```
public class HasCycle {
    public static boolean hasCycle(ListNode head) {
        if(head == null || head.next == null) return false;
        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != slow){
            slow = slow.next;
            if(fast == null || fast.next == null) return false;
            fast = fast.next.next;
        }
        return true;
    }
}
```