# IsValidBST
> [链接](https://leetcode-cn.com/problems/validate-binary-search-tree/)
```
给定一个二叉树，判断其是否是一个有效的二叉搜索树。
假设一个二叉搜索树具有如下特征：
节点的左子树只包含小于当前节点的数。
节点的右子树只包含大于当前节点的数。
所有左子树和右子树自身必须也是二叉搜索树。

示例 1:
输入:
    2
   / \
  1   3
输出: true

示例 2:
输入:
    5
   / \
  1   4
     / \
    3   6
输出: false
解释: 输入为: [5,1,4,null,null,3,6]。
     根节点的值为 5 ，但是其右子节点值为 4 。
```
## 1 解题思路一 

### 1.1 思路
> 本题只要注意每个节点的最大值与最小值就可以。举个栗子，假设当前x是根节点(root)的左孩子，那么按照本题的规则x必定小于root,x符合之后递归判断x的孩子节点，这时x的右孩子xr该如何判断呢？
很明显xr要大于x且小于root,注意此时的最大值root与最小值x,我们继续递归判断xr的左孩子xrl，注意此时最大值应该是xr，最小值时x,注意到规则变化了吗？没有的话建议画张图，找到每个节点的最大值与最小值的变化规则。
### 1.2 具体实现
```
public class IsValidBST {
    public static boolean isValidBST(TreeNode root) {
        if(root == null) return true;
        return valid(root.left, root.val, null) && valid(root.right,null, root.val);
    }
    private static boolean valid(TreeNode node,Integer max,Integer min){
        if(node == null) return true;

        if(max != null && node.val >= max) return false;

        if(min != null && node.val <= min) return false;

        return valid(node.left, node.val, min) && valid(node.right, max, node.val);
    }
    public static void main(String[] args) {
        String tree1 = "[2,1,3]"; // true
        String tree2 = "[5,1,4,null,null,3,6]"; // false
        String tree3 = "[1,1]"; // false
        System.out.println(isValidBST(TreeUtil.stringToTreeNode(tree3)));
    }
}
```




