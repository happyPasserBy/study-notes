# BinaryTreePaths
> 给定一个二叉树，返回所有从根节点到叶子节点的路径。说明: 叶子节点是指没有子节点的节点。[链接](https://leetcode-cn.com/problems/binary-tree-paths/)
## 1. 解题思路一 自上而下的递归
### 1.1 思路
> 从根节点开始，将当前存在的路径作为参数进行传递直至递归到叶子节点添加到数组中，叫做自上而下的原因是路径的拼接是从根节点开始的
### 1.2 具体实现
```
public class BinaryTreePaths257 {
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public List<String> binaryTreePaths(TreeNode root) {
        if(root == null)return list;
        if( root.left == null && root.right == null){
            list.add(String.valueOf(root.val));
        }
        write(String.valueOf(root.val),root.left);
        write(String.valueOf(root.val),root.right);
        return list;
    }
    private List<String> list = new ArrayList<>();
    private void write(String path, TreeNode node){
        if(node == null)return;
        path+="->"+node.val;
        if(node.left == null && node.right == null){
            list.add(path);
        }
        if(node.left !=null){
            write(path,node.left);
        }
        if(node.right !=null){
            write(path,node.right);
        }
    }
}
```
## 2. 解题思路二 自下而上的递归
### 2.1 思路
> 从根节点开始递归直到叶子节点时，将当前叶子节点返回给父亲节点拼接路径，叫做自下而上的原因是路径的拼接是从叶子节点开始的
### 2.2 具体实现
```
待实现
```
