# LevelOrderBottom
> 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）[链接](https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/)
## 1.解题思路一 队列
### 1.1 思路
> 与102题相似
### 1.2 具体实现
```
public class levelOrderBottom {
    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    private class WrapperNode {
        TreeNode node;
        int level;
        WrapperNode(TreeNode node,int level){
            this.level = level;
            this.node = node;
        }
    }
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<WrapperNode> queue = new ArrayList<>();
        Stack<List<Integer>> ret = new Stack<>();

        if(root == null) return ret;

        queue.add(new WrapperNode(root,0));
        while (queue.size() != 0){
            WrapperNode cur = queue.remove(0);
            if(ret.size() > cur.level){
                ret.get(cur.level).add(cur.node.val);
            }else{
                List<Integer> list = new ArrayList<>();
                list.add(cur.node.val);
                ret.add(cur.level,list);
            }
            if(cur.node.left != null) {
                queue.add(new WrapperNode(cur.node.left,cur.level+1));
            }
            if(cur.node.right != null) {
                queue.add(new WrapperNode(cur.node.right,cur.level+1));
            }
        }
        Collections.reverse(ret);
        return ret;
    }
}
```



## 2.解题思路二 队列
### 1.1 思路
> 在遍历节点将其数值插入到结果集的头部
### 1.2 具体实现
```
待实现
```