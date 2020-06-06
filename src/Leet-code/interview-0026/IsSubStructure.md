# IsSubStructure
> [链接](https://leetcode-cn.com/problems/shu-de-zi-jie-gou-lcof/)
```
输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)

B是A的子结构， 即 A中有出现和B相同的结构和节点值。

例如:
给定的树 A:

     3
    / \
   4   5
  / \
 1   2
给定的树 B：

   4 
  /
 1
返回 true，因为 B 与 A 的一个子树拥有相同的结构和节点值。

示例 1：

输入：A = [1,2,3], B = [3,1]
输出：false
示例 2：

输入：A = [3,4,5,1,2], B = [4,1]
输出：true
```
## 1. 解题思路一 
### 1.1 具体实现
```java
class Solution {
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        List<TreeNode> list = new ArrayList<>();
        get(list,A,B);
        // 遍历所有相同节点后进行深度对比
        for (int i = 0; i < list.size(); i++) {
            if(same(list.get(i),B))return true;
        }
        return false;
    }

    // 查找与B根节点相同的节点
    private static void get(List<TreeNode> res, TreeNode A, TreeNode B){
        if(A == null || B == null) return;
        if(A.val == B.val) res.add(A);
        get(res,A.left,B);
        get(res,A.right,B);
    }

    // 深度对比
    private static boolean same(TreeNode A, TreeNode B){

        if(B == null) return true;
        if(A == null) return false;
        if(A.val != B.val)return false;
        return same(A.left,B.left) && same(A.right,B.right);
    }
}
```