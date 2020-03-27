# DeleteNode
>请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点，你将只被给定要求被删除的节点。 [链接](https://leetcode-cn.com/problems/delete-node-in-a-linked-list/submissions/)
## 1.解题思路一 值复制
### 1.1 思路
> 将待删除元素的下一个元素的值复制到当前元素中，删除下一个元素
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
public class DeleteNode {
    public static void deleteNode(ListNode node) {
        ListNode next = node.next;
        node.val = next.val;
        node.next = next.next;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(4);
        ListNode listNode2 = new ListNode(5);
        ListNode listNode3 = new ListNode(1);
        ListNode listNode4 = new ListNode(9);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        deleteNode(listNode3);
        System.out.println(listNode3);
    }
}
```