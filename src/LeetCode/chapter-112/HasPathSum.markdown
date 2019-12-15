# HasPathSum
> 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。说明: 叶子节点是指没有子节点的节点 [链接](https://leetcode-cn.com/problems/path-sum/)
## 1. 解题思路一 自上而下的递归
### 1.1 思路
> 从根节点开始，计算sum-当前节点值后在左右子树中递归查找，当递归到叶子节点时判断是否包含指定值
### 1.2 具体实现
```
public class HasPathSum {
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null) return false;
        if(root.left == null && root.right == null && root.val == sum){
            return true;
        }
        int search = sum-root.val;
        boolean left = hasPathSum(root.left,search);
        boolean right = hasPathSum(root.right,search);
        return left || right ? true : false;
    }
}
```