# MirrorTree
> [链接](https://leetcode-cn.com/problems/er-cha-shu-de-jing-xiang-lcof/)
```
请完成一个函数，输入一个二叉树，该函数输出它的镜像。

例如输入：

     4
   /   \
  2     7
 / \   / \
1   3 6   9
镜像输出：

     4
   /   \
  7     2
 / \   / \
9   6 3   1

 

示例 1：

输入：root = [4,2,7,1,3,6,9]
输出：[4,7,2,9,6,3,1]
```
## 1. 解题思路一 深度优先(DFS)
### 1.1 具体实现
```
class Solution {
    public TreeNode mirrorTree(TreeNode root) {
        if(root == null) return null;
        TreeNode mirror = new TreeNode(0);
        create(root,mirror);
        return mirror;
    }

    private void create(TreeNode realNode, TreeNode mirrorNode){
        mirrorNode.val = realNode.val;
        if(realNode.left != null) {
            mirrorNode.right = new TreeNode(0);
            create(realNode.left,mirrorNode.right);
        }
        if(realNode.right != null) {
            mirrorNode.left = new TreeNode(0);
            create(realNode.right,mirrorNode.left);
        }
    }
}
```