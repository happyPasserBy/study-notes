# SortList
> [链接](https://leetcode-cn.com/problems/sort-list/)
```
在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。

示例 1:
输入: 4->2->1->3
输出: 1->2->3->4

示例 2:
输入: -1->5->3->4->0
输出: -1->0->3->4->5
```
## 1.解题思路一 归并
### 1.1 思路
> 就是归并排序的思路。首先使用快慢指针拆分链表，把链表拆分成2个节点一组的短链表，对每个短链表继续排序，然后对排序后的短链表进行按序拼接。
### 1.2 具体实现
```
public class SortList {
    public static ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode temp = slow.next;
        slow.next = null;
        head = sortList(head);
        temp = sortList(temp);
        ListNode virtualNode = new ListNode(0);
        ListNode h = virtualNode;
        while (head != null && temp != null) {
            if(head.val > temp.val) {
                virtualNode.next = temp;
                temp = temp.next;
            }else {
                virtualNode.next = head;
                head = head.next;
            }
            virtualNode = virtualNode.next;
        }
        virtualNode.next = head == null ? temp : head;
        return h.next;
    }

    public static void main(String[] args) {
        String arr1 = "[4,2,1,3]";
        String arr2 = "[-1,5,3,4,0]";
        ListNode res = sortList(ListNodeUtil.stringToListNode(arr2));
        System.out.println(res);
    }
}
```

## 参考
1. https://leetcode-cn.com/problems/sort-list/solution/sort-list-gui-bing-pai-xu-lian-biao-by-jyd/