# BuildTree
> [链接](https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/)
```
根据一棵树的中序遍历与后序遍历构造二叉树。
注意:
你可以假设树中没有重复的元素。

例如，给出
中序遍历 inorder = [9,3,15,20,7]
后序遍历 postorder = [9,15,7,20,3]
```
## 1.解题思路一 
### 1.1 思路
> 根据后序的特性可以推算出最后一个节点是根节点，在根据中序遍历的特性找到根节点所在的位置，在中序遍历中根节点的左侧就是根的左孩子，右侧就是右孩子，不断循环上述过程，遍历后序节点后根据在中序中的位置不断拆分拼装二叉树即可。
### 1.2 具体实现
```
class Solution {
    int postIndex = 0;
    int[] inorder;
    int[] postorder;
    Map<Integer,Integer> indexMap = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if(inorder.length == 0)return null;
        this.inorder = inorder;
        this.postorder = postorder;
        this.postIndex = postorder.length-1;

        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i],i);
        }
        return helper(0,postorder.length);
    }

    private TreeNode helper(int left, int right){
        if(left == right) {
            return null;
        }

        int rootVal = this.postorder[postIndex];
        int rootIndex = indexMap.get(rootVal);

        TreeNode root = new TreeNode(rootVal);
        postIndex--;
        root.right = helper(rootIndex+1,right);
        root.left = helper(left,rootIndex);

        return root;
    }
}
```