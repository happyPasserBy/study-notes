# PostorderTraversal
>给定一个二叉树，返回它的后序遍历(非递归)。 [链接](https://leetcode-cn.com/problems/binary-tree-postorder-traversal/submissions/)
## 1.解题思路二 栈
### 1.1 思路
> 使用栈模拟递归调用时系统栈的执行方式并增加执行描述
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
    private class Command{
        String desc;
        TreeNode node;
        public Command(String desc, TreeNode node) {
            this.desc = desc;
            this.node = node;
        }
    }
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if(root == null) return list;
        Stack<Command> stack = new Stack<>();
        stack.push(new Command("go",root));
        while (!stack.isEmpty()){
            Command command = stack.pop();
            if(command.desc.equals("print")){
                list.add(command.node.val);
            }else{
                stack.push(new Command("print",command.node));
                if(command.node.right != null){
                    stack.push(new Command("go",command.node.right));
                }
                if(command.node.left != null){
                    stack.push(new Command("go",command.node.left));
                }
            }
        }
        return list;
    }
}
```