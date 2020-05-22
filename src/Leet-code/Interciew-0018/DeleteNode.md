# DeleteNode
> [链接](https://leetcode-cn.com/problems/shan-chu-lian-biao-de-jie-dian-lcof/)
```
给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。

返回删除后的链表的头节点。

注意：此题对比原题有改动

示例 1:

输入: head = [4,5,1,9], val = 5
输出: [4,1,9]
解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
```
## 1. 解题思路一 虚拟头节点+双指针
### 1.1 具体实现
```
public class Interview0018 {
    public ListNode deleteNode(ListNode head, int val) {
        if(head == null) return null;
        // 创建虚拟头节点便于操作
        ListNode virtualHead = new ListNode(0);
        virtualHead.next = head;
        // 保存上一个节点
        ListNode pre = virtualHead;
        // 当前节点
        ListNode cur = head;
        
        while (cur != null) {
            // 值相等则删除
            if(cur.val == val) {
                pre.next = cur.next;
                cur = null;
                break;
            }
            // 继续移动
            pre = pre.next;
            cur = cur.next;
        }

        return virtualHead.next;
    }
}
```