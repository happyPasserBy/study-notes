# 二叉树
## 1.什么是二叉树
* 只有一个根节点
* 每个节点最多有两个孩子节点
* 每个节点最多有一个父节点

![](https://image-static.segmentfault.com/399/477/3994777747-5c2e0d4a06c2c_articlex)
## 2.二叉树的分类
### 2.1 满二叉树
* 假设树的深度为h,节点数为2^h-1
* 叶子节点数为2^(h-1)

### 2.2 完全二叉树
> 假设树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边


## 2.二叉树的简单实现
```
package com.example.demo.BST;

import sun.reflect.generics.tree.Tree;

import javax.sound.midi.Soundbank;
import java.security.PublicKey;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

/**
 *
 */
public class BST<E extends Comparable<E>> {

    private class Node {
        public E e;
        public Node left, right;

        public Node(E e) {
            this.e = e;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public BST() {
        this.root = null;
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    // 添加节点
    public void add(E e) {
        root = add(root, e);
    }

    private Node add(Node node, E e) {
        if (node == null) {
            this.size++;
            return new Node(e);
        }
        if (node.e.compareTo(e) == -1) {
            node.right = add(node.right, e);
        } else if (node.e.compareTo(e) == 1) {
            node.left = add(node.left, e);
        }
        return node;
    }

    // 查询是否包含该节点
    public boolean contains(E e) {
        return contains(this.root, e);
    }

    private boolean contains(Node node, E e) {
        if (node == null) {
            return false;
        }

        if (node.e.compareTo(e) == -1) {
            return contains(node.right, e);
        } else if (node.e.compareTo(e) == 1) {
            return contains(node.left, e);
        } else {
            return true;
        }
    }

    // 前序遍历
    public void preOrder() {
        this.preOrder(this.root);
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.e);
        this.preOrder(node.left);
        this.preOrder(node.right);
    }

    // 中序遍历
    public void inOrder() {
        this.inOrder(this.root);
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        this.inOrder(node.left);
        System.out.println(node.e);
        this.inOrder(node.right);
    }

    // 后序遍历
    public void postOrder() {
        this.postOrder(this.root);
    }

    private void postOrder(Node node) {
        if (node == null) {
            return;
        }
        this.postOrder(node.left);
        this.postOrder(node.right);
        System.out.println(node.e);
    }

    public void levelOrder() {
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node cur = q.remove();
            System.out.println(cur.e);
            if (cur.left != null) {
                q.add(cur.left);
            }
            if (cur.right != null) {
                q.add(cur.right);
            }
        }
    }

    private void generateBSTString(Node node, int depth, StringBuilder stringBuilder) {
        if (node == null) {
            stringBuilder.append(generateDepthString(depth) + "null\n");
            return;
        }
        stringBuilder.append(this.generateDepthString(depth) + node.e + "\n");
        this.generateBSTString(node.left, depth + 1, stringBuilder);
        this.generateBSTString(node.right, depth + 1, stringBuilder);
    }

    private String generateDepthString(int depth) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            stringBuilder.append("--");
        }
        return stringBuilder.toString();
    }

    // 查询最小值
    public E minimum() {
        if (size == 0) {
            throw new IllegalArgumentException("BST is empty");
        }
        return this.minimum(this.root);
    }

    private E minimum(Node node) {
        if (node.left == null) {
            return node.e;
        }
        return this.minimum(node.left);
    }

    // 查询最大值
    public E maxmum() {
        if (size == 0) {
            throw new IllegalArgumentException("BST is empty");
        }
        return this.maxmum(this.root);
    }

    private E maxmum(Node node) {
        if (node.right == null) {
            return node.e;
        }
        return this.maxmum(node.right);
    }

    // 删除最小值
    public E removeMin() {
        E ret = this.minimum();
        this.root = this.removeMin(this.root);
        return ret;
    }

    private Node removeMin(Node node){
        if(node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            this.size--;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    // 删除最大值
    public E removeMax() {
        E ret = maxmum();
        this.root = removeMax(this.root);
        return ret;
    }

    private Node removeMax(Node node) {
        if(node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            this.size--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }

    // 删除指定元素
    public void remove(E e) {
        this.root = this.remove(this.root,e);
    }

    private Node remove(Node node, E e) {
        if(node == null) {
            return null;
        }
        if(e.compareTo(node.e) == -1){
            node.left = this.remove(node.left,e);
            return node;
        }else if(e.compareTo(node.e) == 1) {
            node.right = this.remove(node.right,e);
            return node;
        }
        if(node.left == null) {
            node = this.removeMin(node);
            return node;
        }
        if(node.right == null) {
            node = this.removeMax(node);
            return node;
        }
        E convertE = this.minimum(node.right);
        node.right = this.removeMin(node.right);
        node.e = convertE;
        return node;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        generateBSTString(root, 0, stringBuilder);
        return stringBuilder.toString();
    }

}

```

## 参考
1. http://www.imooc.com/t/108955
2. https://segmentfault.com/a/1190000017761929