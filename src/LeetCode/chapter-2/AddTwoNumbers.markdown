# addTwoNumbers
> 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。[链接](https://leetcode-cn.com/problems/add-two-numbers/)

## 1.解题思路 模拟个位数计算
### 1.1 思路
> 本题给出的是倒叙数字，而要求的答案也是倒叙，因此可以使用倒叙的特性来解题，题目中说明每个节点都是个位，而我们日常的整数计算也都是从数字的个位开始计算，因此我们可以同时遍历两个链表将当前两个节点的值相加并记录否需要进位(满十进一)，下次的两节点计算要加上一次循环的进位数，这样倒叙的数字结果就出来了，本题想要倒叙结果的链表，只要将之前每次的计算结果创建成链表并连接起来就ok了。
### 1.2 
```
public class AddTwoNumbers {
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l1Cur = l1;
        ListNode l2Cur = l2;
        Integer point = 0;
        ListNode virtualNode = new ListNode(0);
        ListNode pre = virtualNode;
        int sum = 0;
        while (l1Cur != null || l2Cur != null || point > 0){
            int x = l1Cur == null ? 0 :  l1Cur.val;
            int y = l2Cur == null ? 0 :  l2Cur.val;
            sum = x + y + point;

            if(sum >=10) {
                point = 1;
                sum = sum - 10;
            }else {
                point = 0;
            }
            l1Cur = l1Cur == null ? null : l1Cur.next;
            l2Cur = l2Cur == null ? null : l2Cur.next;
            ListNode cur = new ListNode(sum);
            pre.next = cur;
            pre = pre.next;
            sum = 0;
        }
        return virtualNode.next;
    }
    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(4);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(5);
        ListNode listNode5 = new ListNode(6);
        ListNode listNode6 = new ListNode(4);
        listNode1.next = listNode2;
        listNode2.next = listNode3;

        listNode4.next = listNode5;
        listNode5.next = listNode6;
        ListNode reverseList = addTwoNumbers(listNode1,listNode4);
        System.out.println(reverseList);
    }
}
```
