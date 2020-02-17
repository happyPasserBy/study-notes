# 红黑树
* 节点非黑即红
* 根节点为黑色
* 每一个叶子节点为黑色
* 红色节点的子节点都为黑色
* 从任意节点到其叶子节点，经过的黑色节点是一样的
## 1.红黑树餐前开胃菜 2-3树
* 满足二分搜索树的基本性质
* 节点可以存放一个或2个元素
* 从根节点到其任意叶子节点所经过的节点数量相同

![](./images/2-3_Tree.png)
### 1.1 插入小记
* 插入的节点绝不会插入到空节点上
* 当插入的位置为2节点时则直接插入进行融合
* 当插入的位置为3节点时

    1.1.1 暂时融合为4节点
    ```
    // 树片段
    // 待插入值:25
    [22,25,29]
    ```
    1.1.2 在当前层级拆分
    ```
    //树片段
                [25]
                /  \
              [22] [29]
    ```
    1.2.3 拆分后必定破环树2-3特性: 从根节点到其任意叶子节点所经过的节点数量相同,所以需要提取中间节点向上融合,
    ```
    // 树片段
    // 如果融合节点为2节点则完毕，如是3节点则需要再次拆分
               [25,30]
               /  |  \
             [22][29][35]
    ```
    1.2.4 融合后为4节点情况
    ```
    // 树片段
             [25, 30, 43]
             /  |    |  \
           [22][29][35][45]
    // 拆分为
                [ 30 ]
               /      \
             [25]     [43]
            /    \   /    \
          [22] [29] [35]  [45]
    ```
## 2.正餐来临-红黑树
### 2.1 维护红黑树特性-左旋转

![](./images/RED_AND_BLACK_TREE_1.png)

![](./images/RED_AND_BLACK_TREE_2.png)

### 2.2 维护红黑树特性-右旋转

![](./images/RED_AND_BLACK_TREE_3.png)

![](./images/RED_AND_BLACK_TREE_4.png)

### 2.2 维护红黑树特性-变色

![](./images/RED_AND_BLACK_TREE_6.png)

![](./images/RED_AND_BLACK_TREE_7.png)

### 2.3 添加操作

![](./images/RED_AND_BLACK_TREE_5.png)

### 2.4 简单实现
```
public class RBTree<K extends Comparable<K>, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;


    private class Node{
        public K key;
        public V value;
        public Node left, right;
        public boolean color;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            color = RED;
        }
    }

    private Node root;
    private int size;

    public RBTree(){
        root = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // 向二分搜索树中添加新的元素(key, value)
    public void add(K key, V value){
        root = add(root, key, value);
        root.color = BLACK; // 根节点为黑色
    }

    // 向以node为根的红黑树中插入元素(key, value)，递归算法
    // 返回插入新节点后红黑索树的根
    private Node add(Node node, K key, V value){

        if(node == null){
            size ++;
            return new Node(key, value);
        }

        if(key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if(key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else // key.compareTo(node.key) == 0
            node.value = value;

        if(isRed(node.right) && !isRed(node.left)) {
            node = this.leftRotate(node);
        }

        if(isRed(node.left) && isRed(node.left.left)) {
            node = this.rightRotate(node);
        }

        if(isRed(node.left) && isRed(node.right)) {
            this.flipColors(node);
        }
        return node;
    }

    // 左旋转
    private Node leftRotate(Node node) {
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }
    // 右旋转
    private Node rightRotate(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }


    // 颜色翻转
    private void flipColors(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    private boolean isRed(Node node) {
        return  node.color == RED;
    }

    // 返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node, K key){

        if(node == null)
            return null;

        if(key.equals(node.key))
            return node;
        else if(key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else // if(key.compareTo(node.key) > 0)
            return getNode(node.right, key);
    }

    public boolean contains(K key){
        return getNode(root, key) != null;
    }

    public V get(K key){

        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue){
        Node node = getNode(root, key);
        if(node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = newValue;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node){

        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 从二分搜索树中删除键为key的节点
    public V remove(K key){

        Node node = getNode(root, key);
        if(node != null){
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key){

        if( node == null )
            return null;

        if( key.compareTo(node.key) < 0 ){
            node.left = remove(node.left , key);
            return node;
        }
        else if(key.compareTo(node.key) > 0 ){
            node.right = remove(node.right, key);
            return node;
        }
        else{   // key.compareTo(node.key) == 0

            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况

            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }
}

```

### 2.5 红黑树与AVL树对比
* 红黑树插入性能优于AVL树
* AVL树的查询性能优于红黑树


## 参考
1. https://coding.imooc.com/class/207.html (强烈推荐)
2. https://www.cnblogs.com/lishanlei/p/10707791.html
3. https://www.cnblogs.com/finite/p/8251587.html

