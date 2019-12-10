# RemoveElements
> 删除链表中等于给定值 val 的所有节点。
[链接](https://leetcode-cn.com/problems/remove-linked-list-elements/)
## 1.解题思路一 虚拟头节点
### 1.1 思路
> 创建虚拟头节点,方便遍历
### 1.2 具体实现
```
public class RemoveElements {
    public static ListNode removeElements(ListNode head, int val) {
        if(head == null)return head;
        ListNode virtual = new ListNode(0);
        virtual.next = head;
        ListNode cur = virtual;
        while(cur != null){
            ListNode next = cur.next;
            if(next != null && next.val == val){
                cur.next = next.next;
                next = null;
            }else{
                cur = cur.next;
            }
        }
        return virtual.next;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(6);
        ListNode listNode4 = new ListNode(3);
        ListNode listNode5 = new ListNode(4);
        ListNode listNode6 = new ListNode(6);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;
        ListNode reverseList = removeElements(listNode1,6);
        System.out.println(reverseList);
    }
}
```