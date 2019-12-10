# SwapPairs
> 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。[链接](https://leetcode-cn.com/problems/swap-nodes-in-pairs/)
## 1.解题方式一 虚拟节点+三指针
### 1.1 思路
> 创建虚拟节点方便循环操作，创建三指针分别指向起始节点、待交换节点1、待交换节点2，循环链表进行交换
### 1.2具体实现
```
 /**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class SwapPairs {
    public static ListNode swapPairs(ListNode head) {
        if(head == null) return head;
        ListNode virtual = new ListNode(0);
        virtual.next = head;
        ListNode p = virtual;
        ListNode node1 = virtual.next;
        if(node1.next == null) {
            return head;
        }
        ListNode node2 = node1.next;
        while (node1 != null && node2 != null){
            node1.next = node2.next;
            node2.next = node1;
            p.next = node2;

            p = node1;
            if(p == null) break;
            node1 = p.next;
            if(node1 == null)break;
            node2 = node1.next;
            if(node2 == null) break;
        }
        return virtual.next;
    }
    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        ListNode reverseList = swapPairs(listNode1);
        System.out.println(reverseList);
    }
}

```
