# MinDepth
> 给定一个二叉树，找出其最小深度。最小深度是从根节点到最近叶子节点的最短路径上的节点数量。说明: 叶子节点是指没有子节点的节点。 [链接](https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/)
## 1. 解题思路一 求最小高度
### 1.1 思路
> 看本题时建议先看第110号题。本题说白了就是求最小高度，但这个最小高度有一个前提条件就是叶子节点不能是根节点，在细细读题"根节点到叶子节点的最短路径"就是这个意思。解题思路就是递归返回每个节点的最小高度。
### 1.2 具体实现
```
public class MinDepth {
    public static int minDepth(TreeNode root) {
        if(root == null) return 0;
        return getHeight(root);
    }

    private static int getHeight(TreeNode node){
        if(node == null)return 0;
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        if(leftHeight != 0 && rightHeight != 0) {
            return Math.min(leftHeight,rightHeight) + 1;
        }
        if(leftHeight > 0) return leftHeight+1;
        return rightHeight+1;
    }

    public static void main(String[] args) {
        String str1 = "[3,9,20,null,null,15,7]";
        String str2 = "[1,2]";
        String str3 = "[-9,-3,2,null,4,4,0,-6,null,-5]";
        System.out.println(minDepth(TreeNode.stringToTreeNode(str2))); // leetcode提供
    }
}
```