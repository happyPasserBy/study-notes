# BuildTree
> [链接](https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)
```
根据一棵树的前序遍历与中序遍历构造二叉树。
注意:
你可以假设树中没有重复的元素。

例如，给出
前序遍历 preorder = [3,9,20,15,7]
中序遍历 inorder = [9,3,15,20,7]
返回如下的二叉树：

    3
   / \
  9  20
    /  \
   15   7
```
## 1.解题思路一
### 1.1 思路
> 与106题相似，请看106题。
### 1.2 具体实现
```
class Solution {
    int pre_idx = 0;
    int[] preorder;
    int[] inorder;
    HashMap<Integer, Integer> idx_map = new HashMap<Integer, Integer>();


    public TreeNode helper(int in_left, int in_right) {
        if(in_left == in_right) {
            return null;
        }
        int rootVal = preorder[pre_idx];
        TreeNode root = new TreeNode(rootVal);
        int inIndex = idx_map.get(rootVal);

        pre_idx++;
        root.left = helper(in_left,inIndex);
        root.right = helper(inIndex+1,in_right);

        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        this.inorder = inorder;

        int idx = 0;
        for (Integer val : inorder)
            idx_map.put(val, idx++);
        return helper(0, inorder.length);
    }
}
```
