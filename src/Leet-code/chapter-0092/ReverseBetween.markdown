# ReverseBetween
> 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。说明:1 ≤ m ≤ n ≤ 链表长度。 [连接](https://leetcode-cn.com/problems/reverse-linked-list-ii/)
## 1. 解题思路一 三指针
### 1.1 思路
> 创建point计数器用于判断是否符合m、n条件。创建三个指针firstNode用于保存待交换节点的起始节点、cur保存当前节点、pre保存当前节点的上一个节点。不断循环当cur指向待交换节点时，使用 pre.next = cur.nex删除当前节点，
同时使用cur.next=firstNode.next;firstNode.next=cur将当前节点拼接到firstNode之后，至此一个节点的位移已经完成，依次遍历即可。
### 1.2 具体实现
```
public class ReverseBetween {
    public static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
    }
    public static ListNode reverseBetween(ListNode head, int m, int n) {

        ListNode firstNode = null;
        ListNode virtualNode = new ListNode(0); // 虚拟头节点、方便判断m节点
        ListNode cur = virtualNode;
        virtualNode.next = head;
        ListNode pre = null; // 临时节点,存储待交换元素的上一个元素
        int point = 0; // 计数器

        while (cur != null){
            // 记录待交换元素的起始节点
            if(point == m-1){
                firstNode = cur;
            }
            // m+1的原因是 第一个待交换节点无需移动
            if(point >= m+1 && point <= n){
                // 删除当前节点
                ListNode next = cur.next;
                pre.next = next;
                // 将当前节点拼接到起始节点之后
                cur.next =  firstNode.next;
                firstNode.next = cur;
                cur = pre;
            }
            // 记录当前指针，保存下次循环的上一个节点
            pre = cur;
            cur = cur.next;
            point++;
        }
        return virtualNode.next;
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
        ListNode reverseList = reverseBetween(listNode1,1,2);
        System.out.println(reverseList);
    }
}
```