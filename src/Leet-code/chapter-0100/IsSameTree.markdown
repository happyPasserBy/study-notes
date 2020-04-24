# IsSameTree
> 给定两个二叉树，编写一个函数来检验它们是否相同。如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。 [链接](https://leetcode-cn.com/problems/same-tree/)
## 1. 解题思路一 递归
### 1.1 思路
> 二叉树具有天然的递归性。而本题最终的要求就是判断每个节点是否相同，结构上的判断也是一个有效节点与一个null节点的对比，使用递归对比每个节点即可。
## 1.2 具体实现
```
public class IsSameTree {
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null) {
            return true;
        }
        if((p == null && q != null) || (p != null && q == null) || p.val != q.val) {
            return false;
        }
        return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }
}
```

## 2. 解题思路二 遍历
### 1.1 思路
> 与递归相似不再赘述
......
