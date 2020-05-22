# GetKthFromEnd
> [链接](https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/)
```
输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。例如，一个链表有6个节点，从头节点开始，它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个节点是值为4的节点。

示例：
给定一个链表: 1->2->3->4->5, 和 k = 2.
返回链表 4->5.
```
## 1.解题思路一 快慢指针/窗口
### 1.1 具体实现
```java
public class Interview0022 {
    public ListNode getKthFromEnd(ListNode head, int k) {
        // 快指针
        ListNode fast = head;
        // 慢指针
        ListNode slow = head;
        // 先将快指针移动k次，此时快与慢的间隔就是k
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }
        /*
            假设链表长度为n,
            要求返回第(n-k)个元素，
            此时快指针已经移动k次， 循环同时移动快慢指针知道快指针==null,此时慢指针就是结果
        */
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }
}
```