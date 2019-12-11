# RemoveNthFromEnd
> 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。 [链接](https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/submissions/)
## 1.解题思路一 双指针
### 1.1 思路
> 假设删除倒数第2个节点，则设两指针pre与cur，pre与cur两指针间隔为2，不断移动cur指针直到为null，则pre指针的next指向的就是待删除节点
### 1.2 具体实现
```
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class RemoveNthFromEnd {
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null) return null;
        ListNode virtual = new ListNode(0);
        virtual.next = head;
        ListNode pre = virtual;
        ListNode cur = virtual.next;
        Integer distance = 0;
        while (cur != null){
            if(distance < n){
                cur = cur.next;
                distance++;
            }else{
                pre = pre.next;
                cur = cur.next;
            }
        }
        pre.next = pre.next.next;
        return virtual.next;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        removeNthFromEnd(listNode1,2);
        System.out.println(listNode1);
    }
}

```