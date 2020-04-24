# SumNumbers
> 给定一个二叉树，它的每个结点都存放一个 0-9 的数字，每条从根到叶子节点的路径都代表一个数字。例如，从根到叶子节点路径 1->2->3 代表数字 123。计算从根到叶子节点生成的所有数字之和。说明: 叶子节点是指没有子节点的节点。【[链接](https://leetcode-cn.com/problems/sum-root-to-leaf-numbers/)

## 1. 解题思路一 递归
### 1.1 思路
> 递归每个节点使用字符串记录路径，到达叶子节点时求和即可。递归要注意三点:1.递归的终止条件，2.递归的执行体，3.思路不要陷入递归中。
### 1.2 具体实现
```
public class SumNumbers {

    private static int total = 0;

    public static int sumNumbers(TreeNode root) {
        total = 0;
        sum("",root);
        return total;
    }

    private static void sum(String path, TreeNode node){
        if(node == null) return; // 递归终止条件
        // ↓↓↓ 递归体
        String nowPath = path+node.val;
        if(node.left != null) {
            sum(nowPath,node.left);
        }
        if(node.right != null) {
            sum(nowPath,node.right);
        }
        // ↑↑↑ 递归体

        // 递归终止条件
        if(node.left == null && node.right == null){
            total += Integer.parseInt(nowPath);
        }
    }
    public static void main(String[] args) {
        String str1 = "[1,2,3]";
        String str2 = "[4,9,0,5,1]";
        System.out.println(sumNumbers(TreeNode.stringToTreeNode(str2)));// stringToTreeNode leetcode提供
    }
}
```


