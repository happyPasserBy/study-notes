# 平衡二叉树
## 1.什么是平衡二叉树
* 对于任意一个节点，左子树和右子树的高度相差不能超过1  
* 树的高度和节点数量的关系是O(longn)
* 每个节点都拥有高度(叶子节点为1，非叶子节点为左右子树最大值+1)
* 每个节点都拥有平衡因子(叶子节点为0，非叶子节点为左子树高度-右子树高度)
## 2. 破坏平衡性的四种状况
* 新插入的节点在左子树并且属于左叶子节点
* 新插入的节点在左子树并且属于右叶子节点
* 新插入的节点在右子树并且属于右叶子节点
* 新插入的节点在右子树并且属于左叶子节点
## 3. 维护平衡性的操作
### 3.1 RR-右旋转(对应处理破坏平衡性的四种状况第一条)
* 暂时移除x节点的右子树T3节点
* 让失衡点y变为x的右子树
* 设置T3节点为y节点的左子树

![](./images/AVL_2.png)
![](./images/AVL_3.png)
### 3.2 LL-左旋转(对应处理破坏平衡性的四种状况第三条)
* 暂时移除x节点的左子树T3节点
* 让失衡点y变为x的左子树
* 设置T3节点为y节点的右子树

![](./images/AVL_4.png)
![](./images/AVL_5.png)
### 3.3 LR-先左后右旋转
* 先对失衡点的左子树进行左旋转，这样会将树转变为第一种失衡的情况
* 再对树进行右旋转
### 3.4 RL-先右后左旋转
* 先对失衡点的左子树进行右旋转，这样会将树转变为第三种失衡的情况
* 再对树进行左旋转

![](./images/AVL_1.png)

## 4. 简单的AVL实现
```
package com.fxm.AVL;

import com.fxm.FileOperation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AVLTree<K extends Comparable<K>, V> {
    private class Node{
        public K key;
        public V value;
        public Node left, right;
        public int height;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 1;
        }
    }

    private Node root;
    private int size;

    public AVLTree(){
        root = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // 获得节点node的高度
    private int getHeight(Node node){
        if(node == null)
            return 0;
        return node.height;
    }

    // 获得节点node的平衡因子
    private int getBalanceFactor(Node node){
        if(node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    // 向二分搜索树中添加新的元素(key, value)
    public void add(K key, V value){
        root = add(root, key, value);
    }

    // 向以node为根的二分搜索树中插入元素(key, value)，递归算法
    // 返回插入新节点后二分搜索树的根
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

        // 更新height
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // 计算平衡因子
        int balanceFactor = getBalanceFactor(node);

        // LL
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0)
            return rightRotate(node);

        // RR
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0)
            return leftRotate(node);

        // LR
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //RL
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }
    // 右旋转
    private Node rightRotate(Node y) {
        Node xNode = y.left;
        Node xRight = xNode.right;
        xNode.right = y;
        y.left = xRight;
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        xNode.height = 1 + Math.max(getHeight(xNode.left), getHeight(xNode.right));
        return xNode;
    }

    // 左旋转
    private Node leftRotate(Node y) {
        Node xNode = y.right;
        Node xLeft = xNode.left;
        xNode.left = y;
        y.right = xLeft;
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        xNode.height = 1 + Math.max(getHeight(xNode.left), getHeight(xNode.right));
        return xNode;
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

    // 是否是一颗二分搜索树
    public boolean isBST(){
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root,keys);
        for (int i=1;i<keys.size();i++) {
            if(keys.get(i-1).compareTo(keys.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    private void inOrder(Node node, List<K> keys){
        if(node == null) return;
        inOrder(node.left,keys);
        keys.add(node.key);
        inOrder(node.right,keys);
    }

    // 判断二叉树是否是一颗平衡二叉树
    public boolean isBalance(){
        return isBalance(this.root);
    }
    private boolean isBalance(Node node) {
        if(node == null) return true;
        int balance = getBalanceFactor(node);
        if(Math.abs(balance) > 1) {
            return false;
        }
        return isBalance(node.left) && isBalance(node.right);
    }

    private Node remove(Node node, K key){

        if( node == null )
            return null;

        Node retNodwe;
        if( key.compareTo(node.key) < 0 ){
            node.left = remove(node.left , key);
            retNodwe =  node;
        }
        else if(key.compareTo(node.key) > 0 ){
            node.right = remove(node.right, key);
            retNodwe =  node;
        }
        else{   // key.compareTo(node.key) == 0

            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                retNodwe =  rightNode;
            }else if(node.right == null){ // 待删除节点右子树为空的情况
                Node leftNode = node.left;
                node.left = null;
                size --;
                retNodwe = leftNode;
            }else {

                // 待删除节点左右子树均不为空的情况

                // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
                // 用这个节点顶替待删除节点的位置
                Node successor = minimum(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;

                node.left = node.right = null;

                retNodwe = successor;
            }
        }
        if(retNodwe == null) {
            return null;
        }

        // 更新height
        retNodwe.height = 1 + Math.max(getHeight(retNodwe.left), getHeight(retNodwe.right));

        // 计算平衡因子
        int balanceFactor = getBalanceFactor(retNodwe);

        // LL
        if (balanceFactor > 1 && getBalanceFactor(retNodwe.left) >= 0)
            return rightRotate(retNodwe);

        // RR
        if (balanceFactor < -1 && getBalanceFactor(retNodwe.right) <= 0)
            return leftRotate(retNodwe);

        // LR
        if (balanceFactor > 1 && getBalanceFactor(retNodwe.left) < 0) {
            retNodwe.left = leftRotate(retNodwe.left);
            return rightRotate(retNodwe);
        }

        //RL
        if (balanceFactor < -1 && getBalanceFactor(retNodwe.right) > 0) {
            retNodwe.right = rightRotate(retNodwe.right);
            return leftRotate(retNodwe);
        }
        return retNodwe;
    }
    // 判断该二叉树是否是一棵平衡二叉树
    public boolean isBalanced(){
        return isBalanced(root);
    }

    // 判断以Node为根的二叉树是否是一棵平衡二叉树，递归算法
    private boolean isBalanced(Node node){

        if(node == null)
            return true;

        int balanceFactor = getBalanceFactor(node);
        if(Math.abs(balanceFactor) > 1)
            return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }
}
```

## 参考
1. https://coding.imooc.com/class/207.html
2. https://www.cnblogs.com/qm-article/p/9349681.html
3. https://blog.csdn.net/javazejian/article/details/53892797





