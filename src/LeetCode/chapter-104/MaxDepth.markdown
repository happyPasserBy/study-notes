# MaxDepth
> 给定一个二叉树，找出其最大深度。二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。说明: 叶子节点是指没有子节点的节点 [链接](https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/)
## 1. 解题思路一 层序遍历
> 具体参考102号问题
## 2. 解题思路二 递归
### 2.1 思路
> 自底而上的计算方式，运用递归从叶子节点向上返回深度
### 2.2 具体实现
```
public class maxDepth104 {
    private class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }
    public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        int leftMaxDepth = maxDepth(root.left);
        int rightMaxDepth = maxDepth(root.right);
        return max(leftMaxDepth,rightMaxDepth)+1;
    }
}
```