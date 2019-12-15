# LnvertTree
> 翻转一棵二叉树 [链接](https://leetcode-cn.com/problems/invert-binary-tree/)
## 1. 解题思路一 递归
### 1.1 思路
> 以当前节点开始递归反转左右孩子节点
### 1.2 解题方式一
```
public class InvertTree {
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public TreeNode invertTree(TreeNode root) {
        if(root == null) return null;
        TreeNode right = root.right;
        root.right = root.left;
        root.left = right;
        root.left = invertTree(root.left);
        root.right = invertTree(root.right);
        return root;
    }
}
```