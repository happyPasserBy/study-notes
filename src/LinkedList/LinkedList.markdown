# 实现简单链表
```
public class LinkedList<E> {

    // 内部节点类
    private class Node {
        private Node next;
        private E e;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this.e = e;
        }

        public Node() {
            this.e = null;
            this.next = null;
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedList() {
        this.dummyHead = new Node();
        this.size = 0;
    }

    // 获取链表长度
    public int getSize() {
        return this.size;
    }

    // 是否为空
    public boolean isEmpty() {
        return this.size == 0;
    }

    // 在指定位置添加节点
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index 参数错误");
        }
        Node node = new Node(e);
        Node oldNode = dummyHead;
        for (int i = 0; i < index; i++) {
            oldNode = oldNode.next;
        }
        node.next = oldNode.next;
        oldNode.next = node;
        size++;
    }

    // 添加头节点
    public void addFirst(E e) {
        this.add(0, e);
    }

    // 添加尾节点
    public void addLast(E e) {
        this.add(this.size, e);
    }

    // 获取指定位置的元素
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index 参数错误");
        }
        Node node = dummyHead.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.e;
    }

    // 获取第一个元素
    public E getFirst() {
        return this.get(0);
    }

    // 获取最后一个元素
    public E getLast() {
        return get(size - 1);
    }

    // 修改指定位置的原色
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index 参数错误");
        }
        Node node = dummyHead.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        node.e = e;
    }

    // 查找链表是否包含指定元素
    public boolean contains(E e) {
        Node node = dummyHead.next;
        for (int i = 0; i < size; i++) {
            if(node.e.equals(e)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }
    public E remove(int index){
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index 参数错误");
        }
        Node node = dummyHead;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        Node removeNode = node.next;
        node.next = removeNode.next;
        removeNode.next = null;
        size--;
        return removeNode.e;
    }

    public E removeFirst(){
        return this.remove(0);
    }
    public E removeLast(){
        return this.remove(this.size-1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node node = dummyHead.next;
        while (node != null) {
            sb.append(node.e);
            if (node.next == null) {
                return sb.append("]").toString();
            }
            node = node.next;
            sb.append(",");
        }
        return sb.toString();
    }
}

```
## 参考
1. http://www.imooc.com/t/108955