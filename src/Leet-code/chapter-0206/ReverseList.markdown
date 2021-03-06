# ReverseList
> 反转一个单链表 
[链接](https://leetcode-cn.com/problems/reverse-linked-list/)
## 1.解题思路一 栈(先进后出)
### 1.1 思路
> 先将所有元素放到栈中，在循环栈创建链表
### 1.2 具体实现
```
public class ReverseList206 {
    public static ListNode reverseList(ListNode head) {
        ListNode search = head;
        Stack<Integer> stack = new Stack<Integer>();
        while (search!= null) {
            stack.push(search.val);
            search = search.next;
        }
        ListNode reverse = null;
        ListNode point = null;
        while (!stack.isEmpty())
            if(reverse == null) {
                reverse = new ListNode(stack.pop());
                point = reverse;
            }else {
                point.next = new ListNode(stack.pop());
                point = point.next;
            }
        return reverse;
    }
     public static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
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
        ListNode reverseList = reverseList(listNode1);
        System.out.println(reverseList);
    }

}
```

## 2.解题思路二 三指针
### 2.1 思路
> 创建三个指针pre、cur、next指针，循环链表每次改变指针指向，防止改变链表的next属性后导致的指针丢失
### 2.2 具体实现
```
public class ReverseList206 {
    public static ListNode reverseList(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = head.next;
        while ( true ){
            cur.next = pre;
            pre = cur;
            cur = next;
            if(next == null) return pre;
            next = cur.next;
        }
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
        ListNode reverseList = reverseList(listNode1);
        System.out.println(reverseList);
    }

}
```