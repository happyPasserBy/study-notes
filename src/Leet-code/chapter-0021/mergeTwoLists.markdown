# MergeTwoLists
> 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
```
输入：1->2->4, 1->3->4
输出：1->1->2->3->4->4
```
## 2.解题方式一 
### 2.1 思路
> 创建两个指针，每次取最小值，直到遍历完两个链表，由最小值组成的链表就是答案
### 2.2 具体实现
```
public class MergeTwoLists {
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null){
            return l1 == null ? l1 : l2;
        }
        int curInt = 0;
        ListNode virtualHead = new ListNode(1);
        ListNode cur = virtualHead;
        while (l1 != null || l2 != null) {
            curInt = l1 == null ? l2.val : l2 == null ? l1.val : l1.val < l2.val ? l1.val : l2.val;
            if(l1 != null && l1.val <= curInt) {
                cur.next = new ListNode(l1.val);
                cur = cur.next;
                l1 = l1.next;
            }
            if(l2 != null && l2.val <= curInt) {
                cur.next = new ListNode(l2.val);
                cur = cur.next;
                l2 = l2.next;
            }
        }
        return virtualHead.next;
    }

    public static void main(String[] args) {
        String str1 = "[]";
        String str2 = "[0]";
        mergeTwoLists(stringToListNode(str1),stringToListNode(str2));
    }
}
```

