# PathSum
> 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。说明: 叶子节点是指没有子节点的节点。[链接](https://leetcode-cn.com/problems/path-sum-ii/)
## 1.解题思路一 自上而下的数值递增
### 1.1 思路
> 从根节点开始递归至每个叶子节点计算路径总和
### 1.2 具体实现
```
public class PathSum {

    private static List<List<Integer>> ret = new ArrayList<>();

    public static List<List<Integer>> pathSum(TreeNode root, int sum) {
        ret = new ArrayList<>();
        path(new ArrayList<>(),0,root,sum);
        return ret;
    }
    private static void path(List<Integer> list,Integer total,TreeNode node, int sum){
        if(node == null)return;
        list.add(node.val);
        if(node.left == null && node.right == null && total + node.val == sum){
            ret.add(list);
        }
        if(node.left != null){
            path(new ArrayList<>(list),total+node.val,node.left,sum);
        }
        if(node.right != null){
            path(new ArrayList<>(list),total+node.val,node.right,sum);
        }
    }
    public static void main(String[] args) {
        String str1 = "[5,4,8,11,null,13,4,7,2,null,null,5,1]";
        int sum = 22;
        System.out.println(pathSum(TreeNode.stringToTreeNode(str1),sum)); // leetcode内部提供
    }
}

```
