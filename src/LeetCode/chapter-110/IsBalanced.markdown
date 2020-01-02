# IsBalanced
> 给定一个二叉树，判断它是否是高度平衡的二叉树。本题中，一棵高度平衡二叉树定义为：一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。[链接](https://leetcode-cn.com/problems/balanced-binary-tree/)

## 1 解题思路一 递归计算高度
### 1.1 思路
> 本题就是平衡二叉树判断平衡的方法，首先要知道左右子树的高度，计算高度使用自上而下的递归方式，叶子节点高度为1，当前节点的高度就是左右子树高度最大数值+1即可。判断平衡就是递归每个节点判断是否平衡。
### 1.2 具体实现
```
public class IsBalanced {
    public static boolean isBalanced(TreeNode root) {
        if(root == null) return true;
        if(Math.abs(getHeight(root.left)-getHeight(root.right)) > 1){
            return false;
        }
        if(root.left != null && !isBalanced(root.left)) {
            return false;
        }
        if(root.right != null && !isBalanced(root.right)) {
            return false;
        }
        return true;
    }
    private static int getHeight(TreeNode node){
        if(node == null)return 0;
        int leftHeight = getHeight(node.left);
        int rightheight = getHeight(node.right);
        return Math.max(leftHeight,rightheight)+1;
    }
    public static void main(String[] args) {
        String str1 = "[3,9,20,null,null,15,7]";
        String str2 = "[1,2,2,3,3,null,2,4,4,null,null,null,2]";
        TreeNode root = TreeNode.stringToTreeNode(str2); // TreeNode与stringToTreeNode是leetcode提供的方法
        System.out.println(isBalanced(root));
    }
}
```

