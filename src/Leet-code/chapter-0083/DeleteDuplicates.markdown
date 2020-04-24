# DeleteDuplicates
> 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。 [链接](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/)
## 1.解题思路一 双指针
### 1.1 思路
> 利用此题的排序性，创建前指针:f、后指针:l,f指向的节点及之前节点为不重复节点，l根据循环依次指向每个元素，如f与l值不同则同时向后移动，若相同则l向后移动直至与f值不同为止，将f的next指向后继续循环,最后判断f是否指向最后的尾节点，非尾节点则说明f之后都是重复直接改为null
### 1.2 具体实现
```
public class DeleteDuplicates83 {
    public static ListNode deleteDuplicates(ListNode head) {
        if(head == null) return head;
        ListNode f = head;
        ListNode l = head.next;
        while (l != null){
            if(f.val != l.val){
                f.next = l;
                f = f.next;
                l = l.next;
            }else{
                l = l.next;
            }
        }
        if(f.next !=null){
            f.next = null;
        }
        return head;
    }
    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(1);
        ListNode listNode3 = new ListNode(2);
        ListNode listNode4 = new ListNode(3);
        ListNode listNode5 = new ListNode(3);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        ListNode reverseList = deleteDuplicates(listNode1);
        System.out.println(reverseList);
    }
}
```







