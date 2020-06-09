# LevelOrder3
> [链接](https://leetcode-cn.com/problems/cong-shang-dao-xia-da-yin-er-cha-shu-iii-lcof/)
```
请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。

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
  [20,9],
  [15,7]
]

```
## 1. 解题思路一 双端队列
### 1.1 具体实现 
```java
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;

        Deque<TreeNode> deque = new LinkedList<>();
        deque.addLast(root);
        int level = 1;
        while (!deque.isEmpty()) {

            LinkedList<Integer> list = new LinkedList<>();
           int j = deque.size();
            for (int i = 0; i < j; i++){
                TreeNode node = deque.pop();
                // 根据奇偶判断从头压入还是从尾压入
                if(level%2 == 0) {
                    list.addFirst(node.val);
                }else {
                    list.addLast(node.val);
                }
                if(node.left != null) deque.addLast(node.left);
                if(node.right != null) deque.addLast(node.right);
            }
            level++;
            res.add(list);

        }

        return res;
    }
}
```