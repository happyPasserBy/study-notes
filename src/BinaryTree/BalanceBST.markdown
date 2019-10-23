# 平衡二叉树
## 1.什么是平衡二叉树
* 对于任意一个节点，左子树和右子树的高度相差不能超过1  
* 树的高度和节点数量的关系是O(longn)
* 每个节点都拥有高度(叶子节点为1，非叶子节点为左右子树最大值+1)
* 每个节点都拥有平衡因子(叶子节点为0，非叶子节点为左子树高度-右子树高度)

![](./images/AVL_1.png)

## 参考
1. https://coding.imooc.com/class/207.html
2. https://www.cnblogs.com/qm-article/p/9349681.html
3. https://blog.csdn.net/javazejian/article/details/53892797