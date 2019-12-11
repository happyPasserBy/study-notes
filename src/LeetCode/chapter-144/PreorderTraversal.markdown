# PreorderTraversal
>给定一个二叉树，返回它的前序遍历(非递归)。 [链接](https://leetcode-cn.com/problems/binary-tree-preorder-traversal/)
## 1.解题思路一 栈
### 1.1 思路
> 使用栈记录二叉树的遍历情况，待遍历则入栈，遍历完成则出栈，换句话也可以说成模拟系统栈
### 1.2 具体实现
```
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if(root == null) return list;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()){
            TreeNode cur = stack.pop();
            if(cur == null) continue;
            list.add(cur.val);
            if(cur.right != null){
                stack.push(cur.right);
            }
            if(cur.left != null){
                stack.push(cur.left);
            }
        }
        return list;
    }
}
```