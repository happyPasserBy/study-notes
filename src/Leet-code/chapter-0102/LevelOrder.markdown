# LevelOrder
> 给定一个二叉树，返回其按层次遍历的节点值。 （即逐层地，从左到右访问所有节点） [链接](https://leetcode-cn.com/problems/binary-tree-level-order-traversal/)
## 1. 解题思路一 队列
### 1.1 思路
> 创建一个队列按层级存放节点，存放时标记每个节点的层级，循环队列按层级拼装数据
### 1.2 具体实现
```
public class LevelOrder {
    private class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }
    private class LevelTreeNode {
        Integer level;
        TreeNode treeNode;
        public LevelTreeNode(Integer level, TreeNode treeNode) {
            this.level = level;
            this.treeNode = treeNode;
        }
    }
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret  = new ArrayList<>();
        if(root == null) return ret;
        List<LevelTreeNode> queue = new ArrayList<>();
        queue.add(new LevelTreeNode(0,root));
        while (queue.size() != 0) {
            LevelTreeNode cur = queue.remove(0);
            if(ret.size() > cur.level){
                ret.get(cur.level).add(cur.treeNode.val);
            }else{
                List<Integer> list = new ArrayList<>();
                list.add(cur.treeNode.val);
                ret.add(cur.level,list);
            }
            if(cur.treeNode.left != null)queue.add(new LevelTreeNode(cur.level+1,cur.treeNode.left));
            if(cur.treeNode.right != null)queue.add(new LevelTreeNode(cur.level+1,cur.treeNode.right));
        }
        return ret;
    }
}
```