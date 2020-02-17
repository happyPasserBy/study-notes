# DiameterOfBinaryTree
> 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。[链接](https://leetcode-cn.com/problems/diameter-of-binary-tree/)
```
示例 :
给定二叉树

          1
         / \
        2   3
       / \     
      4   5    
返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
注意：两结点之间的路径长度是以它们之间边的数目表示。
```
## 1.解题思路一 
### 1.1 思路
> 递归计算每个节点的直径长度保留最大值。注意，最大的直径长度不一定经过根节点，所以是递归计算每个节点。
### 1.2 具体实现
```
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
       if(root == null) return 0;
        max = 0;
        depth(root,0);
        return max;
    }
    private static int max  = 0;

    private static int depth(TreeNode node, int depth){
        if(node == null) return 0;

        int leftDepth =  depth(node.left, depth + 1);
        int rightDepth =  depth(node.right, depth + 1);

        max = Math.max(leftDepth+rightDepth,max);

        return  Math.max(leftDepth,rightDepth)+1;
    }
}
```