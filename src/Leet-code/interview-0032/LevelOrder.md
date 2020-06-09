# LevelOrder
>[链接](https://leetcode-cn.com/problems/cong-shang-dao-xia-da-yin-er-cha-shu-ii-lcof/)
```
从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。

 

例如:
给定二叉树: [3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7
返回其层次遍历结果：

[
  [3],
  [9,20],
  [15,7]
]

```
## 1. 解题思路一 队列
### 1.1 具体实现

```java
public class Solution {

    private static class LevelTreeNode extends TreeNode{
        Integer level;

        public LevelTreeNode(int x,int level) {
            super(x);
        }
    }


    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        if(root == null)return res;
        queue.add(root);
        while (!queue.isEmpty()){
            List<Integer> levelList = new ArrayList<>();
            for (int i = queue.size(); i >= 0; i--) {
                TreeNode cur = queue.remove();
                levelList.add(cur.val);
                if((cur.left != null ))queue.add(cur.left);
                if((cur.right != null ))queue.add(cur.right);
            }
            res.add(levelList);
//            List<TreeNode> list = new ArrayList<>();
//            while (!queue.isEmpty()){
//                TreeNode cur = queue.remove();
//                levelList.add(cur.val);
//                if((cur.left != null ))list.add(cur.left);
//                if((cur.right != null ))list.add(cur.right);
//            }
//            res.add(levelList);
//            queue.addAll(list);
        }
        return res;
    }

    public static void main(String[] args) {
        TreeNode treeNode = TreeUtil.stringToTreeNode("[3,9,20,null,null,15,7]");
        System.out.println(levelOrder(treeNode));
    }
}
``