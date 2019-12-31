# RightSideView
> 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值 [链接](https://leetcode-cn.com/problems/binary-tree-right-side-view/)

## 1.解题思路一 广度优先
### 1.1 思路
> 广度优先对于树形结构来说就是层级遍历，说白了就是一层一层遍历。本题的解题思路是使用队列实现层序遍历，而每层的顺序是从右到左，从而就实现了树的右侧视图。其中还有个小关键就是如何知道当前节点在最右侧呢？答案就是使用一个map容器来存储当前层的第一个元素，每次存储时判断是否已经存在节点。
### 1.2 具体实现
```
public class RightSideView {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private static class WrapperNode{
        int level;
        TreeNode node;

        public WrapperNode(int level, TreeNode node) {
            this.level = level;
            this.node = node;
        }
    }
    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }
        Queue<WrapperNode> queue = new LinkedList<>();
        queue.add(new WrapperNode(0,root));
        HashMap<Integer,TreeNode> rightViewNode = new HashMap<>();
        while (queue.size() > 0) {
            WrapperNode curNode = queue.remove();
            if(!rightViewNode.containsKey(curNode.level)){
                ret.add(curNode.node.val);
                rightViewNode.put(curNode.level,curNode.node);
            }
            if(curNode.node.right != null) queue.add(new WrapperNode(curNode.level+1,curNode.node.right));
            if(curNode.node.left != null) queue.add(new WrapperNode(curNode.level+1,curNode.node.left));
        }
        return ret;
    }
}
```

## 1.解题思路一 深度优先
### 1.1 思路 
> 深度优先顾名思义，对于一个节点优先查找它的最深叶子节点。而本题的解题思路是使用深度优先查找root节点的右子树，而root节点的右子树就是我们想要的右视图。当root左子树树高于右子树时，高出的部分也是我们想要的右视图。此时我们总结一下，使用深度优先与右侧优先就可以得出我们想要的节点，在一个当我们实现的时候需要创建一个容器来判断当前节点是否是右视图节点，因为优先右侧节点所以我们只要判断容器里是否包含当前树高度的节点即可。
### 1.2 解题思路
```
public class RightSideView {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private static class WrapperNode{
        int level;
        TreeNode node;
        public WrapperNode(int level, TreeNode node) {
            this.level = level;
            this.node = node;
        }
    }
    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }
        Stack<WrapperNode> stack = new Stack<>();
        stack.push(new WrapperNode(0,root));
        HashMap<Integer,TreeNode> rightViewNode = new HashMap<>();
        while(stack.size() > 0) {
            WrapperNode curNode = stack.pop();
            if(!rightViewNode.containsKey(curNode.level)){
                rightViewNode.put(curNode.level,curNode.node);
                ret.add(curNode.node.val);
            }
            if(curNode.node.left != null) stack.push(new WrapperNode(curNode.level+1,curNode.node.left));
            if(curNode.node.right != null) stack.push(new WrapperNode(curNode.level+1,curNode.node.right));
        }
        return  ret;
    }
}
```
