# IsSymmetric
> 给定一个二叉树，检查它是否是镜像对称的。 [链接](https://leetcode-cn.com/problems/symmetric-tree/)

## 1. 解题思路一 递归对比左右子树
### 1.1 思路
> 建议先解决第100号题再看本题。100号题的解决思路是递归对比两个树是否相同，本题也是对比两个树像否相似不过是节点要相反，像是一面镜子立在root.left与root.right之间，假设root.left为A节点，root.right为B节点，在递归时只要将A节点的left对比B节点的right,A节点的right对比B节点的left。在直白点就像你站在镜子前伸出右手，此时对于镜子里的那个你伸出的是左手，你只要对比两只手是否相同即可。
### 1.2 具体实现
```
public class IsSymmetric {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public static boolean isSymmetric(TreeNode root) {
        if(root == null) return true;
        return isSameTree(root.left,root.right);
    }

    private static boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null) {
            return true;
        }
        if(q == null || p == null  || p.val != q.val) {
            return false;
        }
        return isSameTree(p.left,q.right) && isSameTree(p.right,q.left);
    }
}
```

## 2. 解题思路二 层需遍历+对撞指针
### 1.1 思路
> 使用层序遍历得到每一层的节点并且保证节点的 从左到右/从右到左 的顺序，创建对撞指针分别指向头尾，循环不断缩小指针判断两节点是否相同即可，注意null也需要存储并判断。
### 1.2 具体实现
......